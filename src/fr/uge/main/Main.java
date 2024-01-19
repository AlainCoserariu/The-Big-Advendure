package fr.uge.main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import fr.uge.GameParameter;
import fr.uge.Options;
import fr.uge.UserEvent;
import fr.uge.display.Display;
import fr.uge.gameEngine.GameUpdate;
import fr.uge.gameEngine.Panel;
import fr.uge.parser.Parser;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.ScreenInfo;

public class Main {

  /**
   * Handle the main loop for the game
   * 
   * @param panel
   * @param images
   * @param context
   * @param gameParameters
   * @param userInput
   * @param options
   * @throws InterruptedException
   */
  private static void gameLoop(Panel panel, Map<String, BufferedImage> images, ApplicationContext context,
      GameParameter gameParameters, UserEvent userInput, Options options) throws InterruptedException {
    while (true) {
      var time = System.nanoTime(); // Get time at the start of the frame

      Display.allDisplay(panel, images, context, gameParameters, userInput);

      userInput.handleEvent(panel, gameParameters, context);
      
      if (userInput.getPlayerAction() == 0) {
        GameUpdate.update(panel, gameParameters, options);
      }

      // Time sleeper, wait 1/framerate - time since the start of the frame
      TimeUnit.MILLISECONDS.sleep((long) (1000 / gameParameters.getFramerate() - (System.nanoTime() - time) / 1000000));
    }
  }

  /**
   * Run the context application
   * 
   * @param panel
   * @param images
   * @param userInput
   * @param options
   */
  private static void application(Panel panel, Map<String, BufferedImage> images, UserEvent userInput, Options options) {
    Application.run(Color.BLACK, context -> {
      // get the size of the screen
      ScreenInfo screenInfo = context.getScreenInfo();

      // Initialize game data
      int framerate = 60;
      GameParameter gameParameters = new GameParameter((int) screenInfo.getWidth(), (int) screenInfo.getHeight(), framerate);

      try {
        gameLoop(panel, images, context, gameParameters, userInput, options);
      } catch (InterruptedException e) {
        System.err.println(e.getMessage());
        context.exit(1);
        System.exit(1);
        return;
      }
    });
  }
  
  /**
   * Initialize game variable and start the main loop if map file exist and contain 0 errors
   * 
   * @param map
   * @param options
   * @throws IOException
   */
  private static void game(String map, Options options) throws IOException {
    Panel panel;
    try {
      panel = new Panel(Path.of("maps").resolve(map));
    } catch (IOException e) {
      System.out.println("Can't find " + map + " file. Ending program");
      return;
    }
    
    if (Parser.getErrors().size() != 0) {
      System.out.println("Errors found in " + map + " file");
      return;
    } else if (options.validate()) {
      System.out.println(map + " file is correct");
      return;
    }
    
    application(panel, Display.loadImage(), new UserEvent(), options);
  }

  public static void main(String[] args) throws IOException {
    Options options = new Options(args);
    game(options.map(), options);
  }
}
