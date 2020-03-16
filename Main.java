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
import loot.Collectible;
import loot.StrPotion;
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
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void handle(ActionEvent event) {
		//  no relevance
		
	}
}
