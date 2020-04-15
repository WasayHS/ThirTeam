package loot;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 * Class for the images of the loot in the game
 * @author Bonnie's Computer
 *
 */
public class LootImg {
	private ImagePattern pot;
	
	/**
	 * Constructor to intialize the potion
	 * @param pot ImagePattern of the desired potion
	 */
	public LootImg(ImagePattern pot) {
		setPot(pot);
	}

	/**
	 * Getter for the potion
	 * @return ImagePattern of the potion
	 */
	public ImagePattern getPot() {
		return pot;
	}

	/**
	 * Setter for the potion
	 * @param pot ImagePattern of the potion to set it to
	 */
	public void setPot(ImagePattern pot) {
		this.pot = pot;
	}
}
