package map;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import application.Main;
import application.SceneChange;
import battle.BattleThread;
import battle.EnemyMove;
import loot.Inventory;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import unit.Enemy;
import unit.Player;
import unit.Unit;

public class MapSetup { 
	public static final ImagePattern playerImg = new ImagePattern(new Image("/entities/player.png"));
	public static final ImagePattern enemyImg = new ImagePattern(new Image("/entities/enemyFull.png"));
	public static final ImagePattern wallImg = new ImagePattern(new Image("/entities/wall.png"));
	public static final ImagePattern emptyImg = new ImagePattern(new Image("/entities/floor.png"));
	public static final ImagePattern portalImg = new ImagePattern(new Image("/entities/lockedDoor.png"));
	public static final ImagePattern terrainImg = new ImagePattern(new Image("/entities/spikes.png"));
	public static final ImagePattern floor_hover = new ImagePattern(new Image("/entities/floor_hover.png"));
	public static final ImagePattern enemy_hover = new ImagePattern(new Image("/entities/enemyFullHover.png"));
	public static final ImagePattern player_hover = new ImagePattern(new Image("/entities/playerHover.png"));
	public static final ImagePattern spikes_hover = new ImagePattern(new Image("/entities/spikesHover.png"));
	public static final ImagePattern locked_hover = new ImagePattern(new Image("/entities/lockedHover.png"));
	public static final ImagePattern unlocked = new ImagePattern(new Image("/entities/unlockedDoor.png"));
	public static final ImagePattern opened = new ImagePattern(new Image("/entities/openDoor.png"));
	public static final ImagePattern def_hover = new ImagePattern(new Image("/entities/defPotHover.png"));
	public static final ImagePattern str_hover = new ImagePattern(new Image("/entities/strPotHover.png"));
	public static final ImagePattern mag_hover = new ImagePattern(new Image("/entities/magPotHover.png"));
	
	public static Map<Position, Enemy> ENEMY_POS = new HashMap<Position, Enemy>();
	public static int num;
	
	public static List<Position> createEnemyPositions(int count, int size, boolean bool){ //Returns list of enemy coords
		List<Position> enemies = new ArrayList<Position>();
		List<Position> terrain = new ArrayList<Position>();
		Random randX = new Random();
		Random randY = new Random();
		
		for (int i = 0; i < count; i++) { // Randomizing x,y
			Position p;
			do { // Keep randomizing values for x,y while enemies spawn on same location
				int x = randX.nextInt((size-2))+1;
				int y = randY.nextInt((size-2))+1;
				p = new Position(x, y);
				
			} while(enemies.contains(p) || p.getX() == size-2 || terrain.contains(p) || p.getX() == 1);
				if (bool) {terrain.add(p);}
				else {enemies.add(p); ENEMY_POS.put(p, new Enemy(25, 2, 0, 0, p));}
		}
			if (bool) {return terrain;}
			else {return enemies;}
	}

	public static Enemy getEnemy(int x, int y) {
		Position coords = new Position(x,y);
		for (Entry <Position, Enemy> entries : ENEMY_POS.entrySet()) {
			if (entries.getKey().getX() == x && entries.getKey().getY() == y) {
				return entries.getValue();
			}
		}
		return null;
	}
	
//	- - - - - Checks for valid move; if move is valid, grid is updated
	public static boolean checkMove(GridPane grid, Position p, Player player) {
		Rectangle newPosition = getNode(grid, p);
		if (Math.abs(player.getPosition().getX()-p.getX()) > 1 || Math.abs(player.getPosition().getY()-p.getY()) > 1 ||
				(Math.abs(player.getPosition().getX()-p.getX()) == 1 && Math.abs(player.getPosition().getY()-p.getY()) == 1) || newPosition.getFill().equals(spikes_hover)) {
			return false;
		}
		return newPosition.getFill().equals(floor_hover) || (numberOfEnemies(grid) == 0 && newPosition.getFill().equals(portalImg)); //If location empty, moves, if no more enemies, portal to next level opens
	}

//  - - - - - Check for valid enemy move; if move is valid, grid is updated
	public static boolean checkEnemyMove(GridPane grid, Position newPos, Enemy e){
		Rectangle newPosition = getNode(grid,newPos);
		boolean Move = !(newPosition.getFill().equals(terrainImg) || newPosition.getFill().equals(playerImg) || newPosition.getFill().equals(wallImg) 
				         ||newPosition.getFill().equals(spikes_hover)|| newPosition.getFill().equals(player_hover)|| newPosition.getFill().equals(enemy_hover)
				         || newPosition.getFill().equals(enemyImg));
		
		return Move;
	}
//		- - - - - Get the 'char' in each cell; Returns "P", "E" etc..
	public static Rectangle getNode(GridPane grid, Position p) {
		for (Node node : grid.getChildren()) {
			if (grid.getRowIndex(node) == p.getX() && grid.getColumnIndex(node) == p.getY()) {
				return (Rectangle)node;
			}
		}
		return null;
	}
	
//		- - - - - Updates map per valid move
	public static void updateGrid(GridPane grid, Position newPosition, Player player, Stage window) {
		Rectangle cell = getNode(grid, newPosition);
		Inventory pot = new Inventory(num);
		
		boolean enemyDrop = cell.getFill().equals(pot.getImage().getPot());
		boolean melee = checkMelee(grid, newPosition, player);
		boolean ranged = checkRanged(grid, newPosition, player);
		
		if (cell.getFill().equals(opened)) {
			Label message = new Label ("The door will lead you to the next level.");
			Main.continueBtn(message);
			SceneChange.mapSize+=2;
			SceneChange.newLevel(window);
		}
		
		if (numberOfEnemies(grid) == 0) {
			Position p = new Position(0, SceneChange.mapSize/2);
			Rectangle door = getNode(grid, p);
			door.setFill(unlocked);
		}
		
		if (checkMove(grid, newPosition, player)){ 
			moveUnit(grid, player, newPosition);
			EnemyMove.move(newPosition, grid);
		}
		
		if (melee || ranged) {
			Enemy enemy = MapSetup.getEnemy(newPosition.getX(), newPosition.getY());
			while(player.getStats().getHealth() > 0 && enemy.getStats().getHealth() > 0) {
				BattleThread battle = new BattleThread(grid, player, melee, ranged, newPosition, cell, window);
				}
		}
		if (cell.getFill().equals(def_hover) || cell.getFill().equals(str_hover) || cell.getFill().equals(mag_hover)) {
			Main.pickUpItemWindow(grid, pot, newPosition, cell, player);
		}
	}
	
