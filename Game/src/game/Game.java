package game;

import images.Images;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import player.Player;
import environment.BaseEnvironment;
import environment.Platform;
import environment.StandardFloor;

/**
 * Klassen som ritar ut allt och kör Game-loopen Senast uppdaterad av: Jacob
 * Pålsson Senast uppdaterad den: 4/30/2013
 */
public class Game implements Runnable {

	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private final int SCREENWIDTH = (int) screenSize.getWidth();
	private final int SCREENHEIGHT = (int) ((SCREENWIDTH/16)*9);
	private final double WIDTHSCALE = (double)SCREENWIDTH / 1920;
	private final double HEIGHTSCALE = (double)SCREENHEIGHT / 1080;
	private final Color backgroundColor = new Color(127,224,239);
	private List<BaseEnvironment> environment = Collections.synchronizedList(new ArrayList<BaseEnvironment>());
	private Player player = new Player(SCREENWIDTH, SCREENHEIGHT);
	private JFrame app = new JFrame();
	public boolean running = false;
	private BufferedImage bi;
	private int fps = 0;
	Images images = new Images();	//Laddar in alla bilder
	public static void main(String[] args) {
		new Game().start();
	}

	public Game() {

		
		
		environment.add(new Platform(0, 500));
		environment.add(new Platform(400, 1000));
		
		app.setIgnoreRepaint(true);

		app.setUndecorated(true);
		// Sätter muspekaren till ett hårkors
		app.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

		// Add ESC listener to quit...
		app.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				player.keyPressed(e);
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					stop();
			}

			public void keyReleased(KeyEvent e) {
				player.keyReleased(e);
			}
		});

		app.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent m) {
				player.mouseClicked(m);
			}

			public void mousePressed(MouseEvent m) {
				player.mousePressed(m);
			}

			public void mouseReleased(MouseEvent m) {
				player.mouseReleased(m);
			}

		});
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

		// Get graphics configuration...

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();

		// Change to full screen

		gd.setFullScreenWindow(app);

		if (gd.isDisplayChangeSupported()) {
			gd.setDisplayMode(new DisplayMode(SCREENWIDTH, SCREENHEIGHT, 32, 60));
		}

		// Create BackBuffer...
		app.createBufferStrategy(2);
		BufferStrategy buffer = app.getBufferStrategy();

		// Create off-screen drawing surface
		bi = gc.createCompatibleImage(SCREENWIDTH, SCREENHEIGHT);

		// Objects needed for rendering...
		Graphics graphics = null;
		Graphics2D g2d = null;

		// Variables for counting frames per seconds
		int ups = 0;
		fps = 0;
		int frames = 0;
		long totalTime = 0;
		long curTime = System.currentTimeMillis();
		long lastTime = curTime;
		long currentTime = System.currentTimeMillis();

		while (running) {

			try {

				// count Frames per second...

				lastTime = curTime;

				curTime = System.currentTimeMillis();

				totalTime += curTime - lastTime;

				if (totalTime > 1000) {

					totalTime -= 1000;

					fps = frames;

					frames = 0;

				}

				++frames;

				// draw some rectangles...

				render(g2d);

				if (ups <= 100) {
					update();
					ups++;
				}

				if (System.currentTimeMillis() - currentTime > 1000) {
					currentTime = System.currentTimeMillis();
					ups = 0;
				}

				// Blit image and flip...

				graphics = buffer.getDrawGraphics();
				graphics.drawImage(bi, 0, 0, null);

				if (!buffer.contentsLost())
					buffer.show();

			} finally {

				// release resources

				if (graphics != null)
					graphics.dispose();
			}

		}

		gd.setFullScreenWindow(null);
		System.exit(0);
	}

	public void update() {
		player.move(environment);

	}

	public void render(Graphics2D g2d) {
		g2d = bi.createGraphics();

		g2d.scale(WIDTHSCALE, HEIGHTSCALE);
		
		// draw background
		g2d.setColor(backgroundColor);
		g2d.fillRect(0, 0, SCREENWIDTH, SCREENHEIGHT);

		// display frames per second...
		g2d.setFont(new Font("Courier New", Font.PLAIN, 12));
		g2d.setColor(Color.GREEN);
		g2d.drawString(String.format("FPS: %s", fps), 20, 20);

		// Det som ritas ut relaterar till kamerans position
		g2d.translate(-player.getCamera().x, -player.getCamera().y);

		

		for (int i = 0; i < environment.size(); i++) {
			if (environment.get(i).intersects(player.getCamera())) {
				// Ritar ut miljö
				environment.get(i).render(g2d,images);
			}
		}
		
		player.render(g2d, images); // Ritar ut spelare
		
		g2d.dispose();
	}

}
