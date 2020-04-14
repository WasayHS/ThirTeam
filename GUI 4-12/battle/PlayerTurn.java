package battle;

import java.util.ArrayList;
import java.util.Random;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import loot.Inventory;
import loot.LootImg;
import map.MapSetup;
import map.Position;
import unit.Boss;
import unit.Enemy;
import unit.Player;
import unit.Unit;

public class PlayerTurn extends Thread{
	
	public PlayerTurn(GridPane grid, Unit player, boolean melee, boolean ranged, Position p, Rectangle node, Enemy enemy, Boss b) {
		run(grid, player, melee, ranged, p, node, enemy, b);
	}
	

	public void run(GridPane grid, Unit player, boolean melee, boolean ranged, Position p, Rectangle node, Enemy enemy, Boss b) {
		Stage playerAtk = new Stage();
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
			   		boolean isDead = false;
			   		Button sourceBtn = (Button)event.getSource();
			   		message.setText("You attacked them!");
			   		Main.continueBtn(message);
			   		if(enemy != null){
			   			type.attackedThem(enemy, AttackTypes.valueOf(sourceBtn.getText().toUpperCase()));
			   			
			   			isDead = enemy.getStats().getHealth() <= 0;
			   		}
			   		else if(b != null){
			   			type.attackedThem(b, AttackTypes.valueOf(sourceBtn.getText().toUpperCase()));
			   			
			   			isDead = b.getStats().getHealth() <= 0;
			   		}
			   		Rectangle node = MapSetup.getNode(grid, p);
			   		
			   		if (isDead) { // Enemy health <= 0
			   			message.setText("You have defeated the opposer!");
				   		Main.continueBtn(message);
				   		if(b == null)
				   			MapSetup.ENEMY_POS.remove(enemy.getPosition());
				   		else
				   			MapSetup.BOSS_POS.remove(b.getPosition());
			   			boolean prob = new Random().nextInt(3)==0; // Enemy item drop probability
			   			if(b == null){
				   			if (prob) {
				   				message.setText("The Enemy dropped an item.");
						   		Main.continueBtn(message);
				   				Inventory pot = MapSetup.enemyDrop(grid, enemy, p);
				   				
				   				playerAtk.close();
				   			}
				   			else {
					   			MapSetup.moveUnit(grid, enemy, p);
					   			playerAtk.close();
				   			}
			   			}else{
			   				if (prob) {
				   				message.setText("The Boss dropped an item.");
						   		Main.continueBtn(message);
						   		Inventory pot = MapSetup.enemyDrop(grid, b, p);
				   				playerAtk.close();
				   			}
				   			else {
					   			MapSetup.moveUnit(grid, b, p);
					   			playerAtk.close();
				   			}
			   			}
			   		}
			   		
			   		else {
			   			BattleThread.playerTurn = false;
				   		message.setText("Enemy's turn to attack.");
				   		Main.continueBtn(message);
				   		playerAtk.close();
			   		}
			   	}});
		 
		  defendBtn.setOnAction(new EventHandler<ActionEvent>() // If player tries to defend, they have a 50/50 chance to block the damage
		   {
		   	@Override
		   	public void handle(ActionEvent event)
		   	{
		   		boolean prob = new Random().nextInt(2)==0;
		   		if (prob) {
		   			message.setText("You successfully blocked the attack.");
			   		Main.continueBtn(message);
		   		}
		   		else {
		   			message.setText("You failed to block the attack.");
			   		Main.continueBtn(message);
			   		BattleThread.playerTurn = false;
			   		playerAtk.close();
		   		}
		   	}});
		  
		  potionBtn.setOnAction(new EventHandler<ActionEvent>()
		   {
		   	@Override
		   	public void handle(ActionEvent event)
		   	{
		   		ComboBox<String> listBox;
		   		VBox box = new VBox();
		   		Stage viewInventory = new Stage();
		   		Label l = new Label("Choose pot");
		   		ArrayList <String> potList = new ArrayList<String>();
		   		for (int i = 0; i<31; i++){
		   			if(Inventory.inventory.containsKey(i)){
		   				potList.add(Inventory.getPotType(i));
		   			}
		   		}
		   		if(potList.isEmpty()){
		   			
		   			l.setText("There is nothing in your Inventory");
		   			Button leave = new Button("leave");
		   			leave.setOnAction(e-> viewInventory.close());
		   			box.getChildren().add(leave);
		   			
		   		}else{
		   			listBox = new ComboBox<String>(FXCollections.observableArrayList(potList) );
		   			listBox.setOnAction(e->{
		   				Button useItem = new Button ("Use Item");
		   				useItem.setOnAction(a->{
		   					Inventory.use(listBox.getValue(),(Player)player);
		   					potList.remove(listBox.getValue());
		   					
		   					
		   					LootImg keyImg = Inventory.getImageFromType(listBox.getValue());
		   					int key = Inventory.getKey(keyImg.getPot());
		   					
		   					
		   					Inventory.inventory.remove(key);
		   					
		   					viewInventory.close();
		   				});
		   				box.getChildren().add(useItem);
		   				
		   			});		   			
		   			box.setAlignment(Pos.CENTER);
		   			
		   			box.getChildren().add(listBox);
		   		}
		   		
		   		
		   		box.setAlignment(Pos.TOP_CENTER);
		   		box.getChildren().add(l);
				Scene inventoryScene = new Scene(box, 300, 100);
				viewInventory.setScene(inventoryScene);
				viewInventory.show();
		   	}});
		  
		  if (attackBtn != null) {
			  root.getChildren().add(attackBtn);
		  }
		  
		  root.setAlignment(Pos.CENTER);
		  root.getChildren().add(defendBtn);
		  root.getChildren().add(potionBtn);
		  root.getChildren().add(message);
		  Scene scene = new Scene(root, 300, 100);
		  		  
		  playerAtk.setScene(scene);
		  playerAtk.showAndWait();
	}
}
