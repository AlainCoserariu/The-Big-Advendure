package fr.uge;

import java.util.HashMap;
import java.util.Map;

import fr.uge.gameEngine.Panel;
import fr.uge.gameEngine.entity.Player;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;

/**
 * Keep the track of user interaction with the keyboard and interact with the
 * game
 */
public class UserEvent {
  private final Map<KeyboardKey, Boolean> keyPressed;

  public UserEvent() {
    keyPressed = new HashMap<KeyboardKey, Boolean>();

    for (KeyboardKey key : KeyboardKey.values()) {
      keyPressed.put(key, false);
    }
  }

  /**
   * Update keys
   * 
   * @param context
   */
  private void getEvent(ApplicationContext context) {
    Event event = context.pollEvent();
    if (event != null) { // Key detection
      Action action = event.getAction();
      if (action == Action.KEY_PRESSED) {
        KeyboardKey key = event.getKey();
        keyPressed.put(key, true);
      } else if (action == Action.KEY_RELEASED) {
        KeyboardKey key = event.getKey();
        keyPressed.put(key, false);
      }
    }
  }
  
  /**
   * Move the player in a given direction for legal movement (not colliding into a wall) 
   * 
   * @param panel
   * @param direction
   * @param deltaX
   * @param deltaY
   */
  private boolean movePlayer(Panel panel, KeyboardKey direction, double deltaX, double deltaY) {
    if (keyPressed.get(direction)) {
      panel.getPlayer().move(panel.getPlayer().getX() + deltaX, panel.getPlayer().getY() + deltaY);
      if (panel.getPlayer().collideWithObstacle(panel.getField()) || panel.getPlayer().collideWithEnemy(panel.getEnemies())) {
        panel.getPlayer().move(panel.getPlayer().getX() - deltaX, panel.getPlayer().getY() - deltaY);
      }
      return true;
    }
    return false;
  }
  
  /**
   * Check all movement key to move the player
   * 
   * @param panel
   * @param parameters
   */
  private void handlePlayerMovement(Panel panel, GameParameter parameters) {
    double step = panel.getPlayer().getSpeed() / parameters.getFramerate();
    
    if (movePlayer(panel, KeyboardKey.UP, 0, -step)) panel.getPlayer().setDirection(0);
    if (movePlayer(panel, KeyboardKey.LEFT, -step, 0)) panel.getPlayer().setDirection(1);
    if (movePlayer(panel, KeyboardKey.DOWN, 0, step)) panel.getPlayer().setDirection(2);
    if (movePlayer(panel, KeyboardKey.RIGHT, step, 0)) panel.getPlayer().setDirection(3);
    
    if (panel.getPlayer().isAttacking()) {
      panel.getPlayer().updateWeaponPos();
    }
  }
  
  private void handleSpaceBarAction(Panel panel, GameParameter parameters) {
    panel.getPlayer().attack();
  }
  
  public void handleEvent(Panel panel, GameParameter parameters, ApplicationContext context) {
    getEvent(context);
    
    handlePlayerMovement(panel, parameters);
    
    if (keyPressed.get(KeyboardKey.SPACE)) {
      handleSpaceBarAction(panel, parameters);      
    }
    
    if (keyPressed.get(KeyboardKey.Q)) {
      context.exit(0);
    }
  }
  
}
