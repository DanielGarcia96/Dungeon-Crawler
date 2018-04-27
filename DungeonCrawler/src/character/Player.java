package character;
import MainGraphicPackage.AnimatedGraphic;
import MainGraphicPackage.LightSource;
import Inventory.Inventory;
import Inventory.Item;
import Inventory.ItemPool;
import Inventory.Equipment;

import java.util.ArrayList;
import java.util.Random;

public class Player extends AnimatedGraphic implements Character, LightSource{
	
	private int level;
	private int maxHP;
	private int maxAtt;
	private int maxDef;
	private int health;
	private int defense;
	private int attack;
	private int PlayerX, PlayerY;
	private String name;
	private int currentScore;
	private ArrayList<Integer> Scores;
	private Random rand;
	private int LightRadius;
	private int Dificulty;
	private boolean WalkAnimationPlaying;
	private AnimatedGraphic LeftMovement, RightMovement, UpMovement, DownMovement;
	private Item item;
	private Inventory inv;
	private ItemPool ip;
	boolean done1, done2, done3, done4, done5, lvlDone1, lvlDone2, lvlDone3;
	
	
	public Player(String ImageFileName, int x, int y){
		super(ImageFileName, x, y);
		this.LightRadius = 96;
		//this.setDificulty(Dificulty);
		
		this.setWalkAnimationPlaying(false);
		this.LeftMovement = new AnimatedGraphic("\\Images\\Characters\\Player_Left", x, y, 5);
		this.LeftMovement.setPaused(true);
		this.LeftMovement.setLoopOnce(true);
		this.RightMovement = new AnimatedGraphic("\\Images\\Characters\\Player_Right", x, y, 5);
		this.RightMovement.setPaused(true);
		this.RightMovement.setLoopOnce(true);
		this.UpMovement = new AnimatedGraphic("\\Images\\Characters\\Player_Up", x, y, 5);
		this.UpMovement.setPaused(true);
		this.UpMovement.setLoopOnce(true);
		this.DownMovement = new AnimatedGraphic("\\Images\\Characters\\Player_Down", x, y, 5);
		this.DownMovement.setPaused(true);
		this.DownMovement.setLoopOnce(true);
	
		this.setName("Hero");
		level = 0;				//Current level of play
		Scores = new ArrayList<Integer>();
		rand = new Random();
		currentScore = 0;
		//Score booleans for rewards
		done1 = false;
		done2 = false;
		done3 = false;
		done4 = false;
		done5 = false;
		lvlDone1 = false;
		lvlDone2 = false;
		lvlDone3 = false;
		
	}
	
	//Call after Boss is defeated/stairs down hit
	public void levelTraversed(){
		//stat caps for reference values
		if(Dificulty != 0){
			setMaxHP(getMaxHP() + 5*Dificulty);
			setMaxAtt(getMaxAtt() + 3*Dificulty);
			setMaxDef(getMaxDef() + 3*Dificulty);
		}
		
		//actual player values modified 
		setHealth(maxHP);
		setAttack(maxAtt);
		setDefense(maxDef);
		
		//Other values to handle
		incrementLevel();
		addLevelScore(currentScore);
		currentScore = 0;
		
		lvlDone1 = false;
		lvlDone2 = false;
		lvlDone3 = false;
	}

	/**
	 * @return the current level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void incrementLevel() {
		level = level++;
	}

	/**
	 * @return the maxHP
	 */
	public int getMaxHP() {
		return maxHP;
	}

	/**
	 * @param maxHP the maxHP to set
	 */
	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	/**
	 * @return the maxAtt
	 */
	public int getMaxAtt() {
		return maxAtt;
	}

	/**
	 * @param maxAtt the maxAtt to set
	 */
	public void setMaxAtt(int maxAtt) {
		this.maxAtt = maxAtt;
	}

	/**
	 * @return the maxDef
	 */
	public int getMaxDef() {
		return maxDef;
	}

