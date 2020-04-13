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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import map.MapSetup;
import map.Position;
import unit.Player;
import unit.Stats;

public class Inventory {	
	public static final ImagePattern STR_POT = new ImagePattern(new Image("/entities/strPot.png"));
	public static final ImagePattern MAG_POT = new ImagePattern(new Image("/entities/magPot.png"));
	public static final ImagePattern DEF_POT = new ImagePattern(new Image("/entities/defPot.png"));
	public static Map< Integer,LootImg> inventory = new HashMap< Integer,LootImg>();
	
	private LootStats lootStats;
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
			return new LootStats (0,0,5);
		}
		if(type.equals("mag")){
			return new LootStats (0,5,0);
		}
		if(type.equals("def")){
			return new LootStats (5,0,0);
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
		LootStats l = getLootStats(s);
		LootImg img =getImageFromType(s);
		int playerOldStat;
		if(img.equals(STR_POT)){
			playerOldStat = p.getStats().getStr();
			System.out.println("old "+playerOldStat);
			int strInc = l.getStr();
			p.getStats().setStr(playerOldStat+strInc);
			System.out.println(playerOldStat);
		}
		if(img.equals(DEF_POT)){
			playerOldStat = p.getStats().getDef();
			System.out.println("old "+playerOldStat);
			int defInc = l.getDef();
			p.getStats().setDef(playerOldStat + defInc);
			System.out.println(playerOldStat);
		}
		if(img.equals(MAG_POT)){
			int magInc = l.getMag();
			playerOldStat = p.getStats().getMag();
			System.out.println("old "+playerOldStat);
			p.getStats().setMag(playerOldStat + magInc);
			System.out.println(playerOldStat);
		}
	}
	
	

}
