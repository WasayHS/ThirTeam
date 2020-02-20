
public class Map {
	
	private char[][] grid;
	
	public Map(int x, int y) {
		this.grid = new char[x][y];
		for (int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid.length; j++) {
				grid[i][j] = 'O'; // O for empty space.
				
			}
		}
			}
	
	
	public void setPlayerPosition(int x, int y) {
		grid[x][y] = 'P'; // P for player.
	}
	
	public void setEnemyPosition(int x, int y) {
		grid[x][y] = 'E'; // E for enemy.
	}
	
	public void move(int x, int y){ // Move method
		if (grid[x][y] != 'E'){ // Lets the player move to any space where the enemy isn't.
			for(int i = 0; i < x; i++) {
				for(int j = 0; j < y; j++) {
					if(grid[i][j] == 'P'){ // Changes the previous player position into an empty space.
							grid[i][j] = 'O';
							grid[x][y] = 'P';
					}
				}
			}
		}
		else {
			System.out.println("Invalid Move" + "\n"); // Prints invalid message and map remains unchanged.
		}
		}
	
	public String toString() { // String with map.
		String result = "";
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				result += grid[i][j] + " ";
			}
			result += "\n";
		}
		return result;
	}
	
		public static void main(String[] args) { // temporary main method to test out the map and methods.
			Map b = new Map(5, 5);
			b.setPlayerPosition(4, 2);
			b.setEnemyPosition(1, 2);
			b.setEnemyPosition(3, 4);
			b.move(1, 2);
			System.out.println(b);
			
			
		}
	
	
	
}
