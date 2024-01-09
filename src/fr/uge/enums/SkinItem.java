package fr.uge.enums;

public enum SkinItem {
  KEY,
  SWORD;
  
  public static boolean contains(String elt) {
    try {
      SkinItem.valueOf(elt);
      return true;
    } catch (RuntimeException e) {
      return false;
    }
  }
}
