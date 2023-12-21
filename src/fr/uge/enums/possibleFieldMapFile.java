package fr.uge.enums;

import java.util.EnumSet;
import java.util.stream.Collectors;

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
  
  public static boolean contains(String elt) {
    var set = EnumSet.allOf(possibleFieldMapFile.class).stream().map(t -> t.toString()).collect(Collectors.toSet());

    return set.contains(elt) ? true : false;
  }
}
