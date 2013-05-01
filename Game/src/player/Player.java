package player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import environment.BaseEnvironment;

public class Player {
	private int X = 0;	//Spelarens X-position
	private int Y = 0;	//Spelarens Y-position
	private int WIDTH = 25;
	private int HEIGHT = 25;
	private int X_DIRECTION=0; // Variabel som säger åt vilket håll i x-led spelaren är på väg.
	private int Y_DIRECTION=0; // Variabel som säger åt vilket håll i x-led spelaren är på väg.
	private double YSPEED=2;	//Hastighet i Y-led
	private double XSPEED=2;	//Hastighet i X-led
	private Rectangle HITBOX;;
	private final double GRAVITY=0.5;	//Konstant gravitation
	private final double JUMPHEIGHT=-10;
	private ArrayList<BaseEnvironment> environment;
	
	public Player(){
		HITBOX = new Rectangle(X,Y,WIDTH,HEIGHT);
	}
	public void move(){
		boolean intersected=false;
		YSPEED+=GRAVITY;	//Ökar hastigheten i Y-led beroende på gravitation
		X += X_DIRECTION*XSPEED;	//Förflyttar i X-led om knapp är nedtryckt
		for(int i=0; i<environment.size();i++)
		{
			if((new Rectangle(HITBOX.x, HITBOX.y+(int)YSPEED, HITBOX.width, HITBOX.height)).intersects(environment.get(i).getHitbox()))
				intersected = true;
		}
		if(intersected)	//Stannar vid "golvet" på 500 px
		{
			Y_DIRECTION=-1;	//Sätter direction så spelaren hoppar
			YSPEED=0;	//Sätter hastigheten till noll
		}
		else
			Y += YSPEED;	//Ökar position i Y-led
		
		HITBOX.setRect(X, Y, WIDTH, HEIGHT);	//Uppdaterar HITBOX
		
		
	}
	//Ritar ut spelaren
	public void paint(Graphics2D g)
	{
		g.setColor(Color.RED);
		g.fillRect(X, Y, WIDTH, HEIGHT);
	}
	//Tar hand om knapptryckningar
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_D)
			X_DIRECTION = 1;	// Sätter förflyttning i positiv riktning
		if (e.getKeyCode()==KeyEvent.VK_A)
			X_DIRECTION = -1;	// Sätter förflyttning i negativ riktning
		if (e.getKeyCode()==KeyEvent.VK_W)
		{
			if(Y_DIRECTION == -1)
				YSPEED = JUMPHEIGHT;	//spelaren hoppar
			Y_DIRECTION = 0;	//Sätter Y_DIRECTION till 0 så spelaren inte kan hoppa förän han träffar backen igen.
		}
		
		
	}
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_D && X_DIRECTION == 1)
			X_DIRECTION = 0;
		if (e.getKeyCode()==KeyEvent.VK_A && X_DIRECTION == -1)
			X_DIRECTION = 0;	
		if(e.getKeyCode()==KeyEvent.VK_W && YSPEED < 0)
			YSPEED=0;	// Stannar gubben om hoppknappen släpps
		
	}
	public void setEnvironment(ArrayList<BaseEnvironment> ENVIRONMENT) {
		environment = ENVIRONMENT;
		
	}
}
