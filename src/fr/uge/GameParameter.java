package fr.uge;

/**
 * Represents all the game parameters
 */
public class GameParameter {
  private final int windowWidth;  // In pixel
  private final int windowHeight;
  
  private final int framerate;
  
  private final int tileSize;  // In pixel

  /**
   * Initialize all game parameters
   * 
   * @param windowWidth
   * @param windowHeight
   * @param framerate
   */
  public GameParameter(int windowWidth, int windowHeight, int framerate) {
    super();
    this.windowWidth = windowWidth;
    this.windowHeight = windowHeight;
    this.framerate = framerate;
    
    int maxTileWidth = 80;
    tileSize = windowWidth / maxTileWidth;
  }

  public int getWindowWidth() {
    return windowWidth;
  }

  public int getWindowHeight() {
    return windowHeight;
  }

  public int getFramerate() {
    return framerate;
  }

  public int getTileSize() {
    return tileSize;
  }
  
}
