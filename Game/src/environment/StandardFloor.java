package environment;

import java.awt.Color;
import java.awt.Graphics2D;

public class StandardFloor extends BaseEnvironment {

	public StandardFloor(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.fillRect(x, y, width, height);
	}

	@Override
	protected int damager() {
		return 0;
	}

}
