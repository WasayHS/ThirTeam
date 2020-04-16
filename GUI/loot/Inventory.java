package loot;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import unit.Player;
import unit.Stats;

/**
 * Class for the player inventory for potions
 * @author Bonnie's Computer
 *
 */
public class Inventory {	
	public static final ImagePattern STR_POT = new ImagePattern(new Image("/entities/strPot.png"));
	public static final ImagePattern MAG_POT = new ImagePattern(new Image("/entities/magPot.png"));
	public static final ImagePattern DEF_POT = new ImagePattern(new Image("/entities/defPot.png"));
	public static Map< Integer,LootImg> inventory = new HashMap< Integer,LootImg>();
	
	private static LootImg image;
	
	/**
	 * Constructor for the class to create potions and put them into the Map
	 * @param num int to determine which potion is dropped
	 */
	public Inventory (int num) {
		if (num <= 10 && num >= 0) { //Strength potion is given
			Inventory.image = new LootImg(STR_POT);
			
		}
		else if (num >= 10 && num <= 20){ // Magic Potion is given
			Inventory.image = new LootImg(MAG_POT);
		}
		else { // Defense Potion given
			Inventory.image = new LootImg(DEF_POT);
		}
		inventory.put(num, image );

	}
	
	/**
	 * Method to get the stats of the type of potion
	 * @param type String representing the type of potion
	 * @return LootStats for the potion required
	 */
	public static LootStats getLootStats(String type) {
		if(type.equals("str")){
			return new LootStats (3,0,0);
		}
		if(type.equals("mag")){
			return new LootStats (0,3,0);
		}
		if(type.equals("def")){
			return new LootStats (0,0,2);
		}
		return null;
	}

	/**
	 * Method to get the image of the type of potion
	 * @param type String of the type of potion
	 * @return LootImg of the image of the potion
	 */
	public static LootImg getImageFromType(String type) {
		if(type.equals("str")){
			return new LootImg (STR_POT);
		}
		if(type.equals("mag")){
			return new LootImg (MAG_POT);
		}
		if(type.equals("def")){
			return new LootImg (DEF_POT);
		}
		return null;
	}
	
	/**
	 * Method to get the key of the potion in inventory from the image
	 * @param img ImagePattern of the potion
	 * @return int of the key
	 */
	public static int getKey(ImagePattern img){
		for (int i = 0; i<31; i++){
			if(Inventory.inventory.containsKey(i)){
				if (Inventory.inventory.get(i).getPot().equals(img)){
					return i;
				}
			}
		}
		return 0;
		
	}

	/**
	 * Method to get the potion type from the key 
	 * @param key int of the potion in the inventory
	 * @return String of the type of potion
	 */
	public static String getPotType(int key){
		ImagePattern img = inventory.get(key).getPot();
				
		if(img.equals(MAG_POT)){
			return "mag";
		}
		if(img.equals(DEF_POT)){
			return "def";
		}
		if(img.equals(STR_POT)){
			return "str";
		}
		return null;
		
	}
	
	/**
	 * Method to use the potion depending on type of potion
	 * potion increases players stats
	 * @param s String of the type of potion
	 * @param p Player with stats that will change
	 */
	public static void use(String s, Player p){
		LootStats l = getLootStats(s);
		LootImg img =getImageFromType(s);
		Stats playerOldStats;
		Stats newStats;
		if(img.getPot().equals(STR_POT)){
			playerOldStats = p.getStats();
			int oldStr = playerOldStats.getStr();
			
			int strInc = l.getStr();
			newStats = playerOldStats;
			newStats.setStr(oldStr+strInc);
			p.setStats(newStats);
			
		}
		if(img.getPot().equals(DEF_POT)){
			playerOldStats = p.getStats();
			int oldDef = playerOldStats.getDef();
			
			int defInc = l.getDef();
			newStats = playerOldStats;
			newStats.setDef(oldDef+defInc);
			p.setStats(newStats);
			
		}
		if(img.getPot().equals(MAG_POT)){
			playerOldStats = p.getStats();
			int magInc = l.getMag();
			int oldMag = playerOldStats.getMag();
			
			newStats = playerOldStats;
			newStats.setMag(oldMag+magInc);
			p.setStats(newStats);
			
		}
	}
	
	

}
