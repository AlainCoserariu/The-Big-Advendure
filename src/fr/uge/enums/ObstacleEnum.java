package fr.uge.enums;

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
  WALL,
  // TEMPORAR
  LAVA,
  WATER,
  ICE;
  
  /**
   * Tell without throwing errors if a string is in the enum
   * 
   * @param elt
   * @return boolean
   */
  public static boolean contains(String elt) {
    try {
      ObstacleEnum.valueOf(elt);
      return true;
    } catch (RuntimeException e) {
      return false;
    }
  }
}