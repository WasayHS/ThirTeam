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
import loot.Inventory;
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
import javafx.scene.shape.Rectangle;


public class Main extends Application implements EventHandler<ActionEvent> {
	//@Override
	
	static Stage window;
	
	public void start(Stage primaryStage)throws Exception{
		window = primaryStage;
		Scene start;
		//goes to the titlescreen
		//titlescreen has a button that will start the game
		//sceneChanges will be handled in SceneChange
		start = SceneChange.getTitleScene(primaryStage);
		window.setScene(start);
		window.show();
	}
	
	public static void startBattle(GridPane grid, Unit player, boolean melee, boolean ranged, Position p, Rectangle node) {
		Stage battleStage = new Stage();
		window = battleStage;
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
			   			boolean prob = new Random().nextInt(3)==0;
			   			if (prob) {
			   				MapSetup.enemyDrop(grid, enemy, p);
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
	
	public static void pickUpItemWindow(GridPane grid, Inventory pot, Position p, Rectangle cell, Player player) {
		Stage pickup = new Stage();
		window = pickup;
		VBox root = new VBox();
        Button yesBtn = new Button("Yes");
        Button noBtn = new Button("No");
        Label message;
        
        message = new Label("Pick up item?");
        
        yesBtn.setOnAction(new EventHandler<ActionEvent>()
		   {@Override
		   	public void handle(ActionEvent event)
		   	{
		   		message.setText("The item will be added to your inventory.");
			   	pot.inventory.put(pot.getImage(), pot.getLootStats());
			   	continueBtn(message);
			   	MapSetup.moveUnit(grid, player, p);
			   	pickup.close();
		   	}});

        noBtn.setOnAction(new EventHandler<ActionEvent>()
		   {@Override
		   	public void handle(ActionEvent event)
		   	{
			   message.setText("The item will disappear.");
			   continueBtn(message);
			   cell.setFill(MapSetup.emptyImg);
			   pickup.close();
		   	}});
        
      root.getChildren().add(message);
      root.getChildren().add(yesBtn);
      root.getChildren().add(noBtn);
	  Scene scene = new Scene(root, 300, 100);
	  
	  pickup.setScene(scene);
	  pickup.showAndWait();
	}
	
	public static void continueBtn(Label message) {
		Stage contWindow = new Stage();
		window = contWindow;
		VBox root = new VBox();
		Button continueBtn = new Button("Continue");
		continueBtn.setOnAction(new EventHandler<ActionEvent>()
		   {@Override
		   	public void handle(ActionEvent event)
		   	{
			   	contWindow.close();
		   	}});
		
		root.getChildren().add(message);
		root.getChildren().add(continueBtn);
		Scene scene = new Scene(root, 300, 100);
  	  
		contWindow.setScene(scene);
		contWindow.showAndWait();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void handle(ActionEvent event) {
		//  no relevance
		
	}
}
