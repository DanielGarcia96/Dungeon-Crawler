package Core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import Combat.CombatContent;
import HighScore.Highscore;
import HighScore.User;
import character.Player;
import Inventory.Chests;
import Inventory.Equipment;
import Inventory.Inventory;
import Inventory.ItemPool;
import Light.LightingEngine;
import MainGraphicPackage.AnimatedGraphic;
import MainGraphicPackage.Graphic;
import MainGraphicPackage.Lightable;
import Map.Clutter.Clutter;
import Map.Tile.StairUp;
import Map.Tile.Tile;

/**
 * Where all game content is located
 * Main place for tieing all components together
 * --Warning Swing workers stay active may need to close them---
 * @author Porter
 *
 */
@SuppressWarnings("serial")
public class MainContentPanel extends JPanel{
	
	private boolean Lighting_Engine = true;
	private boolean Animations = true;
	private int Dificulty;
	
	private Equipment MainEquip;
	private Inventory MainInv;
	private Chests CurrentChest;
	private ItemPool MainItemPool = new ItemPool();
	
	private boolean PlayerInCombat;
	private CombatContent MainCombatPanel;
	
	private boolean EscapeKeyPressed, ReturnMainMenu;
	private ScreenAnimations SAnimation;
	
	private boolean PlayerMoved;
	private int TotalFramesPast, PlayerMovementDelay;
	
	private boolean GeneratingLevel;
	private int CurrentLevel;
	private ArrayList <GenerateLevel> LevelList;
	private ArrayList <Graphic> MainGraphicList;
	private ViewPort MainCam;
	private Player MainPlayer;
	
