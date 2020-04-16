package battle;

import application.ButtonEvents;
import application.GameState;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import unit.Unit;

public class EnemyTurn extends Thread {

	public EnemyTurn(Unit player, boolean melee, boolean ranged, Unit antagonist, Stage window) {
		run(player, melee, ranged, antagonist, window);
	}

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
 