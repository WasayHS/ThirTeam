import java.util.Random;
public class AttackType {
	
	AttackTypes attack;
	int damage;
	Player p;
	Enemy e;
	Random random = new Random();

	AttackType(Player p, Enemy e, AttackTypes attack){
		this.attack = attack;
		this.p = p;
		this.e = e;
		}
	
	//this method changed the stats of the player and enemy depending on their stats
	public void attackedThem(AttackTypes attack) {
	switch(attack) {
		case MELEE:
			//this is for melee attacks and is dependent on their damage
			int randDmg = random.nextInt(4);
			int melDamage = attack.getDamage();
			melDamage = ((melDamage + p.getStr())*-1) -(-randDmg); //strength level is just added to their base damage
			e.setHealth(melDamage);
			break;
			
		case RANGED:
			//ranged attacks dependent on magic
			int magDamage = attack.getDamage();
			magDamage = ((magDamage + p.getMag())*-1); //their magic level is just added to their base damage
			e.setHealth(magDamage);
			break;
			
		case HEAL:
			//player heals themselves
			//extra heal is from the mag stat of the player
			int heal = attack.getHealth();
			heal = (heal + p.getMag());
			p.setHealth(heal);
			break;
			
		case DEFEND:
			//put in the math for defend
			//I have left this blank for now
			break;
		}
	}
	
	
}
