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
import javafx.geometry.Pos;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import loot.Inventory;
import map.MapSetup;
import map.Position;
import unit.Boss;
import unit.Enemy;
import unit.Player;
import unit.Unit;

public class SceneChange {
	
	static boolean morality;
	static int level = 1;
	public static int mapSize = 9; // From 7, map goes up by 2 each level
	
	public static void setMorality(boolean morality) {
		SceneChange.morality = morality;
	}

	//method action created to set the morality and also start a new level
	public static void action(Stage window, boolean amorality) {
		setMorality(amorality);
		newLevel(window);
	}
	
	/*newLevel generates a new level based on startGame and shows it to the window
	level should be updated every time after a newLevel
	size should be switched out after and size of the level should be determined by level number
	e.g		if level = 0
	 		get cutscene
	 		else if level = 1
	 			if morality = true
	 				cutscene for good
	 			else
	 				cutscene for bad
	 		else if level = 2
	 			nextScene = startGame(roundOneSize)
	*/
	public static void newLevel(Stage window) {
		Scene nextScene;
		if(level == 3){
			nextScene = bossLevel(window, mapSize, level);
		}else{
			nextScene = startGame(window, mapSize);
		}
		
		level++;
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
					w.write("To move: Bring your mouse cursor one block above or beside the player (the player cannot move diagonally) \n");
					w.write("The player can attack if they're beside, infront, behind the enemy. The player can also attack diagonally \n");
					w.write("To attack, click on an enemy that is close to the player (behind, infront, beside, diag)\n");
					w.write("Go to the next level by going to the door/portal on the top of the grid, this door can only be opened after killing all the enemies\n");
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
		evil.setOnAction(e->action(window,false));
		evil.setTranslateX(150);
		evil.setTranslateY(225);
		
		Button good = new Button();
		good.setText("High (good)");
		good.setOnAction(g->action(window,true));
		good.setTranslateX(300);
		good.setTranslateY(225);
		
		layout.getChildren().add(evil);
		layout.getChildren().add(good);
		Scene begin = new Scene(layout, 500,500);
		
		window.setScene(begin);
		window.show();
	}
	
	
	public static Scene startGame(Stage window, int size){
		Position portal = new Position(0,(int)size/2);
		int screen = 500;
		int enemyCount = (int)(size*0.85); // Occurrence of enemy spawning in a map
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
						cell.setFill(MapSetup.ENEMY_IMG);
					}
				}
				
				for (Position p: terrain) { // ------------- Not sure how to make a maze like pattern, feel free to do so.
					if (i == p.getX() && j == p.getY() && !cell.getFill().equals(MapSetup.ENEMY_IMG)) {
						cell.setFill(MapSetup.TERRAIN_IMG);
					}
				}
				
				if (!cell.getFill().equals(MapSetup.ENEMY_IMG) && !cell.getFill().equals(MapSetup.TERRAIN_IMG)) { //Spaces with no enemies
					if (i == 0 && j == (int)size/2) { // Portal to next level
						cell.setFill(MapSetup.PORTAL_IMG);
					}
					else if (i == 0 || j == 0 || i == size-1 || j == size-1) { // Set edge of grid as wall
						cell.setFill(MapSetup.WALL_IMG);
					}
					else if (i == size-2 && j == (int)size/2) { // Initial player spawn
						cell.setFill(MapSetup.PLAYER_IMG);
					} 
					else {
						cell.setFill(MapSetup.EMPTY_IMG);
					}
				}
				
				gameEventHandling(cell, grid, window, player, i, j, null);
				
			}	
		}
		Scene scene = new Scene(grid, 500, 500);
		return scene;
	}
	
	public static void gameEventHandling(Rectangle cell, GridPane grid, Stage window, Player player, int i, int j, Boss b){
		cell.setOnMouseEntered(enter -> {
			Position p = new Position(GridPane.getRowIndex(cell), GridPane.getColumnIndex(cell));
			cell.setFill(MapSetup.enterHover(grid, p, cell));
		
			cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
				MapSetup.updateGrid(grid, p, player, window, b);
				
			});
			
			cell.setOnMouseExited(exit -> cell.setFill(MapSetup.exitHover(grid, p, cell)));
		});
		
		grid.setRowIndex(cell, i);
		grid.setColumnIndex(cell, j);
		grid.getChildren().add(cell);
	}
	
	public static Scene bossLevel(Stage window, int size, int level){
		Player player = new Player((int)(size/2),2);
		Boss b;
		/*
			if(level == 3){
				b = new Boss(45, 5, 5, 3,2,(int)(size/2));
			}else if (level == 6){
				b = new Boss(65, 8, 6, 4,2,(int)(size/2));
			}else{
				throw new InvalidBossLevelException() ;
			}
		*/
		b = new Boss(45, 5, 5, 3,2,(int)(size/2));
		List<Position> BossList = MapSetup.setBossPosition(size, b);
		GridPane grid = new GridPane();
		int xPlayer = player.getPosition().getX();
		int yPlayer = player.getPosition().getY();
		int xBoss = b.getPosition().getX();
		int yBoss = b.getPosition().getY();
		///--------Creates walls, spikes and empty spots-------\\\
		for (int j = 0; j<size; j++){
			for (int i = 0; i<size; i++){
				Rectangle cell = new Rectangle(i,j,500/size, 500/size);
				if(i==0 || i==size-1){
					cell.setFill(MapSetup.WALL_IMG);
				}else if ((j==0|| j == size-1)&&j!=size/2){
					cell.setFill(MapSetup.TERRAIN_IMG);
				
				}else{
					cell.setFill(MapSetup.EMPTY_IMG);
				}
				if(i==0&&j==(size-1)/2){
					cell.setFill(MapSetup.PORTAL_IMG);
				}
				if(i==xPlayer && j==yPlayer){
					cell.setFill(MapSetup.PLAYER_IMG);
				}
				if(i==xBoss && j==yBoss){
					cell.setFill(MapSetup.BOSS_IMG);
				}
				gameEventHandling(cell, grid, window, player, i, j, b);
			}
		}
		Scene initBossLevel = new Scene(grid, 500,500);
		return initBossLevel;
	}

	
	
	
	//this is how the cutScenes will be accessed depending on the level they are on
	//there will be a button that the player can press after to end the cutscene
	public Scene getCutScene(boolean morality) {
		Scene cutScene = null;
		switch(level) {
		//case 0 is the exposition of the story
		case 0:
			Label exposition = new Label("Exposition of the story");
			StackPane stack = new StackPane();
			stack.getChildren().addAll(exposition);
			cutScene = new Scene(stack, 500,500);
		case 1:
			Label goodMessageOne = new Label("Exposition of the story");
			StackPane goodOne = new StackPane();
			goodOne.getChildren().addAll(goodMessageOne);
			cutScene = new Scene(goodOne, 500,500);
		case 2:
			if(morality == true) { //just taking in the morality since good and bad have different text routes
			Label goodMessageTwo = new Label("Exposition of the story");
			StackPane goodTwo = new StackPane();
			goodTwo.getChildren().addAll(goodMessageTwo);
			cutScene = new Scene(goodTwo, 500,500);
			}
			else {
				return null;
			}
		}
		return cutScene;
		
	}
	
	
}
