package fr.uge.enums;

import java.util.EnumSet;
import java.util.stream.Collectors;

public enum DecorationEnum {
  ALGAE,
  CLOUD,
  FLOWER,
  FOLIAGE,
  GRASS,
  LADDER,
  LILY, 
  PLANK, 
  REED, 
  ROAD, 
  SPROUT, 
  TILE, 
  TRACK, 
  VINE;
  
  public static boolean contains(String elt) {
    var set = EnumSet.allOf(DecorationEnum.class).stream().map(t -> t.toString()).collect(Collectors.toSet());

    return set.contains(elt) ? true : false;
  }
}