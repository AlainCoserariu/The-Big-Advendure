package fr.uge.entity;

import fr.uge.utils.Vector2D;

public interface Entity {
  double getPosX();
  void setPosX(double x);
  
  double getPosY();
  void setPosY(double y);
  
  int getHealth();
  void setHealth(int health);
  
  double getSpeed();
  
  default public void move(Vector2D vector) {
	setPosX(getPosX() + vector.scale(getSpeed()).vx());
    setPosY(getPosY() + vector.scale(getSpeed()).vy());
  }
  
  default public void loseHP(int damage) {
    setHealth(Integer.max(getHealth() - damage, 0));
  }
}