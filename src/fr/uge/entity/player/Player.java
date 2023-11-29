package fr.uge.entity.player;

import java.util.Objects;

import fr.uge.entity.Entity;
import fr.uge.utils.Direction;
import fr.uge.utils.hitbox.Hitbox;
import fr.uge.utils.hitbox.SquareHitbox;
import fr.uge.weapon.Weapon;

public class Player implements Entity{
  private double posX;
  private double posY;
  private Direction orientation;  // Cardinal direction the player is facing
  
  private final Hitbox hitbox;

  private int health;
  private int maxHealth;
  
  private double speed;
  private double defaultSpeed;
 
  private Weapon weapon;
  private boolean isAttacking;
  
  private final String name;
  
  public Player(double posX, double posY, int health, int maxHealth, double speed, double defaultSpeed, String name, Weapon weapon) {
    Objects.requireNonNull(name);
    if (maxHealth <= 0) {
      throw new IllegalArgumentException("Health must be greater than 0");
    }
    
    this.posX = posX;
    this.posY = posY;
    this.maxHealth = maxHealth;
    this.health = maxHealth;
    this.speed = speed;
    this.defaultSpeed = defaultSpeed;
    isAttacking = false;
    this.weapon = weapon;
    this.hitbox = new SquareHitbox(posX, posY, 0.5);
    this.name = name;
  }
  
  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  @Override
  public double getPosX() {
    return posX;
  }
  
  @Override
  public void setPosX(double posX) {
    this.posX = posX;
  }

  @Override
  public double getPosY() {
    return posY;
  }

  @Override
  public void setPosY(double posY) {
    this.posY = posY;
  }

  public double getSpeed() {
    return speed;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
  }
}
