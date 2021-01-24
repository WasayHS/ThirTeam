package loot;

public class LootName {
	private String pot;
	
	public LootName(String pot) {
		setPot(pot);
	}

	public String getPot() {
		return pot;
	}

	public void setPot(String pot) {
		this.pot = pot;
	}

	public String toString() {
		return pot;
	}
}
