import java.util.Random;
import java.util.Scanner;
import java.util.Random;

public class TextApp{
	private Player player = new Player();
	private AttackTypes attackTypes;
	private Map map = new Map(5, 5);
	private Location location;
	private Enemy enemy = new Enemy();
	private AttackType attackType = new AttackType(player, enemy, attackTypes);
	
	
// ----------------------------------------- Method for initializing game -----------------------------------------
	public void newGame() {
		Boolean playing = true;
		Random random = new Random();
		map.PlayerPosition(4,2); //	Initial player position
		//	Iterating up to 4 to randomize enemy spawn
		for(int i = 0; i < 5; i++) {
			int randLocX = random.nextInt(5); //	random x coordinate
			int randLocY = random.nextInt(5); //	random y coordinate
									
			if(randLocX != 4) { //	Makes sure that the enemies don't spawn in player's initial row
			map.EnemyPosition(randLocX, randLocY);
			}
		}
		System.out.println(map);
		
		while(playing == true) {
			if(player.getHP() > 0) {
				checkLocation(4,2);
				Scanner input = new Scanner(System.in);
			    System.out.println("Move(x,y) @whileNewGame: ");

			    String mapCoor = input.nextLine();
			    String[] parts = mapCoor.split(","); // Split user input to [x, y] for parsing
			    int x = Integer.parseInt(parts[0]); //updated player x location
			    int y = Integer.parseInt(parts[1]); // updated player y location
			    map.move(x,y); //map.move checks if move is valid, should return player current location if move was not valid.

			    System.out.println("Map@NewGame");
			    System.out.println(map);

				checkLocation(x, y); // Passes player current location for checking enemies
			}
			else { //Player dies
				playing = false;
				
				System.out.println("Game Over!");
				Scanner input = new Scanner(System.in);
				System.out.println("1. Play again! \n2. Exit game.");
				int option = input.nextInt();
				
				if (option == 1) {
					StartGame();
				}
				else if (option == 2) {
					exit();
				}
			}
		}
	}
// ----------------------------------------- Method for dealing with options from check method  -----------------------------------------
	public void options(int option) {
		if(option == 1) {
			Scanner input = new Scanner(System.in);
		    System.out.println("Choose enemy to attack (x,y): ");

		    String enemy = input.nextLine();
		    String[] parts = enemy.split(",");
		    
		    int enX = Integer.parseInt(parts[0]);
		    int enY = Integer.parseInt(parts[1]);
		    	if (map.getGrid()[enX][enY] == 'X') {
		    		toBattle(enX, enY);
		    	}
		    	else if (map.getGrid()[enX][enY] != 'X') {
		    		System.out.println("No enemy on selected location.");
		    		options(1);
		    	}
		    	// *** In progress; should say invalid location when player inputs coords greater than index of 4
		    	else if (enX > (map.getGrid().length-1) && enY > (map.getGrid().length-1)) {
		    		System.out.println("Invalid location.");
		    		options(1);
		    	}
		}
		else if(option == 2){
			Scanner input = new Scanner(System.in);
		    System.out.println("Move(x,y)@Option2: ");

		    String mapCoor = input.nextLine();
		    String[] parts = mapCoor.split(",");
		    
		    int x = Integer.parseInt(parts[0]);
		    int y = Integer.parseInt(parts[1]);
			map.move(x, y);
			System.out.println("map@Option2");
			System.out.println(map);
			checkLocation(x,y);

		}
	}
	
// --------------------------------------- Battle ** dudududdudududuel ** ----------------------------------------------
	public void toBattle(int enX, int enY) {
		Boolean health = true;
		int healCount = 2;
		
		while (health == true){ // While the player/enemy is alive
			System.out.println("Player Health: " + player.getHP());
			System.out.println("Enemy Health: " + enemy.getHealth());
			
			Scanner input = new Scanner(System.in);
			System.out.println("\n1. Melee \n2. Heal");
			int choice = input.nextInt();

			if (choice == 1) { // To attack
				AttackTypes attack = AttackTypes.MELEE;
				AttackType temp = new AttackType(player, enemy, attack);
				int initialH = enemy.getHealth();
				temp.attackedThem(attack);
				int afterH = enemy.getHealth();
				
				if (enemy.getHealth() <= 0) {
					map.DefeatedEnemy(enX, enY);
					System.out.println("You dealt " + (initialH - afterH) + " damage to the enemy!" );
					System.out.println("Player Health: " + player.getHP());
					System.out.println("Enemy Health: 0");
					System.out.println("\nYou have defeated the enemy!");
					System.out.println(map);
					health = false;
				}
				else {
					System.out.println("You dealt " + (initialH - afterH) + " damage to the enemy!");
				}
			}
			
			else if (choice == 2) {
				if (healCount >=0) { // Restricts player to only 3 heals.
					int playerH = player.getHP();
					if (playerH >= 50) {
						System.out.println("You have not taken damage yet.");
					}
					else {
						int beforeH = player.getHP();
						AttackTypes attack = AttackTypes.HEAL;
						AttackType temp = new AttackType(player, enemy, attack);
						temp.attackedThem(attack);
						int afterH = player.getHP();
						
						System.out.println("You regained " + (afterH - beforeH) + " HP.");
						System.out.println(healCount + " heals remaining.\n");
						healCount -=1;
					}
				}
				else {
					System.out.println("You have reached your maximum heals.");
				}
			}
		}
	}
// ------------------------------------- Checks for enemies around player -------------------------------------------
	public void checkLocation(int x, int y) {
//	Works for the most part, might have bugs but I haven't encountered one.
		if (x <= (map.getGrid().length - 1) && x!=0) {
			if (y >=0 && y!=(map.getGrid().length - 1)) {
				if (map.getGrid()[x-1][y] == 'X'||map.getGrid()[x][y-1] == 'X') { // Checks up & right
					Scanner input = new Scanner (System.in);
					System.out.println("1. Attack \n2. Move");
					int option = input.nextInt();
					options(option);
				}
			}
			else if (y !=0 && y <= (map.getGrid().length - 1)) {
				if (map.getGrid()[x-1][y] == 'X'||map.getGrid()[x][y-1] == 'X') { // Checks up & left
					Scanner input = new Scanner (System.in);
					System.out.println("1. Attack \n2. Move");
					int option = input.nextInt();
					options(option);
				}
			else {
				if (map.getGrid()[x-1][y] == 'X'||map.getGrid()[x][y + 1] == 'X'||map.getGrid()[x][y-1] == 'X') {
					Scanner input = new Scanner (System.in);
					System.out.println("1. Attack \n2. Move");
					int option = input.nextInt();
					options(option);
				}
			}
		}
			else {
				Scanner input = new Scanner(System.in);
			    System.out.println("Move(x,y)@CLElse: ");
	
			    String mapCoor = input.nextLine();
			    String[] parts = mapCoor.split(",");
			    
			    int x2 = Integer.parseInt(parts[0]);
			    int y2 = Integer.parseInt(parts[1]);
				map.move(x2, y2);
				System.out.println("map@CLElse");
				System.out.println(map);
			}
			
			if (map.getGrid()[x-1][y] == 'X') {
				Scanner input = new Scanner (System.in);
				System.out.println("1. Attack \n2. Move");
				int option = input.nextInt();
				options(option);
			}
		}
		
//		if x !=4 
		if (x >=0 && x!=(map.getGrid().length - 1)) {
			if (y >=0 && y != (map.getGrid().length - 1) ) { //x == 0, y == 0
				if (map.getGrid()[x+1][y] == 'X' || map.getGrid()[x][y + 1] == 'X') { //down & left
					Scanner input = new Scanner (System.in);
					System.out.println("1. Attack \n2. Move");
					int option = input.nextInt();
					options(option);
				}
			}
			else if (y > 0 || y == (map.getGrid().length - 1)) { //x == 0 y == 4
				if (map.getGrid()[x+1][y] == 'X' || map.getGrid()[x][y - 1] == 'X') { // down & right
					Scanner input = new Scanner (System.in);
					System.out.println("1. Attack \n2. Move");
					int option = input.nextInt();
					options(option);
					}
			else {
				if (map.getGrid()[x+1][y] == 'X' || map.getGrid()[x][y + 1] == 'X' || map.getGrid()[x][y-1] == 'X') {
					Scanner input = new Scanner (System.in);
					System.out.println("1. Attack \n2. Move");
					int option = input.nextInt();
					options(option);
					}
			}
		}

		else {
				Scanner input = new Scanner(System.in);
			    System.out.println("Move(x,y)@CLElse: ");
	
			    String mapCoor = input.nextLine();
			    String[] parts = mapCoor.split(",");
			    
			    int x2 = Integer.parseInt(parts[0]);
			    int y2 = Integer.parseInt(parts[1]);
				map.move(x2, y2);
				System.out.println("map@CLElse");
				System.out.println(map);
			}
		}
	}

// ---------------------------------------------- Method for ending game -------------------------------------------------------
		public void exit() {
			System.exit(1);
		}
	
//User selection
	public void StartGame() {
		Boolean valid = true;
		int user;
		System.out.println("=====Welcome to Default Game=====");
		System.out.println("1.New Game \n2.Exit");
		Scanner input = new Scanner(System.in);
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
