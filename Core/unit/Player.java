package unit;

/**
 * Class that extends Unit to create the main character
 * @author Bonnie's Computer
 *
 */
public class Player extends Unit {

	/**
	 * Constructor for the player stats and location
	 * @param x int of the row for the player's positio
	 * @param y int of the row for the player's positio
	 */
	public Player (int x, int y) {
		super (65, 4, 3, 0, x, y);
	}

}
