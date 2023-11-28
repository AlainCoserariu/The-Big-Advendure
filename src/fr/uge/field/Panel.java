package fr.uge.field;

import java.nio.file.Path;

import fr.uge.entity.enemy.EnemyList;
import fr.uge.entity.player.Player;
import fr.uge.field.grid.Grid;
import fr.uge.field.item.ItemList;

public class Panel {
  private final Player player;
  private final EnemyList listEnemy;
  private final ItemList itemList;
  private final Grid grid;
  
  public Panel(Path mapFile) {
    player = null;
    listEnemy = null;
    itemList = null;
    grid = null;
  }
  
}
