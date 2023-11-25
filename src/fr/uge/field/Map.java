package fr.uge.field;

import java.nio.file.Path;

public class Map {
  private final Element map[][];
  
  public Map(Path mapFile) {
    
    this.map = new Element[10][10];
  }
}
