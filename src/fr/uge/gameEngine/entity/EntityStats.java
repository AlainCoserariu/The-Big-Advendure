package fr.uge.gameEngine.entity;

import java.util.Objects;

import fr.uge.gameEngine.fieldElement.FieldElement;
import fr.uge.gameEngine.utility.Hitbox;

/**
 * Class used for delegation. Represent all the fields used in differents
 * classes like player, monster or friend
 */
public class EntityStats {
  private double x;
  private double y;

  private int direction; // 0 = north, 1 = west, 2 = south, 3 = east
  
  private double speed; // Tiles per seconds traveled

  private int health;
  private int maxHealth;

  private final Hitbox hitbox;

  private final int iframeDuration; // Number of invincibility frames after taking damage
  private int iframe;

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
  public EntityStats(double x, double y, double speed, int maxHealth, String name, int iframeDuration) {
    Objects.requireNonNull(name);

    this.x = x;
    this.y = y;

    hitbox = new Hitbox(x, y, 0.8);

    this.speed = speed;

    this.maxHealth = maxHealth;
    this.health = maxHealth;

    this.iframeDuration = iframeDuration;
    iframe = 0;
    
    this.direction = 2;

    this.name = name;
  }

  public void move(double x, double y) {
    this.x = x;
    this.y = y;

    hitbox.updateHitbox(this.x, this.y);
  }

  public void takeDamage(int damage) {
    if (iframe == 0) {
      health = Integer.max(0, health - damage);
      iframe = iframeDuration;      
    }
  }
  
  public void updateIframes() {
    if (iframe > 0) {
      iframe--;      
    }
  }

  public void heal(int healPoint) {
    health = Integer.max(health + healPoint, maxHealth);
  }
  
  /**
   * Check if the entity is colliding with a field element. Check only surrounding
   * tiles
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
  
  public Hitbox getHitbox() {
    return hitbox;
  }
  
  public String getName() {
    return name;
  }
  
  public int getIframe() {
    return iframe;
  }
  
  public int getDirection() {
    return direction;
  }
  
  public void setDirection(int direction) {
    this.direction = direction;
  }
}