package fr.uge.entity.enemy;

import java.util.Objects;

import fr.uge.entity.Entity;
import fr.uge.utils.Direction;
import fr.uge.utils.hitbox.Hitbox;

public class Enemy implements Entity {
  private double posX;
  private double posY;
  private Direction orientation;
  
  private int health;
  private int maxHeatlth;
  
  private double speed;
  private double defaultSpeed;
  
  private final Hitbox hitbox;
  private String name;
  
  public Enemy(int posX, int posY, Direction orientation, int speed, Hitbox hitbox, String name) {
    Objects.requireNonNull(hitbox);
    if (speed < 0) {
      throw new IllegalArgumentException("Cant't have negative speed");
    }
    
    this.posX = posX;
    this.posY = posY;
    this.orientation = orientation;
    this.speed = speed;
    this.hitbox = hitbox;
    this.name = name;
  }

  public double getPosX() {
    return posX;
  }

  public void setPosX(double posX) {
    this.posX = posX;
  }

  public double getPosY() {
    return posY;
  }

  public void setPosY(double posY) {
    this.posY = posY;
  }

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public double getSpeed() {
    return speed;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
  }
}
