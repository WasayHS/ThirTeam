package map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import application.ButtonEvents;
import application.Main;
import application.SceneChange;
import battle.BattleThread;
import battle.EnemyMove;
import loot.Inventory;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import unit.Boss;
import unit.Enemy;
import unit.Player;
import unit.Unit;

/**
 * Class to setup the map
 * @author Bonnie's Computer
 *
 */
public class MapSetup { 
	/*
	* Character References
	* Player: https://oco.itch.io/medieval-fantasy-character-pack-4
	* Enemy: https://jesse-m.itch.io/skeleton-pack
	* Boss: https://oco.itch.io/medieval-fantasy-character-pack-2
	* Other: https://0x72.itch.io/dungeontileset-ii
	*/
	public static final ImagePattern PLAYER_IMG = new ImagePattern(new Image("/entities/player.png"));
	public static final ImagePattern ENEMY_IMG = new ImagePattern(new Image("/entities/enemyFull.png"));
	public static final ImagePattern WALL_IMG = new ImagePattern(new Image("/entities/wall.png"));
	public static final ImagePattern EMPTY_IMG = new ImagePattern(new Image("/entities/floor.png"));
	public static final ImagePattern PORTAL_IMG = new ImagePattern(new Image("/entities/lockedDoor.png"));
	public static final ImagePattern TERRAIN_IMG = new ImagePattern(new Image("/entities/spikes.png"));
	public static final ImagePattern FLOOR_HOVER = new ImagePattern(new Image("/entities/floor_hover.png"));
	public static final ImagePattern ENEMY_HOVER = new ImagePattern(new Image("/entities/enemyFullHover.png"));
	public static final ImagePattern PLAYER_HOVER = new ImagePattern(new Image("/entities/playerHover.png"));
	public static final ImagePattern SPIKES_HOVER = new ImagePattern(new Image("/entities/spikesHover.png"));
	public static final ImagePattern LOCKED_HOVER = new ImagePattern(new Image("/entities/lockedHover.png"));
	public static final ImagePattern UNLOCKED = new ImagePattern(new Image("/entities/unlockedDoor.png"));
	public static final ImagePattern OPENED = new ImagePattern(new Image("/entities/openDoor.png"));
	public static final ImagePattern DEF_HOVER = new ImagePattern(new Image("/entities/defPotHover.png"));
	public static final ImagePattern STR_HOVER = new ImagePattern(new Image("/entities/strPotHover.png"));
	public static final ImagePattern MAG_HOVER = new ImagePattern(new Image("/entities/magPotHover.png"));
	public static final ImagePattern BOSS_IMG = new ImagePattern(new Image("/entities/bossFull.png"));
	public static final ImagePattern BOSS_HOVER = new ImagePattern(new Image("/entities/bossFullHover.png"));
	public static Map<Position, Boss> BOSS_POS = new HashMap<Position, Boss>();
	
	public static Map<Position, Enemy> ENEMY_POS = new HashMap<>();
	public static ArrayList <Integer> keys = new ArrayList<>();
	public static int num;
	
	/**
	 * Method to create a list of positions of the enemies locations
	 * @param count int of how many enemies are spawned
	 * @param size int of the size of the map
	 * @return List<Position> of the positions of the enemies
	 */
	public static List<Position> createEnemyPositions(int count, int size) {
		List<Position> enemies = new ArrayList<>();
		Position p;

		for (int i = 0; i < count; i++) {
			p = generatePosition(enemies, size);
			enemies.add(p);
			ENEMY_POS.put(p, new Enemy(10, 2, 0, 0, p));
		}
		return enemies;
	}

	/**
	 * Method to create a list of positions of the terrain locations
	 * @param count int of how many terrain locations were spawned
	 * @param size int of the size of the map
	 * @return List<Position> of the positions of the terrain locations
	 */
	public static List<Position> createTerrainPositions(int count, int size) {
		List<Position> terrain = new ArrayList<>();
		Position p;

		for (int i = 0; i < count; i++) {
			p = generatePosition(terrain, size);
			terrain.add(p);
		}
		return terrain;
	}

