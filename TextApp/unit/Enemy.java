package unit;

import map.Position;

public class Enemy extends Unit {

    /* Enemy(int, int, int, int, Position)
     * Constructor: calls the superclass Unit
     *
     * @param hp: Type int - the enemy's hp
     * @param str: Type int - the enemy's str value
     * @param mag: Type int - the enemy's mag value
     * @param def: Type int - the enemy's def value
     * @param p: Type Position - the current (x,y) value of the enemy
     */

    public Enemy(int hp, int str, int mag, int def, Position p) {
        super (hp, str, mag, def, p);
    }
}
