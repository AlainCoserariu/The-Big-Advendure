package fr.uge.entity;

import fr.uge.fieldElement.FieldElement;

public interface Entity {
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
}
