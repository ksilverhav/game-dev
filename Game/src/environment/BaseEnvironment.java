package environment;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class BaseEnvironment extends Rectangle {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int spawnX, spawnY;

	public BaseEnvironment(int x, int y, int width, int height){
		this.setBounds(x, y, width, height);
		this.x = x;
		this.y = y;
		setSpawnLocation(x, y);
		this.width = width;
		this.height = height;
	}
	
	public Rectangle getHitbox(){
		return this;
	}
	
	private void setSpawnLocation(int x, int y){
		spawnX = x;
		spawnY = y;
	}
	
	public int getSpawnX(){
		return spawnX;
	}
	
	public int getSpawnY(){
		return spawnY;
	}
	
	public abstract void render(Graphics2D g, int xOffset, int yOffset);
	
	protected abstract int damager();
	
}
