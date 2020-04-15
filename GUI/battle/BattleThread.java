package battle;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import map.MapSetup;
import map.Position;
import unit.Boss;
import unit.Enemy;
import unit.Unit;

/**
 * Class for the battle thread of the game
 * @author Bonnie's Computer
 *
 */
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
	
	/**
	 * Constructor
	 * @param grid GridPane of the entire map containing all the Rectangle nodes
	 * @param enemy Enemy of the enemy in battle
	 * @param player Player of the player in battle
	 * @param melee boolean if attack is a melee attack
	 * @param ranged boolean if attack is a ranged attack
	 * @param p Position of the player
	 * @param window Stage of the battle scene
	 * @param cell Rectangle on the map where the enemy is
	 */
	public BattleThread(GridPane grid, Unit enemy, Unit player, boolean melee, boolean ranged, Position p, Stage window, Rectangle cell) {
		Unit opponent = enemyType(enemy, p);
		Stage hDisplay = new Stage();
		if (opponent instanceof Enemy) {
				setEnemyImage(opponent, player, ENEMY1, ENEMY2, ENEMY3, cell, hDisplay);
			} else {
				setEnemyImage(opponent, player, BOSS1, BOSS2, BOSS3, cell, hDisplay);
		}
		
		if (playerTurn) {
			this.playerT = new PlayerTurn(grid, player, melee, ranged, p, opponent);
			playerT.start();
		} else {
			this.enemyT = new EnemyTurn(player, melee, ranged, opponent, window);
			enemyT.start();
			
		}
		hDisplay.close();
	}

	/**
	 * Method to set the enemy image on the Rectangle
	 * @param enemyUnit Unit of the enemy
	 * @param player Unit of the player
	 * @param image1 ImagePattern of the enemy at full health
	 * @param image2 ImagePattern of the enemy at medium health
	 * @param image3 ImagePattern of the enemy at low health
	 * @param cell Rectangle where the enemy image is changing 
	 * @param healthStage Stage of the popup window to display health
	 */
	private void setEnemyImage(Unit enemyUnit, Unit player, ImagePattern image1, ImagePattern image2, ImagePattern image3, Rectangle cell, Stage healthStage) {
		if (enemyUnit.getStats().getHealth() >= 18 && enemyUnit.getStats().getHealth() < 25) {cell.setFill(image3);}
		else if (enemyUnit.getStats().getHealth() < 18 && enemyUnit.getStats().getHealth() >= 11) {cell.setFill(image2);}
		else if (enemyUnit.getStats().getHealth() < 11 && enemyUnit.getStats().getHealth() > 0) {cell.setFill(image1);}
		
		displayHealth(player, enemyUnit, healthStage);
		
	}

	/**
	 * Method to determine enemy type, boss or normal enemy
	 * @param enemy Unit of the enemy
	 * @param p Position of where the enemy is located
	 * @return Unit of the type of enemy
	 */
	private Unit enemyType(Unit enemy, Position p) {
		if (enemy instanceof Boss) {
			return MapSetup.getBoss(p.getX(),p.getY());
		}
		return MapSetup.getEnemy(p.getX(),p.getY());
	}
	
	/**
	 * Method to display health during battle in another window
	 * @param player Unit of the player
	 * @param enemyUnit Unit of the enemy unit
	 * @param hDisplay Stage of the popup box for health
	 */
	private void displayHealth(Unit player, Unit enemyUnit, Stage hDisplay) {
		VBox textBox = new VBox();
		Label pHealth = new Label(String.format("Player health: %s", player.getStats().getHealth()));
		Label antagHealth = new Label(String.format("%s health: %s", enemyUnit.getClass().toString().replace("class unit.", ""), enemyUnit.getStats().getHealth()));
		textBox.getChildren().add(antagHealth);
		textBox.getChildren().add(pHealth);
		Scene scene = new Scene(textBox, 350,100);
		hDisplay.setScene(scene);
		hDisplay.show();
	}
}
