
public enum AttackTypes {
	MELEE (5,0), //melee has a set damage of 5 
	RANGED (5,0), //ranged has a set damage of 5
	HEAL(0,5), //heal has no damage
	DEFEND(0,0); //defend has no damage
	
	
	// initialize the damage and health 
	private int damage;
	private int health;
	
	//constructor so the constant can have these fields
	AttackTypes(int initialDamage, int initialHealth) {
			this.damage = initialDamage;
			this.health = initialHealth;
    }

	public int getDamage() {
		return this.damage;
	}

	public int getHealth() {
		return this.health;
		}
	}
