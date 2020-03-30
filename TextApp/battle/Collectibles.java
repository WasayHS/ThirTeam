package battle;
import java.util.ArrayList;

public class Collectibles {

	public ArrayList<String> battlePowerups(){ // We can change the name of the collectibles later. For now it's just a description on what it does.
	    ArrayList<String> collectibles = new ArrayList<String>();
	    collectibles.add("Increase Strength by 3");
	    collectibles.add("Increase Defense by 2");
	    collectibles.add("Increase Magic by 1");
		return collectibles;
	 }
	
// Can add more methods for collectibles after boss drops, in places on the map and such... Just need everyone to finalize specific ideas on what these are going to be.
	
	/* public static void main(String[] args){  Example of Main method to print the array of collectibles.
		Collectibles c = new Collectibles();
		System.out.println(c.battlePowerups());
	}*/
	
}
	
	
	
