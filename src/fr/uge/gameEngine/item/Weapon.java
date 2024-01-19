package fr.uge.gameEngine.item;

import java.util.List;

import fr.uge.enums.SkinItem;
import fr.uge.gameEngine.entity.Enemy;
import fr.uge.gameEngine.utility.Hitbox;

public final class Weapon implements Item {
  private double x;
  private double y;
  
  private final int damage;
  private final Hitbox hitbox;
  
  private final SkinItem skin;
  
  private final String name;
  
  public Weapon(double x, double y, int damage, Hitbox hitbox, SkinItem skin, String name) {
    this.x = x;
    this.y = y;
    
    this.damage = damage;
    this.hitbox = hitbox;
    this.skin = skin;
    this.name = name;
  }
  
  public void move(double x, double y) {
    this.x = x;
    this.y = y;

    hitbox.updateHitbox(x, y);
  }
  
  /**
   * Check if an enemy is hit by the weapon of the player
   * 
   * @param enemy
   */
  public void checkHitEnemies(List<Enemy> enemy) {
    enemy.forEach(e -> {
      if (e.getHitbox().collide(hitbox)) {
        e.takeDamage(damage);
      }
    });
  }
  
  public double getX() {
    return x;
  }
  
  public double getY() {
    return y;
  }
  
  public int getDamage() {
    return damage;
  }
  
  public Hitbox getHitbox() {
    return hitbox;
  }
  
  public String getSkin() {
    return skin.toString();
  }
  
  public String getName() {
    return name;
  }
  
}
