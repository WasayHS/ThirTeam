package battle;

import java.util.Random;

import javafx.scene.layout.GridPane;
import map.MapSetup;
import map.Position;
import unit.Enemy;

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
		for (int xc = chX; xc<=endX; xc++){
			for (int yc = chY; yc<=endY; yc++){
				if (MapSetup.getEnemy(playerPos.getX()+xc,playerPos.getY()+xc) != null){
					Enemy enemyMoves = MapSetup.getEnemy(playerPos.getX()+xc,playerPos.getY()+xc);
					if (upDown){
						moveX = playerPos.getX()+xc;
						moveY = playerPos.getY();
					}else if (moveSide){
						moveY = playerPos.getY()+yc;
						moveX = playerPos.getX();
					}
					Position newPos = new Position(moveX,moveY);
					MapSetup.moveUnit(grid, enemyMoves, newPos);
				}
			}
		}
	}
}
