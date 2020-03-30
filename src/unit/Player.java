package unit;

import map.Position;

public class Player extends Unit {

	public Player (int x, int y) {
		super (10, 3, 2, 0, x, y);
	}

	public Player(Position p) {
		super (10, 3, 2, 0, p);
	}

}
