package player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Player {
	private int X = 0;
	private int Y = 0;
	private int X_DIRECTION=0; // Variabel som säger åt vilket håll i x-led spelaren är på väg.
	private int Y_DIRECTION=0; // Variabel som säger åt vilket håll i x-led spelaren är på väg.
	private final int SPEED=2;
	
	public Player(){
		
	}
	public void move(){
		X += X_DIRECTION*SPEED;
		Y += Y_DIRECTION*SPEED;
	}
	public void paint(Graphics2D g)
	{
		g.setColor(Color.RED);
		g.fillRect(X, Y, 25, 25);
	}
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_D)
			X_DIRECTION = 1;
		else if (e.getKeyCode()==KeyEvent.VK_A)
			X_DIRECTION = -1;
		else if (e.getKeyCode()==KeyEvent.VK_W)
			Y_DIRECTION = -1;
		else if(e.getKeyCode()==KeyEvent.VK_S)
			Y_DIRECTION = 1;
		
	}
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_D && X_DIRECTION == 1)
			X_DIRECTION = 0;
		else if (e.getKeyCode()==KeyEvent.VK_A && X_DIRECTION == -1)
			X_DIRECTION = 0;
		else if (e.getKeyCode()==KeyEvent.VK_W && Y_DIRECTION == -1)
			Y_DIRECTION = 0;
		else if(e.getKeyCode()==KeyEvent.VK_S && Y_DIRECTION == 1)
			Y_DIRECTION = 0;
		
	}
}
