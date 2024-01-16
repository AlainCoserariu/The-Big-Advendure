package fr.uge.display;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import fr.uge.GameParameter;
import fr.uge.gameEngine.Panel;
import fr.umlv.zen5.ApplicationContext;

public class Display {
  
  /**
   * Center the panel in the middle of the screen if the total lenght or height is less than the maximal (80x44 tiles)
   * 
   * @param panel
   * @param graphics
   * @param parameters
   */
  private static void centerPanel(Panel panel, Graphics2D graphics, GameParameter parameters) {
    int xMargin = 0;
    int yMargin = 0;
    if (panel.getField()[0].length < 80) {
      xMargin = (parameters.getWindowWidth() - (panel.getField()[0].length * parameters.getTileSize())) / 2;
    }
    if (panel.getField().length < 44) {
      yMargin = (parameters.getWindowHeight() - (panel.getField().length * parameters.getTileSize())) / 2;
    }
    graphics.translate(xMargin, yMargin);
  }
  
  /**
   * Center the camera on the player if the map is too big to be fully displayed
   * 
   * @param panel
   * @param graphics
   * @param parameters
   */
  private static void centerPlayer(Panel panel, Graphics2D graphics, GameParameter parameters) {
    int xShift = 0;
    int yShift = 0;
    if (panel.getField()[0].length > 80) { // Check if the grid is too large and if the player is to far from a border
      if (panel.getPlayer().getX() > 80 / 2 && panel.getPlayer().getX() < panel.getField()[0].length - 80 / 2) {
        xShift = (int) (panel.getPlayer().getX() * parameters.getTileSize() - parameters.getWindowWidth() / 2);
      } else if (panel.getPlayer().getX() > panel.getField()[0].length - 80 / 2) {
        xShift = panel.getField()[0].length * parameters.getTileSize() - parameters.getWindowWidth();
      }
    }
    if (panel.getField().length > 44) {
      if (panel.getPlayer().getY() > 44 / 2 && panel.getPlayer().getY() < panel.getField().length - 44 / 2) {
        yShift = (int) (panel.getPlayer().getY() * parameters.getTileSize() - parameters.getWindowHeight() / 2);
      } else if (panel.getPlayer().getY() > panel.getField().length - 44 / 2) {
        yShift = panel.getField().length * parameters.getTileSize() - parameters.getWindowHeight();
      }      
    }
    graphics.translate(-xShift, -yShift);
  }
  
  private static void initGraphics(Panel panel, Graphics2D graphics, GameParameter parameters) {
    graphics.setFont(new Font("DIALOG", 0, 12));
    graphics.setColor(Color.BLACK);
    graphics.fill(new Rectangle2D.Float(0, 0, parameters.getWindowWidth(), parameters.getWindowHeight()));
    
    centerPanel(panel, graphics, parameters);
    centerPlayer(panel, graphics, parameters);
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
      AffineTransform tmpTransform = graphics.getTransform();
      initGraphics(pan, graphics, parameters);
      
      DisplayField.displayField(pan.getField(), images, graphics, parameters);
      DipslayEntity.displayEnemies(pan.getEnemies(), images, graphics, parameters.getTileSize());
      DipslayEntity.displayPlayer(pan.getPlayer(), images, graphics, parameters.getTileSize());
      
      graphics.setTransform(tmpTransform);
    });
  }

  /**
   * Load all images in resources folder
   * 
   * @param parameters
   * @return
   * @throws IOException
   */
  public static Map<String, BufferedImage> loadImage() throws IOException {
    return Map.copyOf(Files.walk(Path.of("ressources"), 10, FileVisitOption.FOLLOW_LINKS)
        .filter(f -> f.getFileName().toString().endsWith(".png")) // Get all png files
        .collect(Collectors.toMap(s -> s.getFileName() // KeyMapper, transform path into game name element (i.e.
                                                       // ressources/assets/obstacle/wall become WALL)
            .toString()
            .substring(0, s.getFileName().toString().length() - ".png".length())
            .toUpperCase(Locale.ROOT), s -> { // ValueMapper
              try {
                return ImageIO.read(s.toFile());
              } catch (IOException e) {
                throw new RuntimeException(e);
              }
            })));
  }
}
