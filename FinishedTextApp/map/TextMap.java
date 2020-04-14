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
    private char [][] map;
    private int mapSize;
    private List<Position> spikesCoords = new ArrayList<>();
    private List<Position> enemyCoords = new ArrayList<>();
    private Map<Position, Enemy> enemyPos = new HashMap<>();
    private Player player;
    private HostileEntityState enemy;
    private HostileEntityState spikes;
    private BorderedStrings title = new LevelTitle();
    private BorderedStrings choices = new OptionsText();

    public static List<Position> itemCoords = new ArrayList<>();

    public TextMap(int mapSize) {
        setEnemy(mapSize);
        setSpikes(mapSize);
        setMapSize(mapSize);
        setMap(mapSize);
        setEnemyCoords();
        setEnemyPos();
        setSpikesCoords();
        setPlayer(mapSize-2, mapSize/2);
    }

    public void printMap() {
        for (int x = 0; x < getMapSize(); x++) {
            for (int y = 0; y < getMap()[0].length; y++) {
                this.map[x][y] = spawnEntityChar(x, y, getPlayer());
                System.out.print(this.map[x][y] + "  ");
            }
            System.out.println();
        }
    }

    public boolean loopPositionList(List<Position> list, int x, int y) {
        for (Position pos : list) {
            if ((x == pos.getX() && y == pos.getY())) {
                return true;
            }
        } return false;
    }

    public char spawnEntityChar(int x, int y, Player player) {
        if (loopPositionList(getEnemyCoords(), x, y)) { return 'X'; }
        if (loopPositionList(getItemCoords(), x, y)) { return '&'; }
        if (loopPositionList(getSpikesCoords(), x, y)) { return '^'; }

        if (x == player.getPosition().getX() && y == player.getPosition().getY()) {
            return 'P';
        } else if (x == 0 && y == getMapSize()/2) {
            return 'O';
        } else if (x == 0 || x == getMapSize()-1 || y == 0 || y == getMapSize()-1) {
            return '#';
        } else {
            return '.';
        }
    }

    public void updateMap(int x, int y, Player player) {
        boolean melee = checkMelee(x, y, player);
        boolean ranged = checkRanged(x, y, player);
        Position position = new Position(x, y);

        if ((melee||ranged) && getMap()[x][y] == 'X') {
            enemyCoordsEntered(x, y, player, melee, ranged);

            if (player.getStats().getHealth() <= 0) {
                return;
            }
            this.enemyCoords.remove(enemy.getEnemy(x,y).getPosition());
            this.enemyPos.remove(enemy.getEnemy(x, y).getPosition());
            dropProbability(position);
        } else if (checkMove(x, y, player)) {
            if (getEnemyCoords().size() == 0 && (x == 0 && y == getMapSize()/2)) {
                System.out.println("Next level");
                TextApp.nextLevel(this.enemyCoords);
            }
            player.getPosition().setX(x);
            player.getPosition().setY(y);
        }

        printMap();
    }

    public void dropProbability(Position position) {
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

    public boolean enemyCoordsEntered(int x, int y, Player player, boolean melee, boolean ranged) {
        Position position = new Position(x, y);
        choices.printBox("Engage in a battle?", "1. Yes", "2. No");

        if (UserInput.validUserChoice(2) == 1) {
            Enemy enemy = getEnemyState().getEnemy(x,y);
            EngageBattle.battleEngaged(player, enemy, position, melee, ranged);
            return true;
        }
        System.out.print("You fled from the enemy.");
        return false;
    }

    public boolean checkRanged(int x, int y, Player player) {
        if ((Math.abs(player.getPosition().getX() - x) > 1) || (Math.abs(player.getPosition().getY() - y) > 1)
                || ((Math.abs(player.getPosition().getX() - x) != 1) && (Math.abs(player.getPosition().getY() - y) == 1))
                || (map[x][y] == '^')) {
            return false;
        }
        return map[x][y] == 'X' && this.enemyPos.containsKey(Objects.requireNonNull(enemy.getEnemy(x, y)).getPosition());
    }

    public boolean checkMelee(int x, int y, Player player) {
        if (Math.abs(player.getPosition().getX()-x) > 1 || Math.abs(player.getPosition().getY()-y) > 1 ||
           ((Math.abs(player.getPosition().getX()-x) == 1) && (Math.abs(player.getPosition().getY()-y) == 1))
           || map[x][y] == '^') {
            return false;
        }
        return map[x][y] == 'X' && this.enemyPos.containsKey(Objects.requireNonNull(enemy.getEnemy(x, y)).getPosition());
    }

    public boolean checkMove(int x, int y, Player player) {
        if ((Math.abs(player.getPosition().getX()-x) > 1 || Math.abs(player.getPosition().getY()-y) > 1)
           || (Math.abs(player.getPosition().getX()-x) == 1 && Math.abs(player.getPosition().getY()-y) == 1)
           || (map[x][y] == '^')) {
                System.out.println("Invalid Move.");
                return false;
        }
        return (map[x][y] == '.') || (this.enemyCoords.size() == 0 && this.map[x][y] == 'O');
    }

    public void setSpikes(int mapSize) {
        this.spikes = new HostileEntityState(mapSize);
    }

    public void setEnemy(int mapSize) {
        this.enemy = new HostileEntityState(mapSize);
    }

    public void setMapSize(int mapSize) {
        this.mapSize = mapSize;
    }

    private void setMap(int mapSize) {
        this.map = new char [mapSize][mapSize];
    }

    private void setSpikesCoords() {
        this.spikesCoords = spikes.getEntityCoords();
    }

    private void setEnemyCoords() {
        this.enemyCoords = enemy.getEntityCoords();
    }

    private void setEnemyPos () {
        this.enemyPos = enemy.getEnemyMap();
    }

    public void setPlayer(int x, int y) {
        this.player = new Player(x, y);
    }

    public char[][] getMap() {
        return this.map;
    }

    public int getMapSize() {
        return this.mapSize;
    }

    public List<Position> getSpikesCoords() {
        return this.spikesCoords;
    }

    public List<Position> getEnemyCoords() {
        return this.enemyCoords;
    }

    public List<Position> getItemCoords() {
        return itemCoords;
    }

    public Player getPlayer() {
        return player;
    }

    public HostileEntityState getEnemyState() {
        return enemy;
    }

}
