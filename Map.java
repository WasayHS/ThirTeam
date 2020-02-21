
public class Map {
	
	private char[][] grid;
	
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
	
	public void move(int x, int y){ // Move method
		if (getGrid()[x][y] != 'X'){ // Lets the player move to any space where the enemy isn't.
			for(int i = 0; i < getGrid().length; i++) {
				for(int j = 0; j < getGrid().length; j++) {
					if(getGrid()[i][j] == 'P'){ // Changes the previous player position into an empty space.
							getGrid()[i][j] = 'O';
							getGrid()[x][y] = 'P';
					}
				}
			}
		}
		else {
			System.out.println("Invalid Move" + "\n"); // Prints invalid message and map remains unchanged.
		}
		}
	
	public void DefeatedEnemy(int x, int y) { // Method to change the spot of a defeated enemy back into an empty space.
				if(getGrid()[x][y] == 'X'){ // Changes the enemy position into an empty space.
						getGrid()[x][y] = 'O';
				}
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
	
	public static void main(String[] args) { // temporary main method to test out the map, methods, and invalid moves.
		Map b = new Map(5, 5);
		b.PlayerPosition(4, 2);
		b.EnemyPosition(1, 2);
		b.EnemyPosition(3, 3);
		b.EnemyPosition(0, 0);
		b.DefeatedEnemy(3, 3);
		b.move(3, 3);
		b.DefeatedEnemy(1,2);
		b.move(1, 2);
		b.move(0, 0);
		System.out.println(b);
		
	}
	
	
}
