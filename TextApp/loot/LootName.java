package loot;

public class LootName {
	private String pot;

	/**
	 * Constructor for LootName
	 *
	 * @param pot: Type String - the potion name
	 */
	public LootName(String pot) {
		setPot(pot);
	}

	/**
	 * Getter for the potion item
	 * @return String of the potion
	 */
	public String getPot() {
		return pot;
	}

	/**
	 * Setter for the potion item
	 * @param pot String of the type of potion
	 */
	public void setPot(String pot) {
		this.pot = pot;
	}

	/**
	 * Returns the potion name as a String
	 *
	 * @return pot: Type String
	 */
	public String toString() {
		return pot;
	}
}