	/**
	 * @param maxDef the maxDef to set
	 */
	public void setMaxDef(int maxDef) {
		if(maxDef >= 80)
			maxDef = 80;
		this.maxDef = maxDef;
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
	

	/**
	 * @return the currentScore
	 */
	public int getCurrentScore() {
		return currentScore;
	}

	/**
	 * @param amtToAdd the amt to increment score by
	 */
	public void incrementCurrentScore(int amtToAdd) {
		this.currentScore += amtToAdd;
	}

	/**
	 * @return the final score
	 */
	public int getFinalScore() {
		int finalScore = 0;
		for(int score : Scores)
			finalScore += score;
		finalScore += currentScore;
		return finalScore;
	}

	public void checkForPointsReward() {
		int amount = 1000;
		int fin = getFinalScore();
		item = null;
		//Rewards based on total score
		if(fin > amount){
			if(!done1 && getFinalScore() > amount){
				item = ip.randomItem();
				while((item.isWeapon() && item.getItemOffense() < 10) && (item.isArmor() && item.getItemDefense() < 10))
					item = ip.randomItem();
				done1 = true;
			}
			else if(!done2 && getFinalScore() > amount*2){
				item = ip.randomItem();
				while((item.isWeapon() && item.getItemOffense() < 10) && (item.isArmor() && item.getItemDefense() < 10))
					item = ip.randomItem();
				done2 = true;
			}
			else if(!done3 && getFinalScore() > amount*3){
				item = ip.randomItem();
				while((item.isWeapon() && item.getItemOffense() < 10) && (item.isArmor() && item.getItemDefense() < 10))
					item = ip.randomItem();
				done3 = true;
			}
			else if(!done4 && getFinalScore() > amount*4){
				item = ip.randomItem();
				while((item.isWeapon() && item.getItemOffense() < 10) && (item.isArmor() && item.getItemDefense() < 10))
					item = ip.randomItem();
				done4 = true;
			}
			else if(!done5 && getFinalScore() > amount*5){
				item = ip.randomItem();
				while((item.isWeapon() && item.getItemOffense() < 10) && (item.isArmor() && item.getItemDefense() < 10))
					item = ip.randomItem();
				done5 = true;
			}
			if(item != null)
				inv.Invadd(item);
		}
		//Rewards based on level score
		if(!lvlDone1 || !lvlDone2 || !lvlDone3){
			item = null;
			if(!lvlDone1 && getCurrentScore() > 500){
				item = ip.specificItem(9);				
				lvlDone1 = true;
			}
			else if(!lvlDone2 && getCurrentScore() > 1000){
				item = ip.specificItem(9);				
				lvlDone2 = true;
			}
			else if(!lvlDone3 && getCurrentScore() > 1500){
				item = ip.specificItem(9);
				lvlDone3 = true;
			}
			int x;
			if(item != null){
				for(x = 0; x<5; x++)
					inv.Invadd(item);
			}
		}
	}

	/**
	 * @param score the score for a completed level
	 */
	public void addLevelScore(int score) {
		Scores.add(score);
	}
	
	//A few of these could be in the controller?
	/*
	 public void makeInventory(){
	 	Inventory inventory = new Inventory();
	 */
	
	/*
	 public void getLoot(Item item){
	 	addToInventory(item);
	 	}
	 */
	
	/*
	 public void addToInventory(Item item){
	 	inventory.add(item);
	 */
	
	/*
	 public void equipItem(Item item){
	 	if(item.type.equals("Armor"))
	 	else if(item.type.equals("Weapon")
	 	else if(item.type.equals("Consumable")
	 	
	 */
	
	/*
	 public void unequipItem(Item item){
	 	if(item.type.equals("Armor"))
	 	else if(item.type.equals("Weapon")
	 */
	
	/*
	 public void consumeItem(Item item){
	 	if(item.type.equals("Potion"))
	 	healTarget(item.value);
	 	if(getHealth() > maxHP)
	 		setHealth(maxHP);
	 	
	 */
	
	/*
	 public Item availableLoot(Mob mob){
	 	Item item = null;
	 	item = mobLootableItem();
	 	return item;
	 	}
	 */
	
	/*
	 public Item availableLoot(Boss boss){
	 	Item item = null;
	 	item = bossLootableItem();
	 	return item;
	 	}
	 */
	
	

	@Override
	public int damageGiven() {
		int draw = rand.nextInt(10);
		int damage;
		//30% chance to crit
		if(draw >= 7){
			damage = (int) (maxAtt + (getHealth() % (1.0*(rand.nextInt(6+(getLevel()*2))+2)) ));
		}
		//10% chance of miss
		else if(draw == 0){
			damage = 0;
		}
		else
			damage = maxAtt;
		return damage;
	}

	@Override
	public int damageReceived(int damageGiven) {
		int damageTaken;
		//Missed attack
		if(damageGiven == 0)
			damageTaken = 0;
		else{
			damageTaken = (int) ( damageGiven*((100-maxDef)/(1.0*100)) );
		}
		setHealth(getHealth() - damageTaken);
		return damageTaken;
	}

	@Override
	public int getLightRadius() {
		// TODO Auto-generated method stub
		return LightRadius;
	}

	@Override
	public void setLightRadius(int NewLightRadius) {
		// TODO Auto-generated method stub
		LightRadius = NewLightRadius;
	}
	
	public AnimatedGraphic getLeftMovement() {
		return LeftMovement;
	}

	public void setLeftMovement(AnimatedGraphic leftMovement) {
		LeftMovement = leftMovement;
	}

	public int getPlayerX() {
		return PlayerX;
	}

	public void setPlayerX(int playerX) {
		PlayerX = playerX;
		LeftMovement.setX(PlayerX);
		UpMovement.setX(PlayerX);
		RightMovement.setX(PlayerX);
		DownMovement.setX(PlayerX);
	}

	public int getPlayerY() {
		return PlayerY;
	}

	public void setPlayerY(int playerY) {
		PlayerY = playerY;
		LeftMovement.setY(PlayerY);
		UpMovement.setY(PlayerY);
		RightMovement.setY(PlayerY);
		DownMovement.setY(PlayerY);
	}
	public AnimatedGraphic getRightMovement() {
		return RightMovement;
	}
	public void setRightMovement(AnimatedGraphic rightMovement) {
		RightMovement = rightMovement;
	}
	public AnimatedGraphic getUpMovement() {
		return UpMovement;
	}
	public void setUpMovement(AnimatedGraphic upMovement) {
		UpMovement = upMovement;
	}
	public AnimatedGraphic getDownMovement() {
		return DownMovement;
	}
	public void setDownMovement(AnimatedGraphic downMovement) {
		DownMovement = downMovement;
	}
	public boolean isWalkAnimationPlaying() {
		return WalkAnimationPlaying;
	}
	public void setWalkAnimationPlaying(boolean walkAnimationPlaying) {
		WalkAnimationPlaying = walkAnimationPlaying;
	}
	public int getDificulty() {
		return Dificulty;
	}
	public void setDificulty(int dificulty) {
		this.Dificulty = dificulty;
		
		if(Dificulty != 0){
			this.health = BASE_HEALTH;
			this.defense = BASE_DEFENSE + 15;
			this.attack = BASE_ATTACK + 5;
			
			this.maxHP = BASE_HEALTH;
			this.maxDef = BASE_DEFENSE + 15;
			this.maxAtt = BASE_ATTACK + 5;
		}
		else{
			this.health = 1000;
			this.defense = 80;
			this.attack = 500;
			
			this.maxHP = 1000;
			this.maxDef = 80;
			this.maxAtt = 500;
		}
		
	}
	public void setInventory(Inventory inventory) {
		this.inv = inventory;
	}
	
	public void setInvPool(ItemPool pool) {
		this.ip = pool;
	}
	
	public void updateStats(Inventory inv, Equipment equip){
		ArrayList<Item> items = new ArrayList<Item>();
		items = equip.getEquipmentArray();
			
		}
	
	public void decrementLevel() {
		level--;
	}
		
	}

