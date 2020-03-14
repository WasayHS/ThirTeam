package unit;

import java.util.Random;
public class Stats {
	
	private int health; // default stats, what is given at the start
	private int str;
	private int mag;
	private int def;
	private int defaultHP;
	
	public Stats(int hp, int str, int mag, int def){
		this.health = hp;
		this.str = str;
		this.mag = mag;
		this.def = def;
		setDefaultHP(hp);
//		this.items = items;
	}
	
	public int getDefaultHP() {
		return this.defaultHP;
	}
	
	public void setDefaultHP(int hp) {
		this.defaultHP = hp;
	}
	public int getHealth() {
		return health;
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

	public int getDef() {
		return def;
	}

	public void setDef(int def) {
		this.def = def;
	}

}