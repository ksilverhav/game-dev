package player;

import images.Images;
import images.SpriteSheet;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
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
	private double XSPEED = 8; // Hastighet i X-led
	
	private final Point startPoint = new Point(2000,2000);
	
	private final double GRAVITY = 1; // Konstant gravitation
	private final double JUMPHEIGHT = -20; // Höjden på ett hopp

	private final String PLAYERIMAGE = "player.png";
	private Image im;
	private double footMovement = -1;
	private double footSpeed = 0.17;
	
	private double theta=0; //Vinkeln mellan spelare och mus 
	
	private final int PLAYERBODY = 0, PLAYERHEAD = 1, PLAYEREYE = 2, PLAYERHAND = 3, PLAYERFOOT = 4
			, PLAYERGUN = 5;
	private int eyeXoffset = 0,eyeYoffset = 0;
	
	private SpriteSheet spriteSheet = new SpriteSheet();
	
	private ArrayList<Image> sprite = new ArrayList<Image>();
	
	private Rectangle camera;
	private int screenWidth;
	private int screenHeight;

	public Player(int screenWidth, int screenHeight, Images images) {
		x = startPoint.x;
		y = startPoint.y;
		width = 62;
		height = 94;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		camera = new Rectangle(screenWidth, screenHeight);
		
		loadImages(PLAYERIMAGE, images);
	}

	private void updateCamera(int x, int y) {
		int camX = (int) (x - screenWidth / 2);
		int camY = (int) (y - screenHeight / 2);
		if (camX < 0)
			camX = 0;
		if (camY < 0)
			camY = 0;
		camera.setLocation(camX, camY);
	}

	public Rectangle getCamera() {
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
			if ((new Rectangle(x, y + (int) YSPEED, width, height)).intersects(environment.get(i).getHitbox())) {

				// Flyttar spelaren till sista pixeln som inte spelaren krockar
				// med ett föremål
				for (int dy = 0; dy < (int) (YSPEED); dy++) {
					y += 1;
					if (intersects(environment.get(i))) {
						yIntersected = true;
						y -= 1;
						YSPEED = 0;

						break;
					}

				}

				if (yIntersected) {
					Y_DIRECTION = 0; // detta tillåter hopp.

				}

			}
			if (!yIntersected)
				Y_DIRECTION = -1; // Sätter att spelaren inte får hoppa om man
									// inte står på MARKEN
			if (new Rectangle(x + X_DIRECTION * (int) XSPEED, y, width, height).intersects(environment.get(i)
					.getHitbox())) {
				for (int dx = 0; dx != (int) XSPEED; dx += 1) {
					if ((new Rectangle(x + X_DIRECTION, y, width, height).intersects(environment.get(i).getHitbox()))) {
						xIntersected = true;
					} else
						x += X_DIRECTION;
				}
			}
		}

		y += YSPEED; // Ökar position i Y-led
		if (!xIntersected)
			x += X_DIRECTION * XSPEED; // Förflyttar i X-led om knapp är
										// nedtryckt
		if (yIntersected) // Stannar vid "golvet" på 500 px
		{

			YSPEED = 0; // Sätter hastigheten till noll
		}

		updateCamera((int) this.getCenterX(), (int) this.getCenterY());

	}

	// Ritar ut spelaren
	private void drawImage(Graphics2D g,Image img, int xOffset, int yOffset)
	{
			g.drawImage(img, x+xOffset, y+yOffset, img.getWidth(null), img.getHeight(null), null); // left eye
	}
	public void render(Graphics2D g) {
		
			drawImage(g,sprite.get(PLAYERHAND), 45,65); // Höger hand
			AffineTransform trans = g.getTransform();
			double angle = theta-(Math.PI/2.0); // Utifall att vinkeln hinner ändras innan programmet roterat tillbaka
			trans.rotate(angle,x+width/2,y+height/2); //Offset för att rota kring mitten
			g.setTransform(trans);
			drawImage(g,sprite.get(PLAYERGUN), width/2,height/2); // Vapen
			trans.rotate(-angle,x+width/2,y+height/2); //Offset för att rota kring mitten
			g.setTransform(trans);
			drawImage(g,sprite.get(PLAYERHAND), 5,65); // Vänster hand
			
			drawImage(g,sprite.get(PLAYERFOOT), (int)(18 + -X_DIRECTION*Math.cos(footMovement)*10),(int)(88  + Math.sin(Math.abs(X_DIRECTION)*footMovement)*3)); // höger fot
			drawImage(g,sprite.get(PLAYERBODY),16,27);//Kroppen
			drawImage(g,sprite.get(PLAYERHEAD),0,0);//Huvud

			drawImage(g,sprite.get(PLAYEREYE), 35 + eyeXoffset,18 + eyeYoffset); // höger öga
			drawImage(g,sprite.get(PLAYEREYE), 20 + eyeXoffset,18 + eyeYoffset); // Vänster öga

			

			
			drawImage(g,sprite.get(PLAYERFOOT), (int)(21 + X_DIRECTION*Math.cos(footMovement)*10),(int)(88 + Math.sin(Math.abs(X_DIRECTION)*footMovement+Math.PI)*3)); // Vänster fot
			
			footMovement += footSpeed;
			if (footMovement >=1)
				footSpeed*=-1;
	}
	
	private void loadImages(String imagePath, Images images){
		
		im = images.getImage(imagePath);
		sprite.add(spriteSheet.splitSpriteSheet(im,0,59, 29, 64)); //Laddar in kropp
		sprite.add(spriteSheet.splitSpriteSheet(im,0,0, 62, 57)); //Laddar in huvud
		sprite.add(spriteSheet.splitSpriteSheet(im,64,16, 5, 10)); //Laddar in öga
		sprite.add(spriteSheet.splitSpriteSheet(im,64,32, 11, 11)); //Laddar in hand
		sprite.add(spriteSheet.splitSpriteSheet(im,64,8, 21, 7)); //Laddar in fot
		sprite.add(spriteSheet.splitSpriteSheet(im,64,48, 61, 21)); //Laddar in vapen
		
		
	}

	// Tar hand om knapptryckningar
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_D) {
			X_DIRECTION = 1; // Sätter förflyttning i positiv riktning

		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			X_DIRECTION = -1; // Sätter förflyttning i negativ riktning
		}

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			jump = true; // Sätter denna variabel till true för att hoppa

		}

	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_D && X_DIRECTION == 1)
			X_DIRECTION = 0;
		if (e.getKeyCode() == KeyEvent.VK_A && X_DIRECTION == -1)
			X_DIRECTION = 0;
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			jump = false;
			if (YSPEED < -1)
				YSPEED = -1;
		}
	}

	public void mouseClicked(MouseEvent m) {

	}

	public void mousePressed(MouseEvent m) {
		// TODO Auto-generated method stub
		System.out.println("Mouse clicked");

	}

	public void mouseReleased(MouseEvent m) {
		// TODO Auto-generated method stub

	}
			//   X
	public void mouseMoved(MouseEvent m) {
			theta = Math.atan2(m.getPoint().y - screenHeight/2, m.getPoint().x - screenWidth/2); //Tar ut vinkeln mellan mus och mitten av skärmen
		    theta += Math.PI/2.0;  // Gör om vinkel så 0 grader är norr ut
		    eyeXoffset = (int) Math.round(Math.sin(theta)); 
		    eyeYoffset = -(int) Math.round(Math.cos(theta));

		
	}

}
