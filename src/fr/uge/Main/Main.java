package fr.uge.Main;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;

public class Main {

  public static void main(String[] args) {
    Application.run(Color.DARK_GRAY, context -> {

      for (int i = 0; i < 3; i++) {
        context.renderFrame(graphics -> {
          graphics.setColor(Color.GREEN);
          graphics.fill(new Rectangle2D.Float(50, 50, 20, 20));
        });
        
        Event event = context.pollEvent();
        while (event == null) {
          event = context.pollEvent();
          if (event != null) {
            Action action = event.getAction();
            if (action == Action.KEY_PRESSED) {
              System.out.println("oskour");
              context.exit(0);
              return;
            }            
          }
        }
      }
    });
  }

}
