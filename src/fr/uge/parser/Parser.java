package fr.uge.parser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

import fr.uge.enums.Behavior;
import fr.uge.enums.DecorationEnum;
import fr.uge.enums.ObstacleEnum;
import fr.uge.enums.SkinEnemy;
import fr.uge.enums.SkinItem;
import fr.uge.enums.SkinPlayer;
import fr.uge.enums.possibleFieldMapFile;
import fr.uge.gameEngine.entity.Enemy;
import fr.uge.gameEngine.entity.Player;
import fr.uge.gameEngine.fieldElement.Decoration;
import fr.uge.gameEngine.fieldElement.FieldElement;
import fr.uge.gameEngine.fieldElement.Obstacle;
import fr.uge.gameEngine.utility.MovementZone;

public class Parser {
  // Those field will be constructed while parsing the file
  private static Player player = null;
  private final static List<Enemy> enemies = new ArrayList<Enemy>();
  private static FieldElement[][] field = null;

  private static int cmptToken = 0;
  private static int cmptLine = 1;
  // Track all the errors in the file
  private static final List<String> errors = new ArrayList<String>();

  private static void updateCmptToken(List<Result> tokens) {
    if (cmptToken < tokens.size()) {
      if (tokens.get(cmptToken).token() == Token.NEW_LINE) {
        cmptLine++;
      } else if (tokens.get(cmptToken).token() == Token.QUOTE) {
        cmptLine += tokens.get(cmptToken).content().length()
            - tokens.get(cmptToken).content().replace("\n", "").length();
      }
    }
    cmptToken++;
  }

  /**
   * Initialize a HashMap containing each possible field in a block as key.
   * Convert possibleFieldMapFile into String to not handle errors
   * 
   * @return
   */
  private static Map<String, List<Result>> initElementMap() {
    var elements = new HashMap<String, List<Result>>();

    for (var elt : possibleFieldMapFile.values()) {
      elements.put(elt.toString(), new ArrayList<Result>());
    }

    return elements;
  }

  private static boolean checkEncodingValue(List<Result> encoding, int indexToCheck) {
    var token = encoding.get(indexToCheck).content().toUpperCase(Locale.ROOT);
    if (!ObstacleEnum.contains(token) && !DecorationEnum.contains(token)) {
      errors.add("Grid block encodings field : Token " + token + " not recognized in encodings field");
      return false;
    } else if (indexToCheck + 3 >= encoding.size() || encoding.get(indexToCheck + 1).token() != Token.LEFT_PARENS
        || (encoding.get(indexToCheck + 2).token() != Token.IDENTIFIER
            || encoding.get(indexToCheck + 2).content().length() != 1)
        || encoding.get(indexToCheck + 3).token() != Token.RIGHT_PARENS) {
      errors.add("Grid block encodings field : Not enought token after " + token
          + " the encodings format follow this pattern : element ( character ) (example : WALL ( W ))");
      return false;
    }

    return true;
  }

  /**
   * Associate letter with game element from encodings field. For instance the
   * following encoding : LAVA(L) PLANK(p) GRASS(g) return the map : [L=LAVA,
   * p=PLANK, g=GRASS]
   * 
   * @param encoding
   * @return
   */
  private static Map<Character, String> parserEncoding(List<Result> encoding) {
    var res = new HashMap<Character, String>();
    for (int index = 0; index < encoding.size(); index += 4) {
      if (checkEncodingValue(encoding, index)) {
        res.put(encoding.get(index + 2).content().charAt(0), encoding.get(index).content().toUpperCase(Locale.ROOT));
      } else {
        return null;
      }
    }
    return res;
  }

  private static boolean checkDataMap(String lines[], FieldElement[][] field) {
    if (lines.length != field.length) {
      errors.add("Grid block : size not corresponding to map size, " + field.length + " lines expected but got "
          + lines.length);
      return false;
    }
    for (int i = 0; i < lines.length; i++) {
      if (lines[i].length() != field[0].length) {
        errors.add("Grid block : size not corresponding to map size, " + field[0].length + " colons expected at line "
            + i + " but got " + lines[i].length() + " colons");
        return false;
      }
    }
    return true;
  }

