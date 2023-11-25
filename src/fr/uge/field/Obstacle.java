package fr.uge.field;

import fr.uge.utils.hitbox.Hitbox;
import fr.uge.utils.hitbox.SquareHitbox;

public class Obstacle implements Element {
  private final int x;
  private final int y;
  private final Hitbox hitbox;
  
  public Obstacle(int x, int y) {
    this.x = x;
    this.y = y;
    
    hitbox = new SquareHitbox(x + 0.5, y + 0.5, 0.5);
  }
}
