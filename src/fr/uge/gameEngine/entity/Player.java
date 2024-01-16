package fr.uge.gameEngine.entity;

import java.util.List;
import java.util.Objects;

import fr.uge.enums.SkinItem;
import fr.uge.enums.SkinPlayer;
import fr.uge.gameEngine.Weapon;
import fr.uge.gameEngine.fieldElement.FieldElement;
import fr.uge.gameEngine.utility.Hitbox;

public final class Player implements Entity {
  private final EntityStats player;
  private final SkinPlayer skin;
  
  private final Weapon weapon;
  private final int attackFrameDuration; // How many frame the weapon is out
  private int attackFrame;
  
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
    
    weapon = new Weapon(-1, y -1, 5, new Hitbox(-1, -1, 1), SkinItem.SWORD);
    attackFrameDuration = 60;
    attackFrame = 0;
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
  
  public boolean isAttacking() {
    return attackFrame > 0;
  }
  
  public void updateWeaponPos() {
    switch (getDirection()) {
    case 0:
      weapon.move(player.getX(), player.getY() - 0.9);
      break;
    case 1:
      weapon.move(player.getX() - 0.9, player.getY());
      break;
    case 2:
      weapon.move(player.getX(), player.getY() + 0.9);
      break;
    case 3:
      weapon.move(player.getX() + 0.9, player.getY());
      break;
    default:
      break;
    }
  }
  
  public void attack() {
    if (isAttacking()) return;
    
    attackFrame = attackFrameDuration;
    updateWeaponPos();
  }

  public boolean collideWithEnemy(List<Enemy> enemies) {
    var collidingEnemy = enemies.stream().filter(e -> e.getHitbox().collide(player.getHitbox())).findFirst();
    
    if (collidingEnemy.isPresent()) {
      takeDamage(collidingEnemy.get().getDamage());
      return true;
    }
    
    return false;
  }
  
  /**
   * Check if an enemy is hit by the weapon of the player
   * 
   * @param enemy
   */
  public void checkHitEnemies(List<Enemy> enemy) {
    enemy.forEach(e -> {
      if (e.getHitbox().collide(weapon.getHitbox())) {
        e.takeDamage(weapon.getDamage());
      }
    });
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
  
  public Weapon getWeapon() {
    return weapon;
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
    if (attackFrame > 0) attackFrame -= 1;
  }

  @Override
  public int getIframe() {
    return player.getIframe();
  }
  
  @Override
  public int getDirection() {
    return player.getDirection();
  }
  
  @Override
  public void setDirection(int direction) {
    player.setDirection(direction);
  }
}