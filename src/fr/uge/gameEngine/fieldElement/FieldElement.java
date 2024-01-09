package fr.uge.gameEngine.fieldElement;

import fr.uge.gameEngine.utility.Hitbox;

public sealed interface FieldElement permits Obstacle, Decoration {

  default boolean IsObstacle() {
    return false;
  };

  default Hitbox getHitbox() {return null;};
  
  double x();

  double y();
}
