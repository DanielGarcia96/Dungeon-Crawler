package Core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import Combat.CombatContent;
import Inventory.Chests;
import Inventory.Equipment;
import Inventory.Inventory;
import character.Player;

@SuppressWarnings("serial")
public class MainGameHolder extends JPanel{
	
	@SuppressWarnings("rawtypes")
	private SwingWorker worker;
	private Timer GUITimer;
	private boolean GameRunning;
	private Equipment MainEquip;
	private Inventory MainInv;
	private Chests CurrentChest = new Chests(MainInv);
	private MainContentPanel GamePanel;
	private CombatContent CombatPanel;
	private Player MainPlayer;
	
	public MainGameHolder() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		JPanel PlayerContainer = new JPanel();
		MainEquip = new Equipment();
		MainInv = new Inventory();
		PlayerContainer.setLayout(new BoxLayout(PlayerContainer, BoxLayout.Y_AXIS));
		PlayerContainer.setMaximumSize(new Dimension(192, 32*18));
		MainInv.setVisible(false);
		MainInv.setMinimumSize(new Dimension(32*6, 32*6));
		MainInv.setBackground(new Color(147,161,161));
		PlayerContainer.add(MainInv);
		CurrentChest.setVisible(false);
		PlayerContainer.add(CurrentChest);
		MainEquip.setOpaque(true);
		MainEquip.setVisible(false);
		MainEquip.setBackground(new Color(147,161,161));
		PlayerContainer.add(MainEquip);
		GamePanel = new MainContentPanel();
		CombatPanel = new CombatContent(new Player("Images/Characters/Player_Down/PlayerDown_1.png",0,0), 0);
		PlayerContainer.setVisible(false);
		this.add(PlayerContainer);
		this.add(GamePanel);
		
		MainEquip.setInventory(MainInv);
		MainInv.setEquipment(MainEquip);
		
		GamePanel.setMainInv(MainInv);
		GamePanel.setMainEquip(MainEquip);
		GamePanel.setCurrentChest(CurrentChest);
		
		GameRunning = true;
		JPanel This = this;
		worker = new SwingWorker() {
		    @Override
		    public Object doInBackground() {
					ActionListener GUILoop = new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent e) {
							MainInv = GamePanel.getMainInv();
							CurrentChest = GamePanel.getCurrentChest();
							MainEquip = GamePanel.getMainEquip();
							
							This.removeAll();
							PlayerContainer.removeAll();
							if(CurrentChest.isVisible() == true){
								PlayerContainer.add(MainInv);
								PlayerContainer.add(CurrentChest);
								PlayerContainer.add(MainEquip);
							}else{
								PlayerContainer.add(MainInv);
								PlayerContainer.add(MainEquip);
							}
							PlayerContainer.revalidate();
							PlayerContainer.repaint();
							if(MainInv.isVisible() == true || MainEquip.isVisible() == true){
								PlayerContainer.setVisible(true);
							}else{
								PlayerContainer.setVisible(false);
							}
							
							if(GamePanel.isPlayerInCombat() == true && GamePanel.getLoadingAnimationOut() == false){
								MainInv.setVisible(true);
								MainEquip.setVisible(true);
								This.add(PlayerContainer);
								CombatPanel = GamePanel.getMainCombatPanel();
								This.add(CombatPanel);
								if(CombatPanel.getCanEnd() == true && CombatPanel.getLoadingAnimationOut() == true){
									GamePanel.setPlayerInCombat(false);
								}
							}else{
								This.add(PlayerContainer);
								This.add(GamePanel);
								GamePanel.requestFocus(true);
							}
							This.revalidate();
							This.repaint();
							
							MainPlayer = GamePanel.getMainPlayer();
							MainInv.setPlayer(MainPlayer);
							MainEquip.setPlayer(MainPlayer);
							if(MainPlayer != null){
								if(CombatPanel.isDead() == true){
									JOptionPane.showMessageDialog(This, "Total Score: "+MainPlayer.getFinalScore(), "", JOptionPane.PLAIN_MESSAGE);
									GameRunning = false;
								}
						//		MainPlayer.updateStats(MainInv, MainEquip);
							}
							
							if(GamePanel.isEscapeKeyPressed() == true){
								This.requestFocus(true);
								GamePanel.setEscapeKeyPressed(false);
								int PickedOption = JOptionPane.showConfirmDialog(This, "Exit to Main Menu?", "", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
								if(PickedOption == JOptionPane.OK_OPTION){
									GameRunning = false;
								}
							}
							if(GamePanel.isReturnMainMenu() == true){
								This.requestFocus(true);
								GamePanel.setReturnMainMenu(false);
								GameRunning = false;
							}
						}
					};
					GUITimer = new Timer(100, GUILoop);
					GUITimer.start();
					return null;
				};
		};
		//start the thread
		worker.execute();
		
		JPanel Mp = this;
		this.addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent e) {
				PlayerContainer.setMaximumSize(new Dimension(192, Mp.getHeight()));
				PlayerContainer.setPreferredSize(new Dimension(192, Mp.getHeight()));
				Mp.revalidate();
				Mp.repaint();
           }
		});
		
	}
	
	public void GamePanelToggles(boolean AnimationsToggle, boolean LightingEngineToggle, int Dificulty){
		GamePanel.setAnimations(AnimationsToggle);
		GamePanel.setLighting_Engine(LightingEngineToggle);
		GamePanel.setDificulty(Dificulty);
	}

	public boolean isGameRunning() {
		return GameRunning;
	}
	public void StopHolderThread(){
		GUITimer.stop();
	}
	public void setGameRunning(boolean gameRunning) {
		GameRunning = gameRunning;
	}

}
