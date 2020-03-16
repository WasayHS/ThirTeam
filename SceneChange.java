package application;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import battle.AttackType;
import battle.AttackTypes;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import loot.Collectible;
import loot.StrPotion;
import map.MapSetup;
import map.Position;
import unit.Enemy;
import unit.Player;
import unit.Unit;

public class SceneChange {
	
	static boolean morality;
	private static int level = 0;
	public static int roundOneSize = 9;
	public static int roundTwoSize = 11;
	public static int roundThreeSize = 13;
	
	//newLevel generates a new level based on startGame and shows it to the window
	public static void newLevel(Stage window, int size,boolean amorality) {
		morality = amorality;
		Scene nextScene;
		nextScene = startGame(size,morality);
		window.setScene(nextScene);
		window.show();
	}
	
	public static Scene getTitleScene(Stage window)throws Exception{
		window.setTitle("A Beast's Weapon");
		Button start = new Button();
		start.setText("Begin");
		start.setOnAction(e -> setMorality(window));
		start.setTranslateX(180);
		start.setTranslateY(0);
		
		Button instructions = new Button();
		instructions.setText("Instructions");
		instructions.setTranslateX(240);
		instructions.setTranslateY(0);
		instructions.setOnAction(new EventHandler<ActionEvent>(){
			//https://howtodoinjava.com/java/io/how-to-create-a-new-file-in-java/
			@Override
			public void handle(ActionEvent event) {
				File instructions = new File("src/Instructions.txt");
				Desktop d = Desktop.getDesktop();
				try {
					instructions.createNewFile();
					FileWriter w = new FileWriter(instructions);
					w.write("To move: Bring your mouse cursor one block above or beside the player (P)");
					w.close();
					d.open(instructions);
					
				} catch (IOException e) {
					
					System.err.println("unknown error");
				}
			}
					
		});
		
		Pane layout = new Pane();
		try{
			Image gamestart  = new Image(new FileInputStream("src/application/StartGame.png"));
			layout.getChildren().add(new ImageView(gamestart));
		}catch (IllegalArgumentException i){
			System.err.println("Error: \"StartGame.png\" not found");
		}
		
		
		layout.getChildren().add(start);
		layout.getChildren().add(instructions);
		Scene begin = new Scene(layout, 500,500);
		return begin;
	}
	
	public static void setMorality(Stage window){
		Pane layout = new Pane ();
		
		Button evil = new Button();
		evil.setText("Low (evil)");
		evil.setOnAction(e-> newLevel(window, roundOneSize,false));
		evil.setTranslateX(150);
		evil.setTranslateY(225);
		
		Button good = new Button();
		good.setText("High (good)");
		good.setOnAction(g->newLevel(window, roundOneSize,true));
		good.setTranslateX(300);
		good.setTranslateY(225);
		
		layout.getChildren().add(evil);
		layout.getChildren().add(good);
		Scene begin = new Scene(layout, 500,500);
		
		window.setScene(begin);
		window.show();
	}
	
	
	public static Scene startGame(int size,boolean amorality){
		boolean morality = amorality;
		int screen = 500;
		int enemyCount = (int)(size*0.9); // Occurrence of enemy spawning in a map
		Player player = new Player(size-2, (int)(size/2)); // Initial player spawn; always last row, mid col
		
		Random random = new Random ();
		GridPane grid = new GridPane(); // Makes a pretty grid
		
		List<Position> enemies = MapSetup.createEnemyPositions(enemyCount, size, false);
		List<Position> terrain = MapSetup.createEnemyPositions(enemyCount, size, true);
		// - - - - - - - - - - - Initializes map with enemies, unit, wall, empty spaces
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Rectangle cell = new Rectangle (screen/size, screen/size); // Rectangle is used as node; similar properties to TextField but with images
				// Screen/size is the size per cell
				for (Position p: enemies) {
					if (i == p.getX() && j == p.getY()) {
						cell.setFill(MapSetup.enemyImg);
					}
				}
				
				for (Position p: terrain) { // ------------- Not sure how to make a maze like pattern, feel free to do so.
					if (i == p.getX() && j == p.getY() && !cell.getFill().equals(MapSetup.enemyImg)) {
						cell.setFill(MapSetup.terrainImg);
					}
				}
				
				if (!cell.getFill().equals(MapSetup.enemyImg) && !cell.getFill().equals(MapSetup.terrainImg)) { //Spaces with no enemies
					if (i == 0 && j == (int)size/2) { // Portal to next level
						cell.setFill(MapSetup.portalImg);
					}
					else if (i == 0 || j == 0 || i == size-1 || j == size-1) { // Set edge of grid as wall
						cell.setFill(MapSetup.wallImg);
					}
					else if (i == size-2 && j == (int)size/2) { // Initial player spawn
						cell.setFill(MapSetup.playerImg);
					}
					else {
						cell.setFill(MapSetup.emptyImg);
					}
				}
				cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
					Position p = new Position(GridPane.getRowIndex(cell), GridPane.getColumnIndex(cell));
					MapSetup.updateGrid(grid, p, player);
				});
				grid.setRowIndex(cell, i);
				grid.setColumnIndex(cell, j);
				grid.getChildren().add(cell);
			}	
		}
		Scene scene = new Scene(grid, 500,500);
		return scene;
	}
	
	//this is how the cutScenes will be accessed depending on the level they are on
	//there will be a button that the player can press after to end the cutscene
	public Scene getCutScene(int level, boolean morality) {
		Scene cutScene = null;
		switch(level) {
		//case 0 is the exposition of the story
		case 0:
			Label exposition = new Label("Exposition of the story");
			StackPane stack = new StackPane();
			stack.getChildren().addAll(exposition);
			cutScene = new Scene(stack, 500,500);
			level = level++;
		case 1:
			Label goodMessageOne = new Label("Exposition of the story");
			StackPane goodOne = new StackPane();
			goodOne.getChildren().addAll(goodMessageOne);
			cutScene = new Scene(goodOne, 500,500);
			level = level++;
		case 2:
			if(morality == true) { //just taking in the morality since good and bad have different text routes
			Label goodMessageTwo = new Label("Exposition of the story");
			StackPane goodTwo = new StackPane();
			goodTwo.getChildren().addAll(goodMessageTwo);
			cutScene = new Scene(goodTwo, 500,500);
			level = level++;
			}
			else {
				return null;
			}
		}
		return cutScene;
		
	}
	
}