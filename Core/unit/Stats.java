package unit;

/**
 * Class for the stats of the units
 * @author Bonnie's Computer
 *
 */
public class Stats {
	
	private int health; // default stats, what is given at the start
	private int str;
	private int mag;
	private int defaultHP;
	private int def;
	
	/**
	 * Constructor for the stats
	 * @param hp int for health of the unit
	 * @param str int for strength of the unit
	 * @param mag int for magic of the unit
	 * @param def int for defense of the unit
	 */
	public Stats(int hp, int str, int mag, int def){
		this.health = hp;
		this.str = str;
		this.mag = mag;
		setDefaultHP(hp);
	}
	
	/**
	 * Copy constructor for stats
	 * @param s Stats to be initialized
	 */
	public Stats(Stats s){
		this.health = s.getHealth();
		this.def = s.getDef();
		this.mag = s.getMag();
		
	}
	
	/**
	 * Getter for default health stat
	 * @return int for the default health stat
	 */
	public int getDefaultHP() {
		return this.defaultHP;
	}
	
	/**
	 * Setter for the default health stat
	 * @param hp int for the new default health 
	 */
	public void setDefaultHP(int hp) {
		this.defaultHP = hp;
	}

	/**
	 * Getter for the health stat
	 * @return int for the health stat
	 */
	public int getHealth() {
		if (this.health <= 0) {
			return 0;
		}
		else {
			return health;
		}
	}

	/**
	 * Setter for health stat
	 * @param hpChange int for change in the health
	 */
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

	/**
	 * Getter for strength stat
	 * @return int for the strength stat
	 */
	public int getStr() {
		return str;
	}

	/**
	 * Setter for the strength stat
	 * @param str int of the new strength stat
	 */
	public void setStr(int str) {
		
		this.str = str;
	}

	/**
	 * Getter for magic stat
	 * @return int for the magic stat
	 */
	public int getMag() {
		return mag;
	}

	/**
	 * Setter for the magic stat
	 * @param mag int of the new magic stat
	 */
	public void setMag(int mag) {
		this.mag = mag;
	}

	/**
	 * Setter for the defense stat
	 * @param def int for the new defense stat
	 */
	public void setDef(int def) {
		this.def = def;
	}

	/**
	 * Getter for the defense stat
	 * @return int for the defense stat
	 */
	public int getDef(){
		return def;
	}


}