package application;
import java.awt.Point;
import java.util.List;
import java.util.Random;

import battle.AttackType;
import battle.AttackTypes;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import map.LocateEnemies;
import map.MapSetup;
import map.Position;
import unit.Enemy;
import unit.Player;
import unit.Unit;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class Main extends Application implements EventHandler<ActionEvent> {
	//@Override

	public void start(Stage titleScreen){ 
		titleScreen.setTitle("Game start");
		Button start = new Button();
		start.setText("Begin");
		start.setOnAction(e -> startGame(titleScreen)); // goes to the actual game
		StackPane layout = new StackPane();
		layout.getChildren().add(start);
		
		try{ /// might be taken out
			Image gamestart  = new Image("StartGame.png");
			layout.getChildren().add(new ImageView(gamestart));
		}catch (IllegalArgumentException i){
			System.err.println("Error: \"StartGame.png\" not found");
		}
		
		Scene begin = new Scene(layout, 600,400);
		titleScreen.setScene(begin);
		titleScreen.show();
	
	}
	
	public static void startBattle(GridPane grid, Unit player, boolean melee, boolean ranged, Position p, TextField node) {
		Stage battleStage = new Stage();
		VBox root = new VBox();
        Button attackBtn = null;
        Button defendBtn;
        Label message;
	    AttackType type = new AttackType(player);
	    
	      message = new Label("Choose your move.");
	      if (melee) {
	    	  attackBtn = new Button("Melee");
	      	}
	      else if (ranged) {
	    	  attackBtn = new Button("Ranged");
	      	}
	      defendBtn = new Button("Defend");
	      
		  attackBtn.setOnAction(new EventHandler<ActionEvent>() // - - - - Attack event
				   {
			   	@Override
			   	public void handle(ActionEvent event)
			   	{
			   		Button sourceBtn = (Button)event.getSource();
			   		message.setText("You dealt damage to the Enemy!");
			   		Enemy enemy = MapSetup.getEnemy(p.getX(), p.getY());
			   		
			   		type.attackedThem(enemy, AttackTypes.valueOf(sourceBtn.getText().toUpperCase()));
			   		TextField node = MapSetup.getNode(grid, p);
			   		node.setText(String.format("%s , %d", MapSetup.ENEMY, enemy.getStats().getHealth()));
			   		
			   		if (enemy.getStats().getHealth() <= 0) {
			   			MapSetup.moveUnit(grid, enemy, p);
			   			battleStage.close();// closes battle interface when enemy health goes to zero
			   		}
			   	}
		});
	    
		 
		  // Create an anonymous inner class to handle Defend
		  defendBtn.setOnAction(new EventHandler<ActionEvent>()
		   {
		   	@Override
		   	public void handle(ActionEvent event)
		   	{
		   		message.setText("You defended against damage.");
		   	}
		   }
		  );
		  	
		  if (attackBtn != null) {
			  root.getChildren().add(attackBtn);
		  }
		  root.getChildren().add(defendBtn);
		  root.getChildren().add(message);
		  Scene scene = new Scene(root, 300, 100);
		  
		  battleStage.setScene(scene);
		  battleStage.showAndWait();
	}

	public void startGame(Stage primaryStage){
			
			int size = 11; // Size of grid (9x9)
			int enemyCount = (int)(size*0.9); // Occurrence of enemy spawning in a map
			Player player = new Player(size-2, (int)(size/2)); // Initial player spawn; always last row, mid col
			
			Random random = new Random ();
			GridPane grid = new GridPane(); // Makes a pretty grid
			
			List<Position> enemies = MapSetup.createEnemyPositions(enemyCount, size);
			
			// - - - - - - - - - - - Initializes map with enemies, unit, wall, empty spaces
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					
					TextField field = new TextField(); // Text in each square
					field.setPrefHeight(50);
					field.setPrefWidth(50);
					field.setAlignment(Pos.CENTER);
					field.setEditable(false);
					
					
					for (Position p: enemies) { //Iterate through list of enemy coords and sets those as "E"
						if (i == p.getX() && j == p.getY()) {
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
					
					field.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
						Position p = new Position(GridPane.getRowIndex(field), GridPane.getColumnIndex(field));
						MapSetup.updateGrid(grid, p, player);
						
					});
					grid.setRowIndex(field, i);
					grid.setColumnIndex(field, j);
					grid.getChildren().add(field);
				}
				
			}
			
			Scene scene = new Scene(grid, 500,500);
			primaryStage.setTitle("Default Game");
			primaryStage.setScene(scene);
			primaryStage.show();
	
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void handle(ActionEvent event) {
		//  no relevence
		
	}
}
