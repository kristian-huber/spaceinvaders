import java.awt.Color;

public class Player extends Entity {
	private int _lives = 3;
	private int runningTotal = 0;

	public Player(int x, int y) {
		super(x, y, Color.WHITE);
	}

	@Override
	public void getHit() {
		_lives--;
	}

	public int getLives() {
		return this._lives;
	}

	public void addScore(int increment) {
		runningTotal += increment;

		if (runningTotal > 1000) {
			runningTotal -= 1000;
			_lives++;
		}
	}

	public void fire() {

		int count = 0;
		for (Entity e : Window.board.getEntities()) {
			if (e instanceof Projectile && !(e instanceof AlienProjectile))
				count++;
		}
		
		if (count < 2)
			Window.board.addEntity(new Projectile(this, x, y, Color.WHITE, -1));
	}
}
