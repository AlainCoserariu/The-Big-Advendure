package fr.uge.utils.hitbox;

public sealed interface Hitbox permits SquareHitbox {
  public double getCenterX();
  public void setCenterX(double centerX);

  public double getCenterY();
  public void setCenterY(double centerY);

  public double getRadius();
  
  boolean collide(Hitbox hitbox);
}
