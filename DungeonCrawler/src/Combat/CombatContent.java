package Combat;

import javax.swing.JPanel;

import Core.ScreenAnimations;
import MainGraphicPackage.AnimatedGraphic;
import character.Character;
import character.Mob;
import character.Player;

import java.awt.Graphics;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Creates the content for the Combat Panel
 * 
 * @author Daniel
 *
 */
@SuppressWarnings("serial")
public class CombatContent extends JPanel {
	private enum Combat  {
		BLANK, ATTACK,
		DEFEND, FLEE
	}
	
	//Need to have a variable for this class so I can access this
	//instance of this class inside private classes and anonymous classes
	private CombatContent thisWindow;
	//Enum for determining combat
	private Combat combat;
	//Random variable for determining enemy action
	private Random rand;
	//Variables that indicate battle outcomes for you
	private boolean defended = false;
	private boolean flee_success = false;
	private boolean can_end = false;
	private boolean won = false;
	private boolean dead = false;
	private int currentDamage = 0;
	private int damToMob;
	private int damToPlayer;
	//Variables that indicate battle outcomes for enemy
	private String monster_name = "";
	private boolean enemy_fled = false;
	private boolean enemy_defended = false;
	//"Buttons" for attack, defend, and flee
	private Rectangle attack, defend, flee;
	//The setting that the rest of the content is drawn on
	private BufferedImage setting = null;
	private BufferedImage reference_image = null;
	//List of animations file names
	/*
	private ArrayList<String> Enemy_Animations;
	private ArrayList<String> Attack_Animations;
	private ArrayList<String> Defense_Animations;
	*/
	private String Enemy_Animations;//,Attack_Animations,Defense_Animations;
	
	//The enemy graphic
	private AnimatedGraphic enemy = null;
	//A timer variable to handle when to change the animations
	private Timer animationTimer;
	private Player player;
	private Mob mob;

	//Used for screen transitions
	private ScreenAnimations SAnimation;
	
