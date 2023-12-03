package fr.uge.main;


import java.awt.Color;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import fr.uge.display.AllDisplay;
import fr.uge.panel.Panel;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;

public class Main {
  public static void main(String[] args) throws IOException{
  	Panel panel = new Panel(Path.of("maps").resolve("fun.map"));
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
        
        // Display the game
      	try {
					AllDisplay.allDisplay(panel, images, context, width, height);
				} catch (IOException e) {
					e.printStackTrace();
				}
      	
      	System.out.println("Frame : " + frame);
     
        // Event handler
        Event event = context.pollEvent();
        if (event != null) { // Key detection
          Action action = event.getAction();
          if (action == Action.KEY_PRESSED) {
            KeyboardKey key = event.getKey();
            switch (key) {
            case UP -> {
              panel.player.move(0, -panel.player.getSpeed() * tileSize / framerate);
            }
            case DOWN -> {
              panel.player.move(0, panel.player.getSpeed() * tileSize / framerate);
            }
            case RIGHT -> {
              panel.player.move(panel.player.getSpeed() * tileSize / framerate, 0);
            }
            case LEFT -> {
              panel.player.move(-panel.player.getSpeed() * tileSize / framerate, 0);
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
        
        System.out.println(panel.player.getX());
        
        // Time sleeper
        try {
          TimeUnit.MILLISECONDS.sleep(1000 / framerate);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    });
    System.out.println("This is the end");
  }
}
