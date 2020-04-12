package battle;

import map.Position;
import map.TextMap;
import printFormat.BorderedStrings;
import printFormat.LevelTitle;
import printFormat.OptionsText;
import unit.Enemy;
import unit.Unit;

public class EngageBattle {
    private static BorderedStrings title = new LevelTitle();
    private static BorderedStrings choices = new OptionsText();
    static int healsLeft = 3;

    public static void battleEngaged(Unit player, Position p, boolean melee, boolean ranged) {
        if (melee) {
            title.printBox(" ","  Melee Battle", "    Engaged!", " ");
            startBattle(player, p, "Melee");
        } else if (ranged) {
            title.printBox(" ", "  Ranged Battle", "    Engaged!", " ");
            startBattle(player, p, "Ranged");
        }
    }

    private static boolean startBattle(Unit player, Position p, String attack) {
        boolean playerTurn = true;
        Enemy enemy = TextMap.getEnemy(p.getX(), p.getY());

        while (player.getStats().getHealth() > 0 && enemy.getStats().getHealth() > 0) {
            if (playerTurn) {
                System.out.println("\n" + "Player's turn . . . . .");
                runThread(player, enemy, p, attack);
                playerTurn = false;
            } else if (!playerTurn && enemy.getStats().getHealth() > 0) {
                runThread(enemy, player, p, attack.toUpperCase());
                System.out.println(" ");
                BattleThread.displayHealth(player, enemy);
                playerTurn = true;
            }
        }
        return enemy.getStats().getHealth() == 0;
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
                for (int j = 4; j>0; j--) {
                    BattleThread.sleep(500);
                    System.out.print(".  ");
                }
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
