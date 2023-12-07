package fr.uge.entity.player;

import java.util.Objects;

import fr.uge.entity.Entity;
import fr.uge.fieldElement.FieldElement;

public class Player {
  public final Entity player;
  private final SkinPlayer skin;

  /**
   * Player constructor
   * 
   * @param x
   * @param y
   * @param speed
   * @param maxHealth
   * @param name
   */
  public Player(double x, double y, double speed, int maxHealth, String name, SkinPlayer skin) {
    Objects.requireNonNull(name);
    
    player = new Entity(x, y, speed, maxHealth, name);
    this.skin = skin;
  }
  
  public void move(double x, double y) {
    player.move(x, y);
  }

  public void takeDamage(int damage) {
    player.takeDamage(damage);
  }

  public void heal(int healPoint) {
    player.heal(healPoint);
  }
  
  public boolean collideWithObstacle(FieldElement element) {
    return player.collideWithObstacle(element);
  }

  public double getX() {
    return player.getX();
  }

  public double getY() {
    return player.getY();
  }

  public double getSpeed() {
    return player.getSpeed();
  }

  public int hashCode() {
    return player.hashCode();
  }

  public boolean equals(Object obj) {
    return player.equals(obj);
  }

  public String toString() {
    return player.toString();
  }
  
  public SkinPlayer getSkin() {
    return skin;
  }

  public int getHealth() {
    return player.getHealth();
  }
  
  public int getMaxHealth() {
    return player.getMaxHealth();
  }
}