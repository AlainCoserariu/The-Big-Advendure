package fr.uge.gameElement.fieldElement;

import fr.uge.enums.DecorationEnum;

public record Decoration(double x, double y, DecorationEnum type) implements FieldElement {}
