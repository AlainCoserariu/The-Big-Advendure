package fr.uge.entity;

import java.util.Objects;
import java.util.Random;

import fr.uge.fieldElement.FieldElement;
import fr.uge.gameParameter.GameParameter;
import fr.uge.utility.hitboxe.Hitbox;

public class Entity {
  private double x;
  private double y;

  private double speed; // Tiles per seconds traveled

  private int health;
  private int maxHealth;

  public final Hitbox hitbox;

  private final String name;

  /**
   * Entity constructor
   * 
   * @param x
   * @param y
   * @param speed
   * @param maxHealth
   * @param name
   */
  public Entity(double x, double y, double speed, int maxHealth, String name) {
    Objects.requireNonNull(name);

    this.x = x;
    this.y = y;

    hitbox = new Hitbox(x, y, 1);

    this.speed = speed;

    this.maxHealth = maxHealth;
    this.health = maxHealth;

    this.name = name;
  }

  public void move(double x, double y) {
    this.x += x;
    this.y += y;

    hitbox.updateHitbox(this.x, this.y);
  }

  public void takeDamage(int damage) {
    health = Integer.max(0, health - damage);
  }

  public void heal(int healPoint) {
    health = Integer.max(health + healPoint, maxHealth);
  }

  public boolean collideWithObstacle(FieldElement element) {
    Objects.requireNonNull(element);
    if (element.IsObstacle()) {
      return hitbox.collide(element.getHitbox());
    }
    return false;
  }

  public boolean collideWithEnemy(Entity other) {
    Objects.requireNonNull(other);
    return hitbox.collide(other.hitbox);
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getSpeed() {
    return speed;
  }

  public int getHealth() {
    return health;
  }

  public int getMaxHealth() {
    return health;
  }
  
  public void randomMove(FieldElement[][] field, GameParameter parameters){
    Random ran = new Random();
    System.out.println(speed);
    int random = ran.nextInt(3);
    double x = 0;
    double y = 0;
    switch (random) {
    case 0 -> {
      if(ran.nextInt(2) == 1) y = (speed / parameters.getFramerate());
      else y = (-speed / parameters.getFramerate());
    }
    case 1 -> {
      x = (speed / parameters.getFramerate());
    }
    case 2 -> {
      x = (-speed / parameters.getFramerate());
    }
    }
    if((0 <= this.x + x && this.x + x <= field.length) && 0 <= this.y + y && this.y + y <= field[0].length)
      this.move(x, y);
  }
}