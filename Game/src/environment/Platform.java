package environment;

import images.Images;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Platform extends BaseEnvironment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String PLATFORMIMAGE = "platform.png";
	
	public Platform(int x, int y, Images images) {
		super(x, y , 428, 130, images); //Bildens höjd och bredd 
		loadImages(PLATFORMIMAGE, images);
	}
	public Rectangle getHitbox(){
		return new Rectangle(x,y+5,width,height-5);// offset så att spelaren kan gå "i" plattformen
	}

	@Override
	public void render(Graphics2D g2d, Images images) {
		g2d.drawImage(im,x,y,width,height,null);

	}

	@Override
	protected int damager() {
		return 0;
	}

}
