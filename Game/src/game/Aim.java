package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class Aim extends Rectangle{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Aim(){
		x=10;
		y=10;
	}
	public void paint(Graphics2D g2d)
	{
		g2d.setColor(Color.PINK);
		g2d.drawRect(x, y, 10, 10);
	}
	public void mouseMoved(MouseEvent m) {
		x = m.getX();
		y = m.getY();
		
	}

	public void mouseDragged(MouseEvent m) {

		
	}

}
