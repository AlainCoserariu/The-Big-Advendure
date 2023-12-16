package fr.uge.gameElement.entity;

import fr.uge.gameElement.fieldElement.FieldElement;

public sealed interface Entity permits Enemy, Player{
  void move(double x, double y);

  void takeDamage(int damage);

  void heal(int healPoint);

  boolean collideWithObstacle(FieldElement field[][]);
  
  // ---------------------------------GETTERS---------------------------------
  
  double getX();

  double getY();

  double getSpeed();

  int getHealth();

  int getMaxHealth();
  
  EntityStats getBaseEntity();
}
