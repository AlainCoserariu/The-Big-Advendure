package fr.uge.enums;

public enum Behavior {
  shy,
  stroll,
  agressive;

  public static boolean contains(String elt) {
    try {
      Behavior.valueOf(elt);
      return true;
    } catch (RuntimeException e) {
      return false;
    }
  }
}
