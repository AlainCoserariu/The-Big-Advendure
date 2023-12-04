package fr.uge.display;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
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

import fr.uge.entity.enemy.Enemy;
import fr.uge.entity.player.Player;
import fr.uge.fieldElement.FieldElement;
import fr.uge.gameParameter.GameParameter;
import fr.uge.panel.Panel;
import fr.umlv.zen5.ApplicationContext;

public class AllDisplay {

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
          (int) (enemy.getX() * parameters.getTileSize()) - parameters.getTileSize() / 2,
          (int) (enemy.getY() * parameters.getTileSize()) - parameters.getTileSize() / 2);
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
    int i, j;
    for (i = 0; i < field.length; i++) {
      for (j = 0; j < field[i].length; j++) {
        if (field[i][j] != null) {
          graphics.drawImage(images.get(field[i][j].getType().toString()), null, j * parameters.getTileSize(),
              i * parameters.getTileSize());
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
  }
  
  /**
   * Display all elements in the game
   * 
   * @param pan
   * @param images
   * @param context
   * @param parameters
   */
  public static void allDisplay(Panel pan, Map<String, BufferedImage> images, ApplicationContext context, GameParameter parameters) {
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
                e.printStackTrace();
              }
              return null;
            }));
  }
}
