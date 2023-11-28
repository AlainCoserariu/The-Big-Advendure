package fr.uge.utils;

public record Vector2D(double vx, double vy) {  
  public Vector2D add(Vector2D other) {
    return new Vector2D(vx + other.vx, vy + other.vy);
  }
  
  public Vector2D scale(double scalar) {
    return new Vector2D(vx * scalar, vy * scalar);
  }
}
