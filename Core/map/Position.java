package map;

public class Position {
    private int x;
    private int y;

    /** Position(int, int)
     * constructor for Position
     *
     * @param x: Type int - the x coordinate of an entity
     * @param y: Type int - the y coordinate of an entity
     */
    public Position(int x, int y) {
        setX(x);
        setY(y);
    }

    // = = = = = = = = = = = = Setters and Getters for Position;
    public int getX() {
        return this.x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
