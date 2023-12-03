package fr.uge.display;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import fr.uge.panel.Panel;
import fr.umlv.zen5.ApplicationContext;

/* Classe permettant de d'appeler toutes les fonctions d'affichage */
public class AllDisplay {
	
	public static void allDisplay(Panel pan, Map<String, BufferedImage> images, ApplicationContext context) throws IOException{
		context.renderFrame(graphics -> {
			DisplayField.displayField(pan.getField() , images, graphics);
			DisplayPlayer.displayPlayer(pan.player , images, graphics);
			DisplayEnemy.displayEnemy(pan.getEnemies(), images, graphics);
		});
	}
	
	public static Map<String, BufferedImage> loadImage() throws IOException {
    return Files.walk(Path.of("ressources"), 10, FileVisitOption.FOLLOW_LINKS)
        .filter(f -> f.getFileName().toString().endsWith(".png"))
        .collect(Collectors.toMap(s -> s.getFileName().toString().substring(0, s.getFileName().toString().length() - 4), s -> {
          try {
            return ImageIO.read(s.toFile());
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          return null;
        }));
  }
	
	public static void main(String[] args) throws IOException {
		System.out.println(loadImage());
	}
}
