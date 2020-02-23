import java.util.Random;
import java.util.Scanner;
import java.util.Random;

public class TextApp{
	private Player player = new Player();
	private AttackTypes attackTypes;
	private Map map = new Map(5, 5);
	private Location location;
	private Enemy enemy;
	private AttackType attackType = new AttackType(player, enemy, attackTypes);
	
	// Method for ending game
	public void exit() {
		System.exit(1);
	}
	//Method for initializing game
	public void newGame() {
		Boolean playing = true;
		Random random = new Random();
		//Object of map class with 5 as parameters
		map.PlayerPosition(4,2);
		
		//Iterating up to 4 to randomize enemy spawn
		for(int i = 0; i < 5; i++) {
			int randloc1 = random.nextInt(5);
			int randloc2 = random.nextInt(5);
			
			//Makes sure that the enemies don't spawn in the same row as player
			if(randloc1 != 4) {
			map.EnemyPosition(randloc1, randloc2);
			}
		}
		System.out.println(map);
		
		while(playing == true) {
			if(player.getHP() > 0) {
				check();
				Scanner input = new Scanner(System.in);
			    System.out.println("Move(x,y): ");

			    String mapCoor = input.nextLine();
			    String[] parts = mapCoor.split(",");

			    int x = Integer.parseInt(parts[0]);
			    int y = Integer.parseInt(parts[1]);
			    
			    map.move(x,y);
			    System.out.println(map);
			    
			}
			else {
				playing = false;
			}
		}
		
	}
	//Method for dealing with options from check method
	public void options(int option) {
		if(option == 1) {
			//attack here
		}
		//gives the player the option to run
		else if(option == 2){
			Scanner input = new Scanner(System.in);
		    System.out.println("Move(x,y): ");

		    String mapCoor = input.nextLine();
		    String[] parts = mapCoor.split(",");

		    int x = Integer.parseInt(parts[0]);
		    int y = Integer.parseInt(parts[1]);
			map.move(x, y);
			System.out.println(map);
		}
	}
	
	// Checks for adjacent enemies
	//**STILL BUGGY! NEEDS TO BE FIXED**//Need to compare from players current location
		public void check() {
			for(int i = 0; i < map.getGrid().length; i++) {
				for(int j = 0; j < map.getGrid()[i].length; j++) {
					
					//checks for right neighbor
					if ((j + 1) < map.getGrid().length && map.getGrid()[i][j + 1] == 'X') {
						Scanner input = new Scanner(System.in);
						System.out.println("1. Melee Attack \n2. Move");
						int option = input.nextInt();
						options(option);
						input.nextLine();
					}
					//checks for left neighbor
					else if((j - 1) > map.getGrid()[i].length && map.getGrid()[i][j-1] == 'X'){
						Scanner input = new Scanner(System.in);
						System.out.println("1. Melee Attack \n2. Move");
						int option = input.nextInt();
						options(option);
						input.nextLine();
					}
					//checks for one neighbor up
					else if((i - 1) > map.getGrid()[i].length && map.getGrid()[i-1][j] == 'X'){
						Scanner input = new Scanner(System.in);
						System.out.println("1. Melee Attack \n2. Move");
						int option = input.nextInt();
						options(option);
						input.nextLine();
					}
					
					//checks for one neighbor down
					else if((i + 1) < map.getGrid()[i].length && map.getGrid()[i+1][j] == 'X'){
						Scanner input = new Scanner(System.in);
						System.out.println("1. Melee Attack \n2. Move");
						int option = input.nextInt();
						options(option);
						input.nextLine();
					}
				}
			}
		}
	
	//User selection
	public void StartGame() {
		Boolean valid = true;
		int user;
		System.out.println("=====Welcome to Default Game=====");
		Scanner input = new Scanner(System.in);
		System.out.println("1.New Game \n2.Exit");
		user = input.nextInt();
		input.nextLine();
		 
		//Loop to check for valid selection
		while(valid == true) {
		if(user == 1) {
			newGame();
			valid = false;
		}
		else if(user == 2) {
			valid = false;
			exit();
		}
		else {
			System.out.println("Selection is invalid");
			Scanner inputValid = new Scanner(System.in);
			System.out.println("1.New Game \n2.Exit");
			user = inputValid.nextInt();
			inputValid.nextLine();
			}
		}
		input.close();
	}
	
	//Starts the game
	public static void main(String[] args) {
		
		TextApp game = new TextApp();
		game.StartGame();
	}

}
