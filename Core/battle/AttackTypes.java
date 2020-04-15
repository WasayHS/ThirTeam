package battle;

/* enum AttackTypes
 * enumerated attack types
 */
public enum AttackTypes {
    MELEE (5,0), //melee has a set damage of 5
    RANGED (5,0), //ranged has a set damage of 5
    HEAL(0,5), //heal has no damage
    DEFEND(0,0);

    // initialize the damage and health
    private int damage;
    private int heal;

    /** AttackTypes(int, int)
     * Constructor for AttackTypes
     *
     * @param damage: Type int - damage to be dealt to the target
     * @param healValue: Type int - amount of health the user can regenerate
     */
    AttackTypes(int damage, int healValue) {
        this.damage = damage;
        this.heal = healValue;
    }

    /** getDamage()
     * Getter for damage
     * @return damage
     */
    public int getDamage() {
        return this.damage;
    }

    /**getHealth()
     * Getter for health
     * @return health
     */
    public int getHeal() {
        return this.heal;
    }
}
