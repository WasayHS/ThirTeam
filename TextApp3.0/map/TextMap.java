package map;

import battle.EngageBattle;
import loot.TextInventory;
import main.TextApp;
import main.UserInput;
import printFormat.BorderedStrings;
import printFormat.LevelTitle;
import printFormat.OptionsText;
import unit.Enemy;
import unit.Player;

import java.util.*;

public class TextMap {
    public static char [][] map = new char [TextApp.mapSize][TextApp.mapSize];

    public static Player player = new Player(TextApp.mapSize-2, TextApp.mapSize/2);
    public static List<Position> spikesCoords = HostileEntityState.hostileEntityPos(TextApp.mapSize);
    public static List<Position> enemyCoords = HostileEntityState.hostileEntityPos(TextApp.mapSize);
    public static Map<Position, Enemy> enemyPos = HostileEntityState.createEnemyMap(enemyCoords);
    public static List<Position> itemCoords = new ArrayList<>();

    private static BorderedStrings title = new LevelTitle();
    private static BorderedStrings choices = new OptionsText();

    public static void printMap() {

        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                map[x][y] = spawnEntityChar(x, y, player);
                System.out.print(map[x][y] + "  ");
            }
            System.out.println();
        }
    }

    public static boolean loopPositionList(List<Position> list, char c, int x, int y) {
        for (Position pos : list) {
            if ((x == pos.getX() && y == pos.getY())) {
                return true;
            }
        } return false;
    }

    public static char spawnEntityChar(int x, int y, Player player) {
        if (loopPositionList(enemyCoords, 'X', x, y)) { return 'X'; }
        if (loopPositionList(itemCoords, '&', x, y)) { return '&'; }
        if (loopPositionList(spikesCoords, '^', x, y)) {
            return '^';
        }

        if (x == player.getPosition().getX() && y == player.getPosition().getY()) {
            return 'P';
        } else if (x == 0 && y == TextApp.mapSize/2) {
            return 'O';
        } else if (x == 0 || x == TextApp.mapSize-1 || y == 0 || y == TextApp.mapSize-1) {
            return '#';
        } else {
            return '.';
        }
    }

    public static void updateMap(int x, int y, Player player) {
        boolean melee = checkMelee(x, y, player);
        boolean ranged = checkRanged(x, y, player);
        Position position = new Position(x, y);

        if ((melee||ranged) && map[x][y] == 'X') {
            enemyCoordsEntered(x, y, player, melee, ranged);

            if (player.getStats().getHealth() <= 0) {
                return;
            }
            enemyCoords.remove(HostileEntityState.getEnemy(x,y).getPosition());
            enemyPos.remove(HostileEntityState.getEnemy(x, y).getPosition());
            dropProbability(position);

        } else if (checkMove(x, y, player)) {
            if (enemyCoords.size() == 0 && map[x][y] == 'O') {
//                newLevel ()
            }
            player.getPosition().setX(x);
            player.getPosition().setY(y);
        }
        printMap();
    }

    public static void dropProbability(Position position) {
        boolean dropChace = new Random().nextInt(1)==0;
        TextInventory inventory = new TextInventory(position);

        if (dropChace) {
            itemCoords = TextInventory.enemyDrop(position);
            printMap();
            choices.printBox("The enemy dropped an item!", "Pick up the item?", " ", "          1. Yes", "          2. No");

            if (UserInput.validUserChoice(2) == 1) {
                TextInventory.pickUpItem(position);
                String itemPickup = String.format("You obtained a %s !", inventory.getLootName().getPot());
                title.printBox(itemPickup, "The item was added to your inventory.");
            }
        }
    }

    public static boolean enemyCoordsEntered(int x, int y, Player player, boolean melee, boolean ranged) {
        Position position = new Position(x, y);
        choices.printBox("Engage in a battle?", "1. Yes", "2. No");

        if (UserInput.validUserChoice(2) == 1) {
            EngageBattle.battleEngaged(player, position, melee, ranged);
            return true;
        }
        System.out.print("You fled from the enemy.");
        return false;
    }

    public static boolean checkRanged(int x, int y, Player player) {
        if ((Math.abs(player.getPosition().getX() - x) > 1) || (Math.abs(player.getPosition().getY() - y) > 1)
                || ((Math.abs(player.getPosition().getX() - x) != 1) && (Math.abs(player.getPosition().getY() - y) == 1))
                || (map[x][y] == '^')) {
            return false;
        }
        return map[x][y] == 'X' && enemyPos.containsKey(Objects.requireNonNull(HostileEntityState.getEnemy(x, y)).getPosition());
    }

    public static boolean checkMelee(int x, int y, Player player) {
        if (Math.abs(player.getPosition().getX()-x) > 1 || Math.abs(player.getPosition().getY()-y) > 1 ||
           ((Math.abs(player.getPosition().getX()-x) == 1) && (Math.abs(player.getPosition().getY()-y) == 1))
           || map[x][y] == '^') {
            return false;
        }
        return map[x][y] == 'X' && enemyPos.containsKey(Objects.requireNonNull(HostileEntityState.getEnemy(x, y)).getPosition());
    }

    public static boolean checkMove(int x, int y, Player player) {
        if ((Math.abs(player.getPosition().getX()-x) > 1 || Math.abs(player.getPosition().getY()-y) > 1)
           || (Math.abs(player.getPosition().getX()-x) == 1 && Math.abs(player.getPosition().getY()-y) == 1)
           || (map[x][y] == '^')) {
                System.out.println("Invalid Move.");
                return false;
        }
        return (map[x][y] == '.') || (enemyCoords.size() == 0 && map[x][y] == 'O');
    }

}
