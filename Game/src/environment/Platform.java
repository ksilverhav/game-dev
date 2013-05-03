package environment;

import images.Images;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Platform extends BaseEnvironment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String PLATFORMIMAGE = "platform.png";
	
	public Platform(int x, int y) {
		super(x, y , 428, 130); //Bildens höjd och bredd 
	}
	public Rectangle getHitbox(){
		return new Rectangle(x,y+5,width,height-5);// offset så att spelaren kan gå "i" plattformen
	}

	@Override
	public void render(Graphics2D g2d, Images images) {
		g2d.drawImage(images.getImage(PLATFORMIMAGE),x,y,width,height,null);

	}

	@Override
	protected int damager() {
		return 0;
	}

}
