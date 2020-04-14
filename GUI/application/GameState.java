package application;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import loot.Inventory;

public class GameState {

	/** victory(Stage)
	 * Called after the player has finished the game
	 * Exits the game after the exit button is pushed
	 *
	 * @param finished: Type Stage - stage to set for the victory scene
	 */
	public static void victory(Stage victory) {
		StackPane GFinishedLayout = new StackPane();
		Scene scene = new Scene (GFinishedLayout, 800, 450);
		victory.setTitle("GameCompleted");

		GFinishedLayout.getChildren().add(ButtonEvents.exitButton(0, 0));
		victory.setScene(scene);
		victory.show();
}
	/** gameOver(Stage)
	 * Called when the player dies during a battle, player can choose
	 * to play again or exit
	 *
	 * @param endGame: Type Stage - stage to set for the end scene
	 */
	public static void gameOver(Stage endGame) {
		endGame.setTitle("Game over");
		Inventory.inventory.clear();
		Pane GOLayout = new Pane();
		
		GOLayout.getChildren().add(ButtonEvents.playButton(endGame, 265, 230));
		GOLayout.getChildren().add(ButtonEvents.exitButton(165,230));
		Scene scene = new Scene (GOLayout, 500, 500);

		endGame.setScene(scene);
		endGame.show();
	}
}
