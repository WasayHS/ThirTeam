package application;
import java.awt.Point;
import java.io.FileInputStream;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import map.LocateEnemies;
import map.MapSetup;
import map.UnitLocation;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;


public class Main extends Application implements EventHandler<ActionEvent> {
	//@Override
	
	public static int enemyHP = 1; // temporary variable to replace E with P.

	
	public void start(Stage titleScreen) throws Exception{ 
		titleScreen.setTitle("A Beast's Weapon");
		Button start = new Button();
		start.setText("Begin");
		start.setOnAction(e -> startGame(titleScreen)); // goes to the actual game
		Pane layout = new Pane();
		start.setTranslateX(225);
		start.setTranslateY(0);
		
		try{
			Image gamestart  = new Image(new FileInputStream("src/application/StartGame.png"));
			layout.getChildren().add(new ImageView(gamestart));
		}catch (IllegalArgumentException i){
			System.err.println("Error: \"StartGame.png\" not found");
		}
		layout.getChildren().add(start);
		Scene begin = new Scene(layout, 500,500);
		titleScreen.setScene(begin);
		titleScreen.show();
	
	}
	
	public void startBattle() {
		
		Stage battleStage = new Stage();
		 VBox root = new VBox();
	        Button Attack;
	        Button Defend;
	        Label Message;
		  Attack = new Button("Attack");
		  Defend = new Button("Defend");
		  Message = new Label("Choose your move.");

		  // Create an anonymous inner class to handle Attack
		  Attack.setOnAction(new EventHandler<ActionEvent>()
		   {
		   	@Override
		   	public void handle(ActionEvent event)
		   	{
		   		Message.setText("You dealt damage to the Enemy!");
		   		enemyHP = 0; // still need to add damage and health calculations and stuff....
		   		battleStage.close();// closes battle interface when enemy health goes to zero. Have yet to add this condition.
		   	}
		   }
		  );
		  // Create an anonymous inner class to handle Defend
		  Defend.setOnAction(new EventHandler<ActionEvent>()
		   {
		   	@Override
		   	public void handle(ActionEvent event)
		   	{
		   		Message.setText("You defended against damage.");
		   	}
		   }
		  );

		  root.getChildren().add(Attack);
		  root.getChildren().add(Defend);
		  root.getChildren().add(Message);
		  Scene scene = new Scene(root, 500, 500);
		  
		  battleStage.setScene(scene);
		  battleStage.showAndWait();
		  
	}
	
	
	public void startGame(Stage primaryStage){
		
			int size = 11; // Size of grid (9x9)
			int enemyCount = (int)(size*0.9); // Occurrence of enemy spawning in a map
			UnitLocation unit = new UnitLocation(size-2, (int)(size/2)); // Initial player spawn; always last row, mid col
			
			Random random = new Random ();
			GridPane grid = new GridPane(); // Makes a pretty grid
			
			List<Point> enemies = MapSetup.createEnemyPositions(enemyCount, size);
			
			// - - - - - - - - - - - Initializes map with enemies, unit, wall, empty spaces
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					
					TextField field = new TextField(); // Text in each square
					field.setPrefHeight(50);
					field.setPrefWidth(50);
					field.setAlignment(Pos.CENTER);
					field.setEditable(false);
					
					
					for (Point p: enemies) { //Iterate through list of enemy coords and sets those as "E"
						if (i == p.x && j == p.y) {
							field.setText(MapSetup.ENEMY);
							field.setStyle("-fx-text-inner-color: red;-fx-font-weight: bold;");
							
							
						}
					}
					
					if (!field.getText().equals(MapSetup.ENEMY)) { //Spaces with no enemies
						if (i == 0 && j == (int)size/2) { // Portal to next level
							field.setText(MapSetup.PORTAL);
							field.setStyle("-fx-text-inner-color: purple;-fx-font-weight: bold;");
						}
						
						else if (i == 0 || j == 0 || i == size-1 || j == size-1) { // Set edge of grid as wall
							field.setText(MapSetup.WALL);
						}
						else if (i == size-2 && j == (int)size/2) { // Initial player spawn
							field.setText(MapSetup.PLAYER);
							field.setStyle("-fx-text-inner-color: lightgreen;-fx-font-weight: bold;");
						}
						
						else {
							field.setText(MapSetup.EMPTY);
						}
					}
					
					field.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> { // - - - - - Event handling
						int selectedX = GridPane.getRowIndex(field); // x of selected location
						int selectedY = GridPane.getColumnIndex(field); // y of selected location
						
						
						
						MapSetup.updateGrid(grid, selectedX, selectedY, unit);

						if(LocateEnemies.checkRanged(grid, selectedX, selectedY, unit)|| LocateEnemies.checkMelee(grid, selectedX, selectedY, unit)) { // Click on Enemy to enter 
							if (Math.abs(unit.getX()-selectedX) > 1 || Math.abs(unit.getY()-selectedY) > 1 /*|| (Math.abs(unit.getX()-selectedX) == 1 && Math.abs(unit.getY()-selectedY) == 1)*/) {
								MapSetup.updateGrid(grid, selectedX, selectedY, unit);
								}
								else 
							try {
								startBattle();
								MapSetup.updateGrid(grid, selectedX, selectedY, unit);
								enemyHP = 1;

																
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
								}
						
						

					
						System.out.println(GridPane.getRowIndex(field) + "/" + GridPane.getColumnIndex(field)); 
					});
					

					grid.setRowIndex(field, i);
					grid.setColumnIndex(field, j);
					grid.getChildren().add(field);
					
					
				}
				
			}
			
			Scene scene = new Scene(grid, 500,500);
			primaryStage.setTitle("Location 1");
			primaryStage.setScene(scene);
			primaryStage.show();
			

	
	}
	
	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void handle(ActionEvent event) {
		return;
		
	}
}
