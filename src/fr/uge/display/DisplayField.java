package fr.uge.display;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;

import fr.uge.fieldElement.FieldElement;

public class DisplayField {
	
	public static void displayField(FieldElement[][] field, Map<String, BufferedImage> images, Graphics2D graphics){
		int i, j;
		for (i = 0; i < field.length; i++) {
			for (j = 0; j < field[i].length; j++) {
				if (field[i][j] != null) {
					graphics.drawImage(images.get(field[i][j].getType().toString()), null, j * 24, i * 24 );
				}
			}
		}
	}

}
