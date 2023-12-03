package fr.uge.field.grid;

public class Grid {
  private final GridElement map[][];

  public Grid(int width, int height) {
    map = new GridElement[height][width];
  }

	public GridElement[][] getList() {
		return map;
	}
}
