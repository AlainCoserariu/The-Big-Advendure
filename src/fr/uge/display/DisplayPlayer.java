package fr.uge.display;

import java.io.File;
import java.io.IOException;
import fr.umlv.zen5.ApplicationContext;
import javax.imageio.ImageIO;

import fr.uge.entity.player.Player;

/*Fonction permettant l'affichage du Joueur*/
public class DisplayPlayer {
	public static void displayPlayer(Player p, ApplicationContext context) throws IOException {
		var player_display = ImageIO.read(new File("./ressources/assets/pnj/" + p.getSkin()+".png"));
		context.renderFrame(graphics -> 
		graphics.drawImage(player_display, null, (int)p.getX(), (int)p.getY()));
	}
}
