import java.util.ArrayList;

public class Player{
	
// instance variables

	private int health = 50; // default stats, what is given at the start
	private int str = 5;
	private int intellect = 1;
	private int mag = 1;
	
	//private ArrayList <Collectible> items = new ArrayList <Collectible>();
	private Location location;
	
//Constructor
	public Player(){
		this.health = 30;
		this.str = 3;
		//this.items = items;
		this.location = new Location (4, 2);
		this.intellect = 1;
	}
	
// Other Methods
//	public Move getMove(){
//		Move m = new Move ();
//		return new Move (m);// code for user controls (mostly mouse input, minimal keyboard input)
//	}
//	public AttackType getAttack (Enemy e){// which attack type is going to be used
//		/// based out of what the user decides which attack is going to be used (TextApp)
//		
//		AttackType type = new AttackType(null, e, null);
//		
//		
////		int numOfTimes = TextApp.getNumOfTimes();
////		boolean win = true;
////		int num = (int)(numOfTimes/3);
////		if(win = true){
////			setHP(num);
////			setStr(num);
////			setIntellect(num);
////		}
//			
//			/* if the player wins then their intellect
//			and strength will increase by (int)(numoftimes/3)*/
//		return new AttackType(null, e, null);		
//}
	
	public void gameOver(){
		System.exit(0);// ends program
		/// start program again 
		// goes to main menu
	}
	
//Setters
	public void setHP(int change){ // player loses health after enemy attacks or gains health after finishing a fight
		health = health + change;
		if (health == 0){
			gameOver();
		}
		else if (health >= 50) {
			this.health = 50;
		}
	}
	
	public void setStr(int s){ /// Str = Strength
		str = str+s;
		
	}
	
	public void setIntellect(int mag){ // sets the player's ability for ranged attacks (radius and strength of weapon)
		intellect = intellect + mag;
	}
	
	public void setLocation(Location l){
		location = l;
	}
//	public void setCollectible (Collectible c){
//		items.add(c);
//	}
	
	public void setMag(int m) {
		mag = mag + m;
	}
	
// Getters
	public int getHP(){
		return health;
	}
	public int getStr(){
		return str;
	}
	public int getintellect(){
		return intellect;
	}
	public Location getLocation(){
		return location;
	}
	public int getMag() {
		return mag;
	}
//	public ArrayList <Collectible> getCollectibles(){
//		return new ArrayList <Collectible> (items); 
//	}

}