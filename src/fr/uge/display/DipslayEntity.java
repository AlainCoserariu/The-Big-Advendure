package fr.uge.display;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import fr.uge.GameParameter;
import fr.uge.gameElement.entity.EntityStats;
import fr.uge.gameElement.entity.Enemy;
import fr.uge.gameElement.entity.Player;

class DipslayEntity {
  /**
   * Display the red part of the health bar
   * 
   * @param entity
   * @param graphics
   * @param parameters
   */
  private static void displayRedHealthBar(EntityStats entity, Graphics2D graphics, GameParameter parameters) {
    int widthHealthBar = (int) (parameters.getTileSize() * 0.9);
    int heightHealthBar = (int) (parameters.getTileSize() * 0.2);

    // Top left position of the health bar rectangle
    int posX = (int) ((entity.getX() * parameters.getTileSize()) - widthHealthBar / 2);
    int posY = (int) ((entity.getY() * parameters.getTileSize()) - parameters.getTileSize() / 2);

    var healthBar = new Rectangle(posX, posY, widthHealthBar, heightHealthBar);

    graphics.setColor(Color.RED);
    graphics.fill(healthBar);
  }

  /**
   * Display the green part of the health bar
   * 
   * @param entity
   * @param graphics
   * @param parameters
   */
  private static void displayGreenHealthBar(EntityStats entity, Graphics2D graphics, GameParameter parameters) {
    int widthHealthBar = (int) (parameters.getTileSize() * 0.9);
    int heightHealthBar = (int) (parameters.getTileSize() * 0.2);

    // Removing lost health
    widthHealthBar = entity.getHealth() * widthHealthBar / entity.getMaxHealth();

    // Top left position of the health bar rectangle
    int posX = (int) ((entity.getX() * parameters.getTileSize()) - widthHealthBar / 2);
    int posY = (int) ((entity.getY() * parameters.getTileSize()) - parameters.getTileSize() / 2);

    var healthBar = new Rectangle(posX, posY, widthHealthBar, heightHealthBar);

    graphics.setColor(Color.GREEN);
    graphics.fill(healthBar);
  }

  /**
   * Display the health bar for an entity with two rectangle, one red representing
   * lost health and one green representing remaining health
   * 
   * @param entity
   * @param graphics
   * @param parameters
   */
  private static void displayHealthBar(EntityStats entity, Graphics2D graphics, GameParameter parameters) {
    displayRedHealthBar(entity, graphics, parameters);
    displayGreenHealthBar(entity, graphics, parameters);
  }
  
  /**
   * Display the player
   * 
   * @param p
   * @param images
   * @param graphics
   * @param parameters
   */
  public static void displayPlayer(Player p, Map<String, BufferedImage> images, Graphics2D graphics,
      GameParameter parameters) {
    graphics.drawImage(images.get(p.getSkin().toString()), null,
        (int) (p.getX() * parameters.getTileSize()) - parameters.getTileSize() / 2,
        (int) (p.getY() * parameters.getTileSize()) - parameters.getTileSize() / 2);
    displayHealthBar(p.getBaseEntity(), graphics, parameters);
  }
  
  /**
   * Display all enemies on the field
   * 
   * @param list
   * @param images
   * @param graphics
   * @param parameters
   */
  static void displayEnemy(List<Enemy> list, Map<String, BufferedImage> images, Graphics2D graphics,
      GameParameter parameters) {
    list.forEach(enemy -> {
      graphics.drawImage(images.get(enemy.getSkin().toString()), null,
          (int) (enemy.getX() * parameters.getTileSize() - parameters.getTileSize() / 2),
          (int) (enemy.getY() * parameters.getTileSize() - parameters.getTileSize() / 2));
      displayHealthBar(enemy.getBaseEntity(), graphics, parameters);
    });
  }
}
