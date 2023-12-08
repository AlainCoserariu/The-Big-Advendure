package fr.uge.main;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import fr.uge.display.AllDisplay;
import fr.uge.entity.player.SkinPlayer;
import fr.uge.gameParameter.GameParameter;
import fr.uge.panel.Panel;
import fr.uge.userEvent.UserEvent;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ScreenInfo;

public class Main {
  public static void main(String[] args) throws IOException{
  	Panel panel = new Panel(Path.of("maps").resolve("fun.map"));
  	
  	Application.run(Color.DARK_GRAY, context -> {
      // get the size of the screen
      ScreenInfo screenInfo = context.getScreenInfo();

      // Initialize game data
      GameParameter gameParameters = new GameParameter((int) screenInfo.getWidth(), (int) screenInfo.getHeight(), 60);
      
      // Initialize input
      var userInput = new UserEvent();
      
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
      
      var debugInfo = new StringBuilder();
      
      // Main loop
      for (int frame = 0; true; frame++) {
        // Get time
        var time = System.nanoTime();
        
        // Display the game
				AllDisplay.allDisplay(panel, images, context, gameParameters);
     
        // Event handler
      	userInput.handleEvent(panel, gameParameters, context);
        
      	// PROTOTYPE Debug variable, compute the time elapsed since the start of the frame, help to keep track of performances
      	var calcTime = (System.nanoTime() - time) / 1000000;
      	
        // Time sleeper, wait  1/framerate - time since the start of the frame 
        try {
          TimeUnit.MILLISECONDS.sleep((long) (1000 / gameParameters.getFramerate() - (System.nanoTime() - time) / 1000000));
        } catch (InterruptedException e) {}
        
        // PROTOTYPE Debug display
        debugInfo.append("Frame : ").append(frame)
        .append(" Frame duration : ")
        .append((System.nanoTime() - time) / 1000000)
        .append("ms Frame Calc time : ")
        .append(calcTime).append("ms");
        
        System.out.println(debugInfo);
        debugInfo = new StringBuilder();
      }
    });
  }
}
