package battle;
import java.util.Random;
import unit.Unit;

/**
 * Class for different attack types
 * @author Bonnie's Computer
 *
 */
public class AttackType {
	
	private Unit attacker;

	public AttackType(Unit attacker){
		this.attacker = attacker;
		}
	
	/**
	 * Method to change the stats of Unit depending on the move type
	 * @param target Unit of who is receiving the attack
	 * @param type AttackTypes of the attack being dealt to the Unit
	 */
	public void attackedThem(Unit target, AttackTypes type) {
		switch(type) {
			case MELEE:
				//this is for melee attacks and is dependent on their damage
				Random random = new Random();
				int randDmg = random.nextInt(4);
				int melDamage = type.getDamage();
				melDamage = ((melDamage + attacker.getStats().getStr())*-1) -(-randDmg); //strength level is just added to their base damage
				target.getStats().setHealth(melDamage+target.getStats().getDef());
				break;
				
			case RANGED:
				//ranged attacks dependent on magic
				int magDamage = type.getDamage();
				magDamage = ((magDamage + attacker.getStats().getMag())*-1); //their magic level is just added to their base damage
				target.getStats().setHealth(magDamage+target.getStats().getDef());
				break;
				
			case HEAL:
				//player heals themselves
				//extra heal is from the mag stat of the player
				int heal = type.getHeal();
				heal = (heal + attacker.getStats().getMag());
				attacker.getStats().setHealth(heal);
				break;
			
			
				
		}
	}

}
