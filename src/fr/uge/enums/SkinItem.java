package fr.uge.enums;

public enum SkinItem {
  BOOK,
  BOLT,
  BOX,
  CASH,
  CLOCK,
  COG,
  CRYSTAL,
  CUP,
  DRUM,
  FLAG,
  GEM,
  GUITAR,
  HIHAT,
  KEY,
  LAMP,
  LEAF,
  MIRROR,
  MOON,
  ORB,
  PANTS,
  PAPER,
  PLANET,
  RING,
  ROSE,
  SAX,
  SCISSORS,
  SEED,
  SHIRT,
  SHOVEL,
  STAR,
  STICK,
  SUN,
  SWORD,
  TRUMPET,
  VASE;
  
  /**
   * Tell without throwing errors if a string is in the enum
   * 
   * @param elt
   * @return boolean
   */
  public static boolean contains(String elt) {
    try {
      SkinItem.valueOf(elt);
      return true;
    } catch (RuntimeException e) {
      return false;
    }
  }
}
