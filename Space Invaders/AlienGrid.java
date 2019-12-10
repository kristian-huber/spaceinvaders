import java.util.ArrayList;

public class AlienGrid {

	// Probability of aliens firing
	private static final int FIRE = 1000;

	// Holds the aliens
	private ArrayList<Alien> aliens;

	// Use this these determine the next move
	private int direction = 1;
	private int count = 0;
	private int xCount = 0;

	/**
	 * Creates all of the aliens
	 * 
	 * @param board
	 *            - takes the GameBoard so we can add the aliens to it
	 */
	public AlienGrid() {

		// Initialize list
		aliens = new ArrayList<Alien>();

		// Variables for easy access
		int initalX = 25;
		int initalY = 50;
		int hSpacing = 25;
		int vSpacing = 10;

		// Generate the aliens in a grid
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 11; j++) {

				// Generate the type of alien
				String type = "Alien";
				if (i == 0)
					type += "C";
				else if (i < 3)
					type += "B";
				else
					type += "A";

				// Create the alien and add it to the appropriate lists
				Alien a = new Alien(type, ((Entity.SIZE + hSpacing) * j) + initalX,
						((Entity.SIZE + vSpacing) * i) + initalY);
				Window.board.addEntity(a);
				aliens.add(a);
			}
		}
	}

	/**
	 * Moves the aliens
	 * 
	 * @param board
	 *            - takes the GameBoard so we can add the projectiles to it
	 * 
	 * @return - Returns if the grid is empty
	 */
	public boolean update(GameBoard board) {

		// Loop over all the aliens
		for (Alien a : aliens) {

			// Randomly fire based on a probability
			int randomNum = (int) (Math.random() * FIRE);
			if (randomNum == 0)
				board.addEntity(a.fire());

			// Math for movement
			if (count % 10 == 9) {
				if (xCount == 11) {
					a.setSpeed(0, 25);
				} else {
					a.setSpeed(15 * direction, 0);
				}
			} else if (count % 10 == 0) {
				a.setSpeed(0, 0);
			}
		}

		// Update count and return
		count++;
		if (count % 10 == 0)
			xCount++;
		if (xCount == 12) {
			xCount = 0;
			direction *= -1;
		}

		return aliens.isEmpty();
	}

	/**
	 * Removes and alien from the grid
	 * 
	 * @param a
	 *            - Alien to be removed
	 */
	public void remove(Alien a) {
		this.aliens.remove(a);
	}
}
