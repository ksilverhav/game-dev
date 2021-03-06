package environment;

import images.Images;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class BaseEnvironment extends Rectangle {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BaseEnvironment(int x, int y, int width, int height){
		this.setBounds(x, y, width, height);
	}
	
	public Rectangle getHitbox(){
		return this;
	}
	
	public abstract void render(Graphics2D g, Images images);
	
	protected abstract int damager();
	
}
