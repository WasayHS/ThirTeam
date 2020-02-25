public class Map{
	
	private char[][] grid;
	private int lastX;
	private int lastY;
	
	public void setGrid(int x, int y) { // Set method
		this.grid = new char[x][y];
	}	
	
	public char[][]	getGrid() { // Get method
		return grid;
	}
	
	public Map(int x, int y) { // Constructor.
		setGrid(x, y);
		for (int i = 0; i < getGrid().length; i++) {
			for(int j = 0; j < getGrid().length; j++) {
				getGrid()[i][j] = 'O'; // O for empty space.
				
			}
		}
			}
	
	
	public void PlayerPosition(int x, int y) { // Method to choose player position.
		getGrid()[x][y] = 'P'; // P for player.
	}
	
	public void EnemyPosition(int x, int y) { // Method to choose enemy position.
		getGrid()[x][y] = 'X'; // X for enemy.
	}

	public void move(int x, int y){ // Move method.
		if (getGrid()[x][y] != 'X' ){ // Lets the player move to any space where the enemy isn't.
			for(int i = 0; i < getGrid().length; i++) {
				for(int j = 0; j < getGrid().length; j++) {
					if(getGrid()[i][j] == 'P'){ // Changes the previous player position into an empty space.
						if (((i - 1) == x && (y == j)) || ((i+1) == x && (y==j)) || ((x == i) && (1 + j) == y) || ((x == i) && (j - 1) == y)) {
							getGrid()[i][j] = 'O';
							getGrid()[x][y] = 'P';
							this.lastX = x;
							this.lastY = y;
						}//Kyle: Need this since when player turns a valid left and fulfills first if (line 39), console still prints "Invalid move" but stores right x and y coors. Don't know why.
						else if (((i - 1) != x && (y == j)) || ((i+1) != x && (y==j)) || ((x == i) && (j + 1) != y) || ((x == i) && (j - 1) != y)) { 
							this.lastX = i;
							this.lastY = j;
						}
						else {
							System.out.println("Invalid Move in else" + "\n");
						}
					}
				}
			}
		}
		else {
			System.out.println("Invalid Move outside" + "\n"); // Prints invalid message and map remains unchanged.
		}
		}
	
	public void DefeatedEnemy(int x, int y) { // Method to change the spot of a defeated enemy back into an empty space.
				if(getGrid()[x][y] == 'X'){ // Changes the enemy position into an empty space.
						getGrid()[x][y] = 'O';
				
				}
	}
	
	//Kyle: Have to make a method to scan the whole grid if there are still enemies:
	public boolean scanGrid() { //Jose: Method to scan grid. Returns false if there are still enemies and true otherwise. Test it out and let me know if there are any bugs.
		boolean noEnemy = true;
		for (int i = 0; i < getGrid().length; i++) {
			for (int j = 0; j < getGrid().length; j++) {
				if (getGrid()[i][j] == 'X') {
					noEnemy = false;
				}
				
			}
			
		}
		return noEnemy;
	}
	
	public String toString() { // String with map.
		String result = "";
		for (int i = 0; i < getGrid().length; i++) {
			for (int j = 0; j < getGrid().length; j++) {
				result += getGrid()[i][j] + " ";
			}
			result += "\n";
		}
		return result;
	}
	
	public int getLastX() {
		return lastX;
	}
	
	public int getLastY() {
		return lastY;
	}
//	public static void main(String[] args) { // temporary main method to test out the map, methods, and invalid moves.
//		Map b = new Map(5, 5);
//		b.PlayerPosition(4, 2);
//		b.EnemyPosition(1, 2);
//		b.EnemyPosition(3, 3);
//		b.EnemyPosition(0, 0);
//		b.DefeatedEnemy(3, 3);
//		b.move(3, 3);
//		b.DefeatedEnemy(1,2);
//		b.move(1, 2);
//		b.move(0, 0);
//		System.out.println(b);
//		
//	}
	
	
}
