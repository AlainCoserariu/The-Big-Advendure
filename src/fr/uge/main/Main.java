package fr.uge.main;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.TimeUnit;

import fr.uge.entity.player.Player;
import fr.uge.utils.Vector2D;
import fr.uge.weapon.Sword;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;

public class Main {
  public static void main(String[] args) {
    Application.run(Color.DARK_GRAY, context -> {

      // get the size of the screen
      ScreenInfo screenInfo = context.getScreenInfo();
      float width = screenInfo.getWidth();
      float height = screenInfo.getHeight();

      System.out.println("size of the screen (" + width + " x " + height + ")");
      Player p = new Player((int) (width / 2), 20, 100, 100, 1.2, 1.0, "test", new Sword(10, "DragonSlayer"));

      context.renderFrame(graphics -> {
        graphics.setColor(Color.DARK_GRAY);
        graphics.fill(new Rectangle2D.Float(0, 0, width, height));
        graphics.setColor(Color.BLUE);
        graphics.fill(new Rectangle2D.Float((float) p.getPosX(), (float) p.getPosY(), (float) 24, (float) 24));
      });

      for (int frame = 0; true; frame++) {
        Event event = context.pollEvent();
        System.out.println("Frame : " + frame);
        if (event != null) { // Key detection

          Action action = event.getAction();
          if (action == Action.KEY_PRESSED) {
            KeyboardKey key = event.getKey();
            Vector2D vect = new Vector2D(10, 0);
            switch (key) {
            case UP -> {
              vect = new Vector2D(0, -50);
            }
            case DOWN -> {
              vect = new Vector2D(0, 50);
            }
            case RIGHT -> {
              vect = new Vector2D(50, 0);
            }
            case LEFT -> {
              vect = new Vector2D(-50, 0);
            }
            case SPACE -> System.out.println("Ca doit faire une action");
            default -> {
              context.exit(0);
              return;
            }
            }

            System.out.println("Pos player avant");
            System.out.println("(x = " + p.getPosX() + ", y = " + p.getPosY() + ")");
            System.out.println("key = " + key);
            p.move(vect);
            System.out.println("Pos player apres");
            System.out.println("(x = " + p.getPosX() + ", y = " + p.getPosY() + ")\n");
          }
        }

        try {
          TimeUnit.MILLISECONDS.sleep(1000 / 60);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

        // Frame display
        context.renderFrame(graphics -> {
          graphics.setColor(Color.DARK_GRAY);
          graphics.fill(new Rectangle2D.Float(0, 0, width, height));
          graphics.setColor(Color.BLUE);
          graphics.fill(new Rectangle2D.Float((float) p.getPosX(), (float) p.getPosY(), (float) 24, (float) 24));
        });
      }
    });

    System.out.println("This is the end");
  }
}
