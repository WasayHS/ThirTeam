import java.util.ArrayList;

public class Location {
	private final int X_MAX = 1280;
	private final int Y_MAX = 720;
	private ArrayList<ArrayList<Integer>> grid = new ArrayList <ArrayList<Integer>>(7);
	private int xCoord;
	private int yCoord;
	
	public Location(){
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		grid = emptyGrid(new ArrayList <ArrayList<Integer>> (grid));
		this.grid = grid;
	}
	public ArrayList<ArrayList<Integer>> emptyGrid(ArrayList<ArrayList<Integer>> grid){ // makes a grid with empty spots
		
		for (int i = 0; i>=grid.size(); i++){
			for (int a = 0; i>=grid.get(i).size(); a++){
				grid.get(i).add(0);
				// 0 will represent an empty spot
				// 1 will represent player
				// 2 and onwards will represent enemy
			}
		}
		return grid;
	}
	public void move(int x, int y){ // temp method
		if (grid.get(y).get(x) != 1){
			grid.get(y).set(x, 1);
		}
	}
	
	public void moveSouthN (int y){
		boolean won = false;
		boolean canMove = true;
		for (int fe = y-1; fe<grid.size(); fe++){
			for (int e = 0; e<10; e++){
				if (grid.get(fe).get(0) == e){
					canMove = false;
				}
			}
		}
		if(canMove){
			if (grid.get(y-1).get(0) != 1){
				if (grid.get(y-1).get(0) >= 2){
					// fight
					//CODE Player.attackType(null,true);
				}
				if (won){
					grid.get(y-1).set(0,1);
				}else{
					// game over!
				}
			}
			
		}else{
			//error, invalid location
		}
	}
	
	public void moveWestE (int x){
		boolean won = false;
		boolean canMove = true;
		for (int fe = x-1; fe<grid.size(); fe++){
			for (int e = 0; e<10; e++){
				if (grid.get(0).get(fe) == e){
					canMove = false;
				}
			}
		}
		if (canMove)
		if (grid.get(0).get(x-1) != 2){
			if (grid.get(0).get(x-1) >= 2){
				// fight
				//CODE Player.attackType(null,true);
			}
			if(won){
				grid.get(0).set(x-1,1);
			}else{
				// game over!
			}
		}
			
	}

	public void setX(int x){
		if (xCoord+x <= X_MAX){
			xCoord = xCoord + x;
		}
	}
	
	public void setY(int y){
		if (yCoord+y <= Y_MAX){
			yCoord = yCoord + y;
			
		}
	}
	
	public int getX(){
		return xCoord;
	}
	public int getY(){
		return yCoord;
	}
}
