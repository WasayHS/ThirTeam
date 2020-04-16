package battle;


import java.util.Random;
import javafx.scene.layout.GridPane;
import map.MapSetup;
import map.Position;
import unit.Boss;
import unit.Enemy;
import unit.Unit;

/**
 * Class for enemy movement in battle
 * @author Bonnie's Computer
 *
 */
public class EnemyMove {

	/**
	 * Method to check if enemy should follow player if they're close enough
	 * @param playerPos Position of the player
	 * @param grid GridPane of the entire map that consists of Rectangle nodes and positions
	 */
	public static void move(Position playerPos, GridPane grid ){
		int chY = -1, chX = -1;
		int endY = 1, endX = 1;
		if(playerPos.getX() == 1) {// checks boundries
			chX = 0;
		} else if(playerPos.getY() == 1) {
			chY = 0;
		} else if(playerPos.getY() == 1 && playerPos.getX() == 1) {
			chX = 0; chY = 0;
		} else if(playerPos.getX() == 7) {
			endX = 0;
		} else if(playerPos.getY() == 7) {
			endY = 0;
		} else if(playerPos.getY() == 7 && playerPos.getX() == 7) {
			endX = 0; endY = 0;
		}
		boolean goUpDown;
		boolean goRL;
		
		for (int x = chX; x<= endX; x=x+2){
			for (int y = chY; y<= endY; y=y+2){
				Enemy enemy = MapSetup.getEnemy(playerPos.getX()+x,playerPos.getY()+y);
				
				if (enemy != null){
					goRL = MapSetup.checkEnemyMove(grid, new Position(playerPos.getX()+x,playerPos.getY()));
					goUpDown= MapSetup.checkEnemyMove(grid, new Position(playerPos.getX(),playerPos.getY()+y));
					
					if(goRL){
						initiateMove(playerPos.getX()+x, playerPos.getY(), grid, enemy);
					}
					
					if(goUpDown){
						initiateMove(playerPos.getX(), playerPos.getY()+y, grid,enemy);
					}
				}
			}
		}
	}

	/**
	 * Method for enemy to move on the map
	 * @param moveX int x coordinate of where they should move to
	 * @param moveY int y coordinate of where they should move to
	 * @param grid GridPane of the entire map that consists of Rectangle nodes and positions
	 * @param unit Unit that is moving 
	 */
	public static void initiateMove(int moveX, int moveY, GridPane grid, Unit unit){

		Position newPos = new Position(moveX,moveY);
		boolean validMove = MapSetup.checkEnemyMove(grid, newPos);
		
		if (validMove){
			if(unit instanceof Enemy){
				Enemy e = (Enemy) unit;
				MapSetup.moveUnit(grid, e, newPos);
			}else if(unit instanceof Boss){
				Boss b = (Boss) unit;
				MapSetup.moveUnit(grid, b, newPos);
				
			}
		}
	}
	
	/**
	 * Method to compare the boss location to the player location
	 * checks if the boss should move
	 * @param grid GridPane of the entire map that consists of Rectangle nodes and positions
	 * @param b Boss that might move
	 * @param playerPos Position of the player
	 */
	public static void compareBossLocation (GridPane grid, Boss b, Position playerPos){
		Position bossPos = b.getPosition();
		int distanceX = bossPos.getX()-playerPos.getX();
		int distanceY = bossPos.getY()-playerPos.getY();
		Random r = new Random();
		boolean toChangeX = r.nextBoolean();
		int moveBossX=0, moveBossY=0;

		if(distanceX >0) {
			moveBossX = -1;
		} else {
			moveBossX = 1;
		}
		
		if(distanceY >0) {
			moveBossY = -1;
		} else {
			moveBossY = 1;
		}
		if(toChangeX) {
			initiateMove(bossPos.getX()+moveBossX, bossPos.getY(), grid, b);
		} else {
			initiateMove(bossPos.getX(), bossPos.getY()+moveBossY, grid, b);
		}
		
	}
}
