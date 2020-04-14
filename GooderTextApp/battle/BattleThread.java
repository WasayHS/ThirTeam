package battle;

import main.UserInput;
import map.Position;
import printFormat.BorderedStrings;
import printFormat.LevelTitle;
import printFormat.OptionsText;
import unit.Enemy;
import unit.Player;
import unit.Unit;

import java.util.Random;

/* class BattleThread extends Thread
 * threading class for attacking and extends Thread class
 */

public class BattleThread extends Thread {
    private static BorderedStrings title = new LevelTitle();
    private static BorderedStrings choices = new OptionsText();

    /* run(Unit, Unit, Position, DamageCalculator, String)
     * Initiates the battle thread
     *
     * @param attacker: Type Unit which will be used to find instance of player
     * @param target: Type Unit which will be used to pass into executeAttackType()
     * @param p: Type position which will be used to pass into executeAttackType()
     * @param type: Type DamageCalculator which is the type of attack that is used and passed into executeAttackType()
     * @param attack: Type string which specifies what attack is used
     * @return void
     */

    public void run(Unit attacker, Unit target, Position p, DamageCalculator type, String attack) {
        String attackType;

        if (attacker instanceof Player) {
            do {
                attackType = UserInput.playerAttackInput(attack, attacker);
            } while (attackType == null);
            executeAttackType(attacker, target, type, attackType, p);
            return;
        }
        executeAttackType(attacker, target, type, attack, p);
    }

    /* checkTargetHP(Unit, Position)
     * Check the HP of the enemy
     *
     * @param target: Type Unit which indicates the target
     * @param p: Type Position is the position of the object
     * @return void
     */

    private static void checkTargetHP(Unit target, Position p) {
        if (target instanceof Player && target.getStats().getHealth() <= 0) {
            return;
        } else if (target instanceof Enemy && target.getStats().getHealth() <= 0) {
            System.out.println("You have defeated the enemy!");
            EngageBattle.playerTurn = true;
        }
    }

    /* executeAttackType(Unit, Unit, DamageCalculator, String, Position)
     * executes the attack and the type
     *
     * @param attacker: Type Unit which will be used to find instance of player
     * @param target: Type Unit which will get stats of the target
     * @param type: Type DamageCalculator which gets the stats for the attack
     * @param attack: Type string which specifies what attack is used
     * @param p: Type position is the position of the object
     * @return void
     */

    private static void executeAttackType(Unit attacker, Unit target, DamageCalculator type, String attackType, Position p) {
        int oldHp = target.getStats().getHealth();
        int newHp;

        type.attack(target, AttackTypes.valueOf(attackType.toUpperCase()));
        newHp = target.getStats().getHealth();
        displayDamageDealt(attacker, target, oldHp, newHp, attackType);
        checkTargetHP(target, p);
    }

    /* displayHealth(Unit, Unit)
     * Displays the health of the player and enemy
     *
     * @param player: Type Unit  which we can get the stats for player
     * @param enemy: Type Unit  which we can get the stats for enemy
     * @return void
     */

    public static void displayHealth(Unit player, Unit enemy) {
        String playerHealth = String.format("   Player Health: %s", player.getStats().getHealth());
        String enemyHealth = String.format("   Enemy Health: %s", enemy.getStats().getHealth());
        title.printBox("  = Updated Health =", " ", playerHealth, enemyHealth, " ");
    }

    /* displayDamageDealt(Unit, Unit, int, int, String)
     * display battle messages and damage
     *
     * @param attacker: Type Unit which is used to get attack stats
     * @param target: Type Unit which is used to get passed into loadEnemyTurn for loading effect
     * @param oldHp: Type int which is used for prints
     * @param newHp: Type int which is used for prints
     * @param attackType: Type String which is used for determining the attack
     * @return void
     */

    private static void displayDamageDealt(Unit attacker, Unit target, int oldHp, int newHp, String attackType) {
        if (attackType.equals("HEAL")) {
            System.out.printf("You healed to %s health. \nHeals remaining: %s", attacker.getStats().getHealth(), EngageBattle.healsLeft);
            return;
        } else if (attackType.equals("DEFEND")) {
            boolean blockChance = new Random().nextInt(2)==0;

            if(blockChance) {
                EngageBattle.loadEnemyTurn(target);
                System.out.print("The enemy missed! \n You successfully dodged the enemy's attack.");
                EngageBattle.playerTurn = true;
            } else {
                System.out.println("You failed to block the enemy's incoming attack.");
            }
            return;
        }
        String attackerS = attacker.getClass().toString().replace("class unit.", "");
        System.out.printf("%s dealt %s damage." + "\n", attackerS, oldHp-newHp);
    }
}
