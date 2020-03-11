package map;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import unit.Player;

public class LocateEnemies {
	public static boolean checkRanged(GridPane grid, Position p, Player player) { // Returns true if there is enemy diagonal
		if (Math.abs(player.getPosition().getX()-p.getX()) != 1 || Math.abs(player.getPosition().getY()-p.getY()) != 1) {
			return false;
		}
		TextField newPosition = MapSetup.getNode(grid, p);
		return newPosition.getText().equals(MapSetup.ENEMY);
	}
	
	public static boolean checkMelee(GridPane grid, Position p, Player player) { // Returns true if there is enemy adjacent
		if ((Math.abs(player.getPosition().getX()-p.getX()) == 1 && Math.abs(player.getPosition().getY()-p.getY()) == 1)){
			return false;
		}
		
		TextField newPosition = MapSetup.getNode(grid, p);
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
