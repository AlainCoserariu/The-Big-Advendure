package fr.uge.gameEngine.entity;

import java.util.Objects;
import java.util.Random;

import fr.uge.enums.Behavior;
import fr.uge.enums.SkinEnemy;
import fr.uge.gameEngine.Panel;
import fr.uge.gameEngine.fieldElement.FieldElement;
import fr.uge.gameEngine.utility.Hitbox;
import fr.uge.gameEngine.utility.MovementZone;
import fr.uge.gameEngine.utility.RandomGenerator;

public final class Enemy implements Entity {
  private final EntityStats enemy;
  private final SkinEnemy skin;
  private final MovementZone zone;
  private final Behavior behavior;
  private final int damage;

  private double distanceToTravel = 0; // Distance remaining to travel for automatic movement

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
    Objects.requireNonNull(zone);

    enemy = new EntityStats(x, y, speed, maxHealth, name, 60 / 2);
    this.skin = skin;
    this.zone = zone;
    this.behavior = behavior;
    this.damage = damage;
    setDirection(2);
  }

  public void selectRandomDestination() {
    setDirection(RandomGenerator.randint(0, 3));
    distanceToTravel = RandomGenerator.randint(1, 3);
    
    double newX = getX();
    double newY = getY();
    
    switch (getDirection()) {
    case 0: newY -= distanceToTravel; break;
    case 1: newX -= distanceToTravel; break;
    case 2: newY += distanceToTravel; break;
    case 3: newX += distanceToTravel; break; 
    default: break;
    }
    
    if (!zone.isInZone(newX, newY)) distanceToTravel = 0;
  }
  
  private boolean collideWithPlayer(Player player) {
    if (player.getHitbox().collide(getHitbox())) {
      player.takeDamage(damage);
      return true;
    }
    return false;
  }

  private void moveStroll(double step, Panel panel) {
    double x = getX();
    double y = getY();
    
    switch (getDirection()) {
    case 0: move(getX(), getY() - step); break;
    case 1: move(getX() - step, getY()); break;
    case 2: move(getX(), getY() + step); break;
    case 3: move(getX() + step, getY()); break; 
    default: break;
    }
    if (collideWithObstacle(panel.getField()) || collideWithPlayer(panel.getPlayer())) {
      move(x, y);
    }
  }
  
  public void moveEnemy(Panel panel, int framerate) {
    if (distanceToTravel <= 0) {
      if (RandomGenerator.randint(0, framerate) == 0) {
        selectRandomDestination();
      }
      return;
    }
    
    double step = getSpeed() / framerate;
    
    if (behavior == Behavior.stroll) moveStroll(step, panel);
    distanceToTravel -= step;
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
  
  @Override
  public int getDirection() {
    return enemy.getDirection();
  }
  
  @Override
  public void setDirection(int direction) {
    enemy.setDirection(direction);
  }
}