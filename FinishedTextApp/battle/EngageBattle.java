package battle;

import map.Position;
import printFormat.BorderedStrings;
import printFormat.LevelTitle;
import printFormat.OptionsText;
import unit.Enemy;
import unit.Unit;

/* class EngageBattle
 * class for battle engagement
 */

public class EngageBattle {
    private static BorderedStrings title = new LevelTitle();
    private static BorderedStrings choices = new OptionsText();
    public static int healsLeft = 3;
    public static boolean playerTurn = true;

    /* battleEngaged(Unit, Position, boolean, boolean)
     * engages battle
     *
     * @param player: Type Unit which defines player
     * @param p: Type Position which gives exact position of object
     * @param melee: Type boolean to check if melee is used
     * @param ranged: Type boolean to check if ranged is used
     * @return void
     */

    public static void battleEngaged(Unit player, Enemy enemy, Position p, boolean melee, boolean ranged) {
        if (melee) {
            title.printBox(" ","  Melee Battle", "    Engaged!", " ");
            startBattle(player, enemy, p, "Melee");
        } else if (ranged) {
            title.printBox(" ", "  Ranged Battle", "    Engaged!", " ");
            startBattle(player, enemy, p, "Ranged");
        }
    }

    /* startBattle(Unit, Position, String)
     * starts battle using threads
     *
     * @param player: Type Unit which defines player
     * @param p: Type Position which gives exact position of object
     * @param attack: Type String which defines what attack type is used
     * @return void
     */

    private static void startBattle(Unit player, Enemy enemy, Position p, String attack) {

        while (player.getStats().getHealth() > 0 && enemy.getStats().getHealth() > 0) {
            if (playerTurn) {
                System.out.println("\n" + "Player's turn . . . . .");
                playerTurn = false;
                runThread(player, enemy, p, attack);
            } else if (!playerTurn && enemy.getStats().getHealth() > 0) {
                runThread(enemy, player, p, attack.toUpperCase());
                System.out.println(" ");
                BattleThread.displayHealth(player, enemy);
                playerTurn = true;
            }
        }
    }

    private static void runThread(Unit attacker, Unit target, Position p, String attack) {
        loadEnemyTurn(attacker);

        DamageCalculator type = new DamageCalculator(attacker);
        BattleThread battleThread = new BattleThread();

        battleThread.run(attacker, target, p, type, attack);
    }

    public static void loadEnemyTurn(Unit attacker) {
        if((attacker instanceof Enemy) && (attacker.getStats().getHealth() > 0)) {

            try {
                BattleThread.sleep(500);
                System.out.println("\n" + "Enemy's turn . . .");

                for (int j = 0; j < 3; j++) {
                    BattleThread.sleep(500);
                    System.out.print(".  ");
                }
                BattleThread.sleep(300);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean playerHealCount(Unit player) {

        if (healsLeft == 0) {
            System.out.println("You've reached your maximum heal capacity in this battle.");
            return false;
        } else if  (player.getStats().getHealth() < 50 && healsLeft != 0) {
            healsLeft--;
            return true;
        } else if (player.getStats().getHealth() >= 50) {
            System.out.println("Cannot use heal. You have full health.");
            System.out.println(player.getStats().getHealth());
            return false;
        }
        return false;
    }
}