	/**
	 * Initializes appropriate class variables and adds a mouseListener,<\br>
	 * a componentListener, and starts the timer for animations.
	 * 
	 * @param player  Player that is being put through combat
	 */
	public CombatContent(Player player, int CurrentLevel){
		
		SAnimation = new ScreenAnimations();
		SAnimation.setLoadingAnimationIn(true);
				
		//Assign CombatContent variable to this instance
		thisWindow = this;
		
		rand = new Random();
		
		int monsterDraw = rand.nextInt(4);
		monster_name = "";
		
		switch(monsterDraw)  {
		case 0:
				monster_name = "blob";
				break;
		case 1:
				monster_name = "daedra";
				break;
		case 2:
				monster_name = "dementand";
				break;
		case 3: 
				monster_name = "dorver";
				break;
		case 4:
				monster_name = "wyvern";
		}
		
		//Set stats for player and enemy
		Mob mob = new Mob("Images/Mob/"+ monster_name + "/" + monster_name +"1.png", monsterDraw, player.getLevel());
		mob.setDifficulty(player.getDificulty());
		
		this.setSize(400,300);
		this.setVisible(true);
		
		/*
		//Instantiate and add Stance Animations
		Enemy_Animations = new ArrayList<String>();
		
		Enemy_Animations.add("Images/Characters/Goblin_Stance/Goblin_Stance1.png");
		Enemy_Animations.add("Images/Characters/Goblin_Stance/Goblin_Stance2.png");
		Enemy_Animations.add("Images/Characters/Goblin_Stance/Goblin_Stance3.png");
		Enemy_Animations.add("Images/Characters/Goblin_Stance/Goblin_Stance4.png");
		Enemy_Animations.add("Images/Characters/Goblin_Stance/Goblin_Stance5.png");
		
		//Instantiate and add Attack_Animations
		Attack_Animations = new ArrayList<String>();
		
		Attack_Animations.add("Images/Characters/Goblin_Attack/Goblin_Attack1.png");
		Attack_Animations.add("Images/Characters/Goblin_Attack/Goblin_Attack2.png");
		Attack_Animations.add("Images/Characters/Goblin_Attack/Goblin_Attack3.png");
		Attack_Animations.add("Images/Characters/Goblin_Attack/Goblin_Attack4.png");
		Attack_Animations.add("Images/Characters/Goblin_Attack/Goblin_Attack5.png");
		
		//Instantiate and add Defense Animations
		Defense_Animations = new ArrayList<String>();
		
		Defense_Animations.add("Images/Characters/Goblin_Defense/Goblin_Defense1.png");
		Defense_Animations.add("Images/Characters/Goblin_Defense/Goblin_Defense2.png");
		Defense_Animations.add("Images/Characters/Goblin_Defense/Goblin_Defense3.png");
		Defense_Animations.add("Images/Characters/Goblin_Defense/Goblin_Defense4.png");
		Defense_Animations.add("Images/Characters/Goblin_Defense/Goblin_Defense5.png");
		*/
		
		Enemy_Animations = "\\Images\\Mob\\" + monster_name;
		
		this.player = player;
		this.mob = mob;
		
		try {
			reference_image = ImageIO.read(new File("." + Enemy_Animations+"\\"+monster_name+"1.png"));
		} catch(IOException e)  {
			e.printStackTrace();
		}
		
		try {//Real mob
			setting = ImageIO.read(new File("Images\\CombatSettings\\cave_hall.png"));
			enemy = new AnimatedGraphic(Enemy_Animations, this.getWidth()/2-(reference_image.getWidth()/2),
						this.getHeight()/2, 5);
		} catch(IOException e)  {
			e.printStackTrace();
		}
		
		animationTimer = new Timer();
		animationTimer.scheduleAtFixedRate(new RunAnimations(), 0, 1000/enemy.getFrameRate());

		combat = Combat.BLANK;
		attack = new Rectangle(this.getWidth()/18, 33*this.getHeight()/46, 130*this.getWidth()/500, 108*this.getHeight()/500);
		defend = new Rectangle(56*this.getWidth()/160, 33*this.getHeight()/46, 136*this.getWidth()/500, 108*this.getHeight()/500);
		flee = new Rectangle(542*this.getWidth()/800, 33*this.getHeight()/46, 130*this.getWidth()/500, 108*this.getHeight()/500);

		this.addMouseListener(new MouseListener() {

		public void mouseClicked(MouseEvent e)  {
			//System.out.println(mob.getDefense());
			if(attack.contains(e.getPoint()))  {
				if(!can_end && !dead)  {
					try {
				        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Sound\\Swordswing.wav").getAbsoluteFile());
				        Clip clip = AudioSystem.getClip();
				        clip.open(audioInputStream);
				        clip.start();
				    } catch(Exception ex) {
				        System.out.println("Error with playing sound.");
				        ex.printStackTrace();
				    }
					combat = Combat.ATTACK;
					currentDamage = player.damageGiven();
					int hitFor = mob.damageReceived(currentDamage);
					damToMob = hitFor;
					System.out.println("Player hit Mob for "+ hitFor);
					
					if(defended){
						player.setDefense(player.getDefense() - 10);
						defended = false;
					}
					if(mob.getHealth() <= 0) {
						//Play End Transition
						SAnimation.setLoadingAnimationOut(true);
						can_end = true;
						won = true;
					}
					else  {
						enemyAction();
					}
				}
				else  {
					if(defended)  {
						player.setDefense(player.getDefense()-10);
						defended = false;
					}
				}
			}
			else if(defend.contains(e.getPoint()))  {
				if(!can_end && !dead){
					try {
				        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Sound\\Block.wav").getAbsoluteFile());
				        Clip clip = AudioSystem.getClip();
				        clip.open(audioInputStream);
				        clip.start();
				    } catch(Exception ex) {
				        System.out.println("Error with playing sound.");
				        ex.printStackTrace();
				    }
					combat = Combat.DEFEND;
					if(!defended){
						player.setDefense(player.getDefense() + 10);
						defended = true;
					}
					
					enemyAction();
				}
				else  {
					if(defended)  {
						player.setDefense(player.getDefense()-10);
						defended = false;
					}
				}
			}
			else if(flee.contains(e.getPoint()))  {
				if(!can_end && !dead){
					try {
				        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Sound\\Flee.wav").getAbsoluteFile());
				        Clip clip = AudioSystem.getClip();
				        clip.open(audioInputStream);
				        clip.start();
				    } catch(Exception ex) {
				        System.out.println("Error with playing sound.");
				        ex.printStackTrace();
				    }
					combat = Combat.FLEE;
					flee_success = tryFlee();
					can_end = flee_success;
					if(can_end){
						//Play End Transition
						SAnimation.setLoadingAnimationOut(true);
					}
					if(!can_end)  {
						enemyAction();
					}
				}
				else  {
					if(defended)  {
						player.setDefense(player.getDefense()-10);
						defended = false;
					}
				}
			}

			if(won)  {
				//Award points for winning
				player.incrementCurrentScore(mob.awardPoints());
				System.out.println("Score: "+ player.getFinalScore());
//				player.checkForPointsReward();
				mob.setHealth(0);
			}
			repaint();
		}

		public void mouseEntered(MouseEvent e)  {

		}

		public void mouseExited(MouseEvent e)  {

		}

		public void mousePressed(MouseEvent e)  {

		}

		public void mouseReleased(MouseEvent e)  {

		}
		});
		
		this.addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent e) {
				//Resizes the button hit boxes
				attack.setBounds(thisWindow.getWidth()/20, 33*thisWindow.getHeight()/46, 
								130*thisWindow.getWidth()/500, 108*thisWindow.getHeight()/500);
				defend.setBounds(56*thisWindow.getWidth()/160, 33*thisWindow.getHeight()/46,
								136*thisWindow.getWidth()/500, 108*thisWindow.getHeight()/500);
				flee.setBounds(542*thisWindow.getWidth()/800, 33*thisWindow.getHeight()/46, 
								130*thisWindow.getWidth()/500, 108*thisWindow.getHeight()/500);
				enemy.setX(thisWindow.getWidth()/2-(reference_image.getWidth()/2));
				enemy.setY(thisWindow.getHeight()/2);
				repaint();
           }
		});
	}

	/**
	 * Draws combat setting, hud, and enemy<\br>
	 * Also cleans up defense stats for when the battle has ended.
	 * 
	 * @param g Graphics for paint
	 */
	public void paintComponent(Graphics g)  {
		//Clear whatever I had there before
		super.paintComponent(g);
		//draw setting and enemy
		g.drawImage(setting, 0, 0, getWidth(), getHeight(), null);
		g.drawImage(enemy.getMainImage(), enemy.getX(), enemy.getY(), 
					enemy.getMainImage().getWidth(), enemy.getMainImage().getHeight(), null);

		//Draws what just happened and cleans up defense and health stats when the battle ends
		switch(combat){
			case ATTACK:
				if(won) {
					g.drawString("YOU WON!!", 202*this.getWidth()/500, (40*this.getHeight()/500)+5);
					if(defended)  {
						player.setDefense(player.getDefense()-10);
						defended = false;
					}
					break;
				}
				else if(enemy_fled)  {
					g.drawString("Enemy fled!", 202*this.getWidth()/500, (40*this.getHeight()/500)+5);
					break;
				}
				if(currentDamage == 0)
					g.drawString("You missed!!", 202*this.getWidth()/500, (40*this.getHeight()/500)+5);
				else
					g.drawString("You attacked for " + damToMob + " damage.", 202*this.getWidth()/500, (40*this.getHeight()/500)+5);
//Space for this	g.drawString("Mob attacked you for " + damToPlayer + " damage.", 202*this.getWidth()/500, (40*this.getHeight()/500)+25);

				break;
			case DEFEND:
				if(won) {
					g.drawString("YOU WON!!", 202*this.getWidth()/500, (40*this.getHeight()/500)+5);
					break;
				}
				else if(enemy_fled)  {
					g.drawString("Enemy fled!", 202*this.getWidth()/500, (40*this.getHeight()/500)+5);
					if(defended)  {
						player.setDefense(player.getDefense()-10);
						defended = false;
					}
					break;
				}
				g.drawString("You defended", 202*this.getWidth()/500, (40*this.getHeight()/500)+5);
			break;
			case FLEE:
				if(won) {
					g.drawString("YOU WON!!", 202*this.getWidth()/500, (40*this.getHeight()/500)+5);
					if(defended)  {
						player.setDefense(player.getDefense()-10);
						defended = false;
					}
					break;
				}
				else if(enemy_fled)  {
					g.drawString("Enemy fled!", 202*this.getWidth()/500, (40*this.getHeight()/500)+5);
					break;
				}
				if(flee_success){
					g.drawString("You fled", 202*this.getWidth()/500, (40*this.getHeight()/500)+5);
					if(defended)  {
						player.setDefense(player.getDefense()-10);
						defended = false;
					}
					break;
				}
					g.drawString("Flee unsucessful", 202*this.getWidth()/500, (40*this.getHeight()/500)+5);
			break;
			default:
				g.drawString("A " + monster_name + " has appeared.", 202*this.getWidth()/500, (40*this.getHeight()/500)+5);
		}

		drawStats(g);
		
		if(dead)  {
			g.setFont(new Font("Arial", Font.BOLD, getWidth()/10));
			g.setColor(Color.RED);
			g.drawString("You Died", getWidth()/4, getHeight()/2);
		}
		
		//Transition Screen 
		if(SAnimation.isLoadingAnimationOut() == true || SAnimation.isLoadingAnimationIn() == true){
			ArrayList<Rectangle> SquareList = null;
			if(SAnimation.isLoadingAnimationOut() == true){
				SquareList = SAnimation.LoadingAnimationOut(this);
			}else if(SAnimation.isLoadingAnimationIn() == true){
				SquareList = SAnimation.LoadingAnimationIn(this);
			}
			for(int i = 0; i < SquareList.size(); i++){
				g.setColor(new Color(50, 46, 44));
				g.fillRect((int)SquareList.get(i).getX(), (int)SquareList.get(i).getY(), (int)SquareList.get(i).getWidth(), (int)SquareList.get(i).getHeight());
			}
		}
	}

	/**
	 * Returns the result of a flee attempt
	 * 
	 * @return	if true then flee successful, else flee failed
	 */
	private boolean tryFlee() {
		int draw = rand.nextInt(100);
		if(draw < 50)
			return true;
		return false;
	}
	
	/**
	 * Draws the stats for the player and enemy
	 * 
	 * @param g
	 */
	private void drawStats(Graphics g)  {
		//Draw the player's stats
		g.setColor(Color.YELLOW);
		g.drawString("Health", 20*this.getWidth()/500, 237*this.getHeight()/500);
		g.drawString("Defense", 20*this.getWidth()/500, 277*this.getHeight()/500);
		g.setColor(Color.ORANGE);
		g.drawString("Enemy Health", (400-32)*this.getWidth()/500, 237*this.getHeight()/500);
		g.drawString("Enemy Defense", (400-32)*this.getWidth()/500, 277*this.getHeight()/500);
		g.setColor(Color.GRAY);
		g.fillRect(20*this.getWidth()/500, 247*this.getHeight()/500, player.getMaxHP()*this.getWidth()/500, 20*this.getHeight()/500);
		g.setColor(Color.GREEN);
		g.fillRect(20*this.getWidth()/500, 247*this.getHeight()/500, player.getHealth()*this.getWidth()/500, 20*this.getHeight()/500);
		g.setColor(Color.GRAY);
		g.fillRect(20*this.getWidth()/500, 287*this.getHeight()/500, player.getMaxDef()*this.getWidth()/500, 20*this.getHeight()/500);
		g.setColor(Color.BLUE);
		g.fillRect(20*this.getWidth()/500, 287*this.getHeight()/500, player.getDefense()*this.getWidth()/500, 20*this.getHeight()/500);
		
		//Draw the mob's stats
		g.setColor(Color.RED);
		g.fillRect((500-mob.getHealth()-32)*this.getWidth()/500, 247*this.getHeight()/500, mob.getHealth()*this.getWidth()/500, 20*this.getHeight()/500);
		g.setColor(Color.DARK_GRAY);
		g.fillRect((500-mob.getDefense()-32)*this.getWidth()/500, 287*this.getHeight()/500, mob.getDefense()*this.getWidth()/500, 20*this.getHeight()/500);
		
		//get my black Color back
		g.setColor(Color.BLACK);
	}
	
	/**
	 * Indicates whether you can end the battle
	 * 
	 * @return	value of global variable can_end
	 */
	public boolean getCanEnd()  {
		return can_end;
	}
	
	/**
	 * Indicates whether the player is dead or not
	 * 
	 * @return value of global variable "dead"
	 */
	public boolean isDead()  {
		return dead;
	}
	
	/**
	 * Randomly determines enemy action
	 */
	private void enemyAction()  {
		if(dead)
			return;
		
		int draw = rand.nextInt(10);
		
		if(draw<5)  {
			if(enemy_defended)  {
				mob.setDefense(mob.getDefense()-10);
				enemy_defended = false;
			}
			int hitFor = player.damageReceived(mob.damageGiven());
			damToPlayer = hitFor;
			System.out.println("Mob hit Player for "+ hitFor);
			if(player.getHealth() <= 0)
				dead = true;
		}
		else  {
			if(!enemy_defended)  {
				mob.setDefense(mob.getDefense()+10);
				enemy_defended = true;
				System.out.println("Mob bolstered its defenses!");
			}
			//With no other statement, if draw is <5 2x or more then the mob takes no action
			else
				System.out.println("Mob is biding its time...");
		}
		
	}
	public boolean getLoadingAnimationOut(){
		return SAnimation.isTOut();
	}
	/**
	 * Class that moves the animation to the next frame based on the time.
	 * @author Daniel
	 *
	 */
	private class RunAnimations extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			enemy.NextFrame();
			//thisWindow.repaint();
		}
		
	}
}
