package fr.uge.fieldElement.decoration;

import fr.uge.fieldElement.EnumType;
import fr.uge.fieldElement.FieldElement;
import fr.uge.utility.hitboxe.Hitbox;

public record Decoration(double x, double y, DecorationEnum type) implements FieldElement {

	@Override
	public EnumType getType() {
		return type;
	}

  @Override
  public Hitbox getHitbox() {
    return null;
  }

  @Override
  public double getX() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public double getY() {
    // TODO Auto-generated method stub
    return 0;
  }
}
