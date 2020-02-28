
public class Player extends UnitType {
	
	public Player(){
	super();
	this.location = new Location(4,2);
	}
	
	// Jose: I put the stat raising methods here. Can't do it in another class as it will be undefined for Player class and it's the Player stats we need to increase when user chooses a collectible.  
	
	public int addStr() { // Method to raise strength when user picks item that increases strength. 
		str += 3; 
		return str;
		
	}
	
	public int addMag() { // Method to raise magic when user picks item that increases magic.
		mag += 1;
		return mag;
		
	}
	
	public int addDef() { // Method to raise defense when user picks item that increases defense.
		def += 2;
		return def;
	}
	
	
}
