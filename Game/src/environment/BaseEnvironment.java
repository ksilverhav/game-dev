package environment;

import java.awt.Rectangle;

public abstract class BaseEnvironment extends Rectangle {
	
	public BaseEnvironment(int x, int y, int width, int height){
		this.setBounds(x, y, width, height);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Rectangle getHitbox(){
		return this;
	}
	
	protected abstract void render();
	
	protected abstract int damager();
	
}
