package fr.uge.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import fr.uge.entity.enemy.BehaviorEnum;
import fr.uge.entity.enemy.Enemy;
import fr.uge.entity.enemy.SkinEnemy;
import fr.uge.entity.player.Player;
import fr.uge.entity.player.SkinPlayer;
import fr.uge.fieldElement.FieldElement;
import fr.uge.fieldElement.decoration.Decoration;
import fr.uge.fieldElement.decoration.DecorationEnum;
import fr.uge.fieldElement.obstacle.Obstacle;
import fr.uge.fieldElement.obstacle.ObstacleEnum;
import fr.uge.utility.movementZone.MovementZone;

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
      var key = matcher.group();
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
   * @param width
   * @param height
   */
  private void parseMapData(String encoding, String data, int width, int height) {
    var encodingCorrespondence = parserEncoding(encoding);

    // Help to make correspondence between the data and the structure
    String[] obstaclesTmp = new String[] { "BED", "BOG", "BOMB", "BRICK", "CHAIR", "CLIFF", "DOOR", "FENCE", "FORT",
        "GATE", "HEDGE", "HOUSE", "HUSK", "HUSKS", "LOCK", "MONITOR", "PIANO", "PILLAR", "PIPE", "ROCK", "RUBBLE",
        "SHELL", "SIGN", "SPIKE", "STATUE", "STUMP", "TABLE", "TOWER", "TREE", "TREES", "WALL" };
    String[] decorationsTmp = new String[] { "ALGAE", "CLOUD", "FLOWER", "FOLIAGE", "GRASS", "LADDER", "LILY", "PLANK",
        "REED", "ROAD", "SPROUT", "TILE", "TRACK", "VINE" };
    var obstacle = List.of(obstaclesTmp);
    var decoration = List.of(decorationsTmp);

    // Read through the whole data to implement the obstacles and decorations
    int x = 0;
    int y = 0;
    for (int reader = 4; reader < data.length(); reader++) {
      if (encodingCorrespondence.containsKey(Character.toString(data.charAt(reader)))) {
        // Check if we need to initialize an obstacle or a decoration
        if (obstacle.contains(encodingCorrespondence.get(Character.toString(data.charAt(reader))))) {          
          field[y][x] = new Obstacle(x + 0.5, y + 0.5,
              ObstacleEnum.valueOf(encodingCorrespondence.get(Character.toString(data.charAt(reader)))));
        } else if (decoration.contains(encodingCorrespondence.get(Character.toString(data.charAt(reader))))) {
          field[y][x] = new Decoration(x + 0.5, y + 0.5,
              DecorationEnum.valueOf(encodingCorrespondence.get(Character.toString(data.charAt(reader)))));
        }

        x++;
      } else if (data.charAt(reader) == '\n') {
        y++;
        x = 0;
      } else {
        x++;
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

    parseMapData(elements.get("encoding"), elements.get("data"), dimension.get(1), dimension.get(0));
  }

  /**
   * Parse all the player data
   * 
   * @param player
   */
  private void parsePlayer(Map<String, String> player) {
    var pos = parseNumbers(player.get("position"));

    String[] skinTmp = new String[] { "BABA", "BADBAD", "FOFO", "IT" };
    var skin = List.of(skinTmp);

    if (skin.contains(player.get("skin"))) {
      this.player = new Player(pos.get(0) + 0.5, pos.get(1) + 0.5, 3, Integer.parseInt(player.get("health")),
          player.get("name"), SkinPlayer.valueOf(player.get("skin")));
    } else {
      System.out.println("Can't detecte player skin, apply default skin (BABA)");

      this.player = new Player(pos.get(0) + 0.5, pos.get(1) + 0.5, 3, Integer.parseInt(player.get("health")),
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

    String[] skinTmp = new String[] { "BAT", "BEE", "BIRD", "BUG", "BUNNY", "CAT", "CRAB", "DOG", "FISH", "FROG",
        "GHOST", "JELLY", "JIJI", "KEKE", "LIZARD", "ME", "MONSTER", "ROBOT", "SNAIL", "SKULL", "TEETH", "TURTLE",
        "WORM" };
    var skin = List.of(skinTmp);

    String[] behaviorTmp = new String[] { "shy", "stroll", "agressive" };
    var behavior = List.of(behaviorTmp);

    if (!skin.contains(monster.get("skin"))) {
      System.err.println("Can't load the monster skin, monster not created");
      return;
    } else if (!behavior.contains(monster.get("behavior"))) {
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

    // Parse the field
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
