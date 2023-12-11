package fr.uge.display;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import fr.uge.gameElement.Panel;
import fr.uge.gameElement.entity.BaseEntity;
import fr.uge.gameElement.entity.Enemy;
import fr.uge.gameElement.entity.Player;
import fr.uge.gameElement.fieldElement.Decoration;
import fr.uge.gameElement.fieldElement.FieldElement;
import fr.uge.gameElement.fieldElement.Obstacle;
import fr.uge.gameParameter.GameParameter;
import fr.umlv.zen5.ApplicationContext;

public class AllDisplay {

  /**
   * Display the red part of the health bar
   * 
   * @param entity
   * @param graphics
   * @param parameters
   */
  private static void displayRedHealthBar(BaseEntity entity, Graphics2D graphics, GameParameter parameters) {
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
  private static void displayGreenHealthBar(BaseEntity entity, Graphics2D graphics, GameParameter parameters) {
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
  private static void displayHealthBar(BaseEntity entity, Graphics2D graphics, GameParameter parameters) {
    displayRedHealthBar(entity, graphics, parameters);
    displayGreenHealthBar(entity, graphics, parameters);
  }

  /**
   * Display all enemies on the field
   * 
   * @param list
   * @param images
   * @param graphics
   * @param parameters
   */
  private static void displayEnemy(List<Enemy> list, Map<String, BufferedImage> images, Graphics2D graphics,
      GameParameter parameters) {
    list.forEach(enemy -> {
      graphics.drawImage(images.get(enemy.getSkin().toString()), null,
          (int) (enemy.getX() * parameters.getTileSize() - parameters.getTileSize() / 2),
          (int) (enemy.getY() * parameters.getTileSize() - parameters.getTileSize() / 2));
      displayHealthBar(enemy.getBaseEntity(), graphics, parameters);
    });
  }

  /**
   * Display field obstacles and decorations
   * 
   * @param field
   * @param images
   * @param graphics
   * @param parameters
   */
  private static void displayField(FieldElement[][] field, Map<String, BufferedImage> images, Graphics2D graphics,
      GameParameter parameters) {
    for (int i = 0; i < field.length; i++) {
      for (int j = 0; j < field[i].length; j++) {
        if (field[i][j] != null) {
          switch (field[i][j]) {
          case Obstacle o -> {
            graphics.drawImage(images.get(o.getType().toString()), null, j * parameters.getTileSize(),
                i * parameters.getTileSize());
          }

          case Decoration d -> {
            graphics.drawImage(images.get(d.type().toString()), null, j * parameters.getTileSize(),
                i * parameters.getTileSize());
          }

          default -> throw new IllegalArgumentException("Unexpected value: " + field[i][j]);
          }
        }
      }
    }
  }

  /**
   * Display the player
   * 
   * @param p
   * @param images
   * @param graphics
   * @param parameters
   */
  private static void displayPlayer(Player p, Map<String, BufferedImage> images, Graphics2D graphics,
      GameParameter parameters) {
    graphics.drawImage(images.get(p.getSkin().toString()), null,
        (int) (p.getX() * parameters.getTileSize()) - parameters.getTileSize() / 2,
        (int) (p.getY() * parameters.getTileSize()) - parameters.getTileSize() / 2);
    displayHealthBar(p.getBaseEntity(), graphics, parameters);
  }

  /**
   * Display all elements in the game
   * 
   * @param pan
   * @param images
   * @param context
   * @param parameters
   */
  public static void allDisplay(Panel pan, Map<String, BufferedImage> images, ApplicationContext context,
      GameParameter parameters) {
    context.renderFrame(graphics -> {
      graphics.setColor(Color.DARK_GRAY);
      graphics.fill(new Rectangle2D.Float(0, 0, parameters.getWindowWidth(), parameters.getWindowHeight()));

      displayField(pan.getField(), images, graphics, parameters);
      displayPlayer(pan.player, images, graphics, parameters);
      displayEnemy(pan.getEnemies(), images, graphics, parameters);
    });
  }

  /**
   * Load all images in resources folder
   * 
   * @param parameters
   * @return
   * @throws IOException
   */
  public static Map<String, BufferedImage> loadImage(GameParameter parameters) throws IOException {
    return Files.walk(Path.of("ressources"), 10, FileVisitOption.FOLLOW_LINKS)
        .filter(f -> f.getFileName().toString().endsWith(".png")).collect(Collectors.toMap(s -> s.getFileName()
            .toString().substring(0, s.getFileName().toString().length() - 4).toUpperCase(Locale.ROOT), s -> {
              try {
                return ImageIO.read(s.toFile());
              } catch (IOException e) {
                System.err.println(e.getMessage());
              }
              return null;
            }));
  }
}
