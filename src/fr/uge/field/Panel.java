package fr.uge.field;

import java.nio.file.Path;

import fr.uge.entity.enemy.Enemy;
import fr.uge.entity.enemy.EnemyList;
import fr.uge.entity.player.Player;
import fr.uge.field.grid.Grid;
import fr.uge.field.grid.obstacle.Obstacle;
import fr.uge.field.grid.obstacle.ObstacleEnum;
import fr.uge.field.item.ItemList;
import fr.uge.utils.Direction;
import fr.uge.utils.hitbox.SquareHitbox;
import fr.uge.weapon.Sword;

public class Panel {
  private final Player player;
  private final EnemyList listEnemy;
  private final ItemList itemList;
  private final Grid grid;
  
  public Panel(Path mapFile) {
    // Ouvrir le fichier map
      // Tant qu'il reste des éléments à parser : 
        // Sépare le prochain bloc [grid ou elt]
          // Appelle à la fonction correspondent au bloc
    
    player = new Player(1000, 20, 100, 100, 30, 30, "test", new Sword(10, "Durendale","SWORD"), "baba");;
    listEnemy = new EnemyList();
    listEnemy.add(new Enemy(100, 200, Direction.NORTH, 10, new SquareHitbox(10, 10, 10),"Waldo", "crab"));
    itemList = null;
    grid = new Grid(1, 1);
    grid.getList()[0][0] = new Obstacle(0,0, ObstacleEnum.BED);
  }

  public Player getPlayer() {
    return player;
  }

  public EnemyList getListEnemy() {
    return listEnemy;
  }

  public ItemList getItemList() {
    return itemList;
  }

  public Grid getGrid() {
    return grid;
  }
  
  
}
