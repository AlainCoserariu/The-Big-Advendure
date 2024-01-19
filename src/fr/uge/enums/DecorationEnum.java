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
  
  /**
   * Tell without throwing errors if a string is in the enum
   * 
   * @param elt
   * @return boolean
   */
  public static boolean contains(String elt) {
    try {
      DecorationEnum.valueOf(elt);
      return true;
    } catch (RuntimeException e) {
      return false;
    }
  }
}