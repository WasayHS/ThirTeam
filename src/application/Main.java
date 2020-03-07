package application;
import java.awt.Point;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import map.MapSetup;
import map.UnitLocation;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		
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

						System.out.println(GridPane.getRowIndex(field) + "/" + GridPane.getColumnIndex(field)); 
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
}
