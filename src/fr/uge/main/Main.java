package fr.uge.main;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import fr.uge.display.AllDisplay;
import fr.uge.fieldElement.FieldElement;
import fr.uge.gameParameter.GameParameter;
import fr.uge.interaction.Interact;
import fr.uge.panel.Panel;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;

public class Main {
  public static void main(String[] args) throws IOException{
  	Panel panel = new Panel(Path.of("maps").resolve("fun.map"));
  	
  	Application.run(Color.DARK_GRAY, context -> {
      // get the size of the screen
      ScreenInfo screenInfo = context.getScreenInfo();

      // Initialize game data
      GameParameter gameParameters = new GameParameter((int) screenInfo.getWidth(), (int) screenInfo.getHeight(), 60);
      
      // Load images
      Map<String, BufferedImage> images;
      try {
        images = AllDisplay.loadImage(gameParameters);
      } catch (IOException e) {
        System.err.println(e.getMessage());
        context.exit(1);
        System.exit(1);
        return;
      }
      System.out.println("size of the screen (" + gameParameters.getWindowWidth() + " x " + gameParameters.getWindowHeight() + ")");

      for (int frame = 0; true; frame++) {
        // Get time
        var time = System.nanoTime();
        
        // Display the game
				AllDisplay.allDisplay(panel, images, context, gameParameters);
      	
      	System.out.println("Frame : " + frame);
     
        // Event handler
        Event event = context.pollEvent();
        if (event != null) { // Key detection
          Action action = event.getAction();
          if (action == Action.KEY_PRESSED) {
            KeyboardKey key = event.getKey();
            double beforeX = 0;
            double beforeY = 0;
            switch (key) {
            case UP -> {
              panel.player.move(0, -panel.player.getSpeed() / gameParameters.getFramerate());
              beforeY = panel.player.getSpeed() / gameParameters.getFramerate();
            }
            case DOWN -> {
              panel.player.move(0, panel.player.getSpeed() / gameParameters.getFramerate());
              beforeY = -panel.player.getSpeed() / gameParameters.getFramerate();
            }
            case RIGHT -> {
              panel.player.move(panel.player.getSpeed() / gameParameters.getFramerate(), 0);
              beforeX = -panel.player.getSpeed() / gameParameters.getFramerate();
            }
            case LEFT -> {
              panel.player.move(-panel.player.getSpeed() / gameParameters.getFramerate(), 0);
              beforeX = panel.player.getSpeed() / gameParameters.getFramerate();
            }
            case SPACE -> System.out.println("Ca doit faire une action");
            case I -> System.out.println("inventaire");
            default -> {
              context.exit(0);
              return;
            }
            }
            Interact.collisionAfterNextMove(beforeX, beforeY, panel);
          }
        }
        
        // Time sleeper, wait  1/60 seconds - time since the start of the frame
        try {
          TimeUnit.MILLISECONDS.sleep((long) (1000 / gameParameters.getFramerate() - (System.nanoTime() - time) * 0.000001));
        } catch (InterruptedException e) {}
        panel.getEnemies().forEach(enemy -> {enemy.randomMove(panel.getField(), gameParameters);});    
       
        
      }
    });
    System.out.println("This is the end");
  }
}
