package battle;

import application.GameState;
import application.Main;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import unit.Enemy;
import unit.Unit;

public class EnemyTurn extends Thread {

	public void run(Unit player, boolean melee, boolean ranged, Enemy enemy, Stage window) {

		Label message = new Label("The enemy attacked you!");
		DamageCalculator type = new DamageCalculator(enemy);
		
		if (melee) {
			type.attack(player, AttackTypes.MELEE);
		} else if (ranged) {
			type.attack(player, AttackTypes.RANGED);
		}
		
		if (player.getStats().getHealth() <= 0) {
			message = new Label("You were slain by the enemy.");
			try {
				GameState.gameOver(window);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			BattleThread.playerTurn = true;
			Main.continueBtn(message);
		}
	}
}
 