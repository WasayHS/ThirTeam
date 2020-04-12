package map;

import battle.EngageBattle;
import main.UserInput;
import printFormat.BorderedStrings;
import printFormat.LevelTitle;
import printFormat.OptionsText;
import unit.Enemy;
import unit.Player;

import java.util.*;

public class TextMap {
    public static int mapSize = 9;
    public static char [][] map = new char [mapSize][mapSize];
    public static Player player = new Player(TextMap.mapSize-2, TextMap.mapSize/2);

    private static BorderedStrings title = new LevelTitle();
    private static BorderedStrings choices = new OptionsText();

    static List<Position> enemyCoords = hostileEntityPos(mapSize, 25,2,0,3);
    static List<Position> spikesCoords = hostileEntityPos(mapSize, 25,2,0,3);
    static Map<Position, Enemy> enemyPos = createEnemyMap(enemyCoords);

    public static void printMap() {
        System.out.println(enemyPos.size());
        System.out.println(spikesCoords.size());
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                map[x][y] = spawnEntityChar(x, y, player);
                System.out.print(map[x][y] + "  ");
            }
            System.out.println();
        }
    }

    public static char spawnEntityChar(int x, int y, Player player) {
        for (Position pos: enemyPos.keySet()) {
            if (x == pos.getX() && y == pos.getY()) {
                return 'X';
            }
        }

        for (Position pos: spikesCoords) {
            if (x == pos.getX() && y == pos.getY()) {
                return '^';
            }
        }

        if (x == player.getPosition().getX() && y == player.getPosition().getY()) {
            return 'P';
        } else if (x == 0 || x == mapSize-1 || y == 0 || y == mapSize-1) {
            return '#';
        } else if (x == 0 && y == mapSize/2) {
            return 'O';
        } else {
            return '.';
        }
    }

    public static void updateMap(int x, int y, Player player) {
        boolean melee = checkMelee(x, y, player);
        boolean ranged = checkRanged(x, y, player);

        if (checkMove(x, y, player)) {
            map[player.getPosition().getX()][player.getPosition().getY()] = '.';
            player.getPosition().setX(x);
            player.getPosition().setY(y);
            printMap();
        } else if (melee||ranged) {
            if (enemyCoordsEntered(x, y, player, melee, ranged)) {
                enemyPos.remove(getEnemy(x,y).getPosition());
            }
            printMap();
        }
    }

    public static List<Position> hostileEntityPos(int mapSize, int hp, int dmg, int def, int mag) {
        List<Position> entityCoords = new ArrayList<>();
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
        }
        return entityCoords;
    }

    public static Map<Position, Enemy> createEnemyMap(List<Position> list) {
        Map<Position, Enemy> enemMap = new HashMap<>();
        for (Position p : list) {
            enemMap.put(p, new Enemy(25, 2, 0, 0, p));
        }
        return enemMap;
    }

    public static Enemy getEnemy(int x, int y) {
        for (Map.Entry<Position, Enemy> entries : enemyPos.entrySet()) {
            if (entries.getKey().getX() == x && entries.getKey().getY() == y) {
                return entries.getValue();
            }
        }
        return null;
    }

    public static boolean enemyCoordsEntered(int x, int y, Player player, boolean melee, boolean ranged) {
        Position position = new Position(x, y);
        choices.printBox("Engage in a battle?", "1. Yes", "2. No");

        if (UserInput.validUserChoice(2) == 1) {
            System.out.println("Engaged");
            EngageBattle.battleEngaged(player, position, melee, ranged);
            return true;
        }
        System.out.printf("You fled from the enemy.");
        return false;
    }

    public static boolean checkRanged(int x, int y, Player player) {
        if (Math.abs(player.getPosition().getX()-x) > 1 || Math.abs(player.getPosition().getY()-y) > 1 || ((Math.abs(player.getPosition().getX()-x) != 1) && Math.abs(player.getPosition().getY() - y) == 1) || map[x][y] == '^') {
            return false;
        }
        return map[x][y] == 'X' && enemyPos.containsKey(getEnemy(x,y).getPosition());
    }

    public static boolean checkMelee(int x, int y, Player player) {
        if (Math.abs(player.getPosition().getX()-x) > 1 || Math.abs(player.getPosition().getY()-y) > 1 || ((Math.abs(player.getPosition().getX()-x) == 1) && (Math.abs(player.getPosition().getY()-y) == 1)) || map[x][y] == '^') {
            return false;
        }
        return map[x][y] == 'X' && enemyPos.containsKey(getEnemy(x,y).getPosition());
    }

    public static boolean checkMove(int x, int y, Player player) {
        if ((Math.abs(player.getPosition().getX()-x) > 1 || Math.abs(player.getPosition().getY()-y) > 1) || (Math.abs(player.getPosition().getX()-x) == 1 && Math.abs(player.getPosition().getY()-y) == 1) || (map[x][y] == '^')) {
                System.out.println("Invalid Move.");
                return false;
        }
        return (map[x][y] == '.') || (enemyCoords.size() == 0 && map[x][y] == 'O');
    }

}
