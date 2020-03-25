package loot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import map.MapSetup;
import map.Position;
import unit.Stats;

public class Inventory {	
	public static final ImagePattern STR_POT = new ImagePattern(new Image("/entities/strPot.png"));
	public static final ImagePattern MAG_POT = new ImagePattern(new Image("/entities/magPot.png"));
	public static final ImagePattern DEF_POT = new ImagePattern(new Image("/entities/defPot.png"));
	public static Map<LootImg, LootStats> inventory = new HashMap<LootImg, LootStats>();
	
	private LootStats lootStats;
	private static LootImg image;
	
	public Inventory (int num) {
		if (num <= 10 && num >= 0) { //Strength potion
			this.lootStats = new LootStats(5, 0, 0);
			this.image = new LootImg(STR_POT);
		}
		else if (num >= 10 && num <= 20){
			this.lootStats = new LootStats(0, 5, 0);
			this.image = new LootImg(MAG_POT);
		}
		else {
			this.lootStats = new LootStats(0, 0, 5);
			this.image = new LootImg(DEF_POT);
		}
		
	}
	public LootStats getLootStats() {
		return lootStats;
	}

	public void setLootStats(LootStats lootStats) {
		this.lootStats = lootStats;
	}

	public LootImg getImage() {
		return image;
	}

	public void setImage(LootImg image) {
		this.image = image;
	}

}
