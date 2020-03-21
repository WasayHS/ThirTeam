package battle;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import map.MapSetup;
import map.Position;
import unit.Enemy;
import unit.Player;
import unit.Unit;

public class BattleThread {

	PlayerTurn playerT;
	EnemyTurn enemyT;
	public static boolean playerTurn = true;
	
	public BattleThread (GridPane grid, Unit player, boolean melee, boolean ranged, Position p, Rectangle node) {
		Enemy enemy = MapSetup.getEnemy(p.getX(), p.getY());
		
		if (playerTurn) {
			this.playerT = new PlayerTurn(grid, player, melee, ranged, p, node, enemy);
			playerT.start();
		}
		
		else if (!playerTurn) {
			this.enemyT = new EnemyTurn(grid, player, melee, ranged, p, node, enemy);
			enemyT.start();
		}
		System.out.println("Player health: " + player.getStats().getHealth());
		System.out.println("Enemy health: " + enemy.getStats().getHealth());
	}
		
}
