package fr.uge.gameEngine.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class Inventory {
  private final List<Item> items;
  private final int maxLenght;
  
  public Inventory() {
    items = new ArrayList<Item>();
    maxLenght = 10;
  }
  
  public void addItem(Item item) {
    Objects.requireNonNull(item);
    if (items.size() == maxLenght) {
      return;
    }
    items.add(item);
  }
  
  public void delItemIndex(int i) {
    items.remove(i);
  }
  
  public Item getItem(int i) {
    return items.get(i);
  }
  
  public int getSize() {
    return items.size();
  }
  
  public void foreach(Consumer<Item> consumer) {
    items.forEach(consumer);
  }
  
  /**
   * Swap position of two items using their index
   * 
   * @param a
   * @param b
   */
  public void swapItems(int a, int b) {
    Collections.swap(items, a, b);
  }
}
