package fr.uge.enums;

public enum SkinPlayer {
  BABA,
  BADBAD,
  FOFO,
  IT;
  
  /**
   * Tell without throwing errors if a string is in the enum
   * 
   * @param elt
   * @return boolean
   */
  public static boolean contains(String elt) {
    try {
      SkinPlayer.valueOf(elt);
      return true;
    } catch (RuntimeException e) {
      return false;
    }
  }
}
