package fr.uge.gameEngine;

public class Interaction {
  private static void updateIframes(Panel panel) {
    panel.getPlayer().updateIframes();
    panel.getEnemies().forEach(e -> e.updateIframes());
  }
  
  public static void interact(Panel panel) {
    panel.getPlayer().collideWithEnemy(panel.getEnemies());
    
    updateIframes(panel);
    
    return;
  }
}
