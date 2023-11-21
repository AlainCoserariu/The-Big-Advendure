package fr.umlv.zen5;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import fr.umlv.zen5.Event.Action;

/**
 *  Main class of the Zen 4 framework.
 *  Starting a GUI application is as simple as calling {@link #run(Color, Consumer)}.
 */
public final class Application {
  private Application() {
    throw null; // no instance
  }
  
  /** Starts a GUI frame with a drawing area then runs the application code.
   * 
   * @param backgroundColor background color of the drawing area.
   * @param applicationCode code of the application.
   */
  public static void run(Color backgroundColor, Consumer<ApplicationContext> applicationCode) {
    // ...
  }
}
