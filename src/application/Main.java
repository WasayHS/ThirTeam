package application;
import java.awt.Desktop;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import battle.AttackType;
import battle.AttackTypes;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import loot.Collectible;
import loot.StrPotion;
import map.MapSetup;
import map.Position;
import unit.Enemy;
import unit.EnemyAI;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;


public class Main extends Application implements EventHandler<ActionEvent> {
	//@Override
	public void start(Stage titleScreen) throws Exception{ 
		titleScreen.setTitle("A Beast's Weapon");
		Button start = new Button();
		start.setText("Begin");
		start.setOnAction(e -> setMorality(titleScreen));
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
		titleScreen.setScene(begin);
		titleScreen.show();
	
	}
	
	public void setMorality(Stage moralityScreen){
		Pane layout = new Pane ();
		moralityScreen.setTitle("Choose Morality (Choose to be either evil or good)");
		
		Button evil = new Button();
		evil.setText("Low (evil)");
		evil.setOnAction(e-> startGame(moralityScreen,false,13));
		evil.setTranslateX(150);
		evil.setTranslateY(225);
		
		Button good = new Button();
		good.setText("High (good)");
		good.setOnAction(g->startGame(moralityScreen,true,13));
		good.setTranslateX(300);
		good.setTranslateY(225);
		
		layout.getChildren().add(evil);
		layout.getChildren().add(good);
		Scene begin = new Scene(layout, 500,500);
		moralityScreen.setScene(begin);
		moralityScreen.show();
	}
	
	public static void startBattle(GridPane grid, Unit player, boolean melee, boolean ranged, Position p, Rectangle node) {
		Stage battleStage = new Stage();
		VBox root = new VBox();
        Button attackBtn = null;
        Button defendBtn;
        Button potionBtn;
        Label message;
	    AttackType type = new AttackType(player);
	    
	      message = new Label("Choose your move.");
	      defendBtn = new Button("Defend");
	      potionBtn = new Button ("Use potion.");
	      
	      if (melee) {
	    	  attackBtn = new Button("Melee");
	      	}
	      else if (ranged) {
	    	  attackBtn = new Button("Ranged");
	      	}
  
		  attackBtn.setOnAction(new EventHandler<ActionEvent>() // - - - - Attack event
				   {
			   	@Override
			   	public void handle(ActionEvent event)
			   	{
			   		Button sourceBtn = (Button)event.getSource();
			   		message.setText("You dealt damage to the Enemy!");
			   		Enemy enemy = MapSetup.getEnemy(p.getX(), p.getY());
			   		type.attackedThem(enemy, AttackTypes.valueOf(sourceBtn.getText().toUpperCase()));
			   		Rectangle node = MapSetup.getNode(grid, p);
			   		// Have a bar showing enemy health 
			   		
			   		if (enemy.getStats().getHealth() <= 0) {
			   			boolean prob = new Random().nextInt(5)==0; // 1/5 chance the int would be 0, which would make 'prob' true and there is ahn enemy drop
			   			if (prob) {
			   				// Add another probability of which potion would drop.
			   				// I only put str pot and has no stats in it. Pots are child classes of Collectible
			   				Collectible pot = new StrPotion(); // Set instance of Collectible to Str, to compare instances, (parentclass instanceof childclass)
			   				MapSetup.enemyDrop(pot, grid, enemy, p); //Please add event for when player picks up the enemydrop.
			   				battleStage.close();
			   			}
			   			else {
				   			MapSetup.moveUnit(grid, enemy, p);
				   			battleStage.close();
			   			}
			   		}
			   	}});
		 
		  // Create an anonymous inner class to handle Defend
		  defendBtn.setOnAction(new EventHandler<ActionEvent>()
		   {
		   	@Override
		   	public void handle(ActionEvent event)
		   	{
		   		message.setText("You defended against damage.");
		   	}});
		  
		  potionBtn.setOnAction(new EventHandler<ActionEvent>()
		   {
		   	@Override
		   	public void handle(ActionEvent event)
		   	{
		   		message.setText("You used a potion.");
		   	}});
		  
		  if (attackBtn != null) {
			  root.getChildren().add(attackBtn);
		  }
		  root.getChildren().add(defendBtn);
		  root.getChildren().add(potionBtn);
		  root.getChildren().add(message);
		  Scene scene = new Scene(root, 300, 100);
		  
		  battleStage.setScene(scene);
		  battleStage.showAndWait();
	}

	public static void startGame(Stage primaryStage,boolean morality, int size){
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
			primaryStage.setTitle("A Beast's Weapon");
			primaryStage.setScene(scene);
			primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void handle(ActionEvent event) {
		//  no relevance
		
	}
}
