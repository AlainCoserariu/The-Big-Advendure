package fr.uge.utils;

public class Vector2D{
  private double vx;
  private double vy;
  
  public Vector2D(double vx, double vy) {
    this.vx = vx;
    this.vy = vy;
  }

  public void add(Vector2D other) {
    this.vx += other.vx;
    this.vy += other.vy;
  }
  
  public void scale(double scalar) {
    vx *= scalar;
    vy *= scalar;
  }

  public double getVx() {
    return vx;
  }

  public void setVx(double vx) {
    this.vx = vx;
  }

  public double getVy() {
    return vy;
  }

  public void setVy(double vy) {
    this.vy = vy;
  } 
}
