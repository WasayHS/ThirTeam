package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The main class where the game is launched
 * @author Bonnie's Computer
 *
 */
public class Main extends Application implements EventHandler<ActionEvent> {
	static Stage window;
	
	/**
	 * Method to start the game
	 * @param Stage of where the game is displayed
	 */
	public void start(Stage primaryStage) {
		window = primaryStage;
		Scene start = SceneChange.getTitleScene(primaryStage);
		window.setScene(start);
		window.show();
	}
	
	/**
	 * Method that opens the pickup item window
	 * @param inventoryKey Int as the key of the item stored in inventory
	 * @param cell Rectangle of where the item is displayed
	 */
	public static void pickUpItemWindow(int inventoryKey, Rectangle cell) {
		window = new Stage();
		VBox root = new VBox();
        Button yesBtn = ButtonEvents.createButton(0,0, "Yes");
        Button noBtn = ButtonEvents.createButton(0,0,"No");
		List<Button> buttonList = new ArrayList<>(Arrays.asList(yesBtn,noBtn));
        Label message;
        
        message = new Label("Pick up item?");
        
        yesBtn.setOnAction(event -> {
			message.setText("The item will be added to your inventory.\nGo click on the \"Use Potion button\" in your next fight to use it");
        	ButtonEvents.lootItemButton(event, inventoryKey, message, cell, window);
		});

        noBtn.setOnAction(event -> {
			message.setText("The item will disappear.");
			ButtonEvents.lootItemButton(event, inventoryKey, message, cell, window);
		});

		root.setAlignment(Pos.CENTER);
		root.getChildren().add(message);
		ButtonEvents.addButtonToBox(root,buttonList);
		Scene scene = new Scene(root, 300, 100);

		window.setScene(scene);
		window.showAndWait();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void handle(ActionEvent event) {
		return;
	}
}
