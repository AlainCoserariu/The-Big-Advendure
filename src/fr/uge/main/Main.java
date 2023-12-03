package fr.uge.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.TimeUnit;

import fr.uge.entity.player.Player;
import fr.uge.entity.player.SkinPlayer;
import fr.uge.fieldElement.obstacle.Obstacle;
import fr.uge.fieldElement.obstacle.ObstacleEnum;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;

public class Main {
  public static void main(String[] args) {
    Application.run(Color.DARK_GRAY, context -> {
      // Games options
      int framerate = 60;
      int tileSize = 24;

      // get the size of the screen
      ScreenInfo screenInfo = context.getScreenInfo();
      float width = screenInfo.getWidth();
      float height = screenInfo.getHeight();

      var player = new Player(0.5, 0.5, 10, 100, "baba", SkinPlayer.BABA);
      var obstacle = new Obstacle(1.5, 1.5, ObstacleEnum.BRICK);

      System.out.println("size of the screen (" + width + " x " + height + ")");

      context.renderFrame(graphics -> {
        graphics.setColor(Color.DARK_GRAY);
        graphics.fill(new Rectangle2D.Float(0, 0, width, height));
        
        graphics.setColor(Color.BLUE);
        graphics.fill(new Rectangle2D.Double(player.getX() * tileSize - tileSize / 2, player.getY() * tileSize - tileSize / 2, tileSize, tileSize));
        
        graphics.setColor(Color.RED);
        graphics.fill(new Rectangle2D.Double(obstacle.getX() * tileSize - tileSize / 2, obstacle.getY() * tileSize - tileSize / 2, tileSize, tileSize));
      });

      for (int frame = 0; true; frame++) {
        
        // Event handler
        Event event = context.pollEvent();
        if (event != null) { // Key detection

          Action action = event.getAction();
          if (action == Action.KEY_PRESSED) {
            KeyboardKey key = event.getKey();
            switch (key) {
            case UP -> {
              player.move(0, -player.getSpeed() / framerate);
            }
            case DOWN -> {
              player.move(0, player.getSpeed() / framerate);
            }
            case RIGHT -> {
              player.move(player.getSpeed() / framerate, 0);
            }
            case LEFT -> {
              player.move(-player.getSpeed() / framerate, 0);
            }
            case SPACE -> System.out.println("Ca doit faire une action");
            case I -> System.out.println("inventaire");
            default -> {
              context.exit(0);
              return;
            }
            }
          }
        }
        
        // Time sleeper
        try {
          TimeUnit.MILLISECONDS.sleep(1000 / framerate);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

        // Frame display
        context.renderFrame(graphics -> {
          graphics.setColor(Color.DARK_GRAY);
          graphics.fill(new Rectangle2D.Float(0, 0, width, height));
          
          graphics.setColor(Color.BLUE);
          graphics.fill(new Rectangle2D.Double(player.getX() * tileSize - tileSize / 2, player.getY() * tileSize - tileSize / 2, tileSize, tileSize));
          
          graphics.setColor(Color.RED);
          graphics.fill(new Rectangle2D.Double(obstacle.getX() * tileSize - tileSize / 2, obstacle.getY() * tileSize - tileSize / 2, tileSize, tileSize));
        });
      }
    });

    System.out.println("This is the end");
  }
}