	/**
	 * Method to generate positions 
	 * @param listP List<Position> list of the positions to generate
	 * @param size int of the map size
	 * @return Position that was generated from the parameters
	 */
	private static Position generatePosition(List<Position> listP, int size) {
		Random randX = new Random();
		Random randY = new Random();
		Position p;

		do {
			int x = randX.nextInt((size - 2)) + 1;
			int y = randY.nextInt((size - 2)) + 1;
			p = new Position(x, y);
		} while (listP.contains(p) || p.getX() == size - 2 || p.getX() == 1);
		return p;
	}

	/**
	 * Method to set the boss position
	 * @param b Boss whose position is being set
	 * @return List<Position> of the location of the boss
	 */
	public static List<Position> setBossPosition(Boss b){
		List<Position> boss = new ArrayList<Position>();
		boss.add(b.getPosition()); BOSS_POS.put(b.getPosition(), b);
		return boss;
	}

	/**
	 * Method to get enemy
	 * @param x int for the row position to check
	 * @param y int for the column position to check
	 * @return Enemy if located at the passed parameters
	 */
	public static Enemy getEnemy(int x, int y) {
		for (Entry <Position, Enemy> entries : ENEMY_POS.entrySet()) {
			if (entries.getKey().getX() == x && entries.getKey().getY() == y) {
				return entries.getValue();
			}
		}
		return null;
	}

	/**
	 * Method to get Boss
	 * @param x int for the row position to check
	 * @param y int for the column position to check
	 * @return Boss if located at the passed parameters
	 */
	public static Boss getBoss(int x, int y){
		for (Entry <Position, Boss> entries : BOSS_POS.entrySet()) {
			if (entries.getKey().getX() == x && entries.getKey().getY() == y) {
				return entries.getValue();
			}
		}
		return null;
	}

	/**
	 * Method to check for valid player move
	 * grid will update if the move is valid
	 * @param grid GridPane of the entire map containing all the Rectangle nodes
	 * @param p Position of where the player is moving to
	 * @param player Player of the main character
	 * @return boolean if the move is valid
	 */
	public static boolean checkMove(GridPane grid, Position p, Player player) {
		Rectangle newPosition = getNode(grid, p);
		if (Math.abs(player.getPosition().getX()-p.getX()) > 1 || Math.abs(player.getPosition().getY()-p.getY()) > 1 ||
				(Math.abs(player.getPosition().getX()-p.getX()) == 1 && Math.abs(player.getPosition().getY()-p.getY()) == 1) || newPosition.getFill().equals(SPIKES_HOVER)) {
			return false;
		}
		return newPosition.getFill().equals(FLOOR_HOVER) || ( (numberOfEnemies(grid) == 0 || !isBossAlive(grid) )&& newPosition.getFill().equals(PORTAL_IMG)); //If location empty, moves, if no more enemies, portal to next level opens
	}

	/**
	 * Method to check for valid enemy move
	 * grid will update if the move is valid
	 * @param grid GridPane of the entire map containing all the Rectangle nodes
	 * @param newPos Position of where the enemy is moving to
	 * @return boolean if the move is valid
	 */
	public static boolean checkEnemyMove(GridPane grid, Position newPos){
		Rectangle newPosition = getNode(grid, newPos);
		boolean Move = !(newPosition.getFill().equals(TERRAIN_IMG) || newPosition.getFill().equals(PLAYER_IMG) || newPosition.getFill().equals(WALL_IMG) 
				         ||newPosition.getFill().equals(SPIKES_HOVER)|| newPosition.getFill().equals(PLAYER_HOVER)|| newPosition.getFill().equals(ENEMY_HOVER)
				         || newPosition.getFill().equals(ENEMY_IMG)|| newPosition.getFill().equals(PORTAL_IMG));
		return Move;
	}

	/**
	 * Method to get the Rectangle node based on position
	 * @param grid GridPane of the entire map containing all the Rectangle nodes
	 * @param p Position of the Rectangle node
	 * @return Rectangle of node based on parameters passed
	 */
	public static Rectangle getNode(GridPane grid, Position p) {
		for (Node node : grid.getChildren()) {
			if (grid.getRowIndex(node) == p.getX() && grid.getColumnIndex(node) == p.getY()) {
				return (Rectangle)node;
			}
		}
		return null;
	}
	
