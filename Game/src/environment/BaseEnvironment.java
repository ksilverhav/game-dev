package environment;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

public abstract class BaseEnvironment extends Rectangle {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String imageName = "";
	public BaseEnvironment(int x, int y, int width, int height){
		this.setBounds(x, y, width, height);
		
	}
	
	public Rectangle getHitbox(){
		return this;
	}
	public String getImageName(){
		return imageName;
	}
	
	public abstract void render(Graphics2D g, Image image);
	
	protected abstract int damager();
	
}
