package fr.uge.fieldElement;

public interface FieldElement {

	default boolean IsObstacle() { return false;};
  EnumType getType();
}
