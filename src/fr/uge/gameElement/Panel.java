package fr.uge.gameElement;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import fr.uge.gameElement.entity.Enemy;
import fr.uge.gameElement.entity.Player;
import fr.uge.gameElement.fieldElement.FieldElement;
import fr.uge.parser.Parser;

public class Panel {
  private final Player player;
  private final List<Enemy> enemies;
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
  }

  public List<Enemy> getEnemies() {
    return enemies;
  }

  public FieldElement[][] getField() {
    return field;
  }
  
  public Player getPlayer() {
    return player;
  }
  
  public static void main(String[] args) throws IOException {
    Panel panel = new Panel(Path.of("maps").resolve("test1.map"));
    
    for (var i : panel.getField()) {
      for (var j : i) {
        System.out.println(j);
      }
    }
    
  }
}