	/**
	 * Method to update the map per valid move of the player
	 * @param grid GridPane of the entire map containing all the Rectangle nodes
	 * @param newPosition Position of where the player is moving
	 * @param player Player of the main character
	 * @param window Stage of the battle screen
	 * @param b Boss to be passed to initateBattle if it is a boss level
	 */
	public static void updateGrid (GridPane grid, Position newPosition, Player player, Stage window, Boss b) {
		Rectangle cell = getNode(grid, newPosition);
		boolean melee = checkMelee(grid, newPosition, player);
		boolean ranged = checkRanged(grid, newPosition, player);
		
		if (cell.getFill().equals(OPENED)) {
			Label message = new Label ("The door will lead you to the next level.");
			ButtonEvents.continueBtn(message);
			SceneChange.mapSize+=2;
			SceneChange.newLevel(window);
		}
		if (numberOfEnemies(grid) == 0 && !isBossAlive(grid)) {
			Position p = new Position(0, SceneChange.mapSize/2);
			Rectangle door = getNode(grid, p);
			door.setFill(UNLOCKED);
		}
		if (checkMove(grid, newPosition, player)) {
			moveUnit(grid, player, newPosition);
			if (b!= null && isBossAlive(grid)) {
				EnemyMove.compareBossLocation(grid, b, newPosition);
			} else {
				EnemyMove.move(newPosition, grid);
			}
		}
		if (melee || ranged) {
			if (cell.getFill().equals(BOSS_HOVER)) {
				initiateBattle(b, player, grid, melee, ranged, newPosition, cell, window);
			} else {
				Enemy enemy = MapSetup.getEnemy(newPosition.getX(), newPosition.getY());
				initiateBattle(enemy, player, grid, melee, ranged, newPosition, cell, window);
			}
		}
		if (cell.getFill().equals(DEF_HOVER) || cell.getFill().equals(STR_HOVER) || cell.getFill().equals(MAG_HOVER)) {
			pickupItem(cell);
		}
	}

	/**
	 * Method to initiate the battle sequence
	 * Calls the BattleThread
	 * @param enemy Unit of the enemy
	 * @param player Player of the main character
	 * @param grid GridPane of the entire map containing all of the Rectangle nodes
	 * @param melee boolean if the player is engaging in melee
	 * @param ranged boolean if the player is engaging in ranged
	 * @param newPosition Position of the ???????
	 * @param cell Rectangle of where the battle is engaged; where the enemy is 
	 * @param window Stage for the battle 
	 */
	public static void initiateBattle(Unit enemy, Player player, GridPane grid, boolean melee, boolean ranged, Position newPosition, Rectangle cell, Stage window) {
		Stage hDisplay = new Stage();
		while(player.getStats().getHealth() > 0 && enemy.getStats().getHealth() > 0) {
			BattleThread battle = new BattleThread(grid, enemy, player, melee, ranged, newPosition, window, cell, hDisplay);
		}
		hDisplay.close();
	}

	/**
	 * Method to pickup the item/potion
	 * @param cell Rectangle of where the potion is being picked up from
	 */
	public static void pickupItem(Rectangle cell) {
		int key=0;
		if (cell.getFill().equals(DEF_HOVER)) {
			key = Inventory.getKey(DEF_HOVER);
		} else if (cell.getFill().equals(STR_HOVER)) {
			key = Inventory.getKey(STR_HOVER);
		} else {
			key = Inventory.getKey(MAG_HOVER);
		}
		Main.pickUpItemWindow(key, cell);
	}

	/**
	 * Method to drop possibly drop potion after an enemy is dead
	 * @param grid GridPane of the entire map containing all the Rectangle nodes
	 * @param unit Unit that will be replaced by the item/potion
	 * @return Inventory of the new potion that is dropped
	 */
	public static Inventory enemyDrop(GridPane grid, Unit unit) {
		Rectangle oldC = getNode(grid, unit.getPosition());
		boolean use = true;

		do{
			num = new Random().nextInt(30);
			for (int i = 0; i<keys.size(); i++){
				if(keys.get(i)==num){
					use = false;
				}
			}
		} while(!use);

		Inventory pot;
		pot = new Inventory(num);
		String potType = pot.getPotType(num);
		oldC.setFill(pot.getImageFromType(potType).getPot());
		return pot;
	}

