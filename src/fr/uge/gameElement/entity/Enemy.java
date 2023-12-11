package fr.uge.gameElement.entity;

import java.util.Objects;

import fr.uge.gameElement.entity.enemy.BehaviorEnum;
import fr.uge.gameElement.entity.enemy.SkinEnemy;
import fr.uge.gameElement.fieldElement.FieldElement;
import fr.uge.gameElement.utility.movementZone.MovementZone;

public final class Enemy implements Entity {
  private final BaseEntity enemy;
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

    enemy = new BaseEntity(x, y, speed, maxHealth, name);
    this.skin = skin;
    this.zone = zone;
    this.behavior = behavior;
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
  public BaseEntity getBaseEntity() {
    return enemy;
  }
}