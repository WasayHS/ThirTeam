package map;

import unit.Enemy;

import java.util.*;

public class CreateHostileEntity {
    public static Map<Position, Enemy> enemyPos = new HashMap<>();
    public static List<Position> entityCoords = new ArrayList<>();

    public CreateHostileEntity (int mapSize, int hp, int dmg, int def, int mag) {
        Random randX = new Random();
        Random randY = new Random();

        for (int i = 0; i < mapSize; i++) {
            Position position;
            do {
                int x = randX.nextInt((mapSize-2))+1;
                int y = randY.nextInt((mapSize-2))+1;
                position = new Position(x,y);
            } while (entityCoords.contains(position) || position.getX() == (mapSize-2) || position.getX() == 1);
            entityCoords.add(position);
            enemyPos.put(position, new Enemy(hp, dmg, def, mag, position));
        }
    }



}
