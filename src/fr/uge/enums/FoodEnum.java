package fr.uge.enums;

public enum FoodEnum {
  BANANA,
  BOBA,
  BOTTLE,
  BURGER,
  CAKE,
  CHEESE,
  DONUT,
  DRINK,
  EGG,
  FRUIT,
  FUNGUS,
  FUNGI,
  LOVE,
  PIZZA,
  POTATO,
  PUMPKIN,
  TURNIP,
  BUNNY,
  CRAB,
  FISH,
  FROG,
  SNAIL;
  
  /**
   * Tell without throwing errors if a string is in the enum
   * 
   * @param elt
   * @return boolean
   */
  public static boolean contains(String elt) {
    try {
      FoodEnum.valueOf(elt);
      return true;
    } catch (RuntimeException e) {
      return false;
    }
  }
}
