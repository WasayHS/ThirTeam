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
 * Class for when a unit executes an attack; Extends Thread class
 */
public class BattleThread extends Thread {
    private static BorderedStrings title = new LevelTitle();
    private static BorderedStrings choices = new OptionsText();

    /* run(Unit, Unit, Position, DamageCalculator, String)
     * Initiates the thread
     *
     * @param attacker: Type Unit - instance of the attacker
     * @param target: Type Unit - instance of the target; passed into executeAttackType()
     * @param p: Type Position - Position of the enemy; passed into executeAttackType()
     * @param type: Type DamageCalculator - the type of attack that is used; passed into executeAttackType()
     * @param attack: Type string - specifies which attack to execute
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
     * Checks the HP of the target
     *
     * @param target: Type Unit - instance of the target
     * @param p: Type Position - position of the enemy
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
     * Sets the changes to the unit's health according to the type of attack executed
     *
     * @param attacker: Type Unit - instance of the attacker
     * @param target: Type Unit - instance of the target
     * @param type: Type DamageCalculator - calculates and sets the target's hp
     * @param attack: Type String - specifies the type of attack used
     * @param p: Type Position - position of the enemy
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
     * Displays the updated health of the player and enemy after an attack was executed
     *
     * @param player: Type Unit - instance of the player to get the updated stats from
     * @param enemy: Type Unit - instance of the enemy to get the updated stats from
     * @return void
     */
    public static void displayHealth(Unit player, Unit enemy) {
        String playerHealth = String.format("   Player Health: %s", player.getStats().getHealth());
        String enemyHealth = String.format("   Enemy Health: %s", enemy.getStats().getHealth());
        title.printBox("  = Updated Health =", " ", playerHealth, enemyHealth, " ");
    }

    /* displayDamageDealt(Unit, Unit, int, int, String)
     * Displays battle messages and damage dealt/healed by the attacker
     *
     * @param attacker: Type Unit - instance of the attacker
     * @param target: Type Unit - instance of the target
     * @param oldHp: Type int - target's HP before an attack
     * @param newHp: Type int - target's HP after an attack
     * @param attackType: Type String - the attack used by the attacker
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
