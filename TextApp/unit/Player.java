package unit;

import map.Position;

public class Player extends Unit {

	public Player (int x, int y) {
		super (50, 2, 1, 0, x, y);
	}

	public Player(Position loc) {
		super (50, 2, 1, 0, loc);
	}
}
