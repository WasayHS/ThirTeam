package map;

import unit.Enemy;

import java.util.*;

public class HostileEntityState {
    private List<Position> entityCoords = new ArrayList<>();
    private Map<Position, Enemy> enemyMap = new HashMap<>();

    public HostileEntityState(int mapSize) {
        setEntityCoords(mapSize);
        setEnemyMap(this.entityCoords);
    }

    private void setEntityCoords (int mapSize) {
        Random randX = new Random();
        Random randY = new Random();
        int enemyCount = (int)(mapSize*.85)-2;

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

    private void setEnemyMap(List<Position> list) {
        for (Position p : list) {
            this.enemyMap.put(p, new Enemy(25, 2, 0, 0, p));
        }
    }

    public Enemy getEnemy(int x, int y) {
        for (Map.Entry<Position, Enemy> entries : this.enemyMap.entrySet()) {
            if (entries.getKey().getX() == x && entries.getKey().getY() == y) {
                return entries.getValue();
            }
        }
        return null;
    }

    public List<Position> getEntityCoords() {
        return entityCoords;
    }

    public Map<Position, Enemy> getEnemyMap() {
        return enemyMap;
    }
}
