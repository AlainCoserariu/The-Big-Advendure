package fr.uge.utils.hitbox;

public final class SquareHitbox implements Hitbox {
  private double centerX;
  private double centerY;
  private double radius; // Shortest distance between the center and a segment

  public SquareHitbox(double centerX, double centerY, double radius) {
    if (radius <= 0) {
      throw new IllegalArgumentException("Radius have to be > 0");
    }

    this.centerX = centerX;
    this.centerY = centerY;
    this.radius = radius;
  }

  @Override
  public boolean collide(Hitbox hitbox) {
    double x1 = centerX - radius;
    double y1 = centerY - radius;
    double lengh = 2 * radius;
    
    double x2 = hitbox.getCenterX() - hitbox.getRadius();
    double y2 = hitbox.getCenterY() - hitbox.getRadius();
    double lengh2 = 2 * hitbox.getRadius();
    
    boolean isOverlapingX = (x1 < x2 + lengh2) && (x1 + lengh > x2);
    boolean isOverlapingY = (y1 < y2 + lengh2) && (y1 + lengh > y2);
    
    return (isOverlapingX && isOverlapingY);
  }

  public double getCenterX() {
    return centerX;
  }

  public void setCenterX(double centerX) {
    this.centerX = centerX;
  }

  public double getCenterY() {
    return centerY;
  }

  public void setCenterY(double centerY) {
    this.centerY = centerY;
  }

  public double getRadius() {
    return radius;
  }

  public void setRadius(double radius) {
    this.radius = radius;
  }
}
