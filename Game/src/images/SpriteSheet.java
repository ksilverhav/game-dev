package images;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpriteSheet {

	


	public int[] pixels;
	
	
	public SpriteSheet() {
		
	}
	//[1][2][3] | [4] [5] [6]
	//[7][8][9] | [10][11][12]
	public Image splitSpriteSheet(Image IMAGE,int X, int Y, int WIDTH, int HEIGHT){
		BufferedImage singlePicture;
		int width = IMAGE.getWidth(null);
		int height = IMAGE.getHeight(null);
		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		BufferedImage croppedImage = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();
		g2d.drawImage(IMAGE, 0, 0, null);  
	    g2d.dispose();
		
		pixels = image.getRGB(0, 0, width, height, null, 0, width);

		
		for(int h = 0; h < HEIGHT; h++)
		{
		for(int w = 0; w <WIDTH; w++)
		{
			croppedImage.setRGB(w, h, pixels[(X + w) + (h + Y)*(width) ]);
		}
		}
		

		
		return croppedImage;
	}
}
