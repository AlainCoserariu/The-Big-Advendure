package fr.uge.entity;

import fr.uge.utils.Vector2D;

public interface Entity {
  double getPosX();
  void setPosX(double x);
  
  double getPosY();
  void setPosY(double y);
  
  int getHealth();
  void setHealth(int health);
  
  default public boolean verif_move(Vector2D vector) {
  	if(getPosX() + vector.vx() < 0 || getPosX() + vector.vx() > 1920 || getPosY() + vector.vy() < 0 || getPosY() + vector.vy() > 1080)
  		return false;
  	else 
  		return true;
  }
  
  default public void move(Vector2D vector) {
  	if(verif_move(vector)){
  		setPosX(getPosX() + vector.vx());
      setPosY(getPosY() + vector.vy());
  	}
  }
  
  default public void loseHP(int damage) {
    setHealth(Integer.max(getHealth() - damage, 0));
  }
}