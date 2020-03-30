package battle;
import unit.Unit;
import java.util.Random;
public class AttackType {
	
	Unit attacker;

	public AttackType(Unit attacker) {
		this.attacker = attacker;
		}
	
	//this method changed the stats of the player and enemy depending on their stats
	public void attackedThem(Unit defender, AttackTypes attack) {
	switch(attack) {
		case MELEE:
			//this is for melee attacks and is dependent on their damage
			Random random = new Random();
			int randDmg = random.nextInt(4);
			int melDamage = attack.getDamage();
			melDamage = ((melDamage + attacker.getStats().getStr())*-1) -(-randDmg); //strength level is just added to their base damage
			defender.getStats().setHealth(melDamage);
			break;
			
		case RANGED:
			//ranged attacks dependent on magic
			int magDamage = attack.getDamage();
			magDamage = ((magDamage + attacker.getStats().getMag())*-1); //their magic level is just added to their base damage
			defender.getStats().setHealth(magDamage);
			break;
			
		case HEAL:
			//player heals themselves
			//extra heal is from the mag stat of the player
			int heal = attack.getHealth();
			heal = (heal + attacker.getStats().getMag());
			attacker.getStats().setHealth(heal);
			break;
			
		case DEFEND:
			//put in the math for defend
			//I have left this blank for now
			break;
		}
	}

}