	/**
	 * Method to move the Unit
	 * @param grid GridPane of the entire map containing all the Rectangle nodes
	 * @param unit Unit that is moving on the map
	 * @param newPosition Position of the new position that the unit is moving to
	 */
	public static void moveUnit(GridPane grid, Unit unit, Position newPosition) {
		Rectangle newC = getNode(grid, newPosition);
		if (unit instanceof Player) {
			newC.setFill(PLAYER_HOVER);
		} else if (unit instanceof Boss) {
			newC.setFill(BOSS_IMG);
		} else if (unit instanceof Enemy) {
			newC.setFill(ENEMY_IMG);
		}
		Rectangle oldC = getNode(grid, unit.getPosition());
		oldC.setFill(EMPTY_IMG);
		unit.getPosition().setX(newPosition.getX());
		unit.getPosition().setY(newPosition.getY());
	}

	/**
	 * Method to check if the player can perform a ranged attack
	 * Player must be diagonal to position with the enemy
	 * @param grid GridPane of the entire map containing all the Rectangle nodes
	 * @param p Position of the cell where the enemy is located
	 * @param player Player of the main character
	 * @return boolean if player can perform a melee attack or not
	 */
	public static boolean checkRanged(GridPane grid, Position p, Player player) { // Returns true if there is enemy diagonal
		if (Math.abs(player.getPosition().getX()-p.getX()) > 1 || Math.abs(player.getPosition().getY()-p.getY()) > 1 || (Math.abs(player.getPosition().getX()-p.getX()) != 1) && Math.abs(player.getPosition().getY() - p.getY()) == 1) {
			return false;
		}
		Rectangle newPosition = MapSetup.getNode(grid, p);

		return newPosition != null && (newPosition.getFill().equals(ENEMY_HOVER)||newPosition.getFill().equals(BOSS_HOVER));
	}
	
	/**
	 * Method to check if the player can perform a melee attack
	 * Player must be adjacent to position with the enemy
	 * @param grid GridPane of the entire map containing all the Rectangle nodes
	 * @param p Position of the cell where the enemy is located
	 * @param player Player of the main character
	 * @return boolean if player can perform a melee attack or not
	 */
	public static boolean checkMelee(GridPane grid, Position p, Player player) { // Returns true if there is enemy adjacent
		if (Math.abs(player.getPosition().getX()-p.getX()) > 1 || Math.abs(player.getPosition().getY()-p.getY()) > 1 ||(Math.abs(player.getPosition().getX()-p.getX()) == 1) && (Math.abs(player.getPosition().getY()-p.getY()) == 1)){
			return false;
		}
		Rectangle newPosition = MapSetup.getNode(grid, p);
		return newPosition != null && (newPosition.getFill().equals(ENEMY_HOVER)||newPosition.getFill().equals(BOSS_HOVER));
	}
	
	/**
	 * Method to check the number of enemies on the map
	 * Iterates through each Rectangle on the GridPane
	 * @param grid Gridpane of the entire map containing all the Rectangle nodes
	 * @return int of how many enemies remain on the grid
	 */
	public static int numberOfEnemies(GridPane grid) { // Iterates through each cell
		int enemies = 0;

		for (Node node : grid.getChildren()) {
			Rectangle cell = (Rectangle)node;
			boolean enemyExists = cell.getFill().equals(ENEMY_IMG) || cell.getFill().equals(ENEMY_HOVER) || cell.getFill().equals(BOSS_IMG) || cell.getFill().equals(BOSS_HOVER);
			if (enemyExists) {
				enemies++;
			}
		}
		return enemies;
	}

	/**
	 * Method to check if the boss is alive
	 * Iterates through each Rectangle on the GridPane
	 * @param grid Gridpane of the entire map containing all the Rectangle nodes
	 * @return boolean if the boss is alive or not
	 */
	public static boolean isBossAlive(GridPane grid) { // Iterates through each cell
		int b= 0;

		for (Node node : grid.getChildren()) {
			Rectangle cell = (Rectangle)node;
			boolean bossExists = cell.getFill().equals(BOSS_IMG) || cell.getFill().equals(BOSS_HOVER);
			if (bossExists) {
				b++;
			}
		}
		return b>=1;
	}
	
