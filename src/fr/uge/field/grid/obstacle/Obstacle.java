package fr.uge.field.grid.obstacle;

import fr.uge.field.grid.GridElement;
import fr.uge.utils.hitbox.Hitbox;
import fr.uge.utils.hitbox.SquareHitbox;

public class Obstacle implements GridElement {
  private final int x;
  private final int y;
  private final Hitbox hitbox;
  
  private final ObstacleEnum type;
  
  public Obstacle(int x, int y, ObstacleEnum type) {
    this.x = x;
    this.y = y;
    hitbox = new SquareHitbox(x + 0.5, y + 0.5, 0.5);
    
    this.type = type;
  }

	@Override
	public String getFileName() {
		return type.name().toLowerCase();
	}

	@Override
	public boolean IsObstacle() {
		return true;
	}
}
