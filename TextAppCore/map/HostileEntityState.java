package map;

import unit.Enemy;

import java.util.*;

public class HostileEntityState {
    private List<Position> entityCoords = new ArrayList<>();
    private Map<Position, Enemy> enemyMap = new HashMap<>();

    /**
     * Constructor
     *
     * @param mapSize: Type int - the size of the map
     */
    public HostileEntityState(int mapSize) {
        setEntityCoords(mapSize);
        setEnemyMap(this.entityCoords);
    }

    /**
     * Generates positions for non friendly entities (enemy and terrain(spikes))
     *
     * @param mapSize: Type int - the size of the map
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

    /**
     * Creates a map with Position as the key and instances of Enemy as the value
     *
     * @param list: Type List<Position> - a list of the enemy's positions
     */
    private void setEnemyMap(List<Position> list) {
        for (Position p : list) {
            this.enemyMap.put(p, new Enemy(25, 2, 0, 0, p));
        }
    }

    /**
     * Method to get the value (Enemy) from the designated position
     *
     * @param x: Type int - the x value to obtain the enemy
     * @param y: Type int - the y value to obtain the enemy
     */
    public Enemy getEnemy(int x, int y) {
        for (Map.Entry<Position, Enemy> entries : this.enemyMap.entrySet()) {
            if (entries.getKey().getX() == x && entries.getKey().getY() == y) {
                return entries.getValue();
            }
        }
        return null;
    }

    /**
     * Getter for entity coordinates 
     * @return List<Position> list of the entity coordinates
     */
    public List<Position> getEntityCoords() {
        return entityCoords;
    }

    /**
     * Getter for the enemy HashMap 
     * @return Map<Position,Enemy> HashMap of the position and the corresponding enemy
     */
    public Map<Position, Enemy> getEnemyMap() {
        return enemyMap;
    }
}
