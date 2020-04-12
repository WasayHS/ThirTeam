package battle;

import main.UserInput;
import map.CreateHostileEntity;
import map.Position;
import map.TextMap;
import org.w3c.dom.Text;
import printFormat.BorderedStrings;
import printFormat.LevelTitle;
import printFormat.OptionsText;
import unit.Enemy;
import unit.Player;
import unit.Unit;

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
        }
    }

    private static void executeAttackType(Unit attacker, Unit target, DamageCalculator type, String attackType, Position p) {
        int oldHp = target.getStats().getHealth();
        int newHp;

        type.attack(target, AttackTypes.valueOf(attackType.toUpperCase()));
        newHp = target.getStats().getHealth();
        displayDamageDealt(attacker, oldHp, newHp, attackType);
        checkTargetHP(target, p);
    }
    public static void displayHealth(Unit player, Unit enemy) {
        String playerHealth = String.format("Player Health: %s", player.getStats().getHealth());
        String enemyHealth = String.format("Enemy Health: %s", enemy.getStats().getHealth());
        title.printBox(playerHealth, enemyHealth);
    }

    private static void displayDamageDealt(Unit attacker, int oldHp, int newHp, String attackType) {
        String attackerS = attacker.getClass().toString().replace("class unit.", "");
        System.out.printf("%s dealt %s damage." + "\n", attackerS, oldHp-newHp);
    }
}
