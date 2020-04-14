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
import unit.Unit;

public class BattleThread {
	public static final ImagePattern ENEMY3 = new ImagePattern(new Image("/entities/enemy3HP.png"));
	public static final ImagePattern ENEMY2 = new ImagePattern(new Image("/entities/enemy2HP.png"));
	public static final ImagePattern ENEMY1 = new ImagePattern(new Image("/entities/enemy1HP.png"));
	public static final ImagePattern ENEMY3_Hover = new ImagePattern(new Image("/entities/enemy3HPHover.png"));
	public static final ImagePattern ENEMY2_Hover = new ImagePattern(new Image("/entities/enemy2HPHover.png"));
	public static final ImagePattern ENEMY1_Hover = new ImagePattern(new Image("/entities/enemy1HPHover.png"));
	public static final ImagePattern BOSS3 = new ImagePattern(new Image("/entities/boss3HP.png"));
	public static final ImagePattern BOSS2 = new ImagePattern(new Image("/entities/boss2HP.png"));
	public static final ImagePattern BOSS1 = new ImagePattern(new Image("/entities/boss1HP.png"));
	public static final ImagePattern BOSS3_HOVER = new ImagePattern(new Image("/entities/boss3HPHover.png"));
	public static final ImagePattern BOSS2_HOVER = new ImagePattern(new Image("/entities/boss2HPHover.png"));
	public static final ImagePattern BOSS1_HOVER = new ImagePattern(new Image("/entities/boss1HPHover.png"));
	public static boolean playerTurn = true;
	PlayerTurn playerT;
	EnemyTurn enemyT;
	
	public BattleThread(GridPane grid, Unit enemy, Unit player, boolean melee, boolean ranged, Position p, Stage window, Rectangle cell) {
		Unit opponent = enemyType(enemy, p);
		if (playerTurn) {
			this.playerT = new PlayerTurn(grid, player, melee, ranged, p, opponent);
			playerT.start();
		} else {
			this.enemyT = new EnemyTurn(player, melee, ranged, opponent, window);
			enemyT.start();
			if (opponent instanceof Enemy) {
				setEnemyImage(opponent, player, ENEMY1, ENEMY2, ENEMY3, cell);
			} else {
				setEnemyImage(opponent, player, BOSS1, BOSS2, BOSS3, cell);
			}
		}
	}

	private void setEnemyImage(Unit enemyUnit, Unit player, ImagePattern image1, ImagePattern image2, ImagePattern image3, Rectangle cell) {
		if (enemyUnit.getStats().getHealth() >= 18 && enemyUnit.getStats().getHealth() < 25) {cell.setFill(image3);}
		else if (enemyUnit.getStats().getHealth() < 18 && enemyUnit.getStats().getHealth() >= 11) {cell.setFill(image2);}
		else if (enemyUnit.getStats().getHealth() < 11 && enemyUnit.getStats().getHealth() > 0) {cell.setFill(image1);}

		displayHealth(player, enemyUnit);
	}

	private Unit enemyType(Unit enemy, Position p) {
		if (enemy instanceof Boss) {
			return MapSetup.getBoss(p.getX(),p.getY());
		}
		return MapSetup.getEnemy(p.getX(),p.getY());
	}
	private void displayHealth(Unit player, Unit enemyUnit) {
		System.out.println(String.format("Player health: %s", player.getStats().getHealth()));
		System.out.println(String.format("%s health: %s", enemyUnit.getClass().toString().replace("class unit.", ""), enemyUnit.getStats().getHealth()));
	}
}
