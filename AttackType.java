
public class AttackType {
	
	AttackTypes attack;
	int damage;
	Player p;
	Enemy e;

	AttackType(Player p, enemy e, AttackTypes attack){
		this.attack = attack;
		this.p = p;
		this.e = e;
		}
	
	//this method changed the stats of the player and enemy depending on their stats
	public void attackedThem() {
	switch(attack) {
		case MELEE:
			//this is for melee attacks and is dependent on their damage
			int melDamage = attack.getDamage();
			melDamage = ((melDamage + p.getStr())*-1);//strength level is just added to their base damage
			e.setHP(melDamage);
			break;
		case RANGED:
			//ranged attacks dependent on magic
			int magDamage = attack.getDamage();
			magDamage = ((magDamage + p.getMag())*-1); //their magic level is just added to their base damage
			e.setHP(magDamage);
			break;
		case HEAL:
			//player heals themselves
			//extra heal is from the mag stat of the player
			int heal = attack.getHealth();
			heal = (heal + p.getMag());
			p.setHP(heal);
			break;
		case DEFEND:
			//put in the math for defend
			//I have left this blank for now
			break;
	}
	}
	
	
}
