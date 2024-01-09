package fr.uge.gameEngine.entity;

import java.util.List;
import java.util.Objects;

import fr.uge.enums.SkinPlayer;
import fr.uge.gameEngine.fieldElement.FieldElement;
import fr.uge.gameEngine.utility.Hitbox;

public final class Player implements Entity {
  private final EntityStats player;
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

    player = new EntityStats(x, y, speed, maxHealth, name, 60);
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

  public boolean collideWithEnemy(List<Enemy> enemies) {
    var collidingEnemy = enemies.stream().filter(e -> e.getHitbox().collide(player.getHitbox())).findFirst();
    
    if (collidingEnemy.isPresent()) {
      if (getIframe() == 0) takeDamage(collidingEnemy.get().getDamage());
      return true;
    }
    
    return false;
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
  public EntityStats getBaseEntity() {
    return player;
  }

  @Override
  public String getName() {
    return player.getName();
  }

  @Override
  public Hitbox getHitbox() {
    return player.getHitbox();
  }

  @Override
  public void updateIframes() {
    player.updateIframes();
  }

  @Override
  public int getIframe() {
    return player.getIframe();
  }
}