package fr.uge.main;


import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import fr.uge.display.AllDisplay;
import fr.uge.field.Panel;
import fr.uge.utils.Vector2D;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;

public class Main {
  public static void main(String[] args) throws IOException{
  	Panel pan = new Panel(Paths.get("./maps/basic_map.map"));
    Application.run(Color.DARK_GRAY, context -> {

      // get the size of the screen
      ScreenInfo screenInfo = context.getScreenInfo();
      float width = screenInfo.getWidth();
      float height = screenInfo.getHeight();
      
      System.out.println("size of the screen (" + width + " x " + height + ")");
      for (int frame = 0; true; frame++) {
      	context.renderFrame(graphics -> {
          graphics.setColor(Color.DARK_GRAY);
          graphics.fill(new Rectangle2D.Float(0, 0, width, height));
        });
        try {
  				new AllDisplay(pan, context);
  			} catch (IOException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
        Event event = context.pollEvent();
        System.out.println("Frame : " + frame);
        if (event != null) { // Key detection

          Action action = event.getAction();
          if (action == Action.KEY_PRESSED) {
            KeyboardKey key = event.getKey();
            Vector2D vect = new Vector2D(0, 0);
            switch (key) {
            case UP -> {
              vect = vect.add(new Vector2D(0, -1));
            }
            case DOWN -> {
              vect = vect.add(new Vector2D(0, 1));
            }
            case RIGHT -> {
              vect = vect.add(new Vector2D(1, 0));
            }
            case LEFT -> {
              vect = vect.add(new Vector2D(-1, 0));
            }
            case SPACE -> System.out.println("Ca doit faire une action");
            case I -> System.out.println("inventaire");
            default -> {
              context.exit(0);
              return;
            }
            }

            System.out.println("Pos player avant");
            System.out.println("(x = " + pan.getPlayer().getPosX() + ", y = " + pan.getPlayer().getPosY() + ")");
            System.out.println("key = " + key);
            pan.getPlayer().move(vect);
            System.out.println("Pos player apres");
            System.out.println("(x = " + pan.getPlayer().getPosX() + ", y = " + pan.getPlayer().getPosY() + ")\n");
          }
        }
        
        try {
          TimeUnit.MILLISECONDS.sleep(1000 / 60);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    });

    System.out.println("This is the end");
  }
}
