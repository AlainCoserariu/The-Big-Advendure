package fr.uge.fieldElement.obstacle;

import fr.uge.fieldElement.FieldElement;
import fr.uge.utility.hitboxe.Hitbox;

public class Obstacle implements FieldElement {
  private final double x;
  private final double y;
  
  private final ObstacleEnum type;
  
  private final Hitbox hitbox;

  /**
   * Obstacle constructor
   * 
   * @param x
   * @param y
   * @param type
   */
  public Obstacle(double x, double y, ObstacleEnum type) {
    this.x = x;
    this.y = y;
    this.type = type;
    
    hitbox = new Hitbox(x, y, 1);
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }
  
  public ObstacleEnum getType() {
    return type;
  }

	@Override
	public boolean IsObstacle() {
		return true;
	}

  @Override
  public Hitbox getHitbox() {
    return hitbox;
  }
}
