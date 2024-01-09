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
  
  public static boolean contains(String elt) {
    try {
      SkinEnemy.valueOf(elt);
      return true;
    } catch (RuntimeException e) {
      return false;
    }
  }
}
