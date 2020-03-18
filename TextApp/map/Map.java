package map;

import java.io.IOException;
import java.util.Scanner;

import battle.AttackTypes;
import battle.Battle;
import game.TextApp;
import unit.Player;
import map.Position;

public class Map{
	
	private char[][] grid;
	public Map(int x, int y) { // Constructor.
		setGrid(x, y);
		for (int i = 0; i < getGrid().length; i++) {
			for(int j = 0; j < getGrid().length; j++) {
				if (i == 0 || i == x - 1) 
				{
					getGrid()[i][j] = ' ';
				}
				else if(j == 0 || j == y - 1) 
				{
					getGrid()[i][j] = ' ';
				}
				else{
					getGrid()[i][j] = 'O'; // O for empty space.
				}
			}
		}
	}

	public void initPlayer(int playerX, int playerY) {
		Player player = new Player(playerX, playerY);
		getGrid()[playerX][playerY] = 'P';
	}
	
	public void setGrid(int x, int y) { // Set method
		this.grid = new char[x][y];
	}	
	
	public char[][]	getGrid() { // Get method
		return grid;
	}
	
	public void move(int x, int y, Player player, Map map){ // Move method.
		TextApp text = new TextApp();
		int playerX = player.getPosition().getX();
		int playerY = player.getPosition().getY();
		
		if (getGrid()[x][y] != 'X' && ((playerX-x) == 0 && Math.abs(playerY-y) == 1||(Math.abs(playerX-x) == 1) && (playerY-y) == 0)){ // Lets the player move to any space where the enemy isn't.
			getGrid()[x][y] = 'P';
			getGrid()[playerX][playerY] = 'O';
			Player player2 = new Player(x,y);
			System.out.println("");
		}
		
		else if ((Math.abs(playerX-x) == 1 && Math.abs(playerY-y)== 1) && getGrid()[x][y] == 'X') {
			System.out.println("Ranged");
			AttackTypes attack = AttackTypes.RANGED;
			Battle.attackBattle(player, x, y, attack, map);
		}
		
		else if ((getGrid()[x][y] == 'X' && (playerX-x) == 0 && Math.abs(playerY-y) == 1) ||(Math.abs(playerX-x) == 1) && (playerY-y) == 0){
			System.out.println("Melee");
			AttackTypes attack = AttackTypes.MELEE;
			Battle.attackBattle(player, x, y, attack, map);
		}
		else {
			System.out.println("Invalid Move." + "\n");
			
		}
	}
	
	public void DefeatedEnemy(int x, int y) { // Method to change the spot of a defeated enemy back into an empty space.
		getGrid()[x][y] = 'O';// Changes the enemy position into an empty space.
						
	}
	public void EnemyPosition(int x, int y) { // Method to choose enemy position.
		getGrid()[x][y] = 'X'; // X for enemy.
	}


//		-------------- Checks the whole grid for enemies
	public int enemiesPresent() {
		int enemy = 0;
		for(int i = 0; i < getGrid().length; i++) {
			for(int j = 0; j < getGrid().length; j++) {
				if (getGrid()[i][j] == 'X') {
					System.out.println("Debug3");
					enemy+=1;
					System.out.println("Enemy" + enemy);
					}
				}
			}
		return enemy;
	}

//		-------------- Called in textApp when player finishes the game and chooses to play again
	public void resetPlayerLocation(Player player) {
		if (enemiesPresent() == 0) {
			getGrid()[player.getPosition().getX()][player.getPosition().getY()] = 'O';
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
	
	
	
	
}
