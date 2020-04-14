package loot;

public class LootName {
	private String pot;

	/* LootName(String)
	 * Constructor for LootName
	 *
	 * @param pot: Type String - the potion name
	 */
	public LootName(String pot) {
		setPot(pot);
	}

	// = = = = = = = = = = = = = = = Setters and getters for LootName
	public String getPot() {
		return pot;
	}

	public void setPot(String pot) {
		this.pot = pot;
	}

	/* toString()
	 * Returns the potion name as a String
	 *
	 * @return pot: Type String
	 */
	public String toString() {
		return pot;
	}
}
