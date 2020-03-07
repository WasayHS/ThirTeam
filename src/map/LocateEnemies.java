package map;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


public class LocateEnemies {
	public static boolean checkRanged(GridPane grid, int x, int y, UnitLocation unit) { // Returns true if there is enemy diagonal
		if (Math.abs(unit.getX()-x) != 1 || Math.abs(unit.getY()-y) != 1) {
			return false;
		}
		TextField newPosition = MapSetup.getNode(grid, x, y);
		return newPosition.getText().equals(MapSetup.ENEMY);
	}
	
	public static boolean checkMelee(GridPane grid, int x, int y, UnitLocation unit) { // Returns true if there is enemy adjacent
		if ((Math.abs(unit.getX()-x) == 1 && Math.abs(unit.getY()-y) == 1)){
			return false;
		}
		
		TextField newPosition = MapSetup.getNode(grid, x, y);
		return newPosition.getText().equals(MapSetup.ENEMY);
	}
	
	public static int numberOfEnemies(GridPane grid) { // Iterates through each cell
		int enemies = 0;
		for (Node node : grid.getChildren()) {
			TextField tf = (TextField)node;
			System.out.println(tf.toString());
			if (tf.getText().equals(MapSetup.ENEMY)) {
				enemies++;
			}
		}
		return enemies;
	}
}
