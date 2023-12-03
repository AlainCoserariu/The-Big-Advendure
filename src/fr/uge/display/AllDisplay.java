package fr.uge.display;

import java.io.IOException;

import fr.uge.field.Panel;
import fr.umlv.zen5.ApplicationContext;

/* Classe permettant de d'appeler toutes les fonctions d'affichage */
public class AllDisplay {
	public AllDisplay(Panel pan, ApplicationContext context) throws IOException {
		new DisplayPlayer(pan.getPlayer(), context);
		new DisplayEnemy(pan.getListEnemy(), context);
		new DisplayGrid(pan.getGrid(), context);
	}
}
