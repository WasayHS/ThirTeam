package loot;

public class LootStats {

	private int str;
	private int mag;
	private int def;

	public LootStats(int str, int mag, int def) {
		setStr(str);
		setMag(mag);
		setDef(def);
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

	public String toString() {
		return String.format("(Str: %s, Mag: %s, Def: %s)", str, mag, def);
	}
}
