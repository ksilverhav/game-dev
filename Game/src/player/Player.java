package player;

import images.Images;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

import environment.BaseEnvironment;

public class Player extends Rectangle {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int X_DIRECTION = 0; // Variabel som säger åt vilket håll i x-led
									// spelaren är på väg.
	private int Y_DIRECTION = 0; // Variabel som säger åt vilket håll i x-led
									// spelaren är på väg.

	private boolean jump = false; // Variabel som säger om player ska hoppa.
	private double YSPEED = 2; // Hastighet i Y-led
	private double XSPEED = 2; // Hastighet i X-led
	
	private boolean lookingRight=true;
	
	private final double GRAVITY = 1; // Konstant gravitation
	private final double JUMPHEIGHT = -10; // Höjden på ett hopp
	
	private final String PLAYERIMAGE = "player.png";
	
	private Rectangle camera;
	private int screenWidth;
	private int screenHeight;

	public Player(int screenWidth, int screenHeight) {
		width=56;
		height=94;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		camera = new Rectangle(screenWidth, screenHeight);
	}
	
	private void updateCamera(int x, int y){
		int camX = (int) (x - screenWidth / 2);
		int camY = (int) (y - screenHeight / 2);
		if(camX < 0)
			camX = 0;
		if(camY < 0)
			camY = 0;
		camera.setLocation(camX, camY);
	}
	
	public Rectangle getCamera(){
		return camera;
	}

	public void move(List<BaseEnvironment> environment) {
		boolean yIntersected = false;
		boolean xIntersected = false;
		if (jump && Y_DIRECTION == 0) // Om knapp är nedtryckt + står på backen
		{
			YSPEED = JUMPHEIGHT; // spelaren hoppar
		} else
			YSPEED += GRAVITY; // Ökar hastigheten i Y-led beroende på
								// gravitation
		if (YSPEED > 20)
			YSPEED = 20;
		
		for (int i = 0; i < environment.size(); i++) { // Loopar igenom all
														// environment
			if ((new Rectangle(x, y + (int) YSPEED, width,
					height)).intersects(environment.get(i).getHitbox())) {
				
				// Flyttar spelaren till sista pixeln som inte spelaren krockar
				// med ett föremål
				for (int dy = 0; dy < (int) (YSPEED); dy++) {
					y+=1;
					if(intersects(environment.get(i)))
					{
						yIntersected=true;
						y-=1;
						YSPEED=0;
						
						break;
					}
					
				}
				

				if (yIntersected) {
					Y_DIRECTION = 0; // detta tillåter hopp.
					
				}
				
			}
				if(!yIntersected)
				Y_DIRECTION = -1; // Sätter att spelaren inte får hoppa om man
									// inte står på MARKEN
				if(new Rectangle(x+X_DIRECTION * (int)XSPEED,y,width,height).intersects(environment.get(i).getHitbox()))
				{
					for(int dx = 0; dx!=(int)XSPEED; dx+=1){
					if((new Rectangle(x+X_DIRECTION,y,width,height).intersects(environment.get(i).getHitbox())))
					{
						xIntersected=true;
					}
					else
						x+=X_DIRECTION;
					}
				}
		}
		
		y += YSPEED; // Ökar position i Y-led
		if(!xIntersected)
		x += X_DIRECTION * XSPEED; // Förflyttar i X-led om knapp är nedtryckt
		if (yIntersected) // Stannar vid "golvet" på 500 px
		{

			YSPEED = 0; // Sätter hastigheten till noll
		}
		
		updateCamera((int) (this.x + (this.getWidth() / 2)), (int) (this.y + (this.getHeight() / 2)));

	}

	// Ritar ut spelaren
	public void render(Graphics2D g, Images images) {
		Image im = images.getImage(PLAYERIMAGE);
		
		if(lookingRight)
		{
			g.drawImage (im, 
		             x + im.getWidth(null), y, x, y+im.getHeight(null),
		             0, 0, im.getWidth(null), im.getHeight(null),
		             null);
		}
		else
		g.drawImage(images.getImage(PLAYERIMAGE),x,y,width,height,null);
		
		
	}

	// Tar hand om knapptryckningar
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_D)
		{
			X_DIRECTION = 1; // Sätter förflyttning i positiv riktning
			lookingRight = true; // Så utmålningen vet åt vilket håll gubben tittar
		}
		if (e.getKeyCode() == KeyEvent.VK_A)
		{
			X_DIRECTION = -1; // Sätter förflyttning i negativ riktning
			lookingRight = false; // Så utmålningen vet åt vilket håll gubben tittar
		}
		if (e.getKeyCode() == KeyEvent.VK_W) {
			jump = true; // Sätter denna variabel till true för att hoppa

		}

	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_D && X_DIRECTION == 1)
			X_DIRECTION = 0;
		if (e.getKeyCode() == KeyEvent.VK_A && X_DIRECTION == -1)
			X_DIRECTION = 0;
		if (e.getKeyCode() == KeyEvent.VK_W) {
			jump = false;
			if (YSPEED < -1)
				YSPEED = -1;
		}
	}

	public void mouseClicked(MouseEvent m) {
		System.out.println("Mouse clicked");

	}

	public void mousePressed(MouseEvent m) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent m) {
		// TODO Auto-generated method stub
		
	}

}
