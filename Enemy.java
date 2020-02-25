public class Enemy{
	private int health = 25;
	private int str = 1;
	private int mag = 1;
	//private Location location;
	
	public Enemy() {
		this.health = 25;
		this.str = 1;
		this.mag = 1;
		
	}
	
	public Enemy(int health) {
		this.health = health;
	}
	
//Getters
	public int getHealth() {
		return health;
	}
	
	public int getStr() {
		return str;
	}
	
	public int getMag() {
		return mag;
	}
	
//	public Location getLocation() {
//		return location;
//	}
	
//setters
	public void setHP(int change) {
		health = health+change;
	}
	
	public void setStr(int s) {
		str = s;
	}
	
	public void setMag(int m) {
		mag = m;
	}
	
//	public void setLocation(Location l) {
//		location = l;
//	}
	
	public boolean isAlive() {
		if(health > 0) {
			return true;
		}
		else {
			return false;
		}

	}
	
	
	
}