package loot;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import map.MapSetup;

// Saves the player's loot in a list; Max loot is 10
public class Inventory {
	
	private List<Collectible> items = new ArrayList<Collectible>();
	private Collectible loot;
	
	public Inventory (/* add, params, here*/) {
		setItems(loot);
	}

	public List<Collectible> getItems() { // Returns list of items
		return items;
	}

	public void setItems(Collectible loot) { // Inventory max 10
		if (items.size() <= 10) {
			this.items.add(loot);
		}
		//else{
			// window popup "inventory full"
		//}
	}
	
}
