package fr.uge.enums;

public enum Behavior {
  shy,
  stroll,
  agressive;

  /**
   * Tell without throwing errors if a string is in the enum
   * 
   * @param elt
   * @return boolean
   */
  public static boolean contains(String elt) {
    try {
      Behavior.valueOf(elt);
      return true;
    } catch (RuntimeException e) {
      return false;
    }
  }
}
