package fr.uge.gameEngine;

import fr.uge.GameParameter;
import fr.uge.Options;

public class GameUpdate {
  private static void updateEnemies(Panel panel, int framerate, Options options) {
    if (!options.dryRun()) {
      panel.getEnemies().forEach(e -> {
        e.moveEnemy(panel, framerate);
      });      
    }
    
    panel.getEnemies().removeIf(e -> e.getHealth() <= 0);
  }

  private static void updateIframes(Panel panel) {
    panel.getPlayer().updateIframes();
    panel.getEnemies().forEach(e -> e.updateIframes());
  }

  public static void update(Panel panel, GameParameter parameters, Options options) {
    panel.getPlayer().collideWithEnemy(panel.getEnemies());
    panel.getPlayer().collideWithItem(panel.getItems());
    if (panel.getPlayer().isAttacking()) {
      panel.getPlayer().getWeapon().checkHitEnemies(panel.getEnemies());
    }

    updateEnemies(panel, parameters.getFramerate(), options);

    updateIframes(panel);

    return;
  }
}
