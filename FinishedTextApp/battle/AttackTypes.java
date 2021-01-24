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
    private int health;

    /* AttackTypes(int, int)
     * Constructor for AttackTypes
     *
     * @param initialDamage: Type int and is the initial damage
     * @param initialHealth: Type int and is the initial health
     */
    AttackTypes(int initialDamage, int initialHealth) {
        this.damage = initialDamage;
        this.health = initialHealth;
    }

    /* getDamage()
     * Getter for damage
     * @return damage
     */
    public int getDamage() {
        return this.damage;
    }

    /* getHealth()
     * Getter for health
     * @return health
     */
    public int getHealth() {
        return this.health;
    }
}
