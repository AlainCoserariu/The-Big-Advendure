package fr.uge.interaction;

import fr.uge.entity.player.Player;
import fr.uge.fieldElement.FieldElement;

public class Interact {
 public static void collisionAfterNextMove(double beforeX, double beforeY, Player p, FieldElement element) {
   if(element != null && p.collideWithObstacle(element)) {
     p.move(beforeX, beforeY);
   }
 }
}
