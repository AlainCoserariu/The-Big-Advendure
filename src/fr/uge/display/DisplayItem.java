package fr.uge.display;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import fr.uge.gameEngine.item.Item;

public class DisplayItem {
  private static void displayItem(Item item, BufferedImage image, Graphics2D graphics, int tileSize) {
    int xPosScreen = (int) (item.getX() * tileSize);
    int yPosScreen = (int) (item.getY() * tileSize);

    graphics.drawImage(image, null, xPosScreen - tileSize / 2, yPosScreen - tileSize / 2);
    graphics.setColor(Color.yellow);
    graphics.drawString(item.getName(), xPosScreen - (graphics.getFontMetrics().stringWidth(item.getName())) / 2,
        yPosScreen + tileSize / 2 + 10);
  }
  
  static void displayItems(List<Item> items, Map<String, BufferedImage> images, Graphics2D graphics, int tileSize) {
    items.forEach(item -> {
      displayItem(item, images.get(item.getSkin().toString()), graphics, tileSize);
    });
  }
}
