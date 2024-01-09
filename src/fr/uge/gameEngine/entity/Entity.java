package fr.uge.gameEngine.entity;

import fr.uge.gameEngine.fieldElement.FieldElement;
import fr.uge.gameEngine.utility.Hitbox;

public sealed interface Entity permits Enemy, Player{
  void move(double x, double y);

  void takeDamage(int damage);

  void heal(int healPoint);

  boolean collideWithObstacle(FieldElement field[][]);
  
  void updateIframes();
  
  // ---------------------------------GETTERS---------------------------------
  
  double getX();

  double getY();

  double getSpeed();

  int getHealth();

  int getMaxHealth();
  
  EntityStats getBaseEntity();
  
  Hitbox getHitbox();
  
  int getIframe();
  
  String getName();
}
