package battle;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import map.MapSetup;
import map.Position;
import unit.Enemy;
import unit.Player;
import unit.Unit;

public class BattleThread {
	public static final ImagePattern enemy3 = new ImagePattern(new Image("/entities/enemy3HP.png"));
	public static final ImagePattern enemy2 = new ImagePattern(new Image("/entities/enemy2HP.png"));
	public static final ImagePattern enemy1 = new ImagePattern(new Image("/entities/enemy1HP.png"));
	public static final ImagePattern enemy3Hover = new ImagePattern(new Image("/entities/enemy3HPHover.png"));
	public static final ImagePattern enemy2Hover = new ImagePattern(new Image("/entities/enemy2HPHover.png"));
	public static final ImagePattern enemy1Hover = new ImagePattern(new Image("/entities/enemy1HPHover.png"));
	PlayerTurn playerT;
	EnemyTurn enemyT;
	public static boolean playerTurn = true;
	
	public BattleThread (GridPane grid, Unit player, boolean melee, boolean ranged, Position p, Rectangle node, Stage window) {
		Enemy enemy = MapSetup.getEnemy(p.getX(), p.getY());
		Rectangle cell = MapSetup.getNode(grid, p);
		
		if (playerTurn) {
			this.playerT = new PlayerTurn(grid, player, melee, ranged, p, node, enemy);
			playerT.start();
		}
		
		else if (!playerTurn) {
			this.enemyT = new EnemyTurn(grid, player, melee, ranged, p, node, enemy, window);
			enemyT.start();
		}
		
		if (enemy.getStats().getHealth() >= 18 && enemy.getStats().getHealth() < 25) {cell.setFill(enemy3);}
		else if (enemy.getStats().getHealth() < 18 && enemy.getStats().getHealth() >= 11) {cell.setFill(enemy2);}
		else if (enemy.getStats().getHealth() < 11 && enemy.getStats().getHealth() > 0) {cell.setFill(enemy1);}
		
		System.out.println("Player health: " + player.getStats().getHealth());
		System.out.println("Enemy health: " + enemy.getStats().getHealth());
	}
		
}
