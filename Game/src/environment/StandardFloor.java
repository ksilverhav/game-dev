package environment;

public class StandardFloor extends BaseEnvironment {

	public StandardFloor(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	protected void render() {
		
	}

	@Override
	protected int damager() {
		return 0;
	}

}
