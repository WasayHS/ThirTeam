package battle;

import application.Main;
import application.SceneChange;
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
import unit.Player;
import unit.Unit;

public class EnemyTurn extends Thread{

	public EnemyTurn(GridPane grid, Unit player, boolean melee, boolean ranged, Position p, Rectangle node, Enemy enemy, Stage window) {
		run(grid, player, melee, ranged, p, node, enemy,window);
	}
	
	public void run(GridPane grid, Unit player, boolean melee, boolean ranged, Position p, Rectangle node, Enemy enemy, Stage window) {
		Label message = new Label("The enemy attacked you!");

		AttackType type = new AttackType(enemy);
		
		if (melee) {
			type.attackedThem(player, AttackTypes.MELEE);
		}
		
		else if (ranged) {
			type.attackedThem(player, AttackTypes.RANGED);
		}
		
		if (player.getStats().getHealth() <= 0) {
			message = new Label("You were slain by the enemy.");
			Main.continueBtn(message);
			SceneChange.playAgain(window);
		}
		else {
			BattleThread.playerTurn = true;
			Main.continueBtn(message);
		}
		
	}
}
 