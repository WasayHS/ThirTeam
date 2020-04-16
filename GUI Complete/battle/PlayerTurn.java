package battle;

import application.ButtonEvents;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import loot.Inventory;
import loot.LootImg;
import map.MapSetup;
import map.Position;
import unit.Enemy;
import unit.Player;
import unit.Unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PlayerTurn extends Thread {

	public PlayerTurn(GridPane grid, Unit player, boolean melee, boolean ranged, Position p, Unit opponent) {
		run(grid, player, melee, ranged, p, opponent);
	}

	public void run(GridPane grid, Unit player, boolean melee, boolean ranged, Position p, Unit opponent) {
		AttackType type = new AttackType(player);
		Stage playerAtk = new Stage();
		VBox root = new VBox();

		Button attackBtn = attackBtnType(melee, ranged);;
		Button defendBtn = ButtonEvents.createButton(0, 0, "Defend");
		Button potionBtn = ButtonEvents.createButton(0, 0, "Use a potion");
		Button healBtn = ButtonEvents.createButton(0, 0, "Heal");

		Label message;
		message = new Label("Choose your move.");

		attackBtn.setOnAction(event -> { attackEvent(event, message, opponent, type, grid, playerAtk, p); });
		defendBtn.setOnAction(event -> { blockProbability(message, playerAtk); });
		healBtn.setOnAction(event -> { playerHeal(player, message, event, type, opponent); });
		potionBtn.setOnAction(event -> { potionComboBox(player); });

		root.setAlignment(Pos.CENTER);
		List<Button> buttonList = new ArrayList<>(Arrays.asList(attackBtn, defendBtn, potionBtn, healBtn));
		ButtonEvents.addButtonToBox(root, buttonList);
		Scene scene = new Scene(root, 300, 200);

		playerAtk.setScene(scene);
		playerAtk.showAndWait();
	}

	private void potionComboBox(Unit player){
		ComboBox<String> listBox;
		VBox box = new VBox();
		Stage viewInventory = new Stage();
		Label label = new Label("Choose pot");
		List<String> potionList = potionList();

		if (potionList.isEmpty()) {
			emptyInventory(label, viewInventory, box);
		} else {
			listBox = new ComboBox<String>(FXCollections.observableArrayList(potionList));
			listBox.setOnAction(e -> {
				Button useItem = new Button("Use Item");
				useItem.setOnAction(a -> { usePotionEvent(listBox, player, viewInventory); });
				box.getChildren().add(useItem);
			});
			box.setAlignment(Pos.CENTER);
			box.getChildren().add(listBox);
		}
		box.setAlignment(Pos.TOP_CENTER);
		box.getChildren().add(label);
		Scene inventoryScene = new Scene(box, 300, 100);
		viewInventory.setScene(inventoryScene);
		viewInventory.show();
	}

	private void emptyInventory(Label label, Stage viewInventory, VBox box){
		label.setText("There is nothing in your Inventory");
		Button leave = new Button("Leave");
		leave.setOnAction(e -> viewInventory.close());
		box.getChildren().add(leave);
	}

	private void usePotionEvent(ComboBox<String> listBox, Unit player, Stage viewInventory) {
		Inventory.use(listBox.getValue(), (Player) player);
		potionList().remove(listBox.getValue());

		LootImg keyImg = Inventory.getImageFromType(listBox.getValue());
		int key = Inventory.getKey(keyImg.getPot());
		Inventory.inventory.remove(key);

		viewInventory.close();
	}

	private ArrayList<String> potionList() {
		ArrayList<String> potList = new ArrayList<String>();
		for (int i = 0; i < 31; i++) {
			if (Inventory.inventory.containsKey(i)) {
				potList.add(Inventory.getPotType(i));
			}
		}
		return potList;
	}

	private Button attackBtnType(boolean melee, boolean ranged) {
		if (melee) {
			return ButtonEvents.createButton(0, 0, "Melee");
		} else if (ranged) {
			return ButtonEvents.createButton(0, 0, "Ranged");
		}
		return null;
	}

	private void attackEvent(ActionEvent event, Label message, Unit opponent, AttackType type, GridPane grid, Stage playerAtk, Position p) {
		Button sourceBtn = (Button) event.getSource();
		message.setText(String.format("You attacked the %s!", opponent.getClass().toString().replace("class unit.", "")));
		ButtonEvents.continueBtn(message);

		type.attackedThem(opponent, AttackTypes.valueOf(sourceBtn.getText().toUpperCase()));
		checkOpponentHP(opponent, message, grid, playerAtk, p);
	}

	private void blockProbability(Label message, Stage playerAtk) {
		boolean prob = new Random().nextInt(2) == 0;
		if (prob) {
			message.setText("You successfully blocked the attack.");
			ButtonEvents.continueBtn(message);
		} else {
			message.setText("You failed to block the attack.");
			ButtonEvents.continueBtn(message);
			BattleThread.playerTurn = false;
			playerAtk.close();
		}
	}

	private void playerHeal(Unit player, Label message, ActionEvent event, AttackType type, Unit opponent) {
		int oldHP = player.getStats().getHealth();

		if (player.getStats().getHealth() >= player.getStats().getDefaultHP()) {
			message.setText("You have not taken any damage yet.");
			ButtonEvents.continueBtn(message);
			return;
		}

		Button sourceBtn = (Button) event.getSource();
		type.attackedThem(opponent, AttackTypes.valueOf(sourceBtn.getText().toUpperCase()));
		message.setText(String.format("You regained %s health!", player.getStats().getHealth()-oldHP));
		ButtonEvents.continueBtn(message);
	}

	private void checkOpponentHP(Unit opponent, Label message, GridPane grid, Stage playerAtk, Position p) {
		if (opponent.getStats().getHealth() <= 0) {
			message.setText(String.format("You have defeated the %s!", opponent.getClass().toString().replace("class unit.", "")));
			ButtonEvents.continueBtn(message);

			if (opponent instanceof Enemy) {
				MapSetup.ENEMY_POS.remove(opponent.getPosition());
			} else {
				MapSetup.BOSS_POS.remove(opponent.getPosition());
			}
			enemyDropProbability(opponent, message, grid, playerAtk, p);
		} else {
			endPlayerTurn(message, playerAtk);
		}
	}

	private void enemyDropProbability(Unit opponent, Label message, GridPane grid, Stage playerAtk, Position p) {
		boolean prob = new Random().nextInt(3) == 0;

		if (prob) {
			message.setText(String.format("The %s dropped an item.", opponent.getClass().toString().replace("class unit.", "")));
			ButtonEvents.continueBtn(message);
			Inventory pot = MapSetup.enemyDrop(grid, opponent);
			playerAtk.close();
		} else {
			MapSetup.moveUnit(grid, opponent, p);
			playerAtk.close();
		}
	}

	private void endPlayerTurn (Label message, Stage playerAtk) {
		BattleThread.playerTurn = false;
		message.setText("Enemy's turn to attack.");
		ButtonEvents.continueBtn(message);
		playerAtk.close();
	}
}
