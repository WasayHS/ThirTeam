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

/**
 * Class for the player turn during battle
 * @author Bonnie's Computer
 *
 */
public class PlayerTurn extends Thread {

	/**
	 * Constructor
	 * @param grid GridPane of the entire map that consists of Rectangle nodes and positions
	 * @param player Unit of the player
	 * @param melee boolean if player can melee attack
	 * @param ranged bolean if player can ranged attack
	 * @param p Position of the player
	 * @param opponent Unit of the opponenet, enemy or boss
	 */
	public PlayerTurn(GridPane grid, Unit player, boolean melee, boolean ranged, Position p, Unit opponent) {
		run(grid, player, melee, ranged, p, opponent);
	}

	/**
	 * Method to run the thread
	 * @param grid GridPane of the entire map that consists of Rectangle nodes and positions
	 * @param player Unit of the player
	 * @param melee boolean if player can melee attack
	 * @param ranged bolean if player can ranged attack
	 * @param p Position of the player
	 * @param opponent Unit of the opponenet, enemy or boss
	 */
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

	/**
	 * Method for the potion menu
	 * @param player Player of the game
	 */
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

	/**
	 * Method if there is nothing in the inventory
	 * @param label Label telling the player that the inventory is empty
	 * @param viewInventory Stage for the popup box
	 * @param box VBox of layout for the label
	 */
	private void emptyInventory(Label label, Stage viewInventory, VBox box){
		label.setText("There is nothing in your Inventory");
		Button leave = new Button("Leave");
		leave.setOnAction(e -> viewInventory.close());
		box.getChildren().add(leave);
	}

	/**
	 * Method when potion is used
	 * @param listBox ComboBox<String> of the potions in the inventory
	 * @param player Unit of the player
	 * @param viewInventory Stage for viewing the inventory
	 */
	private void usePotionEvent(ComboBox<String> listBox, Unit player, Stage viewInventory) {
		Inventory.use(listBox.getValue(), (Player) player);
		potionList().remove(listBox.getValue());

		LootImg keyImg = Inventory.getImageFromType(listBox.getValue());
		int key = Inventory.getKey(keyImg.getPot());
		Inventory.inventory.remove(key);

		viewInventory.close();
	}

	/**
	 * Method to return the list of potions obtained
	 * @return ArrayList<String>
	 */
	private ArrayList<String> potionList() {
		ArrayList<String> potList = new ArrayList<String>();
		for (int i = 0; i < 31; i++) {
			if (Inventory.inventory.containsKey(i)) {
				potList.add(Inventory.getPotType(i));
			}
		}
		return potList;
	}

	/**
	 * Method to produce buttons for different attacks
	 * @param melee boolean if able to melee
	 * @param ranged boolean if able to ranged
	 * @return Button for the attack type
	 */
	private Button attackBtnType(boolean melee, boolean ranged) {
		if (melee) {
			return ButtonEvents.createButton(0, 0, "Melee");
		} else if (ranged) {
			return ButtonEvents.createButton(0, 0, "Ranged");
		}
		return null;
	}

	/**
	 * Method for player attack in battle
	 * @param event ActionEvent to attack the enemy
	 * @param message Label
	 * @param opponent Unit of the enemy
	 * @param type AttackType of the type of attack
	 * @param grid GridPane of the entire map containing all the Rectangle nodes
	 * @param playerAtk Stage for the player attack
	 * @param p Position of the opponent
	 */
	private void attackEvent(ActionEvent event, Label message, Unit opponent, AttackType type, GridPane grid, Stage playerAtk, Position p) {
		Button sourceBtn = (Button) event.getSource();
		message.setText(String.format("You attacked the %s!", opponent.getClass().toString().replace("class unit.", "")));
		ButtonEvents.continueBtn(message);

		type.attackedThem(opponent, AttackTypes.valueOf(sourceBtn.getText().toUpperCase()));
		checkOpponentHP(opponent, message, grid, playerAtk, p);
	}

	/**
	 * Method to calculate the block probability
	 * @param message Label to tell player if they suceeded in blocking or not
	 * @param playerAtk Stage of the player attack 
	 */
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

	/**
	 * Method for player heal
	 * @param player Unit of the player
	 * @param message Label of messages to player
	 * @param event ActionEvent of the heal button
	 * @param type AttackType of the player 
	 * @param opponent Unit of the opponent
	 */
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

	/**
	 * Method to check the opponent's hp to see if they are defeated
	 * @param opponent Unit of the opponent
	 * @param message Label of messages to the player
	 * @param grid GridPane of the entire map containing all the Rectangle nodes
	 * @param playerAtk Stage of the player attack
	 * @param p Position of the opponent
	 */
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

	/**
	 * Method for probability of the enemy to drop a potion
	 * @param opponent Unit of the opponent
	 * @param message Label of the messages to the player
	 * @param grid GridPane of the entire map containing all the Rectangle nodes
	 * @param playerAtk Stage of a popup for the player
	 * @param p Position of the enemy
	 */
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

	/**
	 * Method to end player turn
	 * @param message Label to tell player their turn has ended
	 * @param playerAtk Stage to preview the message
	 */
	private void endPlayerTurn (Label message, Stage playerAtk) {
		BattleThread.playerTurn = false;
		message.setText("Enemy's turn to attack.");
		ButtonEvents.continueBtn(message);
		playerAtk.close();
	}
}
