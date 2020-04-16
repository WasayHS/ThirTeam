package unit;

import map.Position;

/**
 * Class of to make units in the game
 * @author Bonnie's Computer
 *
 */
public class Unit {

    private Stats stats;
    private Position position;

    /**
     * constructor for unit
     *
     * Parameters passed by subclasses: Enemy and Player
     * @param hp: Type int - the unit's HP
     * @param str: Type int - the unit's HP str
     * @param mag: Type int - the unit's HP mag
     * @param def: Type int - the unit's HP def
     * @param x: Type int - the unit's x value
     * @param y: Type int - the unit's y value
     */
    public Unit (int hp, int str, int mag, int def, int x, int y) {
        this.stats = new Stats(hp, str, mag, def);
        this.position = new Position(x, y);
    }

    public void setStats(Stats stats) {
		this.stats = stats;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	/**
     * constructor for unit
     *
     * Parameters passed by subclasses: Enemy and Player
     * @param hp: Type int - the unit's HP
     * @param str: Type int - the unit's HP str
     * @param mag: Type int - the unit's HP mag
     * @param def: Type int - the unit's HP def
     * @param p: Type Position - the unit's (x,y) value
     */

    public Unit (int hp, int str, int mag, int def, Position p) {
        this.stats = new Stats(hp, str, mag, def);
        this.position = p;
    }

    /**
     * getter for stats
     *
     * @return stats returns Type Stats from class Stats
     */
    public Stats getStats() {
        return stats;
    }

    /**
     * getter for position
     *
     * @return position returns Type Position from class Position
     */
    public Position getPosition() {
        return position;
    }

}
