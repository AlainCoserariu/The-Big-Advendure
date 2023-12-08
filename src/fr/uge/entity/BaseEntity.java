package fr.uge.entity;

import java.util.Objects;

import fr.uge.fieldElement.FieldElement;
import fr.uge.utility.hitboxe.Hitbox;

public class BaseEntity {
  private double x;
  private double y;

  private double speed; // Tiles per seconds traveled

  private int health;
  private int maxHealth;

  public final Hitbox hitbox;

  private final String name;

  /**
   * Entity constructor
   * 
   * @param x
   * @param y
   * @param speed
   * @param maxHealth
   * @param name
   */
  public BaseEntity(double x, double y, double speed, int maxHealth, String name) {
    Objects.requireNonNull(name);

    this.x = x;
    this.y = y;
    
    hitbox = new Hitbox(x, y, 0.8);
    
    this.speed = speed;
    
    this.maxHealth = maxHealth;
    this.health = maxHealth;
    
    this.name = name;
  }
  
  public void move(double x, double y) {
    this.x += x;
    this.y += y;

    hitbox.updateHitbox(this.x, this.y);
  }

  public void takeDamage(int damage) {
    health = Integer.max(0, health - damage);
  }

  public void heal(int healPoint) {
    health = Integer.max(health + healPoint, maxHealth);
  }

  /**
   * Check if the entity is colliding with a field element. Check only surrounding tiles
   * 
   * @param field
   * @return
   */
  public boolean collideWithObstacle(FieldElement field[][]) {
    for (int i = (int) y - 1; i <= (int) y + 1; i++) {
      for (int j = (int) x - 1; j <= (int) x + 1; j++) {
        // Check if index are in range
        if (i >= 0 && j >= 0 && i < field.length && j < field[0].length) {
          // Check the collision, only with obstacles
          if (field[i][j] != null && field[i][j].IsObstacle() && hitbox.collide(field[i][j].getHitbox())) {
            return true;
          }          
        }
      }
    }
    
    return false;
  }
  
  // ---------------------------------GETTERS---------------------------------
  
  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getSpeed() {
    return speed;
  }

  public int getHealth() {
    return health;
  }

  public int getMaxHealth() {
    return maxHealth;
  }
}