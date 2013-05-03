package environment;

import images.Images;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

public abstract class BaseEnvironment extends Rectangle {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Image im;
	
	public BaseEnvironment(int x, int y, int width, int height, Images images){
		this.setBounds(x, y, width, height);
		
	}
	
	public Rectangle getHitbox(){
		return this;
	}
	
	protected void loadImages(String imagePath, Images images){
		im = images.getImage(imagePath);
	}
	
	public abstract void render(Graphics2D g, Images images);
	
	protected abstract int damager();
	
}
