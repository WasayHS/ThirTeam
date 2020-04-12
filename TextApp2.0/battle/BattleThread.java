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

public class BattleThread extends Thread {
    private static BorderedStrings title = new LevelTitle();
    private static BorderedStrings choices = new OptionsText();

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

    private static void checkTargetHP(Unit target, Position p) {
        if (target instanceof Player && target.getStats().getHealth() <= 0) {
            System.out.println("Game Over.");
        } else if (target instanceof Enemy && target.getStats().getHealth() <= 0) {
            System.out.println("You have defeated the enemy!");
            EngageBattle.playerTurn = true;
        }
    }

    private static void executeAttackType(Unit attacker, Unit target, DamageCalculator type, String attackType, Position p) {
        int oldHp = target.getStats().getHealth();
        int newHp;

        type.attack(target, AttackTypes.valueOf(attackType.toUpperCase()));
        newHp = target.getStats().getHealth();
        displayDamageDealt(attacker, target, oldHp, newHp, attackType);
        checkTargetHP(target, p);
    }
    public static void displayHealth(Unit player, Unit enemy) {
        String playerHealth = String.format("Player Health: %s", player.getStats().getHealth());
        String enemyHealth = String.format("Enemy Health: %s", enemy.getStats().getHealth());
        title.printBox(playerHealth, enemyHealth);
    }

    private static void displayDamageDealt(Unit attacker, Unit target, int oldHp, int newHp, String attackType) {
        if (attackType.equals("HEAL")) {
            System.out.printf("You healed to %s health. \nHeals remaining: %s", attacker.getStats().getHealth(), EngageBattle.healsLeft);
            return;
        } else if (attackType.equals("DEFEND")) {
            boolean blockChance = new Random().nextInt(2)==0;

            if(blockChance) {
                EngageBattle.loadEnemyTurn(target);
                System.out.printf("The enemy missed! \n You successfully dodged the enemy's attack.");
                EngageBattle.playerTurn = true;
                return;
            } else {
                System.out.println("You failed to block the enemy's incoming attack.");
                return;
            }
        }
        String attackerS = attacker.getClass().toString().replace("class unit.", "");
        System.out.printf("%s dealt %s damage." + "\n", attackerS, oldHp-newHp);
    }
}
