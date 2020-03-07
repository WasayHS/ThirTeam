package map;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class MapSetup { 
	public static final String PLAYER = "P";
	public static final String ENEMY = "E";
	public static final String WALL = "-";
	public static final String EMPTY = "";
	public static final String PORTAL = "O";
	
	public static List<Point> createEnemyPositions(int count, int size){ //Returns list of enemy coords
		List<Point> enemies = new ArrayList<Point>(); //<Point> = (x,y); list of enemy coords
		Random randX = new Random();
		Random randY = new Random();
	
		for (int i = 0; i < count; i++) { // Randomizing enemy spawn
			Point p;
			do { // Keep randomizing values for x,y while enemies spawn on same location
				int x = randX.nextInt((size-2))+1;
				int y = randY.nextInt((size-2))+1;
				p = new Point(x,y);
				
			} while(enemies.contains(p) || p.x == size-2); 
			enemies.add(p); // Loops stops when p is not similar to any of the coordinates in p list.
		}
		return enemies;
	}

//	- - - - - Checks for valid move; if move is valid, grid is updated
	public static boolean checkMove(GridPane grid, int x, int y, UnitLocation unit) {
		if (Math.abs(unit.getX()-x) > 1 || Math.abs(unit.getY()-y) > 1 || (Math.abs(unit.getX()-x) == 1 && Math.abs(unit.getY()-y) == 1)) {
			return false;
		}
		
		TextField newPosition = getNode(grid, x, y);
		return newPosition.getText().equals(EMPTY) || (LocateEnemies.numberOfEnemies(grid) == 0 && newPosition.getText().equals(PORTAL)); //If location empty, moves, if no more enemies, portal to next level opens
	}

//		- - - - - Get the 'char' in each cell; Returns "P", "E" etc..
	public static TextField getNode(GridPane grid, int x, int y) {
		for (Node node : grid.getChildren()) {
			if (grid.getRowIndex(node) == x && grid.getColumnIndex(node) == y) {
				return (TextField)node;
			}
		}
		return null;
	}
	
//		- - - - - Updates map per valid move/ enemy death
	public static void updateGrid(GridPane grid, int x, int y, UnitLocation unit) {
		if (checkMove(grid, x, y, unit) /*|| UnitType.getHP() <= 0*/){ //UnitType would be instanced to enemy in this case.
			if (checkMove(grid, x, y, unit)){
				TextField tf = getNode(grid, x, y);
				tf.setText(PLAYER);
				tf.setStyle("-fx-text-inner-color: lightgreen;-fx-font-weight: bold;"); 
			}	
			TextField oldTf = getNode(grid, unit.getX(), unit.getY());
			oldTf.setText(EMPTY);
			unit.setX(x);
			unit.setY(y);
			
		}
		
	}
}
