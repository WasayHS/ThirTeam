package loot;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.HashMap;
import java.util.Map;

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
			image = new LootImg(STR_POT);
		} else if (num >= 10 && num <= 20) {
			this.lootStats = new LootStats(0, 5, 0);
			image = new LootImg(MAG_POT);
		} else {
			this.lootStats = new LootStats(0, 0, 5);
			image = new LootImg(DEF_POT);
		}
	}

	public LootStats getLootStats() {
		return lootStats;
	}

	public LootImg getImage() {
		return image;
	}
}
