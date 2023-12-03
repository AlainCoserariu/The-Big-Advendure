package fr.uge.display;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import fr.uge.entity.enemy.Enemy;

/*Fonction permettant d'afficher tous les ennemis*/
public class DisplayEnemy {
	public static void displayEnemy(List<Enemy> list, Map<String, BufferedImage> images, Graphics2D graphics) {
		list.forEach(enemy -> {
			graphics.drawImage(images.get(enemy.getSkin().toString()), null,
					(int) enemy.getX(), (int) enemy.getY());
		});
	}
}
