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
import loot.Collectible;
import loot.StrPotion;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import unit.Enemy;
import unit.Player;
import unit.Unit;

public class MapSetup { 
	// Add photos to a package. Path is "/packagename/imgname.png"
	// To compare a node/cell to another, rectangle.getFill().equals(one of the ImagePatterns)
	public static final ImagePattern playerImg = new ImagePattern(new Image("/entities/player.png"));
	public static final ImagePattern enemyImg = new ImagePattern(new Image("/entities/enemy.png"));
	public static final ImagePattern wallImg = new ImagePattern(new Image("/entities/wall.png"));
	public static final ImagePattern emptyImg = new ImagePattern(new Image("/entities/floor.png"));
	public static final ImagePattern portalImg = new ImagePattern(new Image("/entities/door.png"));
	public static final ImagePattern terrainImg = new ImagePattern(new Image("/entities/spikes.png"));
	public static final ImagePattern strPot = new ImagePattern(new Image("/entities/dmgPot.png"));
	
	public static Map<Position, Enemy> ENEMY_POS = new HashMap<Position, Enemy>();
	
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
				else {enemies.add(p);ENEMY_POS.put(p, new Enemy(25, 2, 0, 0, p));}
				// Loops stops when p is not similar to any of the coordinates in p list.
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
		if (Math.abs(player.getPosition().getX()-p.getX()) > 1 || Math.abs(player.getPosition().getY()-p.getY()) > 1 || (Math.abs(player.getPosition().getX()-p.getX()) == 1 && Math.abs(player.getPosition().getY()-p.getY()) == 1)) {
			return false;
		}
		
		Rectangle newPosition = getNode(grid, p);
		return newPosition.getFill().equals(emptyImg) || (numberOfEnemies(grid) == 0 && newPosition.getFill().equals(portalImg)); //If location empty, moves, if no more enemies, portal to next level opens
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
	public static void updateGrid(GridPane grid, Position newPosition, Player player) {
		if (checkMove(grid, newPosition, player)){ 
				Rectangle cell = getNode(grid, newPosition);
				cell.setFill(playerImg);
				moveUnit(grid, player, newPosition);
		}
		boolean melee = checkMelee(grid, newPosition, player);
		boolean ranged = checkRanged(grid, newPosition, player);
		
		if (melee || ranged) {
			Rectangle node = getNode(grid, newPosition);
			Main.startBattle(grid, player, melee, ranged, newPosition, node);
		}	
	}
	
	public static void moveUnit(GridPane grid, Unit unit, Position newPosition) {
		Rectangle oldC = getNode(grid, unit.getPosition());
		oldC.setFill(emptyImg);
		unit.getPosition().setX(newPosition.getX());
		unit.getPosition().setY(newPosition.getY());
	}

	public static void enemyDrop(Collectible pot, GridPane grid, Unit unit, Position newPosition) {
		Rectangle oldC = getNode(grid, unit.getPosition());
			if (pot instanceof StrPotion) oldC.setFill(strPot); // If the parent class is StrPot, sets image drop to str
//			if (pot instanceof DefPotion) oldC.setFill(defPot);
//			if (pot instanceof MagPotion) oldC.setFill(magPot);
	}
	
//	- - - - - - - - Checking enemies around player
	public static boolean checkRanged(GridPane grid, Position p, Player player) { // Returns true if there is enemy diagonal
		if (Math.abs(player.getPosition().getX()-p.getX()) > 1 || Math.abs(player.getPosition().getY()-p.getY()) > 1 || (Math.abs(player.getPosition().getX()-p.getX()) != 1) && !(Math.abs(player.getPosition().getY()-p.getY()) != 1)) {
			return false;
		}
		Rectangle newPosition = MapSetup.getNode(grid, p);
		return newPosition.getFill().equals(enemyImg);
	}
	
	public static boolean checkMelee(GridPane grid, Position p, Player player) { // Returns true if there is enemy adjacent
		if (Math.abs(player.getPosition().getX()-p.getX()) > 1 || Math.abs(player.getPosition().getY()-p.getY()) > 1 ||(Math.abs(player.getPosition().getX()-p.getX()) == 1) && (Math.abs(player.getPosition().getY()-p.getY()) == 1)){
			return false;
		}
		
		Rectangle newPosition = MapSetup.getNode(grid, p);
		return newPosition.getFill().equals(enemyImg);
	}
	
	public static int numberOfEnemies(GridPane grid) { // Iterates through each cell
		int enemies = 0;
		for (Node node : grid.getChildren()) {
			Rectangle cell = (Rectangle)node;
			if (cell.getFill().equals(enemyImg)) {
				enemies++;
			}
		}
		return enemies;
	}
}

