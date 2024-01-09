package fr.uge.display;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import fr.uge.gameEngine.entity.Enemy;
import fr.uge.gameEngine.entity.EntityStats;
import fr.uge.gameEngine.entity.Player;

class DipslayEntity {
  /**
   * Display the red part of the health bar
   * 
   * @param entity
   * @param graphics
   * @param parameters
   */
  private static void displayRedHealthBar(Graphics2D graphics, int x, int y, int width, int height) {
    var healthBar = new Rectangle(x, y, width, height);

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
  private static void displayGreenHealthBar(Graphics2D graphics, int x, int y, int width, int height) {
    var healthBar = new Rectangle(x, y, width, height);

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
  private static void displayHealthBar(EntityStats entity, Graphics2D graphics, int tileSize) {
    int maxWidth = tileSize;
    int heightHealthBar = (int) (tileSize * 0.2);

    // Removing lost health
    int widthGreenBar = entity.getHealth() * maxWidth / entity.getMaxHealth();

    // Top left position of the health bar rectangle
    int x = (int) ((entity.getX() * tileSize) - maxWidth / 2);
    int y = (int) ((entity.getY() * tileSize) - tileSize / 2 - heightHealthBar - 3);
    
    displayRedHealthBar(graphics, x, y, maxWidth, heightHealthBar);
    displayGreenHealthBar(graphics, x, y, widthGreenBar, heightHealthBar);
  }

  /**
   * Display the player with his name and his health bar
   * 
   * @param p
   * @param images
   * @param graphics
   * @param parameters
   */
  public static void displayPlayer(Player p, BufferedImage playerImage, Graphics2D graphics, int tileSize) {
    int xPosScreen = (int) (p.getX() * tileSize);
    int yPosScreen = (int) (p.getY() * tileSize);

    graphics.drawImage(playerImage, null, xPosScreen - tileSize / 2, yPosScreen - tileSize / 2);
    graphics.setColor(Color.white);
    graphics.drawString(p.getName(), xPosScreen - (graphics.getFontMetrics().stringWidth(p.getName())) / 2,
        yPosScreen + tileSize / 2 + 10);      
    displayHealthBar(p.getBaseEntity(), graphics, tileSize);
  }

  /**
   * Display an enemy with it name and health bar
   * 
   * @param enemy
   * @param enemyImage
   * @param graphics
   * @param tileSize
   */
  private static void displayEnemy(Enemy enemy, BufferedImage enemyImage, Graphics2D graphics, int tileSize) {
    int xPosScreen = (int) (enemy.getX() * tileSize);
    int yPosScreen = (int) (enemy.getY() * tileSize);

    graphics.drawImage(enemyImage, null, xPosScreen - tileSize / 2, yPosScreen - tileSize / 2);
    graphics.setColor(Color.red);
    graphics.drawString(enemy.getName(), xPosScreen - (graphics.getFontMetrics().stringWidth(enemy.getName())) / 2,
        yPosScreen + tileSize / 2 + 10);
    displayHealthBar(enemy.getBaseEntity(), graphics, tileSize);
  }

  /**
   * Display all enemies on the field with their name and health bar
   * 
   * @param list
   * @param images
   * @param graphics
   * @param parameters
   */
  static void displayEnemies(List<Enemy> list, Map<String, BufferedImage> images, Graphics2D graphics, int tileSize) {
    list.forEach(enemy -> {
      displayEnemy(enemy, images.get(enemy.getSkin().toString()), graphics, tileSize);
    });
  }
}
