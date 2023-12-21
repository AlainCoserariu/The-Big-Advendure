package fr.uge.enums;

import java.util.EnumSet;
import java.util.stream.Collectors;

public enum ObstacleEnum {
  BED,  
  BOG,  
  BOMB,  
  BRICK,  
  CHAIR,  
  CLIFF,  
  DOOR,  
  FENCE,  
  FORT,  
  GATE,  
  HEDGE,  
  HOUSE,  
  HUSK,  
  HUSKS,  
  LOCK,  
  MONITOR,  
  PIANO,  
  PILLAR,  
  PIPE,  
  ROCK,  
  RUBBLE,  
  SHELL,  
  SIGN,  
  SPIKE,  
  STATUE,  
  STUMP,  
  TABLE,  
  TOWER,  
  TREE,  
  TREES,  
  WALL;
  
  public static boolean contains(String elt) {
    var set = EnumSet.allOf(ObstacleEnum.class).stream().map(t -> t.toString()).collect(Collectors.toSet());

    return set.contains(elt) ? true : false;
  }
}