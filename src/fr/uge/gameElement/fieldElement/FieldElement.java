package fr.uge.gameElement.fieldElement;

import fr.uge.gameElement.utility.hitboxe.Hitbox;

public sealed interface FieldElement permits Obstacle, Decoration {

  default boolean IsObstacle() {
    return false;
  };

  default Hitbox getHitbox() {return null;};
  
  double x();

  double y();
}