  private static FieldElement initFieldElement(int x, int y, String element) {
    if (DecorationEnum.contains(element)) {
      return new Decoration(x + 0.5, y + 0.5, DecorationEnum.valueOf(element));
    } else if (ObstacleEnum.contains(element)) {
      return new Obstacle(x + 0.5, y + 0.5, ObstacleEnum.valueOf(element));
    }
    return null;
  }

  /**
   * Fill the map following the encoding and the data
   * 
   * @param encoding
   * @param data
   */
  private static void parseMapData(List<Result> encoding, String data) {
    var encodingCorrespondence = parserEncoding(encoding);
    if (encodingCorrespondence == null)
      return;

    // Put all lines inside String Array, suppressing quotes and the first useless
    // line
    String lines[] = data.replaceAll("\"", "").stripIndent().lines().skip(1).toArray(String[]::new);
    if (!checkDataMap(lines, field))
      return;

    for (int y = 0; y < field.length; y++) {
      for (int x = 0; x < field[0].length; x++) {
        var character = lines[y].charAt(x);
        field[y][x] = initFieldElement(x, y, encodingCorrespondence.get(character));
      }
    }
  }

  private static boolean isInteger(String str) {
    try {
      Integer.parseInt(str);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  private static boolean checkSizeField(List<Result> sizeField) {
    if (sizeField.isEmpty()) {
      errors.add("Grid error line " + cmptLine + " : size field not initialized");
      return false;
    }
    if (sizeField.size() != 5) {
      errors.add("Grid error line " + cmptLine + " : wrong size definition, expected ( i x j ) with i and j integers");
      return false;
    }
    boolean token1 = sizeField.get(0).token() == Token.LEFT_PARENS;
    boolean token2 = isInteger(sizeField.get(1).content());
    boolean token3 = sizeField.get(2).content().equals("x");
    boolean token4 = isInteger(sizeField.get(3).content());
    boolean token5 = sizeField.get(4).token() == Token.RIGHT_PARENS;
    if (!token1 || !token2 || !token3 || !token4 || !token5) {
      errors.add("Grid error line " + cmptLine + " : wrong size definition, expected ( i x j ) with i and j integers");
      return false;
    }
    return true;
  }

  private static boolean checkDataField(List<Result> dataField) {
    if (dataField.isEmpty()) {
      errors.add("Grid block : can't find data field");
      return false;
    }
    return true;
  }

  private static boolean checkGridField(Map<String, List<Result>> elements) {
    if (elements.get("encodings").isEmpty()) {
      errors.add("Grid block : can't find encodings field");
      return false;
    }
    if (!checkSizeField(elements.get("size")) || !checkDataField(elements.get("data"))) {
      return false;
    }
    return true;
  }

  /**
   * Parse a grid bloc
   * 
   * @param elements
   */
  private static void parseGrid(Map<String, List<Result>> elements) {
    if (!checkGridField(elements)) {
      return;
    }
    int x = Integer.parseInt(elements.get("size").get(1).content());
    int y = Integer.parseInt(elements.get("size").get(3).content());
    field = new FieldElement[y][x];
    parseMapData(elements.get("encodings"), elements.get("data").get(0).content());
  }

  /**
   * Parse all the player data into player variable
   * 
   * @param player_info
   */
  private static void parsePlayer(Map<String, List<Result>> player_info) {
    if (player_info.get("position").isEmpty() || player_info.get("health").isEmpty()
        || player_info.get("name").isEmpty() || player_info.get("skin").isEmpty()) {
      errors.add(
          "Player block isn't correctly formed, missing at least one of the following field : position, health, name, skin");
    }

    double x = Integer.parseInt(player_info.get("position").get(1).content()) + 0.5;
    double y = Integer.parseInt(player_info.get("position").get(3).content()) + 0.5;
    int health = Integer.parseInt(player_info.get("health").get(0).content());
    player = new Player(x, y, 7, health, player_info.get("name").get(0).content(),
        SkinPlayer.valueOf(player_info.get("skin").get(0).content()));
  }

  /**
   * Parse all the monster data into the enemy map
   * 
   * @param monster
   */
  private static void parseMonster(Map<String, List<Result>> monster) {
    if (monster.get("name").isEmpty() || monster.get("skin").isEmpty() || monster.get("position").isEmpty()
        || monster.get("health").isEmpty() || monster.get("zone").isEmpty() || monster.get("behavior").isEmpty()
        || monster.get("damage").isEmpty()) {
      errors.add(
          "Monster block isn't correctly formed, missing at least one of the following field : name, skin, position, health, zone, behavior, damage");
    }

    double x = Integer.parseInt(monster.get("position").get(1).content()) + 0.5;
    double y = Integer.parseInt(monster.get("position").get(3).content()) + 0.5;
    int health = Integer.parseInt(monster.get("health").get(0).content());
    MovementZone zone = new MovementZone(Integer.parseInt(monster.get("zone").get(1).content()),
        Integer.parseInt(monster.get("zone").get(3).content()), Integer.parseInt(monster.get("zone").get(6).content()),
        Integer.parseInt(monster.get("zone").get(8).content()));
    int damage = Integer.parseInt(monster.get("damage").get(0).content());

    enemies.add(new Enemy(x, y, 10, health, monster.get("name").get(0).content(),
        SkinEnemy.valueOf(monster.get("skin").get(0).content()), zone,
        Behavior.valueOf(monster.get("behavior").get(0).content()), damage));
  }

  /**
   * Implement a element
   * 
   * @param elements
   */
  private static void parseElement(Map<String, List<Result>> elements) {
    if (!elements.get("player").isEmpty() && elements.get("player").get(0).content().equals("true")) {
      parsePlayer(elements);
    } else if (!elements.get("kind").isEmpty() && elements.get("kind").get(0).content().equals("enemy")) {
      parseMonster(elements);
    } else if (!elements.get("kind").isEmpty() && elements.get("kind").get(0).content().equals("obstacle")) {

    } else if (!elements.get("kind").isEmpty() && elements.get("kind").get(0).content().equals("friend")) {

    } else if (!elements.get("kind").isEmpty() && elements.get("kind").get(0).content().equals("item")) {

    }

  }

  private static boolean checkPositionField(List<Result> positionField) {
    if (positionField.isEmpty()) {
      errors.add("Error line " + cmptLine + " : position field not initialized");
      return false;
    }
    if (positionField.size() != 5) {
      errors.add("Error line " + cmptLine + " : wrong position definition, expected ( i , j ) with i and j integers");
      return false;
    }
    boolean token1 = positionField.get(0).token() == Token.LEFT_PARENS;
    boolean token2 = isInteger(positionField.get(1).content());
    boolean token3 = positionField.get(2).content().equals(",");
    boolean token4 = isInteger(positionField.get(3).content());
    boolean token5 = positionField.get(4).token() == Token.RIGHT_PARENS;
    if (!token1 || !token2 || !token3 || !token4 || !token5) {
      errors.add("Error line " + cmptLine + " : wrong position definition, expected ( i x j ) with i and j integers");
      return false;
    }
    return true;
  }

  private static boolean checkZoneField(List<Result> zoneField) {
    if (zoneField.isEmpty()) {
      errors.add("Error line " + cmptLine + " : zone field not initialized");
      return false;
    }
    if (zoneField.size() != 10) {
      errors.add("Error line " + cmptLine
          + " : wrong zone definition, expected ( x , y ) ( i , j ) with x, y, i and j integers");
      return false;
    }
    boolean token1 = zoneField.get(0).token() == Token.LEFT_PARENS;
    boolean token2 = isInteger(zoneField.get(1).content());
    boolean token3 = zoneField.get(2).content().equals(",");
    boolean token4 = isInteger(zoneField.get(3).content());
    boolean token5 = zoneField.get(4).token() == Token.RIGHT_PARENS;
    if (!token1 || !token2 || !token3 || !token4 || !token5) {
      errors.add("Error line " + cmptLine
          + " : wrong zone definition,  expected ( x , y ) ( i x j ) with x, y, i and j integers");
      return false;
    }

    token1 = zoneField.get(5).token() == Token.LEFT_PARENS;
    token2 = isInteger(zoneField.get(6).content());
    token3 = zoneField.get(7).content().equals("x");
    token4 = isInteger(zoneField.get(8).content());
    token5 = zoneField.get(9).token() == Token.RIGHT_PARENS;
    if (!token1 || !token2 || !token3 || !token4 || !token5) {
      errors.add("Error line " + cmptLine
          + " : wrong zone definition, expected ( x , y ) ( i x j ) with x, y, i and j integers");
      return false;
    }

    return true;
  }

  private static void fillDataField(List<Result> block, Map<String, List<Result>> elements, int index) {
    int curLine = cmptLine;
    while (index < block.size() && !possibleFieldMapFile.contains(block.get(index).content())) {
      if (block.get(index).token() == Token.NEW_LINE) {
        curLine++;
      } else if (block.get(index).token() == Token.QUOTE) {
        elements.get("data").add(block.get(index));
        curLine += block.get(index).content().length() - block.get(index).content().replace("\n", "").length();
      } else {
        errors.add(
            "Line " + curLine + ": Unexpected token " + block.get(index).content() + ", quote expression expected");
      }
      index++;
    }
    if (elements.get("data").size() != 1) {
      errors.add("Line " + curLine + ": Unexpected number of token (" + elements.get("data").size()
          + ") for data field, only one quote expected");
    }
  }

  private static void fillSizeField(List<Result> block, Map<String, List<Result>> elements, int index) {
    while (index < block.size() && !possibleFieldMapFile.contains(block.get(index).content())) {
      if (block.get(index).token() != Token.NEW_LINE) {
        elements.get("size").add(block.get(index));
      }
      index++;
    }
    checkSizeField(elements.get("size"));
  }

  private static void fillEncodingsField(List<Result> block, Map<String, List<Result>> elements, int index) {
    while (index < block.size() && !possibleFieldMapFile.contains(block.get(index).content())) {
      if (block.get(index).token() != Token.NEW_LINE) {
        elements.get("encodings").add(block.get(index));
      }
      index++;
    }
  }

  private static void fillNameField(List<Result> block, Map<String, List<Result>> elements, int index) {
    while (index < block.size() && !possibleFieldMapFile.contains(block.get(index).content())) {
      if (block.get(index).token() != Token.NEW_LINE && block.get(index).token() != Token.IDENTIFIER) {
        errors.add("Line " + cmptLine + ": Unexpected token " + block.get(index).content() + ", identifier expected");
      } else if (block.get(index).token() != Token.NEW_LINE) {
        elements.get("name").add(block.get(index));
      }
      index++;
    }
    if (elements.get("name").size() != 1) {
      errors.add("Line " + cmptLine + ": Unexpected number of token (" + elements.get("name").size()
          + ") for name, only one identifier expected");
    }
  }

  private static void fillSkinField(List<Result> block, Map<String, List<Result>> elements, int index) {
    while (index < block.size() && !possibleFieldMapFile.contains(block.get(index).content())) {
      if (block.get(index).token() != Token.NEW_LINE && block.get(index).token() != Token.IDENTIFIER) {
        errors.add("Line " + cmptLine + ": Unexpected token " + block.get(index).content() + ", identifier expected");
      } else if (block.get(index).token() != Token.NEW_LINE) {
        elements.get("skin").add(block.get(index));
      }
      index++;
    }
    if (elements.get("skin").size() != 1) {
      errors.add("Line " + cmptLine + ": Unexpected number of token (" + elements.get("skin").size()
          + ") for skin, only one identifier expected");
    } else if (!DecorationEnum.contains(elements.get("skin").get(0).content())
        && !ObstacleEnum.contains(elements.get("skin").get(0).content())
        && !SkinEnemy.contains(elements.get("skin").get(0).content())
        && !SkinPlayer.contains(elements.get("skin").get(0).content())
        && !SkinItem.contains(elements.get("skin").get(0).content())) {
      errors.add("Line " + cmptLine + ": " + elements.get("skin").get(0).content() + " is not valide");
    }
  }

  private static void fillPlayerField(List<Result> block, Map<String, List<Result>> elements, int index) {
    while (index < block.size() && !possibleFieldMapFile.contains(block.get(index).content())) {
      if (block.get(index).token() != Token.NEW_LINE && block.get(index).token() != Token.IDENTIFIER) {
        errors.add("Line " + cmptLine + ": Unexpected token " + block.get(index).content()
            + ", identifier true or false expected");
      } else if (block.get(index).token() != Token.NEW_LINE){
        elements.get("player").add(block.get(index));
      }
      index++;
    }
    if (elements.get("player").size() != 1) {
      errors.add("Line " + cmptLine + ": Unexpected number of token (" + elements.get("player").size()
          + ") for player, only one identifier true or false expected");
    } else if (!elements.get("player").get(0).content().equals("true")
        && !elements.get("player").get(0).content().equals("false")) {
      errors.add("Line " + cmptLine + ": " + elements.get("player").get(0).content()
          + " is not valide, true or false expected");
    }
  }

  private static void fillPositionField(List<Result> block, Map<String, List<Result>> elements, int index) {
    while (index < block.size() && !possibleFieldMapFile.contains(block.get(index).content())) {
      if (block.get(index).token() != Token.NEW_LINE) {
        elements.get("position").add(block.get(index));
      }
      index++;
    }
    checkPositionField(elements.get("position"));
  }

  private static void fillHealthField(List<Result> block, Map<String, List<Result>> elements, int index) {
    while (index < block.size() && !possibleFieldMapFile.contains(block.get(index).content())) {
      if (block.get(index).token() != Token.NEW_LINE && block.get(index).token() != Token.NUMBER) {
        errors.add("Line " + cmptLine + ": Unexpected token " + block.get(index).content() + ", number expected");
      } else if (block.get(index).token() != Token.NEW_LINE) {
        elements.get("health").add(block.get(index));
      }
      index++;
    }
    if (elements.get("health").size() != 1) {
      errors.add("Line " + cmptLine + ": Unexpected number of token (" + elements.get("damage").size()
          + ") for damage, only one number expected");
    }
  }

  private static void fillKindField(List<Result> block, Map<String, List<Result>> elements, int index) {
    while (index < block.size() && !possibleFieldMapFile.contains(block.get(index).content())) {
      if (block.get(index).token() != Token.NEW_LINE && block.get(index).token() != Token.IDENTIFIER) {
        errors.add("Line " + cmptLine + ": Unexpected token " + block.get(index).content() + ", identifier expected");
      } else if (block.get(index).token() != Token.NEW_LINE){
        elements.get("kind").add(block.get(index));
      }
      index++;
    }
    if (elements.get("kind").size() != 1) {
      errors.add("Line " + cmptLine + ": Unexpected number of token (" + elements.get("skin").size()
          + ") for kind, only one identifier (friend, enemy, item or obstacle) expected");
    } else if (!elements.get("kind").get(0).content().equals("obstacle")
        && !elements.get("kind").get(0).content().equals("item")
        && !elements.get("kind").get(0).content().equals("enemy")
        && !elements.get("kind").get(0).content().equals("friend")) {
      errors.add("Line " + cmptLine + ": " + elements.get("player").get(0).content() + " is not valide");
    }
  }

  private static void fillZoneField(List<Result> block, Map<String, List<Result>> elements, int index) {
    while (index < block.size() && !possibleFieldMapFile.contains(block.get(index).content())) {
      if (block.get(index).token() != Token.NEW_LINE) {
        elements.get("zone").add(block.get(index));
      }
      index++;
    }
    checkZoneField(elements.get("zone"));
  }

  private static void fillBehaviorField(List<Result> block, Map<String, List<Result>> elements, int index) {
    while (index < block.size() && !possibleFieldMapFile.contains(block.get(index).content())) {
      if (block.get(index).token() != Token.NEW_LINE && block.get(index).token() != Token.IDENTIFIER) {
        errors.add("Line " + cmptLine + ": Unexpected token " + block.get(index).content() + ", identifier expected");
      } else if (block.get(index).token() != Token.NEW_LINE) {
        elements.get("behavior").add(block.get(index));
      }
      index++;
    }
    if (elements.get("behavior").size() != 1) {
      errors.add("Line " + cmptLine + ": Unexpected number of token (" + elements.get("behavior").size()
          + ") for behavior, only one identifier expected");
    } else if (!Behavior.contains(elements.get("behavior").get(0).content())) {
      errors.add(
          "Line " + cmptLine + ": " + elements.get("behavior").get(0).content() + " is not a possible behavior valide");
    }
  }

  private static void fillDamageField(List<Result> block, Map<String, List<Result>> elements, int index) {
    while (index < block.size() && !possibleFieldMapFile.contains(block.get(index).content())) {
      if (block.get(index).token() != Token.NEW_LINE && block.get(index).token() != Token.NUMBER) {
        errors.add("Line " + cmptLine + ": Unexpected token " + block.get(index).content() + ", number expected");
      } else if (block.get(index).token() != Token.NEW_LINE) {
        elements.get("damage").add(block.get(index));
      }
      index++;
    }
    if (elements.get("damage").size() != 1) {
      errors.add("Line " + cmptLine + ": Unexpected number of token (" + elements.get("damage").size()
          + ") for damage, only one number expected");
    }
  }

  private static void fillTextField(List<Result> block, Map<String, List<Result>> elements, int index) {
    while (index < block.size() && !possibleFieldMapFile.contains(block.get(index).content())) {
      if (block.get(index).token() != Token.NEW_LINE && block.get(index).token() != Token.QUOTE) {
        errors.add("Line " + cmptLine + ": Unexpected token " + block.get(index).content() + ", quote expected");
      } else if (block.get(index).token() != Token.NEW_LINE) {
        elements.get("text").add(block.get(index));
      }
      index++;
    }
    if (elements.get("text").size() != 1) {
      errors.add("Line " + cmptLine + ": Unexpected number of token (" + elements.get("text").size()
          + ") for text, only one quote expected");
    }
  }

  private static void fillStealField(List<Result> block, Map<String, List<Result>> elements, int index) {
    while (index < block.size() && !possibleFieldMapFile.contains(block.get(index).content())) {
      if (block.get(index).token() != Token.NEW_LINE && block.get(index).token() != Token.IDENTIFIER) {
        errors.add("Line " + cmptLine + ": Unexpected token " + block.get(index).content() + ", identifier expected");
      } else if (block.get(index).token() != Token.NEW_LINE) {
        elements.get("steal").add(block.get(index));
      }
      index++;
    }
    if (elements.get("steal").size() == 0) {
      errors.add("Line " + cmptLine + ": Unexpected number of token, more than 0 token needed for steal field");
    }
  }

  private static void fillTradeField(List<Result> block, Map<String, List<Result>> elements, int index) {
  }

  private static void fillLockedField(List<Result> block, Map<String, List<Result>> elements, int index) {
  }

  private static void fillFlowField(List<Result> block, Map<String, List<Result>> elements, int index) {
  }

  private static void fillPhantomizedField(List<Result> block, Map<String, List<Result>> elements, int index) {
  }

  private static void fillTeleportField(List<Result> block, Map<String, List<Result>> elements, int index) {
  }

  /**
   * Add all the values found after colon into the corresponding field until a new
   * possible field is found in tokens
   * 
   * @param block
   * @param elements
   * @param fieldToFill
   * @param index       Index where the search start
   */
  private static void addValueToElementMap(List<Result> block, Map<String, List<Result>> elements, String fieldToFill,
      int index) {
    if (index + 1 >= block.size() || block.get(index + 1).token() != Token.COLON) {
      errors.add("Line " + cmptLine + ": Unexpected token after " + block.get(index).content() + ", : expected");
      return;
    } else if (!elements.get(fieldToFill).isEmpty()) {
      errors.add("Line " + cmptLine + ": Field " + block.get(index).content() + " already declared in this block");
      return;
    }
    index += 2; // Skip the colon

    switch (fieldToFill) { // Check if the field is correctly write
    case "data" -> fillDataField(block, elements, index);
    case "size" -> fillSizeField(block, elements, index);
    case "encodings" -> fillEncodingsField(block, elements, index);
    case "name" -> fillNameField(block, elements, index);
    case "skin" -> fillSkinField(block, elements, index);
    case "player" -> fillPlayerField(block, elements, index);
    case "position" -> fillPositionField(block, elements, index);
    case "health" -> fillHealthField(block, elements, index);
    case "kind" -> fillKindField(block, elements, index);
    case "zone" -> fillZoneField(block, elements, index);
    case "behavior" -> fillBehaviorField(block, elements, index);
    case "damage" -> fillDamageField(block, elements, index);
    case "text" -> fillTextField(block, elements, index);
    case "steal" -> fillStealField(block, elements, index);
    case "trade" -> fillTradeField(block, elements, index);
    case "locked" -> fillLockedField(block, elements, index);
    case "flow" -> fillFlowField(block, elements, index);
    case "phantomized" -> fillPhantomizedField(block, elements, index);
    case "teleport" -> fillTeleportField(block, elements, index);
    }
  }

  private static void parseBlock(List<Result> block, Map<String, List<Result>> elements) {
    switch (block.get(1).content()) {
    case "grid" -> parseGrid(elements);
    case "element" -> parseElement(elements);
    }
  }

  /**
   * Ignore all tokens until the next field within a block is detected
   * 
   * @param block
   * @param index of the next field
   */
  private static int findNextField(List<Result> block, int index) {
    while (index < block.size() && !possibleFieldMapFile.contains(block.get(index).content())) {
      if (block.get(index).token() == Token.NEW_LINE) {
        cmptLine++;
      } else if (block.get(index).token() == Token.QUOTE) {
        cmptLine += block.get(index).content().length() - block.get(index).content().replace("\n", "").length();
      }
      index++;
    }
    return index;
  }

  /**
   * Put block element into a HashMap that associate field to the actual data. For
   * instance the following line : player: true create an instance inside the map
   * where player is the key and true the value. Because multiple values can be
   * write, the value is an array containing all the tokens related to the field.
   * 
   * @param block
   */
  private static void parseElements(List<Result> block) {
    var elements = initElementMap();
    for (int indexToken = findNextField(block, 3); indexToken < block.size(); indexToken = findNextField(block,
        indexToken + 1)) {
      addValueToElementMap(block, elements, block.get(indexToken).content(), indexToken);
    }
    cmptToken += block.size() - 3;
    cmptLine--;

    if (errors.size() != 0) {
      return; // No need to continue, all errors are checked for this block
    }
    parseBlock(block, elements);
  }

  /**
   * Tell if the token at a given index is the first token of a correctly block
   * identifier. A correct block identifier follow the sequence : [, grid|element,
   * ]
   * 
   * @param tokens
   * @param index
   * @return
   */
  private static boolean isBlockIdentifier(List<Result> tokens, int index) {
    if (!(tokens.get(index).token().equals(Token.LEFT_BRACKET))) {
      return false;
    } else if (!((tokens.get(index + 1).content().equals("grid")
        || tokens.get(index + 1).content().equals("element")))) {
      return false;
    } else if (!(tokens.get(index + 2).token().equals(Token.RIGHT_BRACKET))) {
      return false;
    }

    return true;
  }

  /**
   * Add errors into errors list if the block a block at index cmptToken is
   * wrongly formed
   * 
   * @param tokens
   * @return true : an errors has been added
   */
  private static boolean addBlockError(List<Result> tokens) {
    if (cmptToken >= tokens.size() - 2) {
      errors.add(
          "Line " + cmptLine + ": Not enough token detected to form a block identifier ( expected : [grid|element] )");
      return true; // No remaining block
    }
    if (!(tokens.get(cmptToken).token().equals(Token.LEFT_BRACKET))) {
      errors.add("Line " + cmptLine + ": Unexpected token " + tokens.get(cmptToken).content().replace("\\", "\\\\")
          + ", ( expected : [grid|element] )");
      return true;
    } else if (!((tokens.get(cmptToken + 1).content().equals("grid")
        || tokens.get(cmptToken + 1).content().equals("element")))) {
      errors.add("Line " + cmptLine + ": Unexpected token " + tokens.get(cmptToken + 1).content().replace("\\", "\\\\")
          + ", \"grid\" or \"element\" token expected ( expected : [grid|element] )");
      return true;
    } else if (!(tokens.get(cmptToken + 2).token().equals(Token.RIGHT_BRACKET))) {
      errors.add("Line " + cmptLine + ": Unexpected token " + tokens.get(cmptToken + 2).content().replace("\\", "\\\\")
          + ", ( expected : [grid|element] )");
      return true;
    }
    return false;
  }

  /**
   * Get the index of the start of the next block. Check if the next block is
   * indeed composed of the following sequence : [, grid|element, ]. If not search
   * for the next corrected block identifier ignoring all tokens.
   * 
   * @param tokens
   * @return true : block has been found false : no remaining block to parse
   */
  private static boolean findNextBlock(List<Result> tokens) {
    if (!addBlockError(tokens)) {
      return true;
    }

    while (cmptToken < tokens.size()) {
      if (tokens.get(cmptToken).token() == Token.NEW_LINE) {
        cmptLine++;
      } else if (tokens.get(cmptToken).token() == Token.QUOTE) {
        cmptLine += tokens.get(cmptToken).content().length()
            - tokens.get(cmptToken).content().replace("\n", "").length();
      } else if (isBlockIdentifier(tokens, cmptToken)) {
        return true;
      }
      cmptToken++;
    }

    return false;
  }

  /**
   * Get all the tokens inside a block. A block is composed of an indentifier :
   * [grid] or [element] followed by multiple field. The block end at the start of
   * an other, or at the end of a file
   * 
   * @param tokens
   * @return
   */
  private static List<Result> getBlock(List<Result> tokens) {
    var block = new ArrayList<Result>();

    if (!findNextBlock(tokens)) {
      return null;
    } else if (tokens.get(cmptToken + 1).content().equals("grid") && field != null) {
      errors.add("Line " + cmptLine + ": Correct grid has already been declared");
    }

    // Add block's identifier
    for (int tmp = cmptToken; cmptToken < tmp + 2; cmptToken++) {
      block.add(tokens.get(cmptToken));
    }
    // Add block's fields
    for (int i = cmptToken; i < tokens.size() && !isBlockIdentifier(tokens, i); i++) {
      block.add(tokens.get(i));
    }
    return List.copyOf(block);
  }

  public static void parse(Path mapFile) throws IOException {
    var tokens = Lexer.getAllTokensFromFile(mapFile);

    // Parse all the tokens
    for (; cmptToken < tokens.size(); updateCmptToken(tokens)) {
      if (tokens.get(cmptToken).token() != Token.NEW_LINE && tokens.get(cmptToken).token() != Token.QUOTE) {
        var block = getBlock(tokens);
        // Transform the block into game data
        if (block != null)
          parseElements(block);
      }
    }
    errors.forEach(System.out::println);
  }

  public static Player getPlayer() {
    return player;
  }

  public static List<Enemy> getEnemies() {
    return enemies;
  }

  public static FieldElement[][] getField() {
    return field;
  }
}
