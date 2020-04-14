package unit;

public class Stats {

    private int health; // default stats, values taken from unit's subclasses
    private int str;
    private int mag;
    private int defaultHP;

    /* Stats(int, int, int, int)
     * constructor for Stats class
     *
     * @param hp: Type int - unit's hp
     * @param str: Type int - unit's hp
     * @param mag: Type int - unit's hp
     * @param def: Type int - unit's hp
     */

    public Stats(int hp, int str, int mag, int def){
        this.health = hp;
        this.str = str;
        this.mag = mag;
        setDefaultHP(hp);
    }

    /* getDefaultHP()
     * default hp for the Unit
     *
     * @return defaultHP: returns Type int
     */

    public int getDefaultHP() {
        return this.defaultHP;
    }

    /* setDefaultHP(int)
     * sets the Unit's defaultHP
     *
     * @param defaultHP: Type int which is the defaultHP
     */
    public void setDefaultHP(int hp) {
        this.defaultHP = hp;
    }

    /* getHealth()
     * getter for health
     *
     * @return int
     */
    public int getHealth() {
        return Math.max(this.health, 0);
    }

    /* setHealth(int)
     * sets the strength number
     *
     * @param hpChange: Type int - the amount of hp to be added/subtracted
     */
    public void setHealth(int hpChange) {
        int newHP = health + hpChange;

        if (newHP <= getDefaultHP()) {
            this.health = newHP;
        }
        else if (newHP > getDefaultHP()) {
            this.health = getDefaultHP(); // Max player/enemy health
        }
        else if (newHP <= 0) {
            this.health = 0;
        }
    }

    /* getStr()
     * getter for strength
     *
     * @return str: returns Type int - the value of Unit's strength
     */
    public int getStr() {
        return str;
    }

    /* getMag()
     * getter for magic
     *
     * @return mag: returns Type int - the value of Unit's magic
     */
    public int getMag() {
        return mag;
    }

}