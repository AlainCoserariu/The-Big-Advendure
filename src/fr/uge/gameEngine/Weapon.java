package fr.uge.gameEngine;

import fr.uge.enums.SkinItem;
import fr.uge.gameEngine.utility.Hitbox;

public class Weapon {
  private double x;
  private double y;
  
  private final int damage;
  private final Hitbox hitbox;
  
  private final SkinItem skin;
  
  public Weapon(double x, double y, int damage, Hitbox hitbox, SkinItem skin) {
    this.x = x;
    this.y = y;
    
    this.damage = damage;
    this.hitbox = hitbox;
    this.skin = skin;
  }
  
  public void move(double x, double y) {
    this.x = x;
    this.y = y;

    hitbox.updateHitbox(x, y);
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
  
  public SkinItem getSkin() {
    return skin;
  }
  
}
