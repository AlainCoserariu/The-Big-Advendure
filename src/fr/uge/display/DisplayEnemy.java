package fr.uge.display;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import fr.uge.entity.enemy.EnemyList;
import fr.umlv.zen5.ApplicationContext;

/*Fonction permettant d'afficher tous les ennemis*/
public class DisplayEnemy {
	public DisplayEnemy(EnemyList Elist, ApplicationContext context) throws IOException{
		Elist.getList()
				 .forEach(p -> context.renderFrame(graphics -> {
					try {
						graphics.drawImage(ImageIO.read(
								new File("./ressources/assets/pnj/" + p.getSkin()+".png")),
								null, (int)p.getPosX(), (int)p.getPosY());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}));
	}
}
