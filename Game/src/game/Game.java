package game;

import java.awt.Canvas;
import java.awt.Color;
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
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import player.Player;
import environment.BaseEnvironment;
import environment.StandardFloor;



public class Game implements Runnable, KeyListener {
	/**
	 * Klassen som ritar ut allt och kör Game-loopen Senast uppdaterad av: Jacob
	 * Pålsson Senast uppdaterad den: 4/30/2013
	 */
	private static final long serialVersionUID = 1L;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int SCREENWIDTH = (int) screenSize.getWidth();
	int SCREENHEIGHT = (int) screenSize.getHeight();
	ArrayList<BaseEnvironment> environment = new ArrayList<BaseEnvironment>();
	Player player = new Player();
	
	public boolean running = false;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Game().start();
		
	}
	JFrame app = new JFrame();
	Canvas canvas = new Canvas();
	int i=0;
	public Game() {
		environment.add(new StandardFloor(0, 500, 1000, 50));
		player.setEnvironment(environment);

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

	    // Create game window...
	    JFrame app = new JFrame();
	    int ups=0;
	    app.setIgnoreRepaint( true );

	    app.setUndecorated( true );

	                

	    // Add ESC listener to quit...

	    app.addKeyListener( new KeyAdapter() {

	      public void keyPressed( KeyEvent e ) {
	    	  player.keyPressed(e);

	        if( e.getKeyCode() == KeyEvent.VK_ESCAPE )

	            running = false;

	          }
	      public void keyReleased( KeyEvent e )
	      {
	    	  player.keyReleased(e);
	      }

	    });

	                

	    // Get graphics configuration...

	    GraphicsEnvironment ge = 

	        GraphicsEnvironment.getLocalGraphicsEnvironment();

	    GraphicsDevice gd = ge.getDefaultScreenDevice();

	    GraphicsConfiguration gc = gd.getDefaultConfiguration();



	    // Change to full screen

	    gd.setFullScreenWindow( app );

	    if( gd.isDisplayChangeSupported() ) {

	      gd.setDisplayMode( 

	        new DisplayMode( SCREENWIDTH, SCREENHEIGHT, 32, DisplayMode.REFRESH_RATE_UNKNOWN )

	      );

	    }

	                

	    // Create BackBuffer...

	    app.createBufferStrategy( 2 );

	    BufferStrategy buffer = app.getBufferStrategy();

	                

	    // Create off-screen drawing surface

	    BufferedImage bi = gc.createCompatibleImage( SCREENWIDTH, SCREENHEIGHT );



	    // Objects needed for rendering...

	    Graphics graphics = null;

	    Graphics2D g2d = null;

	    Color background = Color.BLACK;

	    Random rand = new Random();

	                

	    // Variables for counting frames per seconds

	    int fps = 0;

	    int frames = 0;

	    long totalTime = 0;

	    long curTime = System.currentTimeMillis();

	    long lastTime = curTime;

	                

	    running = true;

		long currentTime = System.currentTimeMillis();
		double nsPerTick = 1000000000D / 60D;
		
		double delta = 0;

	    
	    while( running ) {
	    	
	      try {

	        // count Frames per second...

	        lastTime = curTime;

	        curTime = System.currentTimeMillis();

	        totalTime += curTime - lastTime;

	        if( totalTime > 1000 ) {

	          totalTime -= 1000;

	          fps = frames;

	          frames = 0;

	        } 

	        ++frames;



	        // clear back buffer...

	        g2d = bi.createGraphics();

	        g2d.setColor( background );

	        g2d.fillRect( 0, 0, SCREENWIDTH, SCREENHEIGHT );

	                                

	        // draw some rectangles...

	        
	        player.paint(g2d);
	                                

	        // display frames per second...

	        g2d.setFont( new Font( "Courier New", Font.PLAIN, 12 ) );

	        g2d.setColor( Color.GREEN );

	        g2d.drawString( String.format( "FPS: %s", fps ), 20, 20 );
	        
	        
	        
	        
	    	if(ups <=100)
	    	{
	    		
	    		update();
	    		ups++;
	    	}
	    	if(System.currentTimeMillis() - currentTime > 1000)
	    	{
	    		currentTime = System.currentTimeMillis();
	    		ups = 0;
	    	}
	    		
	        // Blit image and flip...

	        graphics = buffer.getDrawGraphics();

	        graphics.drawImage( bi, 0, 0, null );

	                                

	        if( !buffer.contentsLost() )

	          buffer.show();

	        

	      } finally {

	        // release resources

	        if( graphics != null ) 

	          graphics.dispose();

	        if( g2d != null ) 

	          g2d.dispose();

	      }

	    }

	                

	    gd.setFullScreenWindow( null );

	    System.exit(0);
	}

	public void update() {
		player.move();
        
	}
	
	public void render() {
		
	
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
