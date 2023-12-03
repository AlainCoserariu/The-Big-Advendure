package fr.uge.entity;

import java.util.Objects;

import fr.uge.utility.hitboxe.Hitbox;

public class Entity {
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
  public Entity(double x, double y, double speed, int maxHealth, String name) {
    Objects.requireNonNull(name);

    this.x = x;
    this.y = y;
    
    hitbox = new Hitbox(x, y, 1);
    
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

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getSpeed() {
    return speed;
  }

}
