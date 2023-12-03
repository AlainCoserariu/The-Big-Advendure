package fr.uge.display;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;

import fr.uge.entity.player.Player;

/*Fonction permettant l'affichage du Joueur*/
public class DisplayPlayer {
	public static void displayPlayer(Player p,Map<String, BufferedImage> images, Graphics2D graphics){
		graphics.drawImage(images.get(p.getSkin().toString()), null, (int) (p.getX() * 24) - 12, (int) (p.getY() * 24) - 12);
	}
}
