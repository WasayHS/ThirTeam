import java.util.ArrayList;

public class Player {
	
	// instance variables
	private int health = 50; // default stats, what is given at the start
	private int str = 1;
	private int mag = 1;
	private ArrayList <Collectible> items = new ArrayList <Collectible>();
	private Location location;
	
	//Constructor
	public Player(){
		this.health = health;
		this.str = str;
		this.items = items;
		this.location = location;
		this.mag = mag;
	}
	
	// Other Methods
	public direction getDirection(){
		return null;// code for user controls (mostly mouse input, minimal keyboard input)
	}
	public AttackType getMove (Enemy e, boolean ranged){
		if (ranged){
			setMag(0);
			// do a ranged attack
			// Enemy.fight()
			// enemy will lose health -> goes to enemy class
		}else{
			setStr(0);
			// Enemy.fight()
			// do a melee attack
		}
		/* if the player wins then their magic
		and strength will increase by (int)(numoftimes/3)*/
			
	}
	
	//Setters
	public void setHP(int change){ // player loses health after enemy attacks or gains health after finishing a fight
		health = health +change;
		if (health == 0){
			// game over
		}
		
	}
	
	public void setStr(int s){ /// Str = Strength
		str = str+s;
		
	}
	
	public void setMag(int m){ // sets the player's ability for ranged attacks (radius and strength of weapon)
		mag = mag + m;
	}
	
	public void setLocation(Location l){
		location = l;
	}

	
	
	// Getters
	public int getHP(){
		return health;
	}
	public int getStr(){
		return str;
	}
	public int getMag(){// mag = magic
		return mag;
	}
	public Location getLocation(){
		return location;
	}
}
