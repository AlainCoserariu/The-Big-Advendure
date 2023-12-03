package fr.uge.fieldElement.decoration;

import fr.uge.fieldElement.FieldElement;

public record Decoration(double x, double y, DecorationEnum type) implements FieldElement {}
