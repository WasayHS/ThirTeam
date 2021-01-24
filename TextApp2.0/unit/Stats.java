package unit;

public class Stats {

    private int health; // default stats, what is given at the start
    private int str;
    private int mag;
    private int defaultHP;

    public Stats(int hp, int str, int mag, int def){
        this.health = hp;
        this.str = str;
        this.mag = mag;
        setDefaultHP(hp);
    }

    public int getDefaultHP() {
        return this.defaultHP;
    }

    public void setDefaultHP(int hp) {
        this.defaultHP = hp;
    }

    public int getHealth() {
        return Math.max(this.health, 0);
    }

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

    public int getStr() {
        return str;
    }

    public int getMag() {
        return mag;
    }

}