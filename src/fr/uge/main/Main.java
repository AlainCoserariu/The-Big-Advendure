package fr.uge.main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import fr.uge.GameParameter;
import fr.uge.UserEvent;
import fr.uge.display.Display;
import fr.uge.gameEngine.GameUpdate;
import fr.uge.gameEngine.Panel;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.ScreenInfo;

public class Main {

  private static void gameLoop(Panel panel, Map<String, BufferedImage> images, ApplicationContext context,
      GameParameter gameParameters, UserEvent userInput) throws InterruptedException {
    var debugInfo = new StringBuilder();

    for (int frame = 0; true; frame++) {
      var time = System.nanoTime(); // Get time at the start of the frame

      Display.allDisplay(panel, images, context, gameParameters);

      userInput.handleEvent(panel, gameParameters, context);
      
      GameUpdate.update(panel, gameParameters);
      
      // PROTOTYPE Debug variable, compute the time elapsed since the start of the
      // frame, help to keep track of performances
      var calcTime = (System.nanoTime() - time) / 1000000;

      // Time sleeper, wait 1/framerate - time since the start of the frame
      TimeUnit.MILLISECONDS.sleep((long) (1000 / gameParameters.getFramerate() - (System.nanoTime() - time) / 1000000));

      // PROTOTYPE Debug display
      debugInfo.append("Frame : ")
          .append(frame)
          .append(" Frame duration : ")
          .append((System.nanoTime() - time) / 1000000)
          .append("ms Frame Calc time : ")
          .append(calcTime)
          .append("ms");

      // System.out.println(debugInfo);
      debugInfo.delete(0, debugInfo.length());
    }
  }

  public static void game(String map) throws IOException {
    // Init game variables
    Panel panel;
    try {
      panel = new Panel(Path.of("maps").resolve(map));
    } catch (IOException e) {
      System.out.println("Can't find " + map + " file. Ending program");
      return;
    }
    // Load images
    Map<String, BufferedImage> images = Display.loadImage();

    // Initialize input
    var userInput = new UserEvent();

    Application.run(Color.DARK_GRAY, context -> {
      // get the size of the screen
      ScreenInfo screenInfo = context.getScreenInfo();

      // Initialize game data
      int framerate = 60;
      GameParameter gameParameters = new GameParameter((int) screenInfo.getWidth(), (int) screenInfo.getHeight(),
          framerate);

      try {
        gameLoop(panel, images, context, gameParameters, userInput);
      } catch (InterruptedException e) {
        System.err.println(e.getMessage());
        context.exit(1);
        System.exit(1);
        return;
      }
    });
  }

  public static void main(String[] args) throws IOException {
    game("true-fun.map");
  }
}
