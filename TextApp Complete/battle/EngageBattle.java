package battle;

import map.Position;
import printFormat.BorderedStrings;
import printFormat.LevelTitle;
import printFormat.OptionsText;
import unit.Enemy;
import unit.Unit;

public class EngageBattle {
    private static BorderedStrings title = new LevelTitle();
    private static BorderedStrings choices = new OptionsText();
    public static int healsLeft = 3;
    public static boolean playerTurn = true;

    /**
     * Engages the player in a melee or ranged battle
     *
     * @param player: Type Unit - defines player
     * @param enemy: Type Enemy - instance of the enemy to fight
     * @param p: Type Position - position of the enemy
     * @param melee: Type boolean - checks if the type of battle is melee
     * @param ranged: Type boolean - checks if the type of battle is ranged
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

    /**
     * Loops the battle while both the enemy and player are still alive
     *
     * @param player: Type Unit - instance of the player
     * @param p: Type Position - position of the enemy
     * @param attack: Type String - the type of attack to be used
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
                TextAppBattleThread.displayHealth(player, enemy);
                playerTurn = true;
            }
        }
    }

    /**
     * Runs the battle thread
     *
     * @param attacker: Type Unit - instance of the attacker
     * @param target: Type Unit - instance of the target
     * @param p: Type Position - position of the enemy
     * @param attack: Type String - defines what attack type is used
     */
    private static void runThread(Unit attacker, Unit target, Position p, String attack) {
        loadEnemyTurn(attacker);

        DamageCalculator type = new DamageCalculator(attacker);
        TextAppBattleThread battleThread = new TextAppBattleThread();

        battleThread.run(attacker, target, p, type, attack);
    }

    /** 
     * Loading effect for enemy turn
     *
     * @param attacker: Type unit - instance of the attacker
     */
    public static void loadEnemyTurn(Unit attacker) {
        if((attacker instanceof Enemy) && (attacker.getStats().getHealth() > 0)) {

            try {
                TextAppBattleThread.sleep(500);
                System.out.println("\n" + "Enemy's turn . . .");

                for (int j = 0; j < 3; j++) {
                    TextAppBattleThread.sleep(500);
                    System.out.print(".  ");
                }
                TextAppBattleThread.sleep(300);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Tracks how many times a player has healed in a battle
     *
     * @param player: Type Unit - instance of the player
     * @return boolean if they player can heal
     */
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
