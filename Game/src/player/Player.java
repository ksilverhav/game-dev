package player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;

import environment.BaseEnvironment;

public class Player {
	private int X = 0; // Spelarens X-position
	private int Y = 0; // Spelarens Y-position
	private int WIDTH = 25;
	private int HEIGHT = 25;
	private int X_DIRECTION = 0; // Variabel som säger åt vilket håll i x-led
									// spelaren är på väg.
	private int Y_DIRECTION = 0; // Variabel som säger åt vilket håll i x-led
									// spelaren är på väg.

	private boolean jump = false; // Variabel som säger om player ska hoppa.
	private double YSPEED = 2; // Hastighet i Y-led
	private double XSPEED = 2; // Hastighet i X-led
	private Rectangle HITBOX;;
	private final double GRAVITY = 1; // Konstant gravitation
	private final double JUMPHEIGHT = -10; // Höjden på ett hopp

	public Player() {
		HITBOX = new Rectangle(X, Y, WIDTH, HEIGHT); // Sätter hitboxen
	}

	public void move(List<BaseEnvironment> environment) {
		boolean intersected = false;
		if (jump && Y_DIRECTION == 0) // Om knapp är nedtryckt + står på backen
		{
			YSPEED = JUMPHEIGHT; // spelaren hoppar
		} else
			YSPEED += GRAVITY; // Ökar hastigheten i Y-led beroende på
								// gravitation
		if (YSPEED > 20)
			YSPEED = 20;
		X += X_DIRECTION * XSPEED; // Förflyttar i X-led om knapp är nedtryckt
		for (int i = 0; i < environment.size(); i++) { // Loopar igenom all
														// environment
			if ((new Rectangle(HITBOX.x, HITBOX.y + (int) YSPEED, HITBOX.width,
			// Testar om spelaren kommer krocka med något i sin nästa
			// förflyttning
					HITBOX.height)).intersects(environment.get(i).getHitbox())) {
				// Flyttar spelaren till sista pixeln som inte spelaren krockar
				// med ett föremål
				for (int dy = 0; dy < (int) YSPEED; dy++) {
					HITBOX.setLocation(X, Y + dy);
					if (HITBOX.intersects(environment.get(i).getHitbox())) {
						intersected = true; // Om spelaren krockar med något
											// sätts denna till true
						YSPEED = dy; // Sätter speed till den högsta hastigheten
										// tillåten för att nästa förflyttning
										// inte ska krocka med något.
					}
				}
				if (intersected) {
					Y_DIRECTION = 0; // detta tillåter hopp.
					break;
				}
			}
			Y_DIRECTION = -1; //Sätter att spelaren inte får hoppa om man inte står på backen
		}
		Y += YSPEED; // Ökar position i Y-led

		if (intersected) // Stannar vid "golvet" på 500 px
		{

			YSPEED = 0; // Sätter hastigheten till noll
		}

		HITBOX.setRect(X, Y, WIDTH, HEIGHT); // Uppdaterar HITBOX

	}

	// Ritar ut spelaren
	public void paint(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillRect(X, Y, WIDTH, HEIGHT);
	}

	// Tar hand om knapptryckningar
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_D)
			X_DIRECTION = 1; // Sätter förflyttning i positiv riktning
		if (e.getKeyCode() == KeyEvent.VK_A)
			X_DIRECTION = -1; // Sätter förflyttning i negativ riktning
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

}
