package fr.uge.gameEngine.utility;

public record MovementZone(double leftX, double rightX, double topY, double bottomY) {
  public boolean isInZone(double x, double y) {
    return (leftX < x && x < rightX && topY < y && y < bottomY);
  }
}
