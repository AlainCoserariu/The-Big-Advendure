package fr.uge.gameEngine.fieldElement;

import fr.uge.gameEngine.utility.Hitbox;

public sealed interface FieldElement permits Obstacle, Decoration {

  default boolean IsObstacle() {
    return false;
  };

  default Hitbox getHitbox() {return null;};
  
  /**
   * Getter to get x field
   * @return x
   */
  double x();

  /**
   * Getter to get y field
   * @return y
   */
  double y();
}
