package application;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import map.MapSetup;
import map.Position;
import unit.Player;

public class SceneChange {
	
	static boolean morality;
	private static int level = 0;
	public static int mapSize = 9; // From 7, map goes up by 2 each level

	//method action created to set the morality and also start a new level
	public static void action(Stage window, boolean amorality) {
		morality = amorality;
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
		nextScene = startGame(window, mapSize);
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
		//https://howtodoinjava.com/java/io/how-to-create-a-new-file-in-java/
		instructions.setOnAction(event -> {
			File instructions1 = new File("src/Instructions.txt");
			Desktop d = Desktop.getDesktop();
			try {
				instructions1.createNewFile();
				FileWriter w = new FileWriter(instructions1);
				w.write("To move: Bring your mouse cursor one block above or beside the player (the player cannot move diagonally) \n");
				w.write("The player can attack if they're beside, infront, behind the enemy. The player can also attack diagonally \n");
				w.write("To attack, click on an enemy that is close to the player (behind, infront, beside, diag)\n");
				w.write("Go to the next level by going to the door/portal on the top of the grid, this door can only be opened after killing all the enemies\n");
				w.close();
				d.open(instructions1);

			} catch (IOException e) {

				System.err.println("unknown error");
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
		return new Scene(layout, 500,500);
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
		int screen = 500;
		int enemyCount = (int)(size*0.85); // Occurrence of enemy spawning in a map
		Player player = new Player(size-2, (int)(size/2)); // Initial player spawn; always last row, mid col

		GridPane grid = new GridPane(); // Makes a pretty grid
		
		List<Position> enemies = MapSetup.createEnemyPositions(enemyCount, size, false);
		List<Position> terrain = MapSetup.createEnemyPositions(enemyCount, size, true);
		
		// - - - - - - - - - - - Initializes map with enemies, unit, wall, empty spaces
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Rectangle cell = new Rectangle (screen/size, screen/size); // Rectangle is used as node; similar properties to TextField but with images
				// Screen/size is the size per cell
				for (Position p: enemies) { // Separate method
					if (i == p.getX() && j == p.getY()) {
						cell.setFill(MapSetup.enemyImg);
					}
				}
				
				for (Position p: terrain) { //Put in method
					if (i == p.getX() && j == p.getY() && !cell.getFill().equals(MapSetup.enemyImg)) {
						cell.setFill(MapSetup.terrainImg);
					}
				}
				
				if (!cell.getFill().equals(MapSetup.enemyImg) && !cell.getFill().equals(MapSetup.terrainImg)) { //Spaces with no enemies; put in method
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
				cell.setOnMouseEntered(enter -> { // Put in method
					Position p = new Position(GridPane.getRowIndex(cell), GridPane.getColumnIndex(cell));
					cell.setFill(MapSetup.enterHover(cell));

					cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
						MapSetup.updateGrid(grid, p, player, window);
					});

					cell.setOnMouseExited(exit -> cell.setFill(MapSetup.exitHover(cell)));
				});
				
				GridPane.setRowIndex(cell, i);
				GridPane.setColumnIndex(cell, j);
				grid.getChildren().add(cell);
			}	
		}
		return new Scene(grid, 500, 500);
	}

}
