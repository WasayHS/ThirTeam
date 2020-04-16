package map;

/**
 * Class to instantiate positions
 * @author Bonnie's Computer
 *
 */
public class Position {
    private int x;
    private int y;

    /** 
     * constructor for Position
     *
     * @param x int of the x coordinate of an entity
     * @param y int of the y coordinate of an entity
     */
    public Position(int x, int y) {
        setX(x);
        setY(y);
    }

    /**
     * Getter for the x coordinate of an entity
     * @return int for the x coordinate of an entity
     */
    public int getX() {
        return this.x;
    }
    
    /**
     * Setter for the x coordinate of an entity
     * @param x int for the new x coordinate of an entity
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Getter for the y coordinate of an entity
     * @return int for the y coordinate of an entity
     */
    public int getY() {
        return this.y;
    }

    /**
     * Setter for the y coordinate of an entity
     * @param y int for the new y coordinate of an entity
     */
    public void setY(int y) {
        this.y = y;
    }
}
