package fr.uge.field.grid.decoration;

import fr.uge.field.grid.GridElement;

public record Decoration(int x, int y, DecorationEnum decoration) implements GridElement {

	@Override
	public String getFileName() {
		return decoration.name().toLowerCase();
	}

	@Override
	public boolean IsObstacle() {
		return false;
	}
  
}
