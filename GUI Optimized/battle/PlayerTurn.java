package battle;

import java.util.Random;
import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import map.MapSetup;
import map.Position;
import unit.Enemy;
import unit.Unit;

public class PlayerTurn extends Thread{

	public void run(GridPane grid, Unit player, boolean melee, boolean ranged, Position p, Enemy enemy) {
		Stage playerAtk = new Stage();
		VBox root = new VBox();
        Button attackBtn = null;
        Button defendBtn;
        Button potionBtn;
        Label message;
	    DamageCalculator type = new DamageCalculator(player);
	    
	    message = new Label("Choose your move.");
	    defendBtn = new Button("Defend");
	    potionBtn = new Button ("Use potion.");
	      
	      if (melee) {
	    	  attackBtn = new Button("Melee");
	      } else if (ranged) {
	    	  attackBtn = new Button("Ranged");
	      }

		if (attackBtn == null) {
			return;
		}

		attackBtn.setOnAction(event -> {

			Button sourceBtn = (Button)event.getSource();
			message.setText("You attacked the enemy!");
			Main.continueBtn(message);

			type.attack(enemy, AttackTypes.valueOf(sourceBtn.getText().toUpperCase()));
			Rectangle node = MapSetup.getNode(grid, p);

			if (enemy.getStats().getHealth() <= 0) { // Enemy health <= 0
				message.setText("You have defeated the enemy!");
				Main.continueBtn(message);
				MapSetup.ENEMY_POS.remove(enemy.getPosition());

				boolean prob = new Random().nextInt(3)==0; // Enemy item drop probability
				if (prob) {
					message.setText("The enemy dropped an item.");
					Main.continueBtn(message);
					MapSetup.enemyDrop(grid, enemy);
				} else {
					MapSetup.moveUnit(grid, enemy, p);
				}
			} else {
				BattleThread.playerTurn = false;
				message.setText("Enemy's turn to attack.");
				Main.continueBtn(message);
			}
			playerAtk.close();
		});

		// If player tries to defend, they have a 50/50 chance to block the damage
		defendBtn.setOnAction(event -> {
			boolean prob = new Random().nextInt(2)==0;
			if (prob) {
				message.setText("You successfully blocked the enemy's attack.");
				Main.continueBtn(message);
			}
			else {
				message.setText("You failed to block the enemy's attack.");
				Main.continueBtn(message);
				BattleThread.playerTurn = false;
				playerAtk.close();
			}
		});

		potionBtn.setOnAction(event -> message.setText("You used a potion."));

		root.getChildren().add(attackBtn);
		root.setAlignment(Pos.CENTER);
		root.getChildren().add(defendBtn);
		root.getChildren().add(potionBtn);
		root.getChildren().add(message);

		Scene scene = new Scene(root, 300, 100);
		playerAtk.setScene(scene);
		playerAtk.showAndWait();
	}
}