	/**
	 * Method to change the ImagePattern on the Rectangle where there is a hover
	 * @param cell Rectangle of the cell represented on the map
	 * @return ImagePattern to replace the previous ImagePattern on the cell
	 */
	public static ImagePattern enterHover(Rectangle cell) {
		if (cell.getFill().equals(EMPTY_IMG)) {return FLOOR_HOVER;}
		if (cell.getFill().equals(PLAYER_IMG)) {return PLAYER_HOVER;}
		if (cell.getFill().equals(ENEMY_IMG)) {return ENEMY_HOVER;}
		if (cell.getFill().equals(TERRAIN_IMG)) {return SPIKES_HOVER;}
		if (cell.getFill().equals(UNLOCKED)) {return OPENED;}
		if (cell.getFill().equals(PORTAL_IMG)) {return LOCKED_HOVER;}
		if (cell.getFill().equals(Inventory.DEF_POT)) {return DEF_HOVER;}
		if (cell.getFill().equals(Inventory.STR_POT)) {return STR_HOVER;}
		if (cell.getFill().equals(Inventory.MAG_POT)) {return MAG_HOVER;}
		if (cell.getFill().equals(BattleThread.ENEMY3)) {return BattleThread.ENEMY3_Hover;}
		if (cell.getFill().equals(BattleThread.ENEMY2)) {return BattleThread.ENEMY2_Hover;}
		if (cell.getFill().equals(BattleThread.ENEMY1)) {return BattleThread.ENEMY1_Hover;}
		if (cell.getFill().equals(BattleThread.BOSS3)) {return BattleThread.BOSS3_HOVER;}
		if (cell.getFill().equals(BattleThread.BOSS2)) {return BattleThread.BOSS2_HOVER;}
		if (cell.getFill().equals(BattleThread.BOSS1)) {return BattleThread.BOSS1_HOVER;}
		if (cell.getFill().equals(BOSS_IMG)) {return BOSS_HOVER;}
		return WALL_IMG;
	}
	
	/**
	 * Method to change the ImagePattern on the Rectangle when there is no hover
	 * @param cell Rectangle of the cell represented on the map
	 * @return ImagePattern to replace the previous ImagePattern on the cell
	 */
	public static ImagePattern exitHover(Rectangle cell) {
		if (cell.getFill().equals(FLOOR_HOVER)) {return EMPTY_IMG;}
		if (cell.getFill().equals(PLAYER_HOVER)) {return PLAYER_IMG;}
		if (cell.getFill().equals(ENEMY_HOVER)) {return ENEMY_IMG;}
		if (cell.getFill().equals(BOSS_HOVER)) {return BOSS_IMG;}
		if (cell.getFill().equals(SPIKES_HOVER)) {return TERRAIN_IMG;}
		if (cell.getFill().equals(OPENED)) {return UNLOCKED;}
		if (cell.getFill().equals(LOCKED_HOVER)) {return PORTAL_IMG;}
		if (cell.getFill().equals(BOSS_HOVER)) {return BOSS_IMG;}
		if (cell.getFill().equals(DEF_HOVER)) {return Inventory.DEF_POT;}
		if (cell.getFill().equals(STR_HOVER)) {return Inventory.STR_POT;}
		if (cell.getFill().equals(MAG_HOVER)) {return Inventory.MAG_POT;}
		if (cell.getFill().equals(BattleThread.ENEMY3_Hover)) {return BattleThread.ENEMY3;}
		if (cell.getFill().equals(BattleThread.ENEMY2_Hover)) {return BattleThread.ENEMY2;}
		if (cell.getFill().equals(BattleThread.ENEMY1_Hover)) {return BattleThread.ENEMY1;}
		if (cell.getFill().equals(BattleThread.BOSS3_HOVER)) {return BattleThread.BOSS3;}
		if (cell.getFill().equals(BattleThread.BOSS2_HOVER)) {return BattleThread.BOSS2;}
		if (cell.getFill().equals(BattleThread.BOSS1_HOVER)) {return BattleThread.BOSS1;}
		return WALL_IMG;
	}
}
