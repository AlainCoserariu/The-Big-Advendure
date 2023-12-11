package fr.uge.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import fr.uge.gameElement.entity.Enemy;
import fr.uge.gameElement.entity.Player;
import fr.uge.gameElement.entity.enemy.BehaviorEnum;
import fr.uge.gameElement.entity.enemy.SkinEnemy;
import fr.uge.gameElement.entity.player.SkinPlayer;
import fr.uge.gameElement.fieldElement.Decoration;
import fr.uge.gameElement.fieldElement.DecorationEnum;
import fr.uge.gameElement.fieldElement.FieldElement;
import fr.uge.gameElement.fieldElement.Obstacle;
import fr.uge.gameElement.fieldElement.ObstacleEnum;
import fr.uge.gameElement.utility.movementZone.MovementZone;

public class Parser {
  public Player player;
  public ArrayList<Enemy> enemies;
  public FieldElement[][] field;

  private HashMap<String, String> initElementMap() {
    String[] lstPossibleElt = new String[] { "size", "encoding", "data", "name", "skin", "player", "position", "health",
        "kind", "zone", "behavior", "damage", "text", "steal", "trade", "locked", "flow", "phantomized", "teleport", };

    var elements = new HashMap<String, String>();

    for (int i = 0; i < 19; i++) {
      elements.put(lstPossibleElt[i], null);
    }

    return elements;
  }

  /**
   * Make a direct correspondence between the code and the element
   * 
   * @param encoding
   * @return
   */
  private Map<String, String> parserEncoding(String encoding) {
    var res = new HashMap<String, String>();

    var pattern = Pattern.compile("[a-zA-Z]+");
    var matcher = pattern.matcher(encoding);

    while (matcher.find()) {
      var key = matcher.group().toUpperCase(Locale.ROOT);
      matcher.find();
      var value = matcher.group();
      res.put(value, key);
    }

    return res;
  }

  /**
   * Fill the map following the encoding and the data
   * 
   * @param encoding
   * @param data
   */
  private void parseMapData(String encoding, String data) {
    var encodingCorrespondence = parserEncoding(encoding);

    var obstacles = Arrays.stream(ObstacleEnum.values()).map(Enum::toString).toList();
    var decorations = Arrays.stream(DecorationEnum.values()).map(Enum::toString).toList();

    // Separates lines to parse the map
    String lines[] = data.replaceAll("\"", "").stripIndent().lines().skip(1).toArray(String[]::new);
    
    for (int y = 0; y < lines.length; y++) {
      for (int x = 0; x < lines[0].length(); x++) {
        String character = Character.toString(lines[y].charAt(x));
        if (encodingCorrespondence.containsKey(character)) {
          
          // Check if we need to initialize an obstacle or a decoration
          if (obstacles.contains(encodingCorrespondence.get(character))) {
            field[y][x] = new Obstacle(x + 0.5, y + 0.5, ObstacleEnum.valueOf(encodingCorrespondence.get(character)));
          } else if (decorations.contains(encodingCorrespondence.get(character))) {
            field[y][x] = new Decoration(x + 0.5, y + 0.5, DecorationEnum.valueOf(encodingCorrespondence.get(character)));
          }
        }
      }
    }
  }

  /**
   * Finds all numbers in a string and store them in a list
   * 
   * @param s
   * @return
   */
  private ArrayList<Integer> parseNumbers(String s) {
    var res = new ArrayList<Integer>();

    var pattern = Pattern.compile("[0-9]+");
    var matcher = pattern.matcher(s);

    while (matcher.find()) {
      res.add(Integer.parseInt(matcher.group()));
    }

    return res;
  }

  /**
   * Parse a grid bloc
   * 
   * @param elements
   */
  private void parseGrid(Map<String, String> elements) {
    var dimension = parseNumbers(elements.get("size"));
    
    field = new FieldElement[dimension.get(1)][dimension.get(0)];
    parseMapData(elements.get("encoding"), elements.get("data"));
  }

