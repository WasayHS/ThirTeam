package battle;


import application.GameState;
import javafx.scene.layout.GridPane;
import map.MapSetup;
import map.Position;
import unit.Enemy;
public class EnemyMove {

	public static void move(Position playerPos, GridPane grid ){
		int chY = -1, chX = -1;
		int endY = 1, endX = 1;
		if(playerPos.getX() == 1){// checks boundries
			chX = 0;
		}else if(playerPos.getY() == 1){
			chY = 0;
		}else if(playerPos.getY() == 1 && playerPos.getX() == 1){
			chX = 0; chY = 0;
		}else if(playerPos.getX() == GameState.mapSize){
			endX = 0;
		}else if(playerPos.getY() == GameState.mapSize){
			endY = 0;
		}else if(playerPos.getY() == GameState.mapSize && playerPos.getX() == GameState.mapSize){
			endX = 0; endY = 0;
		}
		boolean goUpDown;
		boolean goRL;
		
		for (int x = chX; x<= endX; x=x+2){
			for (int y = chY; y<= endY; y=y+2){
				Enemy enemy = MapSetup.getEnemy(playerPos.getX()+x,playerPos.getY()+y);
				
				if (enemy != null){
					goRL = MapSetup.checkEnemyMove(grid, new Position(playerPos.getX()+x,playerPos.getY()),enemy);
					goUpDown= MapSetup.checkEnemyMove(grid, new Position(playerPos.getX(),playerPos.getY()+y),enemy);
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
	public static void initiateMove(int moveX, int moveY, GridPane grid, Enemy e){

		Position newPos = new Position(moveX,moveY);
		boolean validMove = MapSetup.checkEnemyMove(grid, newPos,e);
		
		if (validMove && e != null){
			MapSetup.moveUnit(grid, e, newPos);
		}	
		return;
	}
}
