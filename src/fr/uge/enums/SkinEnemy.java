package fr.uge.enums;

import java.util.EnumSet;
import java.util.stream.Collectors;

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
    var set = EnumSet.allOf(SkinEnemy.class).stream().map(t -> t.toString()).collect(Collectors.toSet());

    return set.contains(elt) ? true : false;
  }
}
