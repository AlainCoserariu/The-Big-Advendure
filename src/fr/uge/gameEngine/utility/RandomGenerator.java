package fr.uge.gameEngine.utility;

import java.util.Random;

public class RandomGenerator {
  static public int randint(int min, int max) {
    if (max < min) {
      throw new IllegalArgumentException("max < min");
    }
    
    Random r = new Random();
    return r.nextInt((max - min) + 1) + min;
  }
}
