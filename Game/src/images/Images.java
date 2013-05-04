package images;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Images {
	ArrayList<Image> allaBilder = new ArrayList<Image>();
	ArrayList<String> allaBildNamn = new ArrayList<String>();
	private final String loadPath = "./res/images"; // Mappen för bilder

	public Images() {
		loadImages(); // Laddar in alla bilder
	}

	// Loopar igenom alla namn i allaBildNamn-arrayen och letar efter en bild
	// med namnet angivet i imageName och skickar isåfall den bild med samma
	// index från allaBilder
	public Image getImage(String imageName) {
		for (int i = 0; i < allaBildNamn.size(); i++) {
			if (allaBildNamn.get(i).equals(imageName))
				return allaBilder.get(i);
		}
		return null; // Om ingen bild hittats
	}

	// Denna funktion laddar in alla bilder i mappen loadPath
	private void loadImages() {

		try {
			Path startPath = Paths.get(loadPath);
			Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path dir,
						BasicFileAttributes attrs) {
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file,
						BasicFileAttributes attrs) {
					try {
						//Läser in bild och lägger in i array
						allaBilder.add(ImageIO.read(new File(file.toString())));
						//Lägger till bildens namn till allBildNamn
						if(file.toString().lastIndexOf("\\") != -1)
						allaBildNamn.add(file.toString().substring(file.toString().lastIndexOf("\\") + 1));
						else
							allaBildNamn.add(file.toString().substring(
									file.toString().lastIndexOf("/") + 1));
					
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException e) {
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
