package fr.uge.enums;

public enum possibleFieldMapFile {
  size,
  encodings,
  data,
  name,
  skin,
  player,
  position,
  health,
  kind,
  zone,
  behavior,
  damage,
  text,
  steal,
  trade,
  locked,
  flow,
  phantomized,
  teleport;
  
  /**
   * Tell without throwing errors if a string is in the enum
   * 
   * @param elt
   * @return boolean
   */
  public static boolean contains(String elt) {
    try {
      possibleFieldMapFile.valueOf(elt);
      return true;
    } catch (RuntimeException e) {
      return false;
    }
  }
}
