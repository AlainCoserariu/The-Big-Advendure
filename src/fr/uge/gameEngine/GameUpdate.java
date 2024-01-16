package fr.uge.gameEngine;

import fr.uge.GameParameter;

public class GameUpdate {
  private static void updateEnemies(Panel panel, int framerate) {
    panel.getEnemies().forEach(e -> {
      e.moveEnemy(panel, framerate);
    });
    
    panel.getEnemies().removeIf(e -> e.getHealth() <= 0);
  }

  private static void updateIframes(Panel panel) {
    panel.getPlayer().updateIframes();
    panel.getEnemies().forEach(e -> e.updateIframes());
  }

  public static void update(Panel panel, GameParameter parameters) {
    panel.getPlayer().collideWithEnemy(panel.getEnemies());
    panel.getPlayer().checkHitEnemies(panel.getEnemies());

    updateEnemies(panel, parameters.getFramerate());

    updateIframes(panel);

    return;
  }
}
