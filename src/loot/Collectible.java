package loot;

import java.util.ArrayList;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import unit.Stats;

// As of now, the parent class stores items to the Inventory class; Can do stat here as well.
public class Collectible {
	
	private Inventory items;
	//Private Stats stat;
	//Somehow update player stats depending on the pot they use.
	
	public Collectible(/* add, params, here*/){
		System.out.println("Collectible");
		setItems(new Inventory(/* add, params, here*/));
	}
	
	public Inventory getItems() {
		return items;
	}
	
	public void setItems(Inventory items) {
		this.items = items;
	}

}
