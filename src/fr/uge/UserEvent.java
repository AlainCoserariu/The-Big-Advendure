package fr.uge;

import java.util.HashMap;
import java.util.Map;

import fr.uge.gameEngine.Panel;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;

/**
 * Keep the track of user interaction with the keyboard and interact with the
 * game
 */
public class UserEvent {
  private int playerAction; // 0 : in game, 1 : in inventory
  private int selectedItem; // Selected item in inventory
  
  private boolean flagPressed; // Tell if a key was pressed last frame
  
  private final Map<KeyboardKey, Boolean> keyPressed;

  
  public UserEvent() {
    keyPressed = new HashMap<KeyboardKey, Boolean>();

    for (KeyboardKey key : KeyboardKey.values()) {
      keyPressed.put(key, false);
    }
    
    playerAction = 0;
    selectedItem = 0;
    flagPressed = false;
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
        flagPressed = false;
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
  
  private void handleSpaceBarAction(Panel panel) {
    if (keyPressed.get(KeyboardKey.SPACE) && !flagPressed) {
      panel.getPlayer().action();
      flagPressed = true;
    }
  }
  
  private void handleInventoryButton(Panel panel) {
    if (keyPressed.get(KeyboardKey.I)) {
      playerAction = 1;
    }
  }
  
  private void navigateInventory() {
    if (keyPressed.get(KeyboardKey.LEFT) && !flagPressed) {
      selectedItem -= 1; flagPressed = true;
    }
    else if (keyPressed.get(KeyboardKey.RIGHT) && !flagPressed) {
      selectedItem += 1; flagPressed = true;
    }
    else if (keyPressed.get(KeyboardKey.DOWN) && !flagPressed) {
      selectedItem += 5; flagPressed = true;
    }
    else if (keyPressed.get(KeyboardKey.UP) && !flagPressed) {
      selectedItem -= 5; flagPressed = true;
    }
    
    selectedItem = (selectedItem < 0) ? 10 + selectedItem : selectedItem;
    selectedItem %= 10;
  }
  
  public void handleEvent(Panel panel, GameParameter parameters, ApplicationContext context) {
    getEvent(context);
    
    if (playerAction == 0) {
      handlePlayerMovement(panel, parameters);
      handleSpaceBarAction(panel);
      handleInventoryButton(panel);
    } else if (playerAction == 1) {
      navigateInventory();
      if (keyPressed.get(KeyboardKey.SPACE) && !flagPressed) {
        if (selectedItem < panel.getPlayer().getInventory().getSize()) panel.getPlayer().getInventory().swapItems(0, selectedItem);
        playerAction = 0;
        flagPressed = true;
      }
    }
    
    if (keyPressed.get(KeyboardKey.Q)) {
      context.exit(0);
    }
  }
  
  public int getPlayerAction() {
    return playerAction;
  }
  
  public int getSelectedItem() {
    return selectedItem;
  }
}
