package fr.uge.enums;

import java.util.EnumSet;
import java.util.stream.Collectors;

public enum Behavior {
  shy,
  stroll,
  agressive;

  public static boolean contains(String elt) {
    var set = EnumSet.allOf(Behavior.class).stream().map(t -> t.toString()).collect(Collectors.toSet());

    return set.contains(elt) ? true : false;
  }
}
