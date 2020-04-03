package loot;

import javafx.scene.paint.ImagePattern;

public class LootImg {
	private ImagePattern pot;
	
	public LootImg(ImagePattern pot) {
		setPot(pot);
	}

	public ImagePattern getPot() {
		return pot;
	}

	public void setPot(ImagePattern pot) {
		this.pot = pot;
	}
}
