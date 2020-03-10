package battle;

import java.util.Scanner;

import game.TextApp;
import map.Map;
import unit.Enemy;
import unit.Player;
import unit.Unit;

public class Battle {
	
	public void attackBattle(Player player, int enemyX, int enemyY, AttackTypes attack, Map map) {
		int healCount = 2;
		resetEnemyHealth(enemyX, enemyY);
		boolean enemyHealth = true;
		Unit target = new Enemy(25, 2, 0, 0, enemyX, enemyY);
		
		while (enemyHealth) {
			AttackType type= new AttackType(player);
			if (battleInput(attack) == 1) {
				type.attackedThem(target, attack);
				System.out.println("Player Health: " + player.getStats().getHealth());
				System.out.println("Enemy Health: " + target.getStats().getHealth());
				if (target.getStats().getHealth()<=0) {
					System.out.println("You have defeated the enemy!");
					map.DefeatedEnemy(enemyX, enemyY);
					enemyHealth = false;
					}
			}
			else if (battleInput(attack) == 2 && healCount!=0) {
				if (player.getStats().getHealth() == player.getStats().getDefaultHP()) {
					System.out.println("You have full health.");
				}
				else {
					type.attackedThem(target, AttackTypes.HEAL);
					System.out.println("You regained health.");
				}
				healCount--;
			}
			else {
				System.out.println("Invalid input.");
				battleInput(attack);
			}
		}
		TextApp text = new TextApp();
		text.victory();
	}
	
	
	public int battleInput(AttackTypes attack) {
		if (attack == AttackTypes.MELEE) {
			Scanner input = new Scanner(System.in);
			System.out.println("1. Melee \n2. Heal");
			int choice = input.nextInt();
			return choice;
		}
		else {
			Scanner input = new Scanner(System.in);
			System.out.println("1. Ranged \n2. Heal");
			int choice = input.nextInt();
			return choice;
		}
	}
	
	public static void resetEnemyHealth(int x, int y) {
		Unit enemy = new Enemy(25, 4, 3, 2, x, y); // Initial health for enemies
	}
}
