package environment;

import images.Images;

import java.awt.Color;
import java.awt.Graphics2D;

public class StandardFloor extends BaseEnvironment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StandardFloor(int x, int y, int width, int height, Images images) {
		super(x, y, width, height, images);
//		loadImages(imagePath, images);
	}

	@Override
	public void render(Graphics2D g2d,Images images) {
		g2d.setColor(Color.WHITE);
		g2d.fillRect(x, y, width, height);
	}

	@Override
	protected int damager() {
		return 0;
	}

}
