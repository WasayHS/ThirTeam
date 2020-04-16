package battle;

/**
 * Class for the type of attack types and their respective stats
 * @author Bonnie's Computer
 *
 */
public enum AttackTypes {
    MELEE (5,0), //melee has a set damage of 5
    RANGED (5,0), //ranged has a set damage of 5
    HEAL(0,5), //heal has no damage
    DEFEND(0,0);

    private int damage;
    private int heal;

    /** 
     * Constructor for AttackTypes
     *
     * @param damage int for damage to be dealt to the target
     * @param healValue int for amount of health the user can regenerate
     */
    AttackTypes(int damage, int healValue) {
        this.damage = damage;
        this.heal = healValue;
    }

    /**
     * Getter for damage
     * @return int of damage
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * Getter for health
     * @return int of health
     */
    public int getHeal() {
        return this.heal;
    }
}
