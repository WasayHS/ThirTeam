package application;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import loot.Inventory;
import map.MapSetup;
import map.Position;
import unit.Enemy;
import unit.Player;
import unit.Unit;

public class SceneChange {
	
	static boolean morality;
	private static int level = 0;
	public static int mapSize = 9; // From 7, map goes up by 2 each level
	
	public static void setMorality(boolean morality) {
		SceneChange.morality = morality;
	}

	//method action created to set the morality and also start a new level
	public static void action(Stage window, boolean amorality) throws FileNotFoundException {
		setMorality(amorality);
		newLevel(window);
	}
	
	public static void resetLevel() {
		level = 0;
	}
	
	//generates a new level depending on what stage it is
	public static void newLevel(Stage window) throws FileNotFoundException {
		System.out.println(level);
		Scene nextScene;
		if (level == 0 || level == 2 ) {
			getCutScene(window);
		}
		else {
			nextScene = startGame(window, mapSize);
			window.setScene(nextScene);
			window.show();	
		}
		level++;
	}
	
	/* -----Duplicate method, go to Gamestate.gaveOver()
	public static void playAgain(Stage window) {
		Stage restart = new Stage();
		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);
		Label message = new Label("Play again?");
		
		Button yes = new Button("Yes.");
		yes.setOnAction(new EventHandler<ActionEvent>()
		   {@Override
		   	public void handle(ActionEvent event)
		   	{
			   Scene newGame = startGame(window, mapSize);
			   window.setScene(newGame);
		   	}});
		
		Button no = new Button("No.");
		no.setOnAction(new EventHandler<ActionEvent>()
		   {@Override
		   	public void handle(ActionEvent event)
		   	{
			   System.exit(1);
		   	}});
		   	
		root.setAlignment(Pos.CENTER);  
		root.getChildren().add(message);
		root.getChildren().add(yes);
		root.getChildren().add(no);
		Scene scene = new Scene(root, 300, 100);
  	  
		restart.setScene(scene);
		restart.showAndWait();
	}
	*/
	
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
		evil.setOnAction(e->{
			try {
				action(window,false);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		evil.setTranslateX(150);
		evil.setTranslateY(225);
		
		Button good = new Button();
		good.setText("High (good)");
		good.setOnAction(g->{
			try {
				action(window,true);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
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
				
				// - - - - - - - - Mouse hover and click event handling
				cell.setOnMouseEntered(enter -> {
					Position p = new Position(GridPane.getRowIndex(cell), GridPane.getColumnIndex(cell));
					cell.setFill(MapSetup.enterHover(grid, p, cell));
				
					cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
						MapSetup.updateGrid(grid, p, player, window);
					});
					
					cell.setOnMouseExited(exit -> cell.setFill(MapSetup.exitHover(grid, p, cell)));
				});
				
				grid.setRowIndex(cell, i);
				grid.setColumnIndex(cell, j);
				grid.getChildren().add(cell);
			}	
		}
		Scene scene = new Scene(grid, 500, 500);
		return scene;
	}
	
	//this is how the cutScenes will be accessed depending on the level they are on
	//there will be a button that the player can press after to end the cutscene
	public static Scene getCutScene(Stage window) throws FileNotFoundException{
		Scene cutScene = null;
		//create a button that goes to battle
		Button btn = new Button();
		btn.setText("Continue");
		btn.setOnAction(e-> {
			try {
				newLevel(window);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		switch(level) {
		//case 0 is the exposition of the story, base storyline 
		case 0:
			Text expo = new Text();
			VBox neutral = new VBox();
			neutral.setAlignment(Pos.CENTER);
			expo.setText(readText("src/cutsceneRes/Exposition.txt"));
			neutral.getChildren().addAll(expo,btn);
			cutScene = new Scene(neutral, 500,500);
			window.setScene(cutScene);
			break;
		case 2:
			if (morality == true) {
			Text goodOne = new Text();
			VBox good1 = new VBox();
			good1.setAlignment(Pos.CENTER);
			goodOne.setText(readText("src/cutsceneRes/goodOne.txt"));
			good1.getChildren().addAll(goodOne,btn);
			cutScene = new Scene(good1, 500,500);
			}
			//exposition if you're bad
			else {
			Text badOne = new Text();
			VBox bad1 = new VBox();
			bad1.setAlignment(Pos.CENTER);
			badOne.setText(readText("src/cutsceneRes/badOne.txt"));
			bad1.getChildren().addAll(badOne,btn);
			cutScene = new Scene(bad1, 500,500);
			}
			window.setScene(cutScene);
			break;
		}
		return cutScene;
		
	}
	
	//reads the text files for the cutscenes
	public static String readText(String fileLocation) throws FileNotFoundException{
		Scanner reader = new Scanner(new FileReader(fileLocation));
		String text = "";
		while (reader.hasNextLine()) {
			text = text.concat(reader.nextLine() + "\r\n");
		}
		reader.close();
		return text;
	}
}