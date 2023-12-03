package fr.uge.main;


import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import fr.uge.display.AllDisplay;
import fr.uge.entity.player.Player;
import fr.uge.entity.player.SkinPlayer;
import fr.uge.fieldElement.obstacle.Obstacle;
import fr.uge.fieldElement.obstacle.ObstacleEnum;
import fr.uge.panel.Panel;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;



public class Main {
  public static void main(String[] args) throws IOException{
  	Panel panel = new Panel(Path.of("maps").resolve("fun.map"));
  	var player = panel.player;
  	var images = AllDisplay.loadImage();
    Application.run(Color.DARK_GRAY, context -> {
      // Games options
      int framerate = 60;
      int tileSize = 24;

      // get the size of the screen
      ScreenInfo screenInfo = context.getScreenInfo();
      float width = screenInfo.getWidth();
      float height = screenInfo.getHeight();


      System.out.println("size of the screen (" + width + " x " + height + ")");

      for (int frame = 0; true; frame++) {
      	try {
					AllDisplay.allDisplay(panel, images, context);
				} catch (IOException e) {
					e.printStackTrace();
				}
     
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
      }
    });
    System.out.println("This is the end");
  }
}
