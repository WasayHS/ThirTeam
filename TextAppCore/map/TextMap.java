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

/**
 * Class for the map battle map of the game
 * @author Bonnie's Computer
 *
 */
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

    /**
     * Prints the map with the current positions
     * of the player and other entities
     */
    public void printMap() {
        System.out.println(getEnemyCoords().size());
        for (int x = 0; x < getMapSize(); x++) {
            for (int y = 0; y < getMap()[0].length; y++) {
                this.map[x][y] = spawnEntityChar(x, y, getPlayer());
                System.out.print(this.map[x][y] + "  ");
            }
            System.out.println();
        }
    }

    /**
     * Loops through a list of Type Position
     *
     * @param list: Type List<Position> - A list of Position
     * @param c: Type char - the representation of player/enemy/terrain
     * @param x: Type int - x value to be compared by the Position x
     * @param y: Type int - x value to be compared by the Position x
     * @return boolean ; returns true if the given x,y is contained in the list of Position
     */
    public boolean loopPositionList(List<Position> list, int x, int y) {
        for (Position pos : list) {
            if ((x == pos.getX() && y == pos.getY())) {
                return true;
            }
        } return false;
    }

    /**
     * Returns the character appropriate to the given coordinates
     *
     * @param x: Type int - the x coordinate
     * @param y: Type int - the y coordinate
     * @param player: Type Player - Used to get the player's current position
     * @return char that represents an entitiy
     */

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

    /**
     * Updates the state of the map appropriate to the player's
     * current location and input coordinates
     *
     * @param x: Type int - user's x input
     * @param y: Type int - user's y input
     * @param player: Type Player - the current state of the player
     */
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

    /**
     * Drop probability of items after slaying an enemy
     *
     * @param position: Type Position - defines the position of where the item should spawn
     */

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

    /**
     * The method called when an enemy enters coordinates that contain an enemy
     * Engages the player to a battle
     *
     * @param x: Type int - x coordinate entered
     * @param y: Type int - y coordinate entered
     * @param player: Type Player - defines the player's current state
     * @param melee: Type boolean for the attack type
     * @param ranged: Type boolean for the attack type
     * @return boolean if the player engages in battle or not
     */

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

    /**
     * Checks if the input coordinates are diagonal to the player
     * and contains an enemy
     *
     * @param x: Type int - x coordinate entered
     * @param y: Type int - y coordinate entered
     * @param player: Type Player - defines the player's current state
     * @return boolean if the enemy is diagonal to player
     */
    public boolean checkRanged(int x, int y, Player player) {
        if ((Math.abs(player.getPosition().getX() - x) > 1) || (Math.abs(player.getPosition().getY() - y) > 1)
                || ((Math.abs(player.getPosition().getX() - x) != 1) && (Math.abs(player.getPosition().getY() - y) == 1))
                || (map[x][y] == '^')) {
            return false;
        }
        return map[x][y] == 'X' && this.enemyPos.containsKey(Objects.requireNonNull(enemy.getEnemy(x, y)).getPosition());
    }

    /**
     * Checks if the input coordinates are adjacent to the player
     * and contains an enemy
     *
     * @param x: Type int - x coordinate entered
     * @param y: Type int - y coordinate entered
     * @param player: Type Player - defines the player's current state
     * @return boolean if enemy is adjacent to player
     */
    public boolean checkMelee(int x, int y, Player player) {
        if (Math.abs(player.getPosition().getX()-x) > 1 || Math.abs(player.getPosition().getY()-y) > 1 ||
           ((Math.abs(player.getPosition().getX()-x) == 1) && (Math.abs(player.getPosition().getY()-y) == 1))
           || map[x][y] == '^') {
            return false;
        }
        return map[x][y] == 'X' && this.enemyPos.containsKey(Objects.requireNonNull(enemy.getEnemy(x, y)).getPosition());
    }

    /**
     * Checks if the input coordinates are adjacent to the player
     * and is not diagonal
     *
     * @param x: Type int - x coordinate entered
     * @param y: Type int - y coordinate entered
     * @param player: Type Player - defines the player's current state
     * @return boolean if the move is valid
     */
    public boolean checkMove(int x, int y, Player player) {
        if ((Math.abs(player.getPosition().getX()-x) > 1 || Math.abs(player.getPosition().getY()-y) > 1)
           || (Math.abs(player.getPosition().getX()-x) == 1 && Math.abs(player.getPosition().getY()-y) == 1)
           || (map[x][y] == '^')) {
                System.out.println("Invalid Move.");
                return false;
        }
        return (map[x][y] == '.') || (this.enemyCoords.size() == 0 && this.map[x][y] == 'O');
    }

    // = = = = = = = = = = = = Setters and Getters for TextMap
    
    /**
     *Setter for the spikes on the map 
     * @param mapSize int of the map size
     */
    public void setSpikes(int mapSize) {
        this.spikes = new HostileEntityState(mapSize);
    }

    /**
     * Setter for the enemy
     * @param mapSize int of the map size
     */
    public void setEnemy(int mapSize) {
        this.enemy = new HostileEntityState(mapSize);
    }

    /**
     * Setter for the map size
     * @param mapSize int of the map size
     */
    public void setMapSize(int mapSize) {
        this.mapSize = mapSize;
    }

    /**
     * Setter for the map
     * @param mapSize int of the map size
     */
    private void setMap(int mapSize) {
        this.map = new char [mapSize][mapSize];
    }

    /**
     * Setter for spike coordinates
     */
    private void setSpikesCoords() {
        this.spikesCoords = spikes.getEntityCoords();
    }

    /**
     * Setter for enemy coordinates
     */
    private void setEnemyCoords() {
        this.enemyCoords = enemy.getEntityCoords();
    }

    /**
     * Setter for enemy position
     */
    private void setEnemyPos () {
        this.enemyPos = enemy.getEnemyMap();
    }

    /**
     * Setter for player and location
     * @param x int for the x coordinate of the player
     * @param y int for the y coordinate of the player
     */
    public void setPlayer(int x, int y) {
        this.player = new Player(x, y);
    }

    /**
     * Getter for the map
     * @return char[][] of the map representation
     */
    public char[][] getMap() {
        return this.map;
    }

    /**
     * Getter for the map size
     * @return int of the map size
     */
    public int getMapSize() {
        return this.mapSize;
    }

    /**
     * Getter for the all the spike coordinates
     * @return List<Position> of all the spike coordinates
     */
    public List<Position> getSpikesCoords() {
        return this.spikesCoords;
    }

    /**
     * Getter for the enemy coordinates 
     * @return List<Position> of all the enemy coordinates
     */
    public List<Position> getEnemyCoords() {
        return this.enemyCoords;
    }

    /**
     * Getter for item coordinates
     * @return List<Position> of all the item coordinates
     */
    public List<Position> getItemCoords() {
        return itemCoords;
    }

    /**
     * Getter for the player
     * @return Player 
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Getter for enemy state
     * @return enemy
     */
    public HostileEntityState getEnemyState() {
        return enemy;
    }

}
