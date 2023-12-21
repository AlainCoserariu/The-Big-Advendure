package fr.uge.display;

import java.awt.Color;
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
import fr.uge.gameElement.Panel;
import fr.umlv.zen5.ApplicationContext;

public class Display {
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

      DisplayField.displayField(pan.getField(), images, graphics, parameters);
      DipslayEntity.displayPlayer(pan.getPlayer(), images, graphics, parameters);
      DipslayEntity.displayEnemy(pan.getEnemies(), images, graphics, parameters);
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
