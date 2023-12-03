package fr.uge.panel;

import java.awt.DisplayMode;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.tools.Diagnostic;

import fr.uge.entity.enemy.Enemy;
import fr.uge.entity.player.Player;
import fr.uge.fieldElement.FieldElement;
import fr.uge.parser.Lexer;
import fr.uge.parser.Parser;
import fr.uge.parser.Result;
import fr.uge.parser.Token;

public class Panel {
  private final Player player;
  private final ArrayList<Enemy> enemies;
  private final FieldElement[][] field;

  /**
   * Initialize a panel by parsing a map file
   * 
   * @param mapFile
   * @throws IOException
   */
  public Panel(Path mapFile) throws IOException {
    Parser p = new Parser(mapFile);
    
    player = p.player;
    enemies = p.enemies;
    field = p.field;
  }

  public static void main(String[] args) throws IOException {
    // var panel = new Panel(Path.of("big.map"));
    
    Panel panel = new Panel(Path.of("maps").resolve("basic_map.map"));
  }
}
