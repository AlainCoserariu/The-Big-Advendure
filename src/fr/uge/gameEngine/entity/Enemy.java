package fr.uge.gameEngine.entity;

import java.util.Objects;

import fr.uge.enums.Behavior;
import fr.uge.enums.SkinEnemy;
import fr.uge.gameEngine.fieldElement.FieldElement;
import fr.uge.gameEngine.utility.Hitbox;
import fr.uge.gameEngine.utility.MovementZone;

public final class Enemy implements Entity {
  private final EntityStats enemy;
  private final SkinEnemy skin;
  private final MovementZone zone;
  private final Behavior behavior;
  private final int damage;

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
      Behavior behavior, int damage) {
    Objects.requireNonNull(name);

    enemy = new EntityStats(x, y, speed, maxHealth, name, 60 / 2);
    this.skin = skin;
    this.zone = zone;
    this.behavior = behavior;
    this.damage = damage;
  }

  @Override
  public void move(double x, double y) {
    enemy.move(x, y);
  }

  @Override
  public void takeDamage(int damage) {
    enemy.takeDamage(damage);
  }

  @Override
  public void heal(int healPoint) {
    enemy.heal(healPoint);
  }

  @Override
  public double getX() {
    return enemy.getX();
  }

  @Override
  public double getY() {
    return enemy.getY();
  }

  @Override
  public double getSpeed() {
    return enemy.getSpeed();
  }

  public SkinEnemy getSkin() {
    return skin;
  }

  @Override
  public boolean collideWithObstacle(FieldElement[][] field) {
    return enemy.collideWithObstacle(field);
  }

  @Override
  public int getHealth() {
    return enemy.getHealth();
  }

  @Override
  public int getMaxHealth() {
    return enemy.getMaxHealth();
  }

  @Override
  public EntityStats getBaseEntity() {
    return enemy;
  }

  @Override
  public String getName() {
    return enemy.getName();
  }

  @Override
  public Hitbox getHitbox() {
    return enemy.getHitbox();
  }
  
  public int getDamage() {
    return damage;
  }

  @Override
  public void updateIframes() {
    enemy.updateIframes();
  }

  @Override
  public int getIframe() {
    return enemy.getIframe();
  }
}