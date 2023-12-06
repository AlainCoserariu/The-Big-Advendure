package fr.uge.fieldElement;

import fr.uge.utility.hitboxe.Hitbox;

public interface FieldElement {

	default boolean IsObstacle() { return false;};
  EnumType getType();
  default Hitbox getHitbox() {return null;};
}
