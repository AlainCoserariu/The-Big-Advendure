package fr.uge.entity.enemy;

import java.util.Objects;

import fr.uge.entity.Entity;
import fr.uge.utility.movementZone.MovementZone;

public class Enemy {
  private final Entity enemy;
  private final SkinEnemy skin;
  private final MovementZone zone;
  private final BehaviorEnum behavior;

  /**
   * Enemy constructor
   * 
   * @param x
   * @param y
   * @param speed
   * @param maxHealth
   * @param name
   */
  public Enemy(double x, double y, double speed, int maxHealth, String name, SkinEnemy skin, MovementZone zone,
      BehaviorEnum behavior) {
    Objects.requireNonNull(name);

    enemy = new Entity(x, y, speed, maxHealth, name);
    this.skin = skin;
    this.zone = zone;
    this.behavior = behavior;
  }

  public void move(double x, double y) {
    enemy.move(x, y);
  }

  public void takeDamage(int damage) {
    enemy.takeDamage(damage);
  }

  public void heal(int healPoint) {
    enemy.heal(healPoint);
  }

  public double getX() {
    return enemy.getX();
  }

  public double getY() {
    return enemy.getY();
  }

  public double getSpeed() {
    return enemy.getSpeed();
  }

  public int hashCode() {
    return enemy.hashCode();
  }

  public boolean equals(Object obj) {
    return enemy.equals(obj);
  }

  public String toString() {
    return enemy.toString();
  }

  public SkinEnemy getSkin() {
    return skin;
  }
}