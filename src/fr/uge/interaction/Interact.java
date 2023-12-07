package fr.uge.interaction;

import fr.uge.fieldElement.FieldElement;
import fr.uge.panel.Panel;

public class Interact {
  public static void collisionAfterNextMove(double beforeX, double beforeY, Panel panel) {
    FieldElement element = panel.getField()[(int) panel.player.getY()][(int) panel.player.getX()];
    if (element != null && panel.player.player.collideWithObstacle(element)) {
      panel.player.move(beforeX, beforeY);
    }
    collisionWithEnemy(beforeX, beforeY, panel);
  }

 private static void collisionWithEnemy(double beforeX, double beforeY, Panel panel) {
   var tempEnemy = panel.getEnemies();
   for(int i = 0; i < panel.getEnemies().size(); i++) {
     if(tempEnemy.get(i) != null && panel.player.player.collideWithEnemy(tempEnemy.get(i).getEntity())) {
       panel.player.move(beforeX, beforeY);
     }
   }
 }
}
