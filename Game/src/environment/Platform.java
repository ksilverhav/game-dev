package environment;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Platform extends BaseEnvironment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Rectangle hitbox;

	
	public Platform(int x, int y) {
		super(x, y , 1685, 1080); //Bildens höjd och bredd 
		imageName = "platform.png";
		hitbox = new Rectangle(x,y+5,width,height-5);
	}
	public Rectangle getHitbox(){
		return hitbox;// offset så att spelaren kan gå "i" plattformen
	}

	@Override
	public void render(Graphics2D g2d, Image image) {
		g2d.drawImage(image,x,y,width,height,null);

	}

	@Override
	protected int damager() {
		return 0;
	}

}
