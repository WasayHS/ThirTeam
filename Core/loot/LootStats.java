package loot;

public class LootStats {

	private int str;
	private int mag;
	private int def;

	/** LootStats(int, int, int)
	 * Constructor for LootStats
	 *
	 * @param str: Type int which is the strength number
	 * @param mag: Type int which is the magic number
	 * @param def: Type int which is the defense number
	 */
	public LootStats(int str, int mag, int def) {
		setStr(str);
		setMag(mag);
		setDef(def);
	}

	// = = = = = = = = = = = = = = Setters and Getters for LootStats
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

	/** toString()
	 * Returns the name of the potion as a string
	 *
	 * @return String.format: (Str:, Mag:, Def:)
	 */
	public String toString() {
		return String.format("(Str: %s, Mag: %s, Def: %s)", str, mag, def);
	}
}
