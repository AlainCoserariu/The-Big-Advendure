package fr.uge.enums;

public enum SkinEnemy {
  BAT,
  BEE,
  BIRD,
  BUG,
  BUNNY,
  CAT,
  CRAB,
  DOG,
  FISH,
  FROG,
  GHOST,
  JELLY,
  JIJI,
  KEKE,
  LIZARD,
  ME,
  MONSTER,
  ROBOT,
  SNAIL,
  SKULL,
  TEETH,
  TURTLE,
  WORM;
  
  /**
   * Tell without throwing errors if a string is in the enum
   * 
   * @param elt
   * @return boolean
   */
  public static boolean contains(String elt) {
    try {
      SkinEnemy.valueOf(elt);
      return true;
    } catch (RuntimeException e) {
      return false;
    }
  }
}
