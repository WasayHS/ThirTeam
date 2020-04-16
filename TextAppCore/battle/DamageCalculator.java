package battle;
import java.util.Random;

import unit.Unit;

/**
 * class for calculating damage
 */

public class DamageCalculator {

    private Unit attacker;

    /**
     * Constructor for DamageCalculator
     *
     * @param attacker Unit of the instance of the attacker
     */
    public DamageCalculator(Unit attacker) {
        this.attacker = attacker;
    }

    /**
     * Changes the stats of the Unit according to the type of attack executed by the attacker
     *
     * @param target Unit which is the instance of the target
     * @param type AttackTypes which defines the type of attack to be executed
     */
    public void attack(Unit target, AttackTypes type) {

        switch(type) {
            case MELEE:
                //this is for melee attacks and is dependent on their damage
                Random random = new Random();
                int randDmg = random.nextInt(4);
                int melDamage = type.getDamage();
                melDamage = ((melDamage + attacker.getStats().getStr())*-1) -(-randDmg); //strength level is just added to their base damage
                target.getStats().setHealth(melDamage);
                break;
            case RANGED:
                //ranged attacks dependent on magic
                int magDamage = type.getDamage();
                magDamage = ((magDamage + attacker.getStats().getMag())*-1); //their magic level is just added to their base damage
                target.getStats().setHealth(magDamage);
                break;
            case HEAL:
                //player heals themselves
                //extra heal is from the mag stat of the player
                int heal = type.getHeal();
                heal = (heal + attacker.getStats().getMag());
                attacker.getStats().setHealth(heal);
                break;
            case DEFEND:
                System.out.println("You attempted to block");
        }
    }
}
