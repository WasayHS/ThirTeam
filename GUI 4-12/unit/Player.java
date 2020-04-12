package unit;

import map.Position;

public class Player extends Unit {

	public Player (int x, int y) {
		super (80, 6, 2, 0, x, y);
	}

	public Player(Position p) {
		super (80, 6, 2, 0, p);
	}

}
