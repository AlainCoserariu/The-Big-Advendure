package fr.uge.gameEngine;

import fr.uge.GameParameter;

public class GameUpdate {
  private static void updateEnemies(Panel panel, int framerate) {
    panel.getEnemies().forEach(e -> {
      e.moveEnemy(panel, framerate);
    });
  }

  private static void updateIframes(Panel panel) {
    panel.getPlayer().updateIframes();
    panel.getEnemies().forEach(e -> e.updateIframes());
  }

  public static void update(Panel panel, GameParameter parameters) {
    panel.getPlayer().collideWithEnemy(panel.getEnemies());

    updateEnemies(panel, parameters.getFramerate());

    updateIframes(panel);

    return;
  }
}
