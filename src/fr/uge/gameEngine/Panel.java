package fr.uge.gameEngine;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import fr.uge.gameEngine.entity.Enemy;
import fr.uge.gameEngine.entity.Player;
import fr.uge.gameEngine.fieldElement.FieldElement;
import fr.uge.gameEngine.item.Item;
import fr.uge.parser.Parser;

public class Panel {
  private final Player player;
  private final List<Enemy> enemies;
  private final List<Item> items;
  private final FieldElement[][] field;

  /**
   * Initialize a panel by parsing a map file
   * 
   * @param mapFile
   * @throws IOException
   */
  public Panel(Path mapFile) throws IOException {
    Parser.parse(mapFile);

    player = Parser.getPlayer();
    enemies = Parser.getEnemies();
    field = Parser.getField();
    items = Parser.getItems();
  }

  public List<Enemy> getEnemies() {
    return enemies;
  }
  
  public List<Item> getItems() {
    return items;
  }

  public FieldElement[][] getField() {
    return field;
  }
  
  public Player getPlayer() {
    return player;
  }
}
