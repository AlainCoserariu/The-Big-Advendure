package fr.uge.display;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import fr.uge.entity.enemy.Enemy;
import fr.uge.entity.enemy.EnemyList;
import fr.umlv.zen5.ApplicationContext;

/*Fonction permettant d'afficher tous les ennemis*/
public class DisplayEnemy {
	public static void displayEnemy(List<Enemy> list, ApplicationContext context) throws IOException {
		list.forEach(enemy -> context.renderFrame(graphics -> {
			try {
				graphics.drawImage(ImageIO.read(new File("./ressources/assets/pnj/" + enemy.getSkin() + ".png")), null,
						(int) enemy.getX(), (int) enemy.getY());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}));
	}
}
