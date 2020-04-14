package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import loot.Inventory;

public class GameState {
	public static int mapSize = 9;
	public static void victory(Stage finished){
		
		Pane GFinishedLayout = new Pane();
		
		finished.setTitle("GameCompleted");
		/*
		try{
			Image gameCompleted  = new Image("completedGame.png");
			GFinishedLayout.getChildren().add(new ImageView(gameCompleted));
		}catch (IllegalArgumentException i){
			System.err.println("Error: \"completedGame.png\" not found");
		}
		*/
		Button end = new Button("End Game");
		
		
		end.setTranslateX(180);
		end.setTranslateY(230);
		end.setOnAction(a -> {System.exit(2);});
		GFinishedLayout.getChildren().add(end);
		Scene sceneEnding = new Scene (GFinishedLayout, 500, 500);
		finished.setScene(sceneEnding);
		finished.show();
	}
	
	public static void gameOver(Stage endGame) throws Exception{
		endGame.setTitle("Game over");
		Inventory.inventory.clear();
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
		play.setOnAction(e ->{
			Stage newStage = new Stage();
			SceneChange.resetLevel();
			endGame.close();
			SceneChange.setMorality(newStage);
		
		}); // is supposed to restart game
		play.setTranslateX(265);
		play.setTranslateY(230);
		
		
		GOLayout.getChildren().add(play);
		GOLayout.getChildren().add(end);
		Scene scene = new Scene (GOLayout, 500, 500);
		endGame.setScene(scene);
		endGame.show();
	}
}
