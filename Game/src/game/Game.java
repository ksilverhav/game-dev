package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import player.Player;

public class Game extends Frame implements Runnable, KeyListener {
	/**
	 * Klassen som ritar ut allt och kör Game-loopen Senast uppdaterad av: Jacob
	 * Pålsson Senast uppdaterad den: 4/30/2013
	 */
	private static final long serialVersionUID = 1L;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int SCREENWIDTH = (int) screenSize.getWidth();
	int SCREENHEIGHT = (int) screenSize.getHeight();
	private Player player = new Player();
	public boolean running = false;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Game().start();

	}

	public Game() {

		this.addKeyListener(this); // Lägger till KeyListener så spelet kan
									// hantera knapptryckningar

		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment(); // Hämtar
																						// grafiska
																						// miljön
		GraphicsDevice device = env.getDefaultScreenDevice(); //
		GraphicsConfiguration gc = device.getDefaultConfiguration(); // Hämtar
																		// grafikkortets
																		// konfigurationer

		this.setUndecorated(true); // Sätter undecorated så ingen ram finns på
									// spelet, bara det som ritas syns.
		this.setIgnoreRepaint(true); // I HAVE NO IDEA WHAT I'M DOING
		device.setFullScreenWindow(this); // Sätter full screen mode, verkar
											// sätta rätt upplösning direkt
											// anpassat efter skärmen

	}

	public synchronized void start() {
		running = true;
		new Thread(this).start();
	}

	public synchronized void stop() {
		if (!this.running) {
			return;
		}
		this.running = false;
	}

	@Override
	/**
	 * Nedan kommer en funktion som kör gameloopen, anpassat för 60 UPS
	 */
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;

		int ups = 0;
		int fps = 0;

		long lastTimer = System.currentTimeMillis();
		double delta = 0;

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			while (delta >= 1) {
				ups++;
				update();
				delta -= 1;
				shouldRender = true;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (shouldRender) {
				fps++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				System.out.println(ups + " ups, " + fps + " fps");
				this.setTitle("ups: " + ups + ", fps: " + fps);
				fps = 0;
				ups = 0;
			}
		}

	}

	public void update() {
		player.move();
	}
	BufferStrategy bs = getBufferStrategy();
	public void render() {
		
		if (bs == null) {
			
			this.createBufferStrategy(3);
			bs = getBufferStrategy();
			return;
		}

		Graphics g = bs.getDrawGraphics(); // Hämtar graphics-objekt från
											// bufferstrategy
		Graphics2D g2 = (Graphics2D) g; // Konverterar till Graphics2D utifall
										// anti-aliasing vill användas
		// Ritar ut en svart bakgrund på skärmen med samma storlek som skärmen
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, SCREENWIDTH, SCREENHEIGHT);

	
		player.paint(g2);
		// g2.dispose();
		bs.show();
	}

	// Skickar vidare knappytryckningar till JPanel för behandling.
	@Override
	public void keyPressed(KeyEvent e) {
		player.keyPressed(e);
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) // Stänger av spelet om ESC
													// trycks
			System.exit(0);

	}

	@Override
	public void keyReleased(KeyEvent e) {
		player.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}
