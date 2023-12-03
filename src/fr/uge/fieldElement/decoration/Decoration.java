package fr.uge.fieldElement.decoration;

import fr.uge.fieldElement.EnumType;
import fr.uge.fieldElement.FieldElement;

public record Decoration(double x, double y, DecorationEnum type) implements FieldElement {

	@Override
	public EnumType getType() {
		return type;
	}
}
