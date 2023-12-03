package fr.uge.display;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.uge.field.grid.Grid;
import fr.umlv.zen5.ApplicationContext;

/* Classe affichant la grille de jeu */
public class DisplayGrid {
  public DisplayGrid(Grid grid, ApplicationContext context) throws IOException {
  	var map = grid.getList();
  	int i, j;
  	for(i = 0; i < map.length; i++) {
  		for(j = 0; j < map.length; j++) {
  			File f = null;
  			if(map[i][j].IsObstacle()) {
  				f = new File("./ressources/assets/obstacle/" + map[i][j].getFileName() + ".png");
  			}
  			else {
  				f = new File("./ressources/assets/scenery/" + map[i][j].getFileName() + ".png");
  			}
  			var image = ImageIO.read(f);
  			int x = i;
  			int y = j;
  			context.renderFrame(graphics -> 
  			graphics.drawImage(image, null, x * 24, y * 24));
  		}
  	}
  }
}
