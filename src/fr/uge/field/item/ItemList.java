package fr.uge.field.item;

import java.util.ArrayList;

public class ItemList implements Item {
  private final ArrayList<Item> items;

  public ItemList() {
    items = new ArrayList<Item>();
  }
  
  public void add(Item item) {
    java.util.Objects.requireNonNull(item);
    items.add(item);
  }

	public ArrayList<Item> getList() {
		return items;
	}
}
