package map;

import unit.Enemy;

import java.util.*;

public class HostileEntityState {

    public static List<Position> hostileEntityPos(int mapSize) {
        List<Position> enemies = new ArrayList<Position>();
        Random randX = new Random();
        Random randY = new Random();

        for (int i = 0; i < mapSize-3; i++) {
            Position p;
            do {
                int x = randX.nextInt((mapSize-2))+1;
                int y = randY.nextInt((mapSize-2))+1;
                p = new Position(x, y);

            } while(enemies.contains(p) || p.getX() == mapSize-2 || p.getX() == 1);
            enemies.add(p);
        }
        return enemies;
    }

    public static Map<Position, Enemy> createEnemyMap(List<Position> list) {
        Map<Position, Enemy> enemMap = new HashMap<>();
        for (Position p : list) {
            enemMap.put(p, new Enemy(25, 2, 0, 0, p));
        }
        return enemMap;
    }

    public static Enemy getEnemy(int x, int y) {
        for (Map.Entry<Position, Enemy> entries : TextMap.enemyPos.entrySet()) {
            if (entries.getKey().getX() == x && entries.getKey().getY() == y) {
                return entries.getValue();
            }
        }
        return null;
    }
}
