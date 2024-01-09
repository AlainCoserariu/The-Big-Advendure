package fr.uge.enums;

public enum SkinPlayer {
  BABA,
  BADBAD,
  FOFO,
  IT;
  
  public static boolean contains(String elt) {
    try {
      SkinPlayer.valueOf(elt);
      return true;
    } catch (RuntimeException e) {
      return false;
    }
  }
}