	/**
	 * Repaints all components if the viewport intersects them
	 */
	public void paintComponent(Graphics g){
		//Sets focus to this panel for key listeners
		//this.requestFocus(true);
		//Erase all old Stuff
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		/*
		//Makes the Lighting Engine
		if(Lighting_Engine == true){
			LightingEngine Light = new LightingEngine(MainGraphicList);
		}
		*/
		
		//Makes player movement slow down
		if(PlayerMoved == true){
			PlayerMovementDelay++;
			if(PlayerMovementDelay > 0){
				PlayerMoved = false;
				PlayerMovementDelay = 0;
			}
		}
			//Loop through all Graphics in array list
			for(int i = 0; i < MainGraphicList.size(); i++){
				//if they intersect main camera then draw them
				if(MainCam.ViewportIntersection(MainGraphicList.get(i).getCollisionBox())){
					//Don't paint the player if generating a level
					if(GeneratingLevel == false || !(MainGraphicList.get(i) instanceof Player)){
						if((MainGraphicList.get(i)) instanceof AnimatedGraphic && Animations == true){
							if(((AnimatedGraphic)MainGraphicList.get(i)).isPaused() == false){
								if(((AnimatedGraphic)MainGraphicList.get(i)) instanceof Player){
									if(MainPlayer.getLeftMovement().isPaused() == false){
										g2d.drawImage(((Player)MainGraphicList.get(i)).getLeftMovement().getMainImage(), MainGraphicList.get(i).getX() - (int)MainCam.getView().getX(), MainGraphicList.get(i).getY() - (int)MainCam.getView().getY(), null);
									}else if(MainPlayer.getUpMovement().isPaused() == false){
										g2d.drawImage(((Player)MainGraphicList.get(i)).getUpMovement().getMainImage(), MainGraphicList.get(i).getX() - (int)MainCam.getView().getX(), MainGraphicList.get(i).getY() - (int)MainCam.getView().getY(), null);
									}else if(MainPlayer.getRightMovement().isPaused() == false){
										g2d.drawImage(((Player)MainGraphicList.get(i)).getRightMovement().getMainImage(), MainGraphicList.get(i).getX() - (int)MainCam.getView().getX(), MainGraphicList.get(i).getY() - (int)MainCam.getView().getY(), null);
									}else if(MainPlayer.getDownMovement().isPaused() == false){
										g2d.drawImage(((Player)MainGraphicList.get(i)).getDownMovement().getMainImage(), MainGraphicList.get(i).getX() - (int)MainCam.getView().getX(), MainGraphicList.get(i).getY() - (int)MainCam.getView().getY(), null);
									}else{
										MainPlayer.setWalkAnimationPlaying(false);
										g2d.drawImage(MainGraphicList.get(i).getMainImage(), MainGraphicList.get(i).getX() - (int)MainCam.getView().getX(), MainGraphicList.get(i).getY() - (int)MainCam.getView().getY(), null);
									}
								}else{
									//Main Painting for animated graphics if they are not paused
									g2d.drawImage(MainGraphicList.get(i).getMainImage(), MainGraphicList.get(i).getX() - (int)MainCam.getView().getX(), MainGraphicList.get(i).getY() - (int)MainCam.getView().getY(), null);
								}
							}
						}else{
							//Main Painting of everything
							g2d.drawImage(MainGraphicList.get(i).getMainImage(), MainGraphicList.get(i).getX() - (int)MainCam.getView().getX(), MainGraphicList.get(i).getY() - (int)MainCam.getView().getY(), null);
						}
					}
					if(MainGraphicList.get(i) instanceof Lightable){
						g2d.setColor(new Color(25, 23, 22,((Lightable) MainGraphicList.get(i)).getLightLevel()));
						//Disable so whole map is visable
						if(Lighting_Engine == true){
							g2d.fillRect((int)MainGraphicList.get(i).getX() - (int)MainCam.getView().getX(), MainGraphicList.get(i).getY() - (int)MainCam.getView().getY(), (int)MainGraphicList.get(i).getCollisionBox().getWidth(), (int)MainGraphicList.get(i).getCollisionBox().getHeight());
						}
					}
				}
			}
		if(GeneratingLevel == true){
			g2d.setFont(new Font("Courier", Font.BOLD,32));
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.drawString("Loading...", this.getWidth()/2 - 32, this.getHeight()/2);
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
				g2d.setColor(new Color(50, 46, 44));
				g2d.fillRect((int)SquareList.get(i).getX(), (int)SquareList.get(i).getY(), (int)SquareList.get(i).getWidth(), (int)SquareList.get(i).getHeight());
			}
		}
		g2d.dispose();
	}
	/**
	 * Very slow can be greatly faster, dont need to loop through everything
	 * @param MovingObject
	 */
	private void ClutterHandle(Graphic MovingObject){
		for(int i = 0; i < MainGraphicList.size(); i++){
			if(MainGraphicList.get(i) instanceof Clutter){
				if(MainGraphicList.get(i).getCollisionBox().intersects(MovingObject.getCollisionBox())){
					if(MainGraphicList.get(i) instanceof Map.Clutter.Chest){
						if(((Map.Clutter.Chest)MainGraphicList.get(i)).getChestContent() == null){
							((Map.Clutter.Chest)MainGraphicList.get(i)).setChestContent(new Chests(MainInv));
						}
						((Map.Clutter.Chest)MainGraphicList.get(i)).getChestContent().setVisible(true);
						CurrentChest = ((Map.Clutter.Chest)MainGraphicList.get(i)).getChestContent();
					}
				}else if(MainGraphicList.get(i) instanceof Map.Clutter.Chest){
					if(((Map.Clutter.Chest)MainGraphicList.get(i)).getChestContent() != null){
						((Map.Clutter.Chest)MainGraphicList.get(i)).getChestContent().setVisible(false);
					}
				}
			}
		}
	}
	/**
	 * Checks if next move is Valid and makes a new level if stairs hit
	 * @param MovingObject
	 * @param MovingPointX
	 * @param MovingPointY
	 * @return
	 */
	private boolean NextPointVal(Graphic MovingObject, int MovingPointX, int MovingPointY){
		//Player should not move when generating a level
		if(GeneratingLevel == true){
			return false;
		}
		//Makes a new collisionbox to test next move
		Rectangle NewCollisionBox = new Rectangle((int)MovingPointX, (int)MovingPointY, (int)MovingObject.getCollisionBox().getWidth(), (int)MovingObject.getCollisionBox().getHeight());
		ArrayList <Graphic> CollidedGraphics = new ArrayList<Graphic>();
		//Loop through MainGraphicList looking for All collisons and add them to a queue
		for(int i = 0; i < MainGraphicList.size(); i++){
			//Makes sure element given is not compared to itself
			if(!MainGraphicList.get(i).equals(MovingObject)){
				//Check for collision with collision boxes
				if(NewCollisionBox.intersects(MainGraphicList.get(i).getCollisionBox())){
					//if the object collides then add it to the queue
					CollidedGraphics.add(MainGraphicList.get(i));
				}
			}
		}
		//set default return value to true, unless the player cannot move to a certain spot
		boolean ReturnValue = false;
		for(int i = 0; i < CollidedGraphics.size(); i++){
		//Checks for a walkable tile and returns true unless its a downward stair
		if(CollidedGraphics.get(i) instanceof Map.Tile.Tile){
			Tile TestTile = (Tile) CollidedGraphics.get(i);
			if(TestTile.isWalkable()){
				ReturnValue  = true;
				//If its stairs going down make a new level and it must be a player moving
				if(CollidedGraphics.get(i) instanceof Map.Tile.StairDown && MovingObject instanceof Player){
					//Must set class variables on event thread so nothing weird happens in the new thread
					//Set GeneratingLevel to true
			    	GeneratingLevel = true;
			    	//increase the current level
			    	CurrentLevel++;
			    	
					SwingWorker<ArrayList<Graphic>, Void> worker = new SwingWorker<ArrayList <Graphic>, Void>() {
					    @Override
					    public ArrayList <Graphic> doInBackground() {
					    	//check if we should generate a new level
					    	if(CurrentLevel > LevelList.size() - 1){
					    	//Level-score added to arrayList, currentScore set to 0, currentLevel incremented, stats increased
						    MainPlayer.levelTraversed();
					    	//Generate a new level
							GenerateLevel Level1 = new GenerateLevel();
							//add the level to main level list
							LevelList.add(Level1);
							//set the previous levels player x and y for when going up floors
							LevelList.get(CurrentLevel - 1).setPlayerExitX(MainPlayer.getX());
							LevelList.get(CurrentLevel - 1).setPlayerExitY(MainPlayer.getY());
							//save the new levels graphics into a temp array
							ArrayList <Graphic> Temp = Level1.getLevelGraphicListController(MainPlayer, true);
							//delete levels graphics so it does not take room in memory
							Level1.setLevelGraphicList(null);
							//return the temp array
							return Temp;
					    	}else{
					    		//return level in the list if currentlevel is in the list
								//set the previous levels player x and y for when going up floors
								LevelList.get(CurrentLevel - 1).setPlayerExitX(MainPlayer.getX());
								LevelList.get(CurrentLevel - 1).setPlayerExitY(MainPlayer.getY());
								//old levels graphics into temp array
					    		ArrayList <Graphic> Temp = LevelList.get(CurrentLevel).getLevelGraphicListController(MainPlayer, false);
					    		//delete levels graphics so it does not take room in memory
					    		LevelList.get(CurrentLevel).setLevelGraphicList(null);
					    		//return the temp array
					    		return Temp;
					    	}
					    }
					    @Override
					    public void done() {
							try {
								synchronized(MainGraphicList){
									MainGraphicList = (ArrayList<Graphic>) this.get();
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ExecutionException e) {
								e.printStackTrace();
							}
							for(int i = 0; i < MainGraphicList.size(); i++){
								if(MainGraphicList.get(i) instanceof Player){
									//Set Main Player
									MainPlayer = (Player) MainGraphicList.get(i);
									MainGraphicList.add(MainPlayer.getLeftMovement());
									MainGraphicList.add(MainPlayer.getRightMovement());
									MainGraphicList.add(MainPlayer.getUpMovement());
									MainGraphicList.add(MainPlayer.getDownMovement());
									MainCam.getView().setLocation((int)MainGraphicList.get(i).getX() - (int)MainCam.getView().getWidth()/2 + (int)MainGraphicList.get(i).getCollisionBox().getWidth()/2
									, (int)MainGraphicList.get(i).getY()- (int)MainCam.getView().getHeight()/2 + (int)MainGraphicList.get(i).getCollisionBox().getHeight()/2);
								}
							}
							//Set GeneratingLevel to false
					    	GeneratingLevel = false;
					    }
					};
					//start the thread
					worker.execute();
					//return false so player does not move onto stairs
					ReturnValue  = false;
					break;
				}
				if(CollidedGraphics.get(i) instanceof StairUp && MovingObject instanceof Player){
					//makes sure you are not on the first level
					if(CurrentLevel != 0){
						//set current level player x and y for when going down a level
						LevelList.get(CurrentLevel).setPlayerExitX(MainPlayer.getX());
						LevelList.get(CurrentLevel).setPlayerExitY(MainPlayer.getY());	
						//Save perivous levels graphics into main arraylist making it the current level, while removing the old contents
						MainGraphicList = LevelList.get(CurrentLevel - 1).getLevelGraphicListController(MainPlayer, false);
						//delete levels graphics so it does not take room in memory
						LevelList.get(CurrentLevel - 1).setLevelGraphicList(null);
						//sets level to the old level
						CurrentLevel--;
						MainPlayer.decrementLevel();
						//reset the camera
						for(int i1 = 0; i1 < MainGraphicList.size(); i1++){
							if(MainGraphicList.get(i1) instanceof Player){
								MainPlayer = (Player) MainGraphicList.get(i1);
								MainGraphicList.add(MainPlayer.getLeftMovement());
								MainGraphicList.add(MainPlayer.getRightMovement());
								MainGraphicList.add(MainPlayer.getUpMovement());
								MainGraphicList.add(MainPlayer.getDownMovement());
								MainCam.getView().setLocation((int)MainGraphicList.get(i1).getX() - (int)MainCam.getView().getWidth()/2 + (int)MainGraphicList.get(i1).getCollisionBox().getWidth()/2
								, (int)MainGraphicList.get(i1).getY()- (int)MainCam.getView().getHeight()/2 + (int)MainGraphicList.get(i1).getCollisionBox().getHeight()/2);
							}
						}
						
					}else{
						int OptionPicked = JOptionPane.showConfirmDialog(this, "Return to the surface?", null, JOptionPane.YES_NO_OPTION);
						if(OptionPicked == JOptionPane.YES_OPTION){
							Highscore HTable = new Highscore();
							HTable.ReadHighScoreFile("Data\\HighScores.bin");
							
							JButton Ok = new JButton("Ok");
							JButton Save = new JButton("Save");
							if(HTable.CanAddUser(MainPlayer.getFinalScore()) == false){
								Save.setEnabled(false);
							}
							Ok.addActionListener(new ActionListener() {
			                    @Override
			                    public void actionPerformed(ActionEvent e) {
			                    	JOptionPane Options = null;
			                    	JComponent Test = (JComponent) e.getSource();
			                    	while(!(Test instanceof JOptionPane)){
			                    		Test = (JComponent) Test.getParent();
			                    	}
			                    	Options = (JOptionPane) Test;
			                    	Options.setValue(Ok);
			                    }
			                });
							Save.addActionListener(new ActionListener() {
			                    @Override
			                    public void actionPerformed(ActionEvent e) {
			                    	JOptionPane Options = null;
			                    	JComponent Test = (JComponent) e.getSource();
			                    	while(!(Test instanceof JOptionPane)){
			                    		Test = (JComponent) Test.getParent();
			                    	}
			                    	Options = (JOptionPane) Test;
			                    	Options.setValue(Save);
			                    }
			                });
							JButton[] CustomButtons = {Ok, Save};
							OptionPicked = JOptionPane.showOptionDialog(this, "Total Score: "+MainPlayer.getFinalScore(), "", 
						    		 JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, CustomButtons, CustomButtons[0]);
							if(OptionPicked == 1){
								JTextField UserName = new JTextField();
								int MaxTextFieldSize = 8;
								AbstractDocument Document = (AbstractDocument)UserName.getDocument();
								Document.setDocumentFilter(new DocumentFilter(){
									@Override
									public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs){
										//Makes sure textfield does not get any whitespace
										if(text.matches("\\s")){
											Toolkit.getDefaultToolkit().beep();
											return;
										}
										try {
											String TextField = fb.getDocument().getText(0, fb.getDocument().getLength());
											//checks to make sure the length is not greater than 8
											if(TextField.length() < MaxTextFieldSize){
												//writes characters to the popup dialog
												super.replace(fb, offset, length, text, attrs);
											}else{
												Toolkit.getDefaultToolkit().beep();
											}
										} catch (BadLocationException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								});
								final JComponent[] inputs = new JComponent[] {
										new JLabel("Name:"),
										UserName
								};
								do{
									OptionPicked = JOptionPane.showConfirmDialog(this, inputs, "", JOptionPane.PLAIN_MESSAGE);
									if(OptionPicked == JOptionPane.CLOSED_OPTION){
										break;
									}
									if(UserName.getText().length() < 1){
										Toolkit.getDefaultToolkit().beep();
									}
								}while(UserName.getText().length() < 1);
								if(OptionPicked != JOptionPane.CLOSED_OPTION){
									while(UserName.getText().length() < MaxTextFieldSize){
										UserName.setText(UserName.getText() + " ");
									}
									int UserRank = HTable.AddUser(new User(UserName.getText(), MainPlayer.getFinalScore()));
									if(UserRank == -1){
										Toolkit.getDefaultToolkit().beep();
										JOptionPane.showMessageDialog(this, "Too Low of a Score", "", JOptionPane.WARNING_MESSAGE);
									}else{
										HTable.WriteHighScoreFile("Data\\HighScores.bin");
									}
								}else{
									Toolkit.getDefaultToolkit().beep();
									JOptionPane.showMessageDialog(this, "Score was not saved", "", JOptionPane.WARNING_MESSAGE);
								}
							}
							//System.exit(0);
							ReturnMainMenu = true;
						}
					}
					ReturnValue  = false;
					break;
				}
			}else{
				ReturnValue  = false;
				break;
			}
		}
		}
		return ReturnValue;
	}
	/**
	 * Main Content where the whole game takes place
	 */
	public MainContentPanel() {
		//Gives the Illusion of full map by setting background to filler color
		this.setBackground(new Color(25, 23, 22));
		this.setLayout(null);
		SAnimation = new ScreenAnimations();
		//Reset MainGraphicList
		MainGraphicList = new ArrayList <Graphic>();
		//Make Level List
		LevelList = new ArrayList <GenerateLevel>();
		//Generate a new level
		GenerateLevel Level1 = new GenerateLevel();
		//Add Level to Level List
		LevelList.add(Level1);
		//Get elements from new level and add to panel
		MainGraphicList.addAll(Level1.getLevelGraphicListController(null, true));
		//Make MainCamera
		MainCam = new ViewPort(0,0,500,500,0,0,Level1.getLevelMap().getMapTileSizeX() * 32,Level1.getLevelMap().getMapTileSizeY() * 32, this);
		//Loop to find instance of player
		for(int i = 0; i < MainGraphicList.size(); i++){
			if(MainGraphicList.get(i) instanceof Player){
				//Set Main Player
				MainPlayer = (Player) MainGraphicList.get(i);
				MainGraphicList.add(MainPlayer.getLeftMovement());
				MainGraphicList.add(MainPlayer.getRightMovement());
				MainGraphicList.add(MainPlayer.getUpMovement());
				MainGraphicList.add(MainPlayer.getDownMovement());
				//Set camera location with player in center
				MainCam.getView().setLocation((int)MainGraphicList.get(i).getX() - (int)MainCam.getView().getWidth()/2 + (int)MainGraphicList.get(i).getCollisionBox().getWidth()/2
						, (int)MainGraphicList.get(i).getY()- (int)MainCam.getView().getHeight()/2 + (int)MainGraphicList.get(i).getCollisionBox().getHeight()/2);
			}
		}
		//Set Generating Level = false
		this.GeneratingLevel = false;
		
		//Maybe have an animation timer?
		@SuppressWarnings("rawtypes")
		//Timer for the Animations
		SwingWorker worker = new SwingWorker() {
		    @Override
		    public Object doInBackground() {
		    	ActionListener GUILoop = new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						//Cycle all animated graphics to next frame if animations enabled
						if(Animations == true){
							for(int i = 0; i < MainGraphicList.size(); i++){
								if(MainGraphicList.get(i) instanceof AnimatedGraphic){
									if(((AnimatedGraphic) MainGraphicList.get(i)).getFrameRate() != 0 && ((AnimatedGraphic) MainGraphicList.get(i)).isPaused() == false){
										if(TotalFramesPast % ((AnimatedGraphic) MainGraphicList.get(i)).getFrameRate() == 0 && TotalFramesPast != 0){
											((AnimatedGraphic) MainGraphicList.get(i)).NextFrame();
											if(((AnimatedGraphic) MainGraphicList.get(i)).isLoopOnce() == true){
												if(((AnimatedGraphic) MainGraphicList.get(i)).isLoopDone() == true){
													((AnimatedGraphic) MainGraphicList.get(i)).setPaused(true);
												}
											}
										}
									}
								}
							}
							if(TotalFramesPast == 1000){
								TotalFramesPast = 0;
							}else{
								TotalFramesPast++;
							}
						}
					}
				};
				Timer AnimationTimer = new Timer(15, GUILoop);
				AnimationTimer.start();
				return null;
		    }
		};
		//start the thread
		worker.execute();
		
		Thread Lightingloop = null;
		Runnable LightRun = new Runnable(){
			@SuppressWarnings("static-access")
			@Override
			public void run() {
				boolean hi = true;
				while(hi == true){
					//Makes the Lighting Engine
					if(Lighting_Engine == true && GeneratingLevel == false){
						synchronized (MainGraphicList) {
							LightingEngine Light = new LightingEngine(MainGraphicList);	
						}
					}
					try {
						Lightingloop.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		Lightingloop = new Thread(LightRun, "LightingEngine");
		Lightingloop.start();
		
		
		/**
		 * Key Listener Methods
		 */
		this.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				//Don't move if animation playing
				if(SAnimation.isLoadingAnimationOut() == true || SAnimation.isLoadingAnimationIn() == true){
					return;
				}
				Random NumberGen = new Random();
				int Chance = NumberGen.nextInt(100);
				int Speed = 8;
				boolean Moved = false;
				//make sure player can move
				if(PlayerMovementDelay == 0){
						if(e.getKeyCode() == KeyEvent.VK_LEFT){
							//Checks if next point is vaild
							if(NextPointVal(MainPlayer, MainPlayer.getX() - Speed, MainPlayer.getY())){
								//If is move player at speed given
								MainPlayer.setX(MainPlayer.getX() - Speed);
								//Check if player hit a piece of clutter
								ClutterHandle(MainPlayer);
								Moved = true;
								if(MainPlayer.isWalkAnimationPlaying() == false){
									MainPlayer.getLeftMovement().setPaused(false);
									MainPlayer.getLeftMovement().setLoopOnce(false);
									MainPlayer.setWalkAnimationPlaying(true);
								}
							}
						}
						if(e.getKeyCode() == KeyEvent.VK_RIGHT){
							if(NextPointVal(MainPlayer, MainPlayer.getX() + Speed, MainPlayer.getY())){
								MainPlayer.setX(MainPlayer.getX() + Speed);
								ClutterHandle(MainPlayer);
								Moved = true;
								if(MainPlayer.isWalkAnimationPlaying() == false){
									MainPlayer.getRightMovement().setPaused(false);
									MainPlayer.getRightMovement().setLoopOnce(false);
									MainPlayer.setWalkAnimationPlaying(true);
								}
							}
						}
						if(e.getKeyCode() == KeyEvent.VK_DOWN){
							if(NextPointVal(MainPlayer, MainPlayer.getX(), MainPlayer.getY()+ Speed)){
								MainPlayer.setY(MainPlayer.getY() + Speed);
								ClutterHandle(MainPlayer);
								Moved = true;
								if(MainPlayer.isWalkAnimationPlaying() == false){
									MainPlayer.getDownMovement().setPaused(false);
									MainPlayer.getDownMovement().setLoopOnce(false);
									MainPlayer.setWalkAnimationPlaying(true);
								}
							}
						}
						if(e.getKeyCode() == KeyEvent.VK_UP){
							if(NextPointVal(MainPlayer, MainPlayer.getX(), MainPlayer.getY()- Speed)){
								MainPlayer.setY(MainPlayer.getY() - Speed);
								ClutterHandle(MainPlayer);
								Moved = true;
								if(MainPlayer.isWalkAnimationPlaying() == false){
									MainPlayer.getUpMovement().setPaused(false);
									MainPlayer.getUpMovement().setLoopOnce(false);
									MainPlayer.setWalkAnimationPlaying(true);
								}
							}
						}
						//Player must move for the camera to get updated
						if(Moved == true){
							//used for player movement delay
							PlayerMoved = true;
							//Chance to GenerateBattle
							if(Chance == 0){
								MainPlayer.getLeftMovement().setLoopOnce(true);
								MainPlayer.getRightMovement().setLoopOnce(true);
								MainPlayer.getUpMovement().setLoopOnce(true);
								MainPlayer.getDownMovement().setLoopOnce(true);
								
								PlayerInCombat = true;
								SAnimation.setLoadingAnimationOut(true);
								setMainCombatPanel(new CombatContent(MainPlayer, CurrentLevel));
							}
							MainPlayer.setPlayerX(MainPlayer.getX());
							MainPlayer.setPlayerY(MainPlayer.getY());
							MainCam.getView().setLocation((int)MainPlayer.getX() - (int)MainCam.getView().getWidth()/2 + (int)MainPlayer.getCollisionBox().getWidth()/2
								, (int)MainPlayer.getY()- (int)MainCam.getView().getHeight()/2 + (int)MainPlayer.getCollisionBox().getHeight()/2);
						}
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == KeyEvent.VK_LEFT){
					MainPlayer.getLeftMovement().setLoopOnce(true);
				}
				if(e.getKeyCode() == KeyEvent.VK_RIGHT){
					MainPlayer.getRightMovement().setLoopOnce(true);
				}
				if(e.getKeyCode() == KeyEvent.VK_UP){
					MainPlayer.getUpMovement().setLoopOnce(true);
				}
				if(e.getKeyCode() == KeyEvent.VK_DOWN){
					MainPlayer.getDownMovement().setLoopOnce(true);
				}
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
					EscapeKeyPressed = true;
				}
				//If enter key is pressed make a new level on a new thread if you are not generating one
				if(e.getKeyCode() == KeyEvent.VK_ENTER && GeneratingLevel == false){
					//increase the current level
			    	CurrentLevel++;
					//Set GeneratingLevel to true
			    	GeneratingLevel = true;
					SwingWorker<ArrayList<Graphic>, Void> worker = new SwingWorker<ArrayList <Graphic>, Void>() {
						
					    @Override
					    public ArrayList <Graphic> doInBackground() {
					    	//Level-score added to arrayList, currentScore set to 0, currentLevel incremented, stats increased
						    MainPlayer.levelTraversed();
					    	//check if we should generate a new level
					    	if(CurrentLevel > LevelList.size() - 1){
					    	//Generate a new level
							GenerateLevel Level1 = new GenerateLevel();
							//add the level to main level list
							LevelList.add(Level1);
							//set the previous levels player x and y for when going up floors
							LevelList.get(CurrentLevel - 1).setPlayerExitX(MainPlayer.getX());
							LevelList.get(CurrentLevel - 1).setPlayerExitY(MainPlayer.getY());
							//save the new levels graphics into a temp array
							ArrayList <Graphic> Temp = Level1.getLevelGraphicListController(MainPlayer, true);
							//delete levels graphics so it does not take room in memory
							Level1.setLevelGraphicList(null);
							//return the temp array
							return Temp;
					    	}else{
					    		//return level in the list if currentlevel is in the list
								//set the previous levels player x and y for when going up floors
								LevelList.get(CurrentLevel - 1).setPlayerExitX(MainPlayer.getX());
								LevelList.get(CurrentLevel - 1).setPlayerExitY(MainPlayer.getY());
								//old levels graphics into temp array
					    		ArrayList <Graphic> Temp = LevelList.get(CurrentLevel).getLevelGraphicListController(MainPlayer, false);
					    		//delete levels graphics so it does not take room in memory
					    		LevelList.get(CurrentLevel).setLevelGraphicList(null);
					    		//return the temp array
					    		return Temp;
					    	}
					    }

					    @Override
					    public void done() {
							try {
								synchronized(MainGraphicList){
									MainGraphicList = (ArrayList<Graphic>) this.get();
								}
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ExecutionException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//loop to find player and set main character
							for(int i = 0; i < MainGraphicList.size(); i++){
								if(MainGraphicList.get(i) instanceof Player){
									//Set Main Player
									MainPlayer = (Player) MainGraphicList.get(i);
									MainGraphicList.add(MainPlayer.getLeftMovement());
									MainGraphicList.add(MainPlayer.getRightMovement());
									MainGraphicList.add(MainPlayer.getUpMovement());
									MainGraphicList.add(MainPlayer.getDownMovement());
									MainCam.getView().setLocation((int)MainGraphicList.get(i).getX() - (int)MainCam.getView().getWidth()/2 + (int)MainGraphicList.get(i).getCollisionBox().getWidth()/2
									, (int)MainGraphicList.get(i).getY()- (int)MainCam.getView().getHeight()/2 + (int)MainGraphicList.get(i).getCollisionBox().getHeight()/2);
								}
							}
							//Set GeneratingLevel to false
					    	GeneratingLevel = false;
					    }
					};
					//start the thread
					worker.execute();
				}
				//If Back space key is pressed go to previous level
				if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
					//makes sure you are not on the first level
					if(CurrentLevel != 0){
						//set current level player x and y for when going down a level
						LevelList.get(CurrentLevel).setPlayerExitX(MainPlayer.getX());
						LevelList.get(CurrentLevel).setPlayerExitY(MainPlayer.getY());
						//Save perivous levels graphics into main arraylist making it the current level, while removing the old contents
						MainGraphicList = LevelList.get(CurrentLevel - 1).getLevelGraphicListController(MainPlayer, false);
						//delete levels graphics so it does not take room in memory
						LevelList.get(CurrentLevel - 1).setLevelGraphicList(null);
						//sets level to the old level
						CurrentLevel--;
						//reset the camera and set mainplayer
						for(int i = 0; i < MainGraphicList.size(); i++){
							if(MainGraphicList.get(i) instanceof Player){
								MainPlayer = (Player) MainGraphicList.get(i);
								MainGraphicList.add(MainPlayer.getLeftMovement());
								MainGraphicList.add(MainPlayer.getRightMovement());
								MainGraphicList.add(MainPlayer.getUpMovement());
								MainGraphicList.add(MainPlayer.getDownMovement());
								MainCam.getView().setLocation((int)MainGraphicList.get(i).getX() - (int)MainCam.getView().getWidth()/2 + (int)MainGraphicList.get(i).getCollisionBox().getWidth()/2
								, (int)MainGraphicList.get(i).getY()- (int)MainCam.getView().getHeight()/2 + (int)MainGraphicList.get(i).getCollisionBox().getHeight()/2);
							}
						}
						
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_A){
					if(MainInv.Invadd(MainItemPool.randomItem()) == true){
						System.out.println("Item added");
					}else{
						System.out.println("Item Not Added");
					}
					MainInv.repaint();
				}
				if(e.getKeyCode() == KeyEvent.VK_I){
					if(MainInv.isVisible() == false){
						MainInv.setVisible(true);
					}else{
						MainInv.setVisible(false);
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_E){
					if(MainEquip.isVisible() == false){
						MainEquip.setVisible(true);
					}else{
						MainEquip.setVisible(false);
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_H){
					Highscore HTable = new Highscore();
					HTable.ReadHighScoreFile("Data\\HighScores.bin");
					String TextA = "--- HighScore Table ---\n";
					for(int i = 0; i < HTable.getUserList().size(); i++){
						TextA += HTable.getUserList().get(i).getRank() + ") ";
						TextA += HTable.getUserList().get(i).getName() + " ";
						TextA += HTable.getUserList().get(i).getScore()+ "\n";
					}
					JTextArea HighScoreArea = new JTextArea(TextA);
					HighScoreArea.setEditable(false);
					final JComponent[] ScoreTable = new JComponent[] {
							HighScoreArea
					};
					JOptionPane.showMessageDialog(null, ScoreTable, "", JOptionPane.PLAIN_MESSAGE);
				}
				if(e.getKeyCode() == KeyEvent.VK_L){
					if(SAnimation.isLoadingAnimationOut() == false){
						//LoadingAnimationOut = true;
						SAnimation.setLoadingAnimationOut(true);
					}else{
						//LoadingAnimationOut = false;
					}
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {}	
		});
		/**
		 * Checks for window resize event, and changes the viewport
		 */
		this.addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent e) {
			//Resizes the camera
			RefreshViewPort();
           }
		});
		
	}
	public void RefreshViewPort(){
		//Get the panel width and height, used for when window is resized
		int PanelWidth = this.getWidth();
		int PanelHeight = this.getHeight();
		if(PanelWidth <= 0 && PanelHeight <= 0){
			return;
		}
		
       	Rectangle NewCam = new Rectangle((int)MainCam.getView().getX(), (int)MainCam.getView().getY(), PanelWidth, PanelHeight);
       	MainCam.setView(NewCam);
       	//Center camera on player
       	for(int i = 0; i < MainGraphicList.size(); i++){
			if(MainGraphicList.get(i) instanceof Player){
				MainCam.getView().setLocation((int)MainGraphicList.get(i).getX() - (int)MainCam.getView().getWidth()/2 + (int)MainGraphicList.get(i).getCollisionBox().getWidth()/2
				, (int)MainGraphicList.get(i).getY()- (int)MainCam.getView().getHeight()/2 + (int)MainGraphicList.get(i).getCollisionBox().getHeight()/2);
			}
		}
       	
	}
	public int getCurrentLevel(){
		return CurrentLevel;
	}
	public void setLighting_Engine(boolean Lighting_Engine){
		this.Lighting_Engine = Lighting_Engine;
	}
	public void setAnimations(boolean Animations){
		this.Animations = Animations;
	}
	public Equipment getMainEquip() {
		return MainEquip;
	}
	public void setMainEquip(Equipment mainEquip) {
		MainEquip = mainEquip;
	}
	public Inventory getMainInv() {
		return MainInv;
	}
	public void setMainInv(Inventory mainInv) {
		MainInv = mainInv;
	}
	public Chests getCurrentChest() {
		return CurrentChest;
	}
	public void setCurrentChest(Chests currentChest) {
		CurrentChest = currentChest;
	}
	public boolean isPlayerInCombat() {
		return PlayerInCombat;
	}
	public Player getMainPlayer(){
		return MainPlayer;
	}
	public void setPlayerInCombat(boolean playerInCombat) {
		PlayerInCombat = playerInCombat;
	}
	public CombatContent getMainCombatPanel() {
		return MainCombatPanel;
	}
	public void setMainCombatPanel(CombatContent mainCombatPanel) {
		MainCombatPanel = mainCombatPanel;
	}
	public int getDificulty() {
		return Dificulty;
	}
	public void setDificulty(int dificulty) {
		Dificulty = dificulty;
		//Set player difficulty
		MainPlayer.setDificulty(Dificulty);
//		MainPlayer.setInventory(MainInv);
//		MainPlayer.setInvPool(MainItemPool);
	}
	public boolean isEscapeKeyPressed() {
		return EscapeKeyPressed;
	}
	public void setEscapeKeyPressed(boolean escapeKeyPressed) {
		EscapeKeyPressed = escapeKeyPressed;
	}
	public boolean getLoadingAnimationOut(){
		return SAnimation.isLoadingAnimationOut();
	}
	public boolean isReturnMainMenu() {
		return ReturnMainMenu;
	}
	public void setReturnMainMenu(boolean returnMainMenu) {
		ReturnMainMenu = returnMainMenu;
	}
}
