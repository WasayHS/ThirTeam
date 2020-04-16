package battle;

import application.ButtonEvents;
import application.GameState;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import unit.Unit;

/**
 * Class for the enemy's turn in battle
 * @author Bonnie's Computer
 *
 */
public class EnemyTurn extends Thread {

	/**
	 * Method for the enemy turn in battle
	 * @param player Unit for the player
	 * @param melee boolean if the attack is melee
	 * @param ranged boolean if the attack is ranged
	 * @param antagonist Unit for the enemy
	 * @param window Stage for the message
	 */
	public EnemyTurn(Unit player, boolean melee, boolean ranged, Unit antagonist, Stage window) {
		run(player, melee, ranged, antagonist, window);
	}

	/**
	 * Method to run thread for enemy
	 * @param player Unit for the player
	 * @param melee boolean if the attack is melee
	 * @param ranged boolean if the attack is ranged
	 * @param antagonist Unit for the enemy
	 * @param window Stage for the message
	 */
	public void run(Unit player, boolean melee, boolean ranged, Unit antagonist, Stage window) {
		Label message = new Label("The enemy attacked you!");
		AttackType type;
		type = new AttackType(antagonist);

		if (melee) {
			type.attackedThem(player, AttackTypes.MELEE);
		} else if (ranged) {
			type.attackedThem(player, AttackTypes.RANGED);
		}

		checkPlayerHP(player, window, message);
	}

	/**
	 * Method to check the player's health 
	 * @param player Unit of the player
	 * @param window Stage for if player was defeated by the enemy
	 * @param message Label of the message to the player
	 */
	public void checkPlayerHP(Unit player, Stage window, Label message) {
		if (player.getStats().getHealth() <= 0) {
			message = new Label("You were slain by the antagonist.");
			try {
				GameState.gameOver(window);
				ButtonEvents.continueBtn(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		BattleThread.playerTurn = true;
		ButtonEvents.continueBtn(message);
	}
}
 