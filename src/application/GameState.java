package application;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import map.MapSetup;
import map.UnitLocation;

public class GameState {

	public void victory() {
		// After last boss
	}
	
	public void gameOver() {
		// When enemy attacks back
	}
	
	public void openPortal(GridPane grid, int x, int y, UnitLocation unit) { // Open portal to next round; event handling: send player to new map.
		if (MapSetup.checkMove(grid, x, y, unit)) {
			TextField tf = MapSetup.getNode(grid, x, y);
			tf.setText(MapSetup.PLAYER);
			tf.setStyle("-fx-text-inner-color: lightgreen;-fx-font-weight: bold;"); 
			
			TextField oldTf = MapSetup.getNode(grid, unit.getX(), unit.getY());
			oldTf.setText(MapSetup.EMPTY);
			unit.setX(x);
			unit.setY(y);
		}
	}
}
