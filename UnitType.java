
public class UnitType {
	
	protected int health = 50; // default stats, what is given at the start
	protected int str = 5;
	protected int mag = 1;
	protected int def = 2;
	protected Location location;
	
	UnitType(){
		this.health = 50;
		this.str = 5;
		this.mag = 1;
		this.def = 2;
//		this.items = items;
		
	}
	
	public void gameOver(){
		System.exit(0);// ends program
		/// start program again 
		// goes to main menu
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int hpChange) {
		health = health + hpChange;
		if (health == 0){
			gameOver();
		}
		else if (health >= 50) {
			this.health = 50;
		}
	}
	//should we have gameover in the textapp, what if we're setting the health for enemies

	public int getStr() {
		return str;
	}

	public void setStr(int str) {
		this.str = str;
	}

	public int getMag() {
		return mag;
	}

	public void setMag(int mag) {
		this.mag = mag;
	}

	public int getDef() {
		return def;
	}

	public void setDef(int def) {
		this.def = def;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location loc) {
		this.location = loc;
	}
	
//	public ArrayList <Collectible> getCollectibles(){
//	return new ArrayList <Collectible> (items);
//}
	
	public boolean isAlive() {
		if(health > 0) {
			return true;
		}
		else {
			return false;
		}

	}

	
}
