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
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import unit.Enemy;
import unit.Player;
import unit.Unit;

public class MapSetup { 
	public static final String PLAYER = "P";
	public static final String ENEMY = "E";
	public static final String WALL = "-";
	public static final String EMPTY = "";
	public static final String PORTAL = "O";
	public static Map<Position, Enemy> ENEMY_POS = new HashMap<Position, Enemy>();
	
	public static List<Position> createEnemyPositions(int count, int size){ //Returns list of enemy coords
		List<Position> enemies = new ArrayList<Position>(); //<Point> = (x,y); list of enemy coords
		Random randX = new Random();
		Random randY = new Random();
		
		for (int i = 0; i < count; i++) { // Randomizing enemy spawn
			Position p;
			do { // Keep randomizing values for x,y while enemies spawn on same location
				int x = randX.nextInt((size-2))+1;
				int y = randY.nextInt((size-2))+1;
				p = new Position(x, y);
				
			} while(enemies.contains(p) || p.getX() == size-2); 
			enemies.add(p); // Loops stops when p is not similar to any of the coordinates in p list.
			ENEMY_POS.put(p, new Enemy(25, 2, 0, 0, p));
		}
		return enemies;
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
		
		TextField newPosition = getNode(grid, p);
		return newPosition.getText().equals(EMPTY) || (LocateEnemies.numberOfEnemies(grid) == 0 && newPosition.getText().equals(PORTAL)); //If location empty, moves, if no more enemies, portal to next level opens
	}

//		- - - - - Get the 'char' in each cell; Returns "P", "E" etc..
	public static TextField getNode(GridPane grid, Position p) {
		for (Node node : grid.getChildren()) {
			if (grid.getRowIndex(node) == p.getX() && grid.getColumnIndex(node) == p.getY()) {
				return (TextField)node;
			}
		}
		return null;
	}
	
//		- - - - - Updates map per valid move
	public static void updateGrid(GridPane grid, Position newPosition, Player player) {
		
		if (checkMove(grid, newPosition, player)){ 
				TextField tf = getNode(grid, newPosition);
				tf.setText(PLAYER);
				tf.setStyle("-fx-text-inner-color: lightgreen;-fx-font-weight: bold;"); 
				moveUnit(grid, player, newPosition);
		}
		
		boolean melee = LocateEnemies.checkMelee(grid, newPosition, player);
		boolean ranged = LocateEnemies.checkRanged(grid, newPosition, player);
		
		if (melee || ranged) {
			TextField node = getNode(grid, newPosition);
			Main.startBattle(grid, player, melee, ranged, newPosition, node);
		}	
	}
	
	public static void moveUnit(GridPane grid, Unit unit, Position newPosition) {
		TextField oldTf = getNode(grid, unit.getPosition());
		oldTf.setText(EMPTY);
		unit.getPosition().setX(newPosition.getX());
		unit.getPosition().setY(newPosition.getY());
	}
}

