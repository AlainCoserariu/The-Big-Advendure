package fr.uge.enums;

public enum DecorationEnum {
  ALGAE,
  CLOUD,
  FLOWER,
  FOLIAGE,
  GRASS,
  LADDER,
  LILY, 
  PLANK, 
  REED, 
  ROAD, 
  SPROUT, 
  TILE, 
  TRACK, 
  VINE;
  
  public static boolean contains(String elt) {
    try {
      DecorationEnum.valueOf(elt);
      return true;
    } catch (RuntimeException e) {
      return false;
    }
  }
}