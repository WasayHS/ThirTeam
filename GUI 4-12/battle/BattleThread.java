package battle;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import map.MapSetup;
import map.Position;
import unit.Boss;
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
	public static final ImagePattern boss3 = new ImagePattern(new Image("/entities/boss3HP.png"));
	public static final ImagePattern boss2 = new ImagePattern(new Image("/entities/boss2HP.png"));
	public static final ImagePattern boss1 = new ImagePattern(new Image("/entities/boss1HP.png"));
	public static final ImagePattern boss3Hover = new ImagePattern(new Image("/entities/boss3HPHover.png"));
	public static final ImagePattern boss2Hover = new ImagePattern(new Image("/entities/boss2HPHover.png"));
	public static final ImagePattern boss1Hover = new ImagePattern(new Image("/entities/boss1HPHover.png"));
	PlayerTurn playerT;
	EnemyTurn enemyT;
	
	public static boolean playerTurn = true;
	
	public BattleThread (GridPane grid, Unit player, boolean melee, boolean ranged, Position p, Rectangle node, Stage window) {
		Enemy enemy = MapSetup.getEnemy(p.getX(), p.getY());
		Boss b = MapSetup.getBoss(p.getX(), p.getY());
		boolean againstBoss = true;
		if(b == null)
			againstBoss = false;
		
		
		
		Rectangle cell = MapSetup.getNode(grid, p);
		
		if (playerTurn) {
			this.playerT = new PlayerTurn(grid, player, melee, ranged, p, node, enemy, b);
			playerT.start();
		}
		
		else if (!playerTurn) {
			if(!againstBoss){
				this.enemyT = new EnemyTurn(grid, player, melee, ranged, p, node, enemy, window);
				enemyT.start();
			}else{
				this.enemyT = new EnemyTurn(grid, player, melee, ranged, p, node, b, window);
				enemyT.start();
			}
		}
		int health;
		String declaredAs = "Boss ";
		if(!againstBoss){
			if (enemy.getStats().getHealth() >= 18 && enemy.getStats().getHealth() < 25) {cell.setFill(enemy3);}
			else if (enemy.getStats().getHealth() < 18 && enemy.getStats().getHealth() >= 11) {cell.setFill(enemy2);}
			else if (enemy.getStats().getHealth() < 11 && enemy.getStats().getHealth() > 0) {cell.setFill(enemy1);}
			health = enemy.getStats().getHealth();
			declaredAs = "Enemy ";
		}else{
			if (b.getStats().getHealth() >= 33 && b.getStats().getHealth() < 45) {cell.setFill(boss3);}
			else if (b.getStats().getHealth() < 22 && b.getStats().getHealth() >= 11) {cell.setFill(boss2);}
			else if (b.getStats().getHealth() < 11 && b.getStats().getHealth() > 0) {cell.setFill(boss1);}
			health = b.getStats().getHealth();
		}
		
		System.out.println("Player health: " + player.getStats().getHealth());
		System.out.println(declaredAs+"health: " + health);
	}
		
}
