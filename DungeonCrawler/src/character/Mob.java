package character;

import java.util.Random;

import MainGraphicPackage.Graphic;

public class Mob extends Graphic implements Character{
	private static final String[] mobChoice = {"blob", "daedra", "dementand", "dorver", "wyvern"};
	private int health;
	private int defense;
	private int attack;
	private String name;
	private Random rand;
	private int level;
	private int difficulty;
	
	//Constructor
	public Mob(String ImageFileName){
		super(ImageFileName);
	}
	
	//Constructor used to generate random mob encounter
	//accepts 0-4 as parameter for ran
	public Mob(String ImageFileName,int ran, int level) {
		super(ImageFileName);
		this.level = level;
		this.setName(mobChoice[ran]);
		rand = new Random();
		this.health = BASE_HEALTH;
		this.defense = BASE_DEFENSE;
		this.attack = BASE_ATTACK;
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
			damage = (int) (getAttack() + (getHealth() % (1.0*(rand.nextInt(6+(level*2))+2)) ));
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
			damageTaken = (int) ( damageGiven*((100-getDefense())/(1.0*100)) );
		}
		setHealth(getHealth() - damageTaken);
		return damageTaken;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	//Also handles stats
	public void setDifficulty(int diff){
		this.difficulty = diff;
		
		setHealth(getHealth());
		setDefense(getDefense() + rand.nextInt(3*level + 3*(difficulty+1))%(2*difficulty+1) );
		setAttack(getAttack() + rand.nextInt(3*level + 3*(difficulty+1))%(2*difficulty+1) );
	}

	public int awardPoints(){
		return rand.nextInt( (getDefense() + getAttack()) * (3*(difficulty+1)) ) % (25 * (difficulty+1) ) + 10;	
	}
}
