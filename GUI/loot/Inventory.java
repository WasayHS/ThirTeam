package loot;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import unit.Player;
import unit.Stats;

public class Inventory {	
	public static final ImagePattern STR_POT = new ImagePattern(new Image("/entities/strPot.png"));
	public static final ImagePattern MAG_POT = new ImagePattern(new Image("/entities/magPot.png"));
	public static final ImagePattern DEF_POT = new ImagePattern(new Image("/entities/defPot.png"));
	public static Map< Integer,LootImg> inventory = new HashMap< Integer,LootImg>();
	
	private static LootImg image;
	
	public Inventory (int num) {
		if (num <= 10 && num >= 0) { //Strength potion
			this.image = new LootImg(STR_POT);
			
		}
		else if (num >= 10 && num <= 20){
			this.image = new LootImg(MAG_POT);
		}
		else {
			this.image = new LootImg(DEF_POT);
		}
		inventory.put(num, image );

	}
	public static LootStats getLootStats(String type) {
		if(type.equals("str")){
			return new LootStats (3,0,0);
		}
		if(type.equals("mag")){
			return new LootStats (0,3,0);
		}
		if(type.equals("def")){
			return new LootStats (0,0,3);
		}
		return null;
	}

	

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
	
	
	
	public static void  use(String s, Player p){
		System.out.println(s);
		LootStats l = getLootStats(s);
		LootImg img =getImageFromType(s);
		Stats playerOldStats;
		Stats newStats;
		if(img.getPot().equals(STR_POT)){/// works
			playerOldStats = p.getStats();
			int oldStr = playerOldStats.getStr();		
			System.out.println("old "+oldStr+"+"+l.getStr());
			
			int strInc = l.getStr();
			newStats = playerOldStats;
			newStats.setStr(oldStr+strInc);
			p.setStats(newStats);
			System.out.println(p.getStats().getStr());
		}
		if(img.getPot().equals(DEF_POT)){
			playerOldStats = p.getStats();
			int oldDef = playerOldStats.getDef();
			System.out.println("old "+oldDef+"+"+l.getDef());
			
			int defInc = l.getDef();
			newStats = playerOldStats;
			newStats.setDef(oldDef+defInc);
			p.setStats(newStats);
			System.out.println(p.getStats().getDef());
		}
		if(img.getPot().equals(MAG_POT)){
			playerOldStats = p.getStats();
			int magInc = l.getMag();
			int oldMag = playerOldStats.getMag();
			System.out.println("old "+oldMag+"+"+magInc);
			
			newStats = playerOldStats;
			newStats.setMag(oldMag+magInc);
			p.setStats(newStats);
			System.out.println(p.getStats().getMag());
		}
	}
	
	

}
