package fr.uge.panel;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import fr.uge.entity.enemy.Enemy;
import fr.uge.entity.player.Player;
import fr.uge.fieldElement.FieldElement;
import fr.uge.parser.Parser;

public class Panel {
  public final Player player;
  private final ArrayList<Enemy> enemies;
  private final FieldElement[][] field;

  /**
   * Initialize a panel by parsing a map file
   * 
   * @param mapFile
   * @throws IOException
   */
  public Panel(Path mapFile) throws IOException {
    Parser p = new Parser(mapFile);
    
    player = p.player;
    enemies = p.enemies;
    field = p.field;
  }
  
	public List<Enemy> getEnemies() {
		return List.copyOf(enemies);
	}
	public FieldElement[][] getField() {
		FieldElement[][] tmp = field.clone();
		return tmp;
	}
}
