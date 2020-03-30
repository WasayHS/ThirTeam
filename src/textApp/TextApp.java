package textApp;
import unit.Enemy;
import unit.Player;
import unit.Stats;
import unit.Unit;
import map.Position;
import map.Map;
import battle.AttackType;
import battle.AttackTypes;
import java.util.Random;
import java.util.Scanner;


public class TextApp{
	private AttackTypes attackTypes;
	private Position location;
	private static int mapSize = 7;
	private Map map = new Map(mapSize, mapSize);;
	private Player player = new Player(mapSize-2, mapSize/2);
	
// --------------------------------------- Instructions for player -------------------------------------------
	public void howToPlay() {
		System.out.println("To move:\n\tInput coordinates starting from the index \n\tof 1 in the format of (x,y).\n");
		System.out.println("To engage in battle:\n\tWhen adjacent to an enemy, you will be given\n\tan option to attack or move. Chose attack and \n\tenter enemy coordinates in the same format as \n\thow you would move.\n");
		System.out.println("During the battle:\n\tYou will be given the option to attack or heal.\n\tYou are restricted to using only 3 heal in \n\tone round of battle.\n");
		
		Scanner input = new Scanner(System.in);
		System.out.println("1. Start Game");
		int opt =0;
		do{
			opt = input.nextInt();
			if(opt != 1){
				System.err.println("Invalid input, try again!");
			}
		}while(opt != 1);
		newGame();
	}
	
// ----------------------------------------- Method for initializing game -----------------------------------------
	public void newGame() { // Sets up map using Map class
		Random random = new Random();
		int playInitX = mapSize - 2;
		int playInitY = mapSize / 2;
		map.initPlayer(playInitX, playInitY); //	Initial player position

		for(int i = 0; i < 5; i++) {
			int randLocX = random.nextInt(5); //	random x coordinate
			int randLocY = random.nextInt(5); //	random y coordinate
									
			if(randLocX!= playInitX && randLocY != 0 && randLocY != map.getGrid().length - 2 && randLocX != 0 && randLocX != map.getGrid().length - 2) { //	Makes sure that the enemies don't spawn in player's initial row
				map.EnemyPosition(randLocX, randLocY);
			}
		}
		System.out.println(map);
		playGame();
	}
	
//	---------------------------- Starts the Game, runs when enemies are still present
	public void playGame() {
		while(map.enemiesPresent() != 0) {
		    System.out.println("Move(x,y): ");
		    String[] parts = takeInput(); // Take input method
		    int xCoor = xCoors(parts);
		    int yCoor = yCoors(parts);
		    map.move(xCoor, yCoor, player, map); //map.move checks if move is valid, should return player current location if move was not valid.
		    this.player = new Player(xCoor, yCoor);
		    System.out.println(map);
		}
	}
	
//  ----------------------------------- Victory method; called after every battle -----------------------------------
	public void victory() {
		if (map.enemiesPresent() == 0) {
			System.out.println("=========== Victory! ===========");
			Scanner input = new Scanner(System.in);
			System.out.println("1. Play again? \n2. Exit game.");
			int option = input.nextInt();
			if (option == 1) {
				map.resetPlayerLocation(player);
				StartGame();
			}
			else if (option == 2) {
				exit();
			}
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
	    		System.err.println("Invalid coordinates. Please enter in format (x,y)");
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
		    	System.err.println("Invalid coordinates. Please enter x and y values between 0 - " + (map.getGrid().length-1));
		    	takeInput(); // Loops back to taking input
		    }
		    return xCoor;
		}
		public int yCoors(String[] yparts) { // parses y coord from input
			int yCoor = Integer.parseInt(yparts[1]);
			return yCoor;
		}

// ----------------------------------- Method for exiting the game -----------------------------------
	public void exit() {
		System.exit(1);
	}
// ----------------------------------- User selection -----------------------------------
	public void StartGame() {
		Boolean valid = true;
		int user;
		System.out.println("=====A Beast's Weapon=====");
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
				howToPlay(); // method instructions for game
			}
			else if(user == 3) {
				valid = false;
				exit();
			}
			else {
				System.err.println("Selection is invalid");
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

