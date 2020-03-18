package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import map.MapSetup;
import map.Position;
import unit.Player;

public class GameState {

	public void victory(Stage finished) throws Exception{
		
		StackPane GFinishedLayout = new StackPane();
		Scene scene = new Scene (GFinishedLayout, 800, 450);
		finished.setTitle("GameCompleted");
		
		try{
			Image gameCompleted  = new Image("completedGame.png");
			GFinishedLayout.getChildren().add(new ImageView(gameCompleted));
		}catch (IllegalArgumentException i){
			System.err.println("Error: \"gameover.png\" not found");
		}
		
		Button end = new Button();
		end.setText("End Game");
		end.setOnAction(e -> System.exit(0));
		GFinishedLayout.getChildren().add(end);
		
		finished.setScene(scene);
		finished.show();
	}
	
	public void gameOver(Stage endGame) throws Exception{
		endGame.setTitle("Game over");
		StackPane GOLayout = new StackPane();
		Scene scene = new Scene (GOLayout, 800, 450);
		try{
			Image gameOver = new Image("gameover.png");
			GOLayout.getChildren().add(new ImageView(gameOver));
		}catch (IllegalArgumentException i){
			System.err.println("Error: \"gameover.png\" not found");
		}
		
		Button end = new Button();
		end.setText("End Game");
		end.setOnAction(e -> System.exit(0));
		
		Button play = new Button();
		play.setText("Play Again");
		play.setOnAction(event -> System.exit(0)); // is supposed to restart game
		
		HBox layout = new HBox (33);
		layout.getChildren().add(play);
		layout.getChildren().add(end);
		
		endGame.setScene(scene);
		endGame.show();
	}
}
