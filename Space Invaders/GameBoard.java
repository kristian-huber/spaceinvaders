import java.util.ArrayList;

/**
 * Is responsible for keeping track of all the entities and movements
 * 
 */

public class GameBoard {

	// Keep track of everything going on
	private ArrayList<Entity> entities;
	private AlienGrid alienGrid;
	private Player player;

	private int score = 0;

	public GameBoard() {
		// Create an empty entity list
		entities = new ArrayList<Entity>();
	}

	public void spawn() {
		// Add the aliens to the grid
		alienGrid = new AlienGrid();

		// Add the player to the game at the bottom center
		player = new Player((Window.WIDTH - Entity.SIZE) / 2, Window.HEIGHT - Entity.SIZE * 2 - 25);
		entities.add(player);

		// Barriers
		DefensiveBarrier b1 = new DefensiveBarrier(50, 460);
		DefensiveBarrier b2 = new DefensiveBarrier((int) (Window.WIDTH / 2 - Entity.SIZE * 1.45f), 460);
		DefensiveBarrier b3 = new DefensiveBarrier(Window.WIDTH - Entity.SIZE * 3 - 50, 460);
		entities.add(b1);
		entities.add(b2);
		entities.add(b3);
	}

	public void reset() {
		entities.clear();
		score = 0;
		this.spawn();
	}

	/**
	 * Updates the game
	 * 
	 * @return - Returns whether the game is over or not
	 */
	public boolean update(long elapsedTime) {

		// Update the alien grid
		boolean cond = alienGrid.update(this);

		// Spawn a command ship every 10 seconds
		if (elapsedTime % 100 == 0) {
			CommandShip currentShip = new CommandShip(10, 15, commandPointFactory());
			currentShip.setSpeed(15, 0);
			entities.add(currentShip);
		}

		return ((player.getLives() < 0) || cond);
	}

	private int commandPointFactory() {
		int randomNumber = (int) (4 * Math.random());
		switch (randomNumber) {
		case 0:
			return 50;
		case 1:
			return 100;
		case 2:
			return 150;
		}
		return 300;
	}

	/* Getters and Setters */

	public void removeAlienFromGrid(Alien a) {
		alienGrid.remove(a);
	}

	public void addToScore(int pts) {
		this.score += pts;
		player.addScore(pts);
	}

	public int getScore() {
		return score;
	}

	public void addEntity(Entity e) {
		this.entities.add(e);
	}

	public void removeEntity(Entity e) {
		this.entities.remove(e);
	}

	public Player getPlayer() {
		return player;
	}

	public ArrayList<Entity> getEntities() {
		return this.entities;
	}
}
