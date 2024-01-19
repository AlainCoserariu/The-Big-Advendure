package fr.uge.gameEngine.item;

import fr.uge.enums.FoodEnum;
import fr.uge.gameEngine.utility.Hitbox;

public record Food(double x, double y, int healingPoint, Hitbox hitbox, FoodEnum skin, String name) implements Item {
  
  public String getSkin() {
    return skin.toString();
  }
  
  public Hitbox getHitbox() {
    return hitbox;
  }
  
  public double getX() {
    return x;
  }
  
  public double getY() {
    return y;
  }

  public String getName() {
    return name;
  }
}
