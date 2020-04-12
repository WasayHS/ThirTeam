package unit;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import map.MapSetup;
import map.Position;

public class Boss extends Enemy{
	
	public Boss(int hp, int str, int mag, int def, int x, int y) {
		super(hp, str, mag, def, x, y);
	}
	
	

	public Boss(int hp, int str, int mag, int def, Position p) {
		super(hp, str, mag, def, p);
	}
	




}
