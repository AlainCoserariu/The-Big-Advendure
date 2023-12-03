package fr.uge.field.grid;

public interface GridElement {
	/* Méthode permettant de savoir si c'est un obstacle*/
	public boolean IsObstacle();
	/* Méthode permettant de récupérer le nom de l'élément à ouvrir via le enum */
	public String getFileName();
}
