package fr.uge.display;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.uge.fieldElement.FieldElement;
import fr.umlv.zen5.ApplicationContext;

/* Classe affichant la grille de jeu */
public class DisplayField {
	
	public static void displayField(FieldElement[][] field, ApplicationContext context) throws IOException {
		int i, j;
		for (i = 0; i < field.length; i++) {
			for (j = 0; j < field[i].length; j++) {
				File f = null;
				if (field[i][j] != null) {
					if (field[i][j].IsObstacle()) {
						f = new File("./ressources/assets/obstacle/" + field[i][j].getType().toString() + ".png");
					} else {
						f = new File("./ressources/assets/scenery/" + field[i][j].getType().toString() + ".png");
					}
					var image = ImageIO.read(f);
					int x = j;
					int y = i;
					context.renderFrame(graphics -> graphics.drawImage(image, null, x * 24, y * 24));
				}
			}
		}
	}
}
