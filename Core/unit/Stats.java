package unit;

public class Stats {
	
	private int health; // default stats, what is given at the start
	private int str;
	private int mag;
	private int defaultHP;
	private int def;
	
	public Stats(int hp, int str, int mag, int def){
		this.health = hp;
		this.str = str;
		this.mag = mag;
		setDefaultHP(hp);
	}
	public Stats(Stats s){
		this.health = s.getHealth();
		this.def = s.getDef();
		this.mag = s.getMag();
		
	}
	public int getDefaultHP() {
		return this.defaultHP;
	}
	
	public void setDefaultHP(int hp) {
		this.defaultHP = hp;
	}

	public int getHealth() {
		if (this.health <= 0) {
			return 0;
		}
		else {
			return health;
		}
	}

	public void setHealth(int hpChange) {
		int newHP = health + hpChange;

		if (health <= getDefaultHP()) {
			this.health = newHP;
		}
		else if (health > getDefaultHP()){
			this.health = getDefaultHP(); // Max player/enemy health
		}
		else if (health <= 0) {
			this.health = 0;
		}
	}

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

	public void setDef(int def) {
		this.def = def;
		
	}
	
	public int getDef(){
		return def;
	}


}