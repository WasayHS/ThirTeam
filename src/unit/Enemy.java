package unit;

import map.Position;

public class Enemy extends Unit {

	public Enemy (int hp, int str, int mag, int def, int x, int y) {
		super (hp, str, mag, def, x, y);
	}

	public Enemy(int hp, int str, int mag, int def, Position p) {
		super (hp, str, mag, def, p);
	}
}
