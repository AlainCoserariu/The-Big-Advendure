package fr.uge.gameEngine.fieldElement;

import fr.uge.enums.DecorationEnum;

/**
 * Represent decoration, element that is only for visual purpose
 */
public record Decoration(double x, double y, DecorationEnum type) implements FieldElement {}
