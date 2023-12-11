package fr.uge.gameElement.utility.hitboxe;

public class Hitbox {
  private double leftX;
  private double rightX;
  private double topY;
  private double bottomY;
  
  private final double size;
  
  /**
   * Set a Hitbox
   * @param x center x of the hitbox
   * @param y center y of the hitbox
   * @param size Segment's lenght of the hitbox
   */
  public Hitbox(double x, double y, double size) {
    this.size = size;
    
    this.leftX = x - (size / 2);
    this.rightX = x + (size / 2);
    
    this.topY = y - (size / 2);
    this.bottomY = y + (size / 2);
  }
  
  public void updateHitbox(double x, double y) {
    leftX = x - (size / 2);
    rightX = x + (size / 2);
    
    topY = y - (size / 2);
    bottomY = y + (size / 2);
  }
  
  public boolean collide(Hitbox other) {
    boolean isOverlapingX = (leftX < other.rightX) && (rightX > other.leftX);
    boolean isOverlapingY = (topY < other.bottomY) && (bottomY > other.topY);
    
    return (isOverlapingX && isOverlapingY);
  }
  
}
