package fr.uge.display;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Map;

import fr.uge.GameParameter;
import fr.uge.UserEvent;
import fr.uge.gameEngine.Panel;

public class DisplayInventory {
  private static void displayUserInterface(GameParameter parameters, Graphics2D graphics) {
    int width = parameters.getTileSize() * 5 + 10;
    int height = parameters.getTileSize() * 2 + 10;
    int x = parameters.getWindowWidth() / 2 - width / 2;
    int y = parameters.getWindowHeight() / 2 - height / 2;

    graphics.setColor(Color.LIGHT_GRAY);
    graphics.fill(new Rectangle2D.Float(x, y, width, height));
    graphics.setColor(Color.GRAY);
    graphics.fill(new Rectangle2D.Float(x + 5, y + 5, width - 10, height - 10));
  }

  private static void displayItems(Panel panel, GameParameter parameters, Graphics2D graphics,
      Map<String, BufferedImage> images) {
    int width = parameters.getTileSize() * 5;
    int height = parameters.getTileSize() * 2;
    int x = parameters.getWindowWidth() / 2 - width / 2;
    int y = parameters.getWindowHeight() / 2 - height / 2;

    for (int i = 0; i < panel.getPlayer().getInventory().getSize(); i++) {
      if (i < 5) {
        graphics.drawImage(images.get(panel.getPlayer().getInventory().getItem(i).getSkin()), null,
            x + i * parameters.getTileSize(), y);
      } else {
        graphics.drawImage(images.get(panel.getPlayer().getInventory().getItem(i).getSkin()), null,
            x + (i - 5) * parameters.getTileSize(), y + parameters.getTileSize());
      }
    }
  }

  private static void displaySelectedItem(Panel panel, GameParameter parameters, Graphics2D graphics,
      UserEvent userEvent) {
    int width = parameters.getTileSize() * 5;
    int height = parameters.getTileSize() * 2;
    int x = parameters.getWindowWidth() / 2 - width / 2;
    int y = parameters.getWindowHeight() / 2 - height / 2;

    if (userEvent.getSelectedItem() < 5) {
      x += userEvent.getSelectedItem() * parameters.getTileSize();
    } else {
      x += (userEvent.getSelectedItem() - 5) * parameters.getTileSize();
      y += parameters.getTileSize();
    }
    
    graphics.setColor(Color.YELLOW);
    graphics.fill(new Rectangle2D.Float(x, y, parameters.getTileSize(), parameters.getTileSize()));
    graphics.setColor(Color.GRAY);
    graphics.fill(new Rectangle2D.Float(x + 2, y + 2, parameters.getTileSize() - 4, parameters.getTileSize() - 4));
  }

  static void displayInventory(Panel panel, GameParameter parameters, Graphics2D graphics,
      Map<String, BufferedImage> images, UserEvent userEvent) {
    if (userEvent.getPlayerAction() == 0) {
      return;
    }

    displayUserInterface(parameters, graphics);
    displaySelectedItem(panel, parameters, graphics, userEvent);
    displayItems(panel, parameters, graphics, images);
  }
}
