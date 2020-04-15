package loot;

import main.GameFiles;
import map.Position;
import map.TextMap;

import java.util.*;

/**
 * Class for the inventory of player in the game
 * @author Bonnie's Computer
 *
 */
public class TextInventory {
	public static final String STR_POT = "Strength Potion";
	public static String MAG_POT = "Magic Potion";
	public static String DEF_POT = "Defense Potion";
	public static Map<LootName, LootStats> inventory = new LinkedHashMap<LootName, LootStats>();
	private static LootStats lootStats;
	private static LootName potName;

	/**
	 * Constructor for TextInventory
	 *
	 * @param position: Type Position - position of where the loot was dropped
	 */
	public TextInventory(Position position) {
		int lootType = new Random().nextInt(30);

		if (lootType <= 10 && lootType >= 0) { //Strength potion
			this.lootStats = new LootStats(5, 0, 0);
			potName = new LootName(STR_POT);
		} else if (lootType >= 10 && lootType <= 20) {
			this.lootStats = new LootStats(0, 5, 0);
			potName = new LootName(MAG_POT);
		} else {
			this.lootStats = new LootStats(0, 0, 5);
			potName = new LootName(DEF_POT);
		}
	}

	/**
	 * Adds the coordinates of where the items should spawn
	 *
	 * @param position: Type Position - the position of the item
	 * @return itemCoords: returns a list of item positions
	 */
	public static List<Position> enemyDrop(Position position) {
		List<Position> itemCoords = new ArrayList<>();
		itemCoords.add(position);

		return itemCoords;
	}

	/**
	 * this picks up the items and adds to inventory
	 *
	 * @param pos: Type Position to define the position
	 */
	public static void pickUpItem(Position pos) {
		inventory.put(potName, lootStats);
		TextMap.itemCoords.remove(pos);
		GameFiles.addToInventory(potName.toString(), lootStats.toString());
	}

	/**
	 * getter for loot name
	 *
	 * @return potName: returns Type LootName
	 */
	public LootName getLootName() {
		return potName;
	}


}
