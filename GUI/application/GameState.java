package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameState {
	public static int mapSize = 9;
	public void victory(Stage finished) throws Exception{
		
		StackPane GFinishedLayout = new StackPane();
		Scene scene = new Scene (GFinishedLayout, 800, 450);
		finished.setTitle("GameCompleted");
		/*
		try{
			Image gameCompleted  = new Image("completedGame.png");
			GFinishedLayout.getChildren().add(new ImageView(gameCompleted));
		}catch (IllegalArgumentException i){
			System.err.println("Error: \"completedGame.png\" not found");
		}
		*/
		Button end = new Button();
		end.setText("End Game");
		end.setOnAction(e -> System.exit(0));
		GFinishedLayout.getChildren().add(end);
		finished.setScene(scene);
		finished.show();
	}
	
	public static void gameOver(Stage endGame) throws Exception{
		endGame.setTitle("Game over");
		Pane GOLayout = new Pane();
		
		/*
		try{
			Image gameOver = new Image("gameover.png");
			GOLayout.getChildren().add(new ImageView(gameOver));
		}catch (IllegalArgumentException i){
			System.err.println("Error: \"gameover.png\" not found");
		}
		*/
		Button end = new Button();
		end.setText("End Game");
		end.setOnAction(e -> System.exit(1));
		end.setTranslateX(165);
		end.setTranslateY(230);
		
		
		Button play = new Button();
		play.setText("Play Again");
		play.setOnAction(e ->SceneChange.setMorality(endGame)); // is supposed to restart game
		play.setTranslateX(265);
		play.setTranslateY(230);
		
		
		GOLayout.getChildren().add(play);
		GOLayout.getChildren().add(end);
		Scene scene = new Scene (GOLayout, 500, 500);
		endGame.setScene(scene);
		endGame.show();
	}
}
