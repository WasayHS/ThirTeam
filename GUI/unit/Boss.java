package unit;

public class Boss extends Unit {
	
	/**
	 * Constructor for bosses
	 * @param hp Int for health
	 * @param str Int for strength
	 * @param mag Int for mag
	 * @param def Int for def
	 * @param x Int for row on map
	 * @param y Int for column on map
	 */
	public Boss(int hp, int str, int mag, int def, int x, int y) {
		super(hp, str, mag, def, x, y);
	}

}
