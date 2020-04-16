package loot;

/**
 * Class for the stats of the loot/potion
 * @author Bonnie's Computer
 *
 */
public class LootStats {

	private int str;
	private int mag;
	private int def;

	/**
	 * Constructor for LootStats
	 *
	 * @param str int which is the strength number
	 * @param mag int which is the magic number
	 * @param def int which is the defense number
	 */
	public LootStats(int str, int mag, int def) {
		setStr(str);
		setMag(mag);
		setDef(def);
	}

	/**
	 * Getter for strength stat of the potion
	 * @return int of the strength stat
	 */
	public int getStr() {
		return str;
	}

	/**
	 * Setter for the strength stat of the potion
	 * @param str int of the new strength stat
	 */
	public void setStr(int str) {
		this.str = str;
	}

	/**
	 * Getter for magic stat of the potion
	 * @return int of the magic stat
	 */
	public int getMag() {
		return mag;
	}

	/**
	 * Setter for the magic stat of the potion
	 * @param str int of the new magic stat
	 */
	public void setMag(int mag) {
		this.mag = mag;
	}

	/**
	 * Getter for defense stat of the potion
	 * @return int of the defense stat
	 */
	public int getDef() {
		return def;
	}

	/**
	 * Setter for the defense stat of the potion
	 * @param str int of the new defense stat
	 */
	public void setDef(int def) {
		this.def = def;
	}

	/** 
	 * Method returns the name of the potion as a string
	 *
	 * @return String in for the format of (Str:, Mag:, Def:)
	 */
	public String toString() {
		return String.format("(Str: %s, Mag: %s, Def: %s)", str, mag, def);
	}
}
