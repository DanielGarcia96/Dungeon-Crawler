package character;

public interface Character{

	public static final int BASE_HEALTH = 100;
	public static final int BASE_DEFENSE = 0;
	public static final int BASE_ATTACK = 15;


	/**
	 * @return the health
	 */
	public int getHealth();

	/**
	 * @param health the health to set
	 */
	public void setHealth(int health);

	/**
	 * @return the defense
	 */
	public int getDefense();

	/**
	 * @param defense the defense to set
	 */
	public void setDefense(int defense);

	/**
	 * @return the attack
	 */
	public int getAttack();

	/**
	 * @param attack the attack to set
	 */
	public void setAttack(int attack);

	/**
	 * @return the name
	 */
	public String getName();

	/**
	 * @param name the name to set
	 */
	public void setName(String name) ;
	
	//Increases health by healthReceived
	public void healTarget(int healthReceived);
	
	//Calculates damage and returns amount
	public abstract int damageGiven();
	
	//Calculates damage received and sets health to new value
	public abstract int damageReceived(int damageGiven);
	
	

	
}
