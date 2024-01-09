package fr.uge.gameEngine.fieldElement;

import fr.uge.enums.ObstacleEnum;
import fr.uge.gameEngine.utility.Hitbox;

public final class Obstacle implements FieldElement {
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

  public double x() {
    return x;
  }

  public double y() {
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
