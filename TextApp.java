import java.util.Random;
import java.util.Scanner;

public class TextApp{
	private Player player = new Player();
	private AttackTypes attackTypes;
	private Map map = new Map(5, 5);
	private Location location;
	private Enemy enemy = new Enemy();
	private AttackType attackType = new AttackType(player, enemy, attackTypes);
	
// --------------------------------------- Instructions for player -------------------------------------------
	public void howToPlay() {
		System.out.println("To move:\n\tInput coordinates starting from the index \n\tof 0 in the format of (x,y).\n");
		System.out.println("To engage in battle:\n\tWhen adjacent to an enemy, you will be given\n\tan option to attack or move. Chose attack and \n\tenter enemy coordinates in the same format as \n\thow you would move.\n");
		System.out.println("During the battle:\n\tYou will be given the option to attack or heal.\n\tYou are restricted to using only 3 heal in \n\tone round of battle.\n");
		
		Scanner input = new Scanner(System.in);
		System.out.println("1. Start Game");
		int opt = input.nextInt();
		boolean c = true;
		
		while (c == true) {
			if (opt == 1) {
				c = false;
				newGame();
			}
			else {
				Scanner input1 = new Scanner(System.in);
				System.out.println("Invalid input. \n1.Start Game");
				int opt1 = input.nextInt();
			}
		}
	}
	
// ----------------------------------------- Method for initializing game -----------------------------------------
	public void newGame() { // Sets up map using Map class
		Random random = new Random();
		int playInitX = 4;
		int playInitY = 2;
		map.PlayerPosition(playInitX, playInitY); //	Initial player position

		for(int i = 0; i < 5; i++) {
			int randLocX = random.nextInt(5); //	random x coordinate
			int randLocY = random.nextInt(5); //	random y coordinate
									
			if(randLocX != 4) { //	Makes sure that the enemies don't spawn in player's initial row
				map.EnemyPosition(randLocX, randLocY);
			}
		}
		System.out.println(map);
		playGame();
	}
	
//	---------------------------- Starts the Game, runs when enemies are still present
	public void playGame() {
		while(map.enemiesPresent() == true) {
			checkLocation(4,2); // Checks for enemies around player spawn area
		    System.out.println("Move(x,y): ");
		    String[] parts = takeInput(); // Take input method
		    int xCoor = xCoors(parts);
		    int yCoor = yCoors(parts);
		    map.move(xCoor,yCoor); //map.move checks if move is valid, should return player current location if move was not valid.

		    System.out.println("Map@NewGame");
		    System.out.println(map);

			checkLocation(xCoor, yCoor); // Passes player current location for checking enemies

		}
	}
	
// ----------------------------------------- Method for dealing with options from check method when enemies are beside the player -----------------------------------------
	public void options(int option) {
		if(option == 1) { // Player chose to attack the enemy	
		    System.out.println("Choose enemy to attack (x,y): ");
		    String[] parts = takeInput(); 
		    int xCoor = xCoors(parts);
		    int yCoor = yCoors(parts);
		    	if (map.getGrid()[xCoor][yCoor] == 'X') {
		    		toBattle(xCoor, yCoor);
		    	}
		    	else if (map.getGrid()[xCoor][yCoor] != 'X') {
		    		System.out.println("No enemy on selected location.");
		    		options(1);
		    	}
		    	// *** In progress; should say invalid location when player inputs coords greater than index of 4
		    	else if (xCoor > (map.getGrid().length-1) && yCoor > (map.getGrid().length-1)) {
		    		System.out.println("Invalid location.");
		    		options(1);
		    	}
		}
		else if(option == 2){ // Player chose to move
		    System.out.println("Move(x,y): ");
		    String[] parts = takeInput(); // Take input method
		    int xCoor = xCoors(parts);
		    int yCoor = yCoors(parts);
			map.move(xCoor, yCoor);
			System.out.println("map@Option2");
			System.out.println(map);
			checkLocation(xCoor,yCoor);

		}
		else { // Invalid input
			System.out.println("Please enter valid option.");
			Scanner input = new Scanner(System.in);
			System.out.println("1. Attack \n2. Move");
			int choice = input.nextInt();
			options(choice); // Recurses until valid
		}
	}
// --------------------------------------- 3 methods for user input coordinates -------------------------------------------
	public String[] takeInput() {
		Scanner input = new Scanner(System.in);
	    String coordinates = input.nextLine();
	    String[] parts = coordinates.split(",");
	    
	    boolean bool = true;
    	while (bool == true) {
    		if (parts.length != 2) { // If input is not (x,y) format
    		System.out.println("Invalid coordinates. Please enter in format (x,y)");
	    	Scanner input2 = new Scanner(System.in);
		    coordinates = input2.nextLine();
		    parts = coordinates.split(",");
    		}
    		else {
    			bool = false;
    		}
    	}
	    return parts; // returns string array, used in xCoors and yCoors for parsing
	}
	public int xCoors(String[] xparts) { // parses X coord from input
	    int xCoor = Integer.parseInt(xparts[0]);
	    int yCoor = Integer.parseInt(xparts[1]);
	    if (xCoor >= map.getGrid().length || xCoor < 0 || yCoor >= map.getGrid().length || yCoor < 0) {
	    	System.out.println("Invalid coordinates. Please enter x and y values between 0 - " + (map.getGrid().length-1));
	    	takeInput(); // Loops back to taking input
	    }
	    return xCoor;
	}
	public int yCoors(String[] yparts) { // parses y coord from input
		int yCoor = Integer.parseInt(yparts[1]);
		return yCoor;
	}
// --------------------------------------- Battle ** dudududdudududuel ** ----------------------------------------------
	public void toBattle(int enX, int enY) {
		Boolean health = true;
		int healCount = 2; // Used in choice to, lets the player heal only up to 3 times
		Enemy enemy = resetEnemyHealth(); // resets enemy health to 25 after every battle
		
		while (health == true){ // While the player/enemy is alive
			System.out.println("Player Health: " + player.getHP());
			System.out.println("Enemy Health: " + enemy.getHealth());
			
			Scanner input = new Scanner(System.in);
			System.out.println("\n1. Melee \n2. Heal");
			int choice = input.nextInt();

			if (choice == 1) { // To attack
				AttackTypes attack = AttackTypes.MELEE;
				AttackType temp = new AttackType(player, enemy, attack); // Where damage/health is calculated
				int initialH = enemy.getHealth(); // enemy health before attack
				temp.attackedThem(attack); // Deals damage
				int afterH = enemy.getHealth(); //new enemy health after attack (set from attackTypes)
				
				if (enemy.getHealth() <= 0) { // Enemy is dead
					map.DefeatedEnemy(enX, enY); // Removes where the enemy was
					System.out.println("You dealt " + (initialH - afterH) + " damage to the enemy!" );
					System.out.println("Player Health: " + player.getHP());
					System.out.println("Enemy Health: 0");
					System.out.println("\nYou have defeated the enemy!");
					System.out.println(map); // Updated map without the defeated enemy
					health = false; // Kicks out of loop
					victory(); // Scans grid map if there are any enemies left
				}
				else { // Deals damage like usual
					System.out.println("You dealt " + (initialH - afterH) + " damage to the enemy!");
					System.out.println("Enemy attacks back (code in progress)");
				}
			}
			
			else if (choice == 2) {
				if (healCount >=0) { // Restricts player to only 3 heals.
					int playerH = player.getHP();
					if (playerH >= 50) { // Player has full health
						System.out.println("You have not taken damage yet.");
					}
					else {
						int beforeH = player.getHP(); //Same thing as Melee but adds health to the player instead
						AttackTypes attack = AttackTypes.HEAL;
						AttackType temp = new AttackType(player, enemy, attack);
						temp.attackedThem(attack);
						int afterH = player.getHP();
						
						System.out.println("You regained " + (afterH - beforeH) + " HP.");
						System.out.println(healCount + " heals remaining.\n");
						healCount -=1; //Player has 1 less heal
						}
					}
				else {
					System.out.println("You have reached your maximum heals.");
				}
			}
			else {
				System.out.println("Invalid option.");
				Scanner input2 = new Scanner(System.in);
				System.out.println("\n1. Melee \n2. Heal");
				choice = input2.nextInt();
			}
		}
	}
	
// ------------------------------------- Checks for enemies around player -------------------------------------------
	public void checkLocation(int x, int y) {
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

		}
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
	
//  ----------------------------------- Resets enemy health after every battle -----------------------------------
	public Enemy resetEnemyHealth() {
		Enemy enemy = new Enemy(25); // Initial health for enemies
		return enemy;
	}
	
//  ----------------------------------- Victory method; called after every battle -----------------------------------
	public void victory() {
		if (map.enemiesPresent() == false) {
			System.out.println("=========== Victory! ===========");
			Scanner input = new Scanner(System.in);
			System.out.println("1. Play again? \n2. Exit game.");
			int option = input.nextInt();
			if (option == 1) {
				map.resetPlayerLocation();
				StartGame();
			}
			else if (option == 2) {
				exit();
			}
		}
	}
// ----------------------------------- Method for exiting the game -----------------------------------
	public void exit() {
		System.exit(1);
	}
// ----------------------------------- User selection -----------------------------------
	public void StartGame() {
		Boolean valid = true;
		int user;
		System.out.println("=====Welcome to Default Game=====");
		System.out.println("1.New Game \n2.How to Play \n3.Exit"); //Promts user input for choice
		Scanner input = new Scanner(System.in);
		user = input.nextInt();
		input.nextLine();
		 
		//Loop to check for valid selection
		while(valid == true) {
			if(user == 1) {
				newGame(); //New game method
				valid = false;
			}
			else if(user == 2) {
				valid = false;
				howToPlay(); // medthod instructions for game
			}
			else if(user == 3) {
				valid = false;
				exit();
			}
			else {
				System.out.println("Selection is invalid");
				Scanner inputValid = new Scanner(System.in);
				System.out.println("1.New Game \n2.How to Play \n3.Exit");
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
