package battle;
import java.util.Random;

import unit.Unit;

/* class DamageCalculator
 * class for calculating damage
 */

public class DamageCalculator {

    private Unit attacker;

    /* DamageCalculator(Unit)
     * Constructor for DamageCalculator
     *
     * @param attacker: Type Unit - instance of the attacker
     */
    public DamageCalculator(Unit attacker) {
        this.attacker = attacker;
    }

    /* attack(Unit, AttackTypes)
     * Changes the stats of the Unit according to the type of attack executed by the attacker
     *
     * @param target: Type Unit - instance of the target
     * @param type: Type AttackTypes - defines the type of attack to be executed
     * @return void
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
