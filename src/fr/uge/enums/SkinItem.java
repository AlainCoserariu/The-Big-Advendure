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
  
  public static boolean contains(String elt) {
    try {
      SkinItem.valueOf(elt);
      return true;
    } catch (RuntimeException e) {
      return false;
    }
  }
}