  /**
   * Parse all the player data
   * 
   * @param player
   */
  private void parsePlayer(Map<String, String> player) {
    var pos = parseNumbers(player.get("position"));
    
    var skins = Arrays.stream(SkinPlayer.values()).map(Enum::toString).toList();

    if (skins.contains(player.get("skin"))) {
      this.player = new Player(pos.get(0) + 0.5, pos.get(1) + 0.5, 5, Integer.parseInt(player.get("health")),
          player.get("name"), SkinPlayer.valueOf(player.get("skin")));
    } else {
      System.out.println("Can't detecte player skin, apply default skin (BABA)");

      this.player = new Player(pos.get(0) + 0.5, pos.get(1) + 0.5, 5, Integer.parseInt(player.get("health")),
          player.get("name"), SkinPlayer.valueOf("BABA"));
    }
  }

  /**
   * Parse all the monster data
   * 
   * @param monster
   */
  private void parseMonster(Map<String, String> monster) {
    var pos = parseNumbers(monster.get("position"));
    var zoneTmp = parseNumbers(monster.get("zone"));

    var skins = Arrays.stream(SkinEnemy.values()).map(Enum::toString).toList();

    var behaviors = Arrays.stream(BehaviorEnum.values()).map(Enum::toString).toList();;

    if (!skins.contains(monster.get("skin"))) {
      System.err.println("Can't load the monster skin, monster not created");
      return;
    } else if (!behaviors.contains(monster.get("behavior"))) {
      System.err.println("Can't load the monster behavior, monster not created");
      return;
    }

    MovementZone zone = new MovementZone(zoneTmp.get(0), zoneTmp.get(1), zoneTmp.get(0) + zoneTmp.get(2),
        zoneTmp.get(1) + zoneTmp.get(3));
    
    var res = new Enemy(pos.get(0) + 0.5, pos.get(1) + 0.5, 2, Integer.parseInt(monster.get("health")),
        monster.get("name"), SkinEnemy.valueOf(monster.get("skin")), zone,
        BehaviorEnum.valueOf(monster.get("behavior")));
    
    enemies.add(res);
  }

  /**
   * Implement a element
   * 
   * @param elements
   */
  private void parseElement(Map<String, String> elements) {
    if (elements.get("player") != null && elements.get("player").equals("true")) {
      parsePlayer(elements);
    } else if (elements.get("kind") != null && elements.get("kind").equals("enemy")) {
      parseMonster(elements);
    }

  }

  /**
   * Parse an entire block of data
   * 
   * @param elementToParse
   */
  private void parseElements(ArrayList<Result> elementToParse) {
    // Initialize a Hashmap with all the potential field
    var elements = initElementMap();

    // Convert a block in an hashmap corresponding fields and values
    for (int i = 0; i < elementToParse.size(); i++) {

      // Get all the values of a field
      if (elements.containsKey(elementToParse.get(i).content())) {
        int reader = i + 2;
        var info = new StringBuilder("");

        var separator = "";
        while (reader < elementToParse.size() && !elements.containsKey(elementToParse.get(reader).content())) {
          info.append(separator);
          info.append(elementToParse.get(reader).content());
          separator = " ";

          reader++;
        }
        elements.put(elementToParse.get(i).content(), info.toString());
      }
    }

    if (elementToParse.get(1).content().equals("grid")) {
      parseGrid(elements);
    } else if (elementToParse.get(1).content().equals("element")) {
      parseElement(elements);
    } else {
      System.err.println("Unexpected token, 'grid' or 'element' expected");
    }

  }

  public Parser(Path mapFile) throws IOException {
    enemies = new ArrayList<Enemy>();
    
    var text = Files.readString(mapFile);
    var lexer = new Lexer(text);
    var tokens = new ArrayList<Result>();
    Result tmpToken;
    while ((tmpToken = lexer.nextResult()) != null) {
      tokens.add(tmpToken);
    }

    // Parse all the tokens
    int cmptToken = 0;
    for (; cmptToken < tokens.size();) {

      // Find a bloc element or grid
      var element = new ArrayList<Result>();

      if (tokens.get(cmptToken).token().equals(Token.LEFT_BRACKET)) {
        element.add(tokens.get(cmptToken));
        cmptToken++;
      } else {
        System.err.println("Unexpected token, '[' expected");
      }

      // Add all tokens of the current block checked
      for (; cmptToken < tokens.size() && !tokens.get(cmptToken).token().equals(Token.LEFT_BRACKET); cmptToken++) {
        element.add(tokens.get(cmptToken));
      }

      // Transform the block into game data
      parseElements(element);
    }
  }
}
