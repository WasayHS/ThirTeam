package unit;

public class Player extends Unit {

    /* Player(int, int)
     * Constructor: calls the superclass Unit
     *
     * @param x: Type int which is the x value
     * @param y: Type int which is the y value
     */

    public Player (int x, int y) {
        super (50, 25, 25, 0, x, y);
    }
}
