package game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game extends JFrame implements Runnable {
	Board board = new Board(); // Gör ett nytt objekt av JPanel-klasen
	public boolean running = false;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Game().start();

	}

	public Game() {
		this.setPreferredSize(new Dimension(500, 500));
		this.setMinimumSize(new Dimension(500, 500));
		this.setMaximumSize(new Dimension(500, 500));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Gör så att JFrame kan
														// stängas genom att
														// trycka på kryss
		this.add(board); // Lägger till en JPanel i denna JFrame
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true); // Gör JFrame synlig

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
				// System.out.println(ups + " ups, " + fps + " fps");
				this.setTitle("ups: " + ups + ", fps: " + fps);
				fps = 0;
				ups = 0;
			}
		}

	}

	public void update() {
		
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2 = (Graphics2D) g;
		
		
		g2.dispose();
		bs.show();
	}
}