	public static void moveUnit(GridPane grid, Unit unit, Position newPosition) {
		Rectangle newC = getNode(grid, newPosition);
		if (unit instanceof Player){
			newC.setFill(player_hover);
		}else if (unit instanceof Enemy){ 
			newC.setFill(enemyImg);
		}
		Rectangle oldC = getNode(grid, unit.getPosition());
		oldC.setFill(emptyImg);
		unit.getPosition().setX(newPosition.getX());
		unit.getPosition().setY(newPosition.getY());
	}
	
//	- - - - - - - - Enemy drops/ looting items
	public static void enemyDrop(GridPane grid, Unit unit, Position newPosition) {
		Rectangle oldC = getNode(grid, unit.getPosition());
		num = new Random().nextInt(30);
		Inventory pot = new Inventory(num);
		oldC.setFill(pot.getImage().getPot());
	}
//	- - - - - - - - Checking enemies around player
	public static boolean checkRanged(GridPane grid, Position p, Player player) { // Returns true if there is enemy diagonal
		if (Math.abs(player.getPosition().getX()-p.getX()) > 1 || Math.abs(player.getPosition().getY()-p.getY()) > 1 || (Math.abs(player.getPosition().getX()-p.getX()) != 1) && !(Math.abs(player.getPosition().getY()-p.getY()) != 1)) {
			return false;
		}
		Rectangle newPosition = MapSetup.getNode(grid, p);
		return newPosition.getFill().equals(enemy_hover);
	}
	
	public static boolean checkMelee(GridPane grid, Position p, Player player) { // Returns true if there is enemy adjacent
		if (Math.abs(player.getPosition().getX()-p.getX()) > 1 || Math.abs(player.getPosition().getY()-p.getY()) > 1 ||(Math.abs(player.getPosition().getX()-p.getX()) == 1) && (Math.abs(player.getPosition().getY()-p.getY()) == 1)){
			return false;
		}
		
		Rectangle newPosition = MapSetup.getNode(grid, p);
		return newPosition.getFill().equals(enemy_hover);
	}
	
	public static int numberOfEnemies(GridPane grid) { // Iterates through each cell
		int enemies = 0;
		for (Node node : grid.getChildren()) {
			Rectangle cell = (Rectangle)node;
			if (cell.getFill().equals(enemyImg) || cell.getFill().equals(enemy_hover)) {
				enemies++;
			}
		}
		return enemies;
	}
	
	public static ImagePattern enterHover(GridPane grid, Position p, Rectangle cell) {
		if (cell.getFill().equals(emptyImg)) {return floor_hover;}
		if (cell.getFill().equals(playerImg)) {return player_hover;}
		if (cell.getFill().equals(enemyImg)) {return enemy_hover;}
		if (cell.getFill().equals(terrainImg)) {return spikes_hover;}
		if (cell.getFill().equals(unlocked)) {return opened;}
		if (cell.getFill().equals(portalImg)) {return locked_hover;}
		if (cell.getFill().equals(Inventory.DEF_POT)) {return def_hover;}
		if (cell.getFill().equals(Inventory.STR_POT)) {return str_hover;}
		if (cell.getFill().equals(Inventory.MAG_POT)) {return mag_hover;}
		if (cell.getFill().equals(BattleThread.enemy3)) {return BattleThread.enemy3Hover;}
		if (cell.getFill().equals(BattleThread.enemy2)) {return BattleThread.enemy2Hover;}
		if (cell.getFill().equals(BattleThread.enemy1)) {return BattleThread.enemy1Hover;}
		return wallImg;
	}
	
	public static ImagePattern exitHover(GridPane grid, Position p, Rectangle cell) {
		if (cell.getFill().equals(floor_hover)) {return emptyImg;}
		if (cell.getFill().equals(player_hover)) {return playerImg;}
		if (cell.getFill().equals(enemy_hover)) {return enemyImg;}
		if (cell.getFill().equals(spikes_hover)) {return terrainImg;}
		if (cell.getFill().equals(opened)) {return unlocked;}
		if (cell.getFill().equals(locked_hover)) {return portalImg;}
		if (cell.getFill().equals(def_hover)) {return Inventory.DEF_POT;}
		if (cell.getFill().equals(str_hover)) {return Inventory.STR_POT;}
		if (cell.getFill().equals(mag_hover)) {return Inventory.MAG_POT;}
		if (cell.getFill().equals(BattleThread.enemy3Hover)) {return BattleThread.enemy3;}
		if (cell.getFill().equals(BattleThread.enemy2Hover)) {return BattleThread.enemy2;}
		if (cell.getFill().equals(BattleThread.enemy1Hover)) {return BattleThread.enemy1;}
		return wallImg;
	}
}