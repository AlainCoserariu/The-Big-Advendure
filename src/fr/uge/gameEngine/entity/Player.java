package fr.uge.gameEngine.entity;

import java.util.List;
import java.util.Objects;

import fr.uge.enums.SkinPlayer;
import fr.uge.gameEngine.fieldElement.FieldElement;
import fr.uge.gameEngine.item.Food;
import fr.uge.gameEngine.item.Item;
import fr.uge.gameEngine.item.Weapon;
import fr.uge.gameEngine.utility.Hitbox;

public final class Player implements Entity {
  private final EntityStats player;
  private final SkinPlayer skin;

  private final Inventory inventory;
  private Weapon weapon;
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

    inventory = new Inventory();
    
    weapon = null;
    attackFrameDuration = 60;
    attackFrame = 0;
  }

  @Override
  public void move(double x, double y) {
    player.move(x, y);
  }

  @Override
  public void takeDamage(int damage) {
    player.takeDamage(damage);
  }

  @Override
  public void heal(int healPoint) {
    player.heal(getMaxHealth());
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
    if (isAttacking())
      return;

    attackFrame = attackFrameDuration;
    updateWeaponPos();
  }

  public void action() {
    if (inventory.getSize() == 0) {
      return;
    }
    switch (inventory.getItem(0)) {
    case Weapon w -> {
      weapon = w;
      attack();
    }
    case Food f -> {
      heal(f.healingPoint());
      inventory.delItemIndex(0);
    }
    }
  }

  public boolean collideWithEnemy(List<Enemy> enemies) {
    var collidingEnemy = enemies.stream().filter(e -> e.getHitbox().collide(player.getHitbox())).findFirst();

    if (collidingEnemy.isPresent()) {
      takeDamage(collidingEnemy.get().getDamage());
      return true;
    }

    return false;
  }
  
  public void collideWithItem(List<Item> items) {
    var collidingItem = items.stream().filter(i -> i.getHitbox().collide(player.getHitbox())).findFirst();
    if (collidingItem.isPresent()) {
      inventory.addItem(collidingItem.get());
      items.remove(collidingItem.get());
    }
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

  public Inventory getInventory() {
    return inventory;
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
    if (attackFrame > 0)
      attackFrame -= 1;
    else
      weapon = null;
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