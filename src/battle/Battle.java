package battle;

import java.util.Scanner;

import map.Map;
import textApp.TextApp;
import unit.Enemy;
import unit.Player;
import unit.Unit;

public interface Battle{
	
	public static void attackBattle(Player player, int enemyX, int enemyY, AttackTypes attack, Map map) {
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
	
	
	public static int battleInput(AttackTypes attack) {
		int choice;
		if (attack == AttackTypes.MELEE) {
			Scanner sc = new Scanner(System.in);
			System.out.println("1. Melee \n2. Heal");
			choice = sc.nextInt();
			return choice;
		}
		else {
			Scanner sc = new Scanner(System.in);
			System.out.println("1. Ranged \n2. Heal");
			choice = sc.nextInt();
			return choice;
		}
	}
	
	public static void resetEnemyHealth(int x, int y) {
		Unit enemy = new Enemy(25, 4, 3, 2, x, y); // Initial health for enemies
	}
}
