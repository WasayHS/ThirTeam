package map;

import unit.Enemy;

import java.util.*;

public class HostileEntityState {
    private List<Position> entityCoords = new ArrayList<>();
    private Map<Position, Enemy> enemyMap = new HashMap<>();

    /* HostileEntityState(int)
     * Constructor
     *
     * @param mapSize: Type int - the size of the map
     */
    public HostileEntityState(int mapSize) {
        setEntityCoords(mapSize);
        setEnemyMap(this.entityCoords);
    }

    /* setEntityCoords(int)
     * Generates positions for non friendly entities (enemy and terrain(spikes))
     *
     * @param mapSize: Type int - the size of the map
     * @return void
     */
    private void setEntityCoords (int mapSize) {
        Random randX = new Random();
        Random randY = new Random();
        int enemyCount = (int)(mapSize*.70)-1;

        for (int i = 0; i < enemyCount; i++) {
            Position p;
            do {
                int x = randX.nextInt((mapSize-2))+1;
                int y = randY.nextInt((mapSize-2))+1;
                p = new Position(x, y);

            } while(this.entityCoords.contains(p) || p.getX() == mapSize-2 || p.getX() == 1);
            entityCoords.add(p);
        }
    }

    /* setEnemyMap(List<Position>)
     * Creates a map with Position as the key and instances of Enemy as the value
     *
     * @param list: Type List<Position> - a list of the enemy's positions
     * @return void
     */
    private void setEnemyMap(List<Position> list) {
        for (Position p : list) {
            this.enemyMap.put(p, new Enemy(25, 2, 0, 0, p));
        }
    }

    /* getEnemy(int, int)
     * Method to get the value (Enemy) from the designated position
     *
     * @param x: Type int - the x value to obtain the enemy
     * @param y: Type int - the y value to obtain the enemy
     * @return void
     */
    public Enemy getEnemy(int x, int y) {
        for (Map.Entry<Position, Enemy> entries : this.enemyMap.entrySet()) {
            if (entries.getKey().getX() == x && entries.getKey().getY() == y) {
                return entries.getValue();
            }
        }
        return null;
    }

    // = = = = = = = = = = = = = Setters and Getters for HostileEntityState
    public List<Position> getEntityCoords() {
        return entityCoords;
    }

    public Map<Position, Enemy> getEnemyMap() {
        return enemyMap;
    }
}
