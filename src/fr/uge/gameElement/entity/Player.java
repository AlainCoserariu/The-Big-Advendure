package fr.uge.gameElement.entity;

import java.util.Objects;

import fr.uge.gameElement.entity.player.SkinPlayer;
import fr.uge.gameElement.fieldElement.FieldElement;

public final class Player implements Entity {
  private final BaseEntity player;
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
    
    player = new BaseEntity(x, y, speed, maxHealth, name);
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

  public double getX() {
    return player.getX();
  }

  public double getY() {
    return player.getY();
  }

  public double getSpeed() {
    return player.getSpeed();
  }
  
  public SkinPlayer getSkin() {
    return skin;
  }

  @Override
  public boolean collideWithObstacle(FieldElement field[][]) {
    return player.collideWithObstacle(field);
  }

  @Override
  public int getHealth() {
    return player.getHealth();
  }

  @Override
  public int getMaxHealth() {
    return player.getMaxHealth();
  }

  @Override
  public BaseEntity getBaseEntity() {
    return player;
  }
}