package fr.uge.display;

import java.io.IOException;

import fr.uge.panel.Panel;
import fr.umlv.zen5.ApplicationContext;

/* Classe permettant de d'appeler toutes les fonctions d'affichage */
public class AllDisplay {
	
	public static void allDisplay(Panel pan, ApplicationContext context)throws IOException{
		DisplayPlayer.displayPlayer(pan.player ,context);
		DisplayField.displayField(pan.getField() ,context);
		DisplayEnemy.displayEnemy(pan.getEnemies() ,context);
		System.out.println("On est là");
	}
}
