package unit;

import map.Position;

public class Unit {

    private Stats stats;
    private Position position;

    /** Unit(int, int, int, int, int, int)
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

    /** Unit(int, int, int, int, int, Position)
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

    /* getStats()
     * getter for stats
     *
     * @return stats: returns Type Stats from class Stats
     */
    public Stats getStats() {
        return stats;
    }

    /* getPosition()
     * getter for position
     *
     * @return position: returns Type Position from class Position
     */
    public Position getPosition() {
        return position;
    }

}
