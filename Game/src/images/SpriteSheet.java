package images;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpriteSheet {

	


	public int[] pixels;
	private ArrayList<Image> images = new ArrayList<Image>();
	public SpriteSheet() {
		
	}
	//[1][2][3] | [4] [5] [6]
	//[7][8][9] | [10][11][12]
	public ArrayList<Image> splitSpriteSheet(Image IMAGE,int WIDTH, int HEIGHT){
		int width = IMAGE.getWidth(null);
		int height = IMAGE.getHeight(null);
		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();
		g2d.drawImage(IMAGE, 0, 0, null);  
	    g2d.dispose();
		
		pixels = image.getRGB(0, 0, width, height, null, 0, width);
		int rows=(int)Math.floor(width/WIDTH);
		int cols=(int)Math.floor(height/HEIGHT);
		
		BufferedImage singlePicture =  new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_ARGB);
		for(int i = 0; i < rows*cols; i++)
		{
			 singlePicture = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_ARGB);	
			for(int h = 0; h < HEIGHT; h++)
			{
				for(int w = 0; w < WIDTH; w++)
				{
					
					singlePicture.setRGB(w, h, pixels[h*width + w + i*WIDTH]);
					//singlePicture.setRGB(w,h, pixels[i*WIDTH*HEIGHT + h*WIDTH + w ]);
				}
			}
			images.add(singlePicture);
			
		}
		
		
        //images.add(pixelToImage);
		//for (int i = 0; i < pixels.length; i++) {
		//	pixels[i] = (pixels[i] & 0xff) / 64;
		//}
		
		return images;
	}
}
