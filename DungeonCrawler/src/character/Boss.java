package character;

import java.util.Random;

public class Boss extends Mob implements Character{
	private static final String[] bossChoice = {"Boss1", "Boss2", "Boss3",
			"Boss4", "Boss5"};
	private int health;
	private int defense;
	private int attack;
	private String name;
	private Random rand;
	private int level;
	private int maxHP;
	
	public Boss(String ImageFileName, int ran, int level) {
		super(ImageFileName);
		this.level = level;
		this.setName(bossChoice[ran]);
		rand = new Random();
		this.health = BASE_HEALTH;
		this.defense = BASE_DEFENSE;
		this.attack = BASE_ATTACK;
		//Health starts are 120% normal and rises 20 pts each time
		maxHP = getHealth() + 20*(level+1);
		setHealth(maxHP);
		setDefense(getDefense() + 3*level + 5*(ran+1));
		setAttack(getAttack() + 2*level + 5*(ran+1));
		
	}
	
	/**
	 * @return the health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * @param health the health to set
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * @return the defense
	 */
	public int getDefense() {
		return defense;
	}

	/**
	 * @param defense the defense to set
	 */
	public void setDefense(int defense) {
		this.defense = defense;
	}

	/**
	 * @return the attack
	 */
	public int getAttack() {
		return attack;
	}

	/**
	 * @param attack the attack to set
	 */
	public void setAttack(int attack) {
		this.attack = attack;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	//Increases health by healthReceived
	public void healTarget(int healthReceived) {
		setHealth(getHealth() + healthReceived);		
	}
	
	
	@Override
	public int damageGiven() {
		int draw = rand.nextInt(10);
		int damage;
		//Crit chance increases from 20% base
		if(draw >= (9-Math.round(level/2)+1)){
			damage = getAttack() + (getHealth() % (rand.nextInt(6+(level*2))+2));
		}
		//10% chance of miss
		else if(draw == 0){
			damage = 0;
		}
		else
			damage = getAttack();
		return damage;
	}
	@Override
	public int damageReceived(int damageGiven) {
		int damageTaken;
		//Missed attack
		if(damageGiven == 0)
			damageTaken = 0;
		else{
			damageTaken = damageGiven*((100-getDefense())/100);
		}
		setHealth(getHealth() - damageTaken);
		return damageTaken;
	}
	
	public String bossHeal(){
		healTarget(10*(level+2));
		if(getHealth() > maxHP)
			setHealth(maxHP);
		return "Go-Go Bastard-Heals!!";
	}

}
