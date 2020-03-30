<<<<<<< HEAD:src/battle/EnemyMove.java
package battle;

import java.util.Random;

import javafx.scene.layout.GridPane;
import map.MapSetup;
import map.Position;
import unit.Enemy;
import unit.Unit;

public class EnemyMove {
	GridPane grid;
	public EnemyMove(GridPane g){
		this.grid = g;
	}
	
	public void move(Position playerPos ){
		boolean moveSide, upDown;
		Random r = new Random();
		moveSide = r.nextBoolean();
		if (!moveSide){
			upDown = true;
		}else{
			upDown = false;
		}
		int chY = -1, chX = -1;
		int endY = 1, endX = 1;
		int moveX = 0, moveY =0;
		if(playerPos.getX() == 1){// checks boundries
			chX = 0;
		}else if(playerPos.getY() == 1){
			chY = 0;
		}else if(playerPos.getY() == 1 && playerPos.getX() == 1){
			chX = 0; chY = 0;
		}else if(playerPos.getX() == 7){
			endX = 0;
		}else if(playerPos.getY() == 7){
			endY = 0;
		}else if(playerPos.getY() == 7 && playerPos.getX() == 7){
			endX = 0; endY = 0;
		}
		boolean goUpDown;
		boolean goRL;
		for (int x = chX; x<= endX; x=x+2){
			for (int y = chY; y<= endY; y=y+2){
		
				if (MapSetup.getEnemy(playerPos.getX()+x,playerPos.getY()+y) != null){
					goRL = MapSetup.checkEnemyMove(grid, new Position(playerPos.getX()+x,playerPos.getY()), MapSetup.getEnemy(playerPos.getX()+x,playerPos.getY()+y));
					goUpDown= MapSetup.checkEnemyMove(grid, new Position(playerPos.getX(),playerPos.getY()+y), MapSetup.getEnemy(playerPos.getX()+y,playerPos.getY()+y));
					
					if(goRL){
						System.out.println(playerPos.getX()+" "+playerPos.getY());
						initiateMove(playerPos.getX()+x, playerPos.getY(), grid, MapSetup.getEnemy(playerPos.getX()+x,playerPos.getY()+y));
					}
					
					if(goUpDown){
						System.out.println(playerPos.getX()+" "+playerPos.getY());
						initiateMove(playerPos.getX(), playerPos.getY()+y, grid, MapSetup.getEnemy(playerPos.getX()+x,playerPos.getY()+y));
					}
					
				}
			}
		}
	}
	public static void initiateMove(int moveX, int moveY, GridPane grid, Enemy e){
		System.out.println(moveX+" "+moveY);
		
		
		Position newPos = new Position(moveX,moveY);
		boolean validMove = MapSetup.checkEnemyMove(grid, newPos,e);
		System.out.println(validMove && e != null);
		if (validMove && e != null){
			MapSetup.moveUnit(grid, e, newPos);
		}	
		return;
	}
}
=======
package battle;


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
		}else if(playerPos.getX() == 7){
			endX = 0;
		}else if(playerPos.getY() == 7){
			endY = 0;
		}else if(playerPos.getY() == 7 && playerPos.getX() == 7){
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
		System.out.println();
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
>>>>>>> master:GUI/battle/EnemyMove.java
