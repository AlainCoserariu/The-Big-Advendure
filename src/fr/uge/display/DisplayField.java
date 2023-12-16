package fr.uge.display;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;

import fr.uge.GameParameter;
import fr.uge.gameElement.fieldElement.Decoration;
import fr.uge.gameElement.fieldElement.FieldElement;
import fr.uge.gameElement.fieldElement.Obstacle;

class DisplayField {
  /**
   * Display field's elements (obstacles, biome elements, decorations...)
   * 
   * @param field
   * @param images
   * @param graphics
   * @param parameters
   */
  static void displayField(FieldElement[][] field, Map<String, BufferedImage> images, Graphics2D graphics,
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
}
