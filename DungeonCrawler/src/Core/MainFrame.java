package Core;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import Menus.HighScoreScreen;
import Menus.OptionsScreen;
import Menus.TitleScreen;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{

	private int FrameCount, FPSSampleSize, UpdateTime;
	private long [] AverageFps;
	private Timer MainTimer, MenuTimer;
	private JPanel MainPanel;
	private MainGameHolder GameHolder;
	
	public MainFrame(String FrameName, int SizeX, int SizeY) {
		// TODO Auto-generated constructor stub
		this.setTitle(FrameName);
		this.setSize(SizeX, SizeY);
		CardLayout Cards = new CardLayout();
		MainPanel = new JPanel();
		MainPanel.setLayout(Cards);
		
		TitleScreen Title = new TitleScreen();
		MainPanel.add(Title, "Title");
		OptionsScreen Options = new OptionsScreen();
		GameHolder = new MainGameHolder();
		//MainPanel.add(GameHolder, "Game");
		//MainPanel.add(Main, "Game");
		MainPanel.add(Options, "Options");
		HighScoreScreen HighScores = new HighScoreScreen();
		MainPanel.add(HighScores, "HighScore");
		JFrame FpsFrame = new JFrame();
		FpsFrame.setSize(300, 300);
		FpsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel FPSText = new JLabel("FPS: ");
		FpsFrame.add(FPSText);
		//MainPanel.add(FPSText, BorderLayout.NORTH);
		this.add(MainPanel);
		//Background Music
		try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Sound\\background.wav").getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.loop(Clip.LOOP_CONTINUOUSLY);
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
		
		ActionListener MenuLoop = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(Title.isStartButton() == true){
					Title.setStartButton(false);
					GameHolder = new MainGameHolder();
					MainPanel.add(GameHolder, "Game");
					Cards.show(MainPanel, "Game");
					UpdateTime = Options.getFrameRate();
					
					GameHolder.GamePanelToggles(Options.isAnimationsToggle(), Options.isLightingEngineToggle(), Options.getDifficulty());
					if(Options.isFPSCounterToggle() == true){
						FpsFrame.setVisible(true);
					}
					if(UpdateTime == -1){
						FPSSampleSize = 250;
						AverageFps = new long [FPSSampleSize];
						FrameCount = 0;
					Thread Gameloop = null;
					Runnable GameRun = new Runnable(){
						@Override
						public void run() {
							boolean GameLoop = true;
							while(GameLoop == true){
								long Start = System.nanoTime();
								GameHolder.repaint();
								if(GameHolder.isGameRunning() == false){
									GameHolder.StopHolderThread();
									MainPanel.remove(GameHolder);
									MainPanel.revalidate();
									MainPanel.repaint();
									Cards.show(MainPanel, "Title");
									
									MenuTimer.start();
									GameLoop = false;
								}
								long End = System.nanoTime();
								if(FrameCount < FPSSampleSize && (End - Start) != 0){
									AverageFps[FrameCount] = 1000000/(End - Start);
								}else{
									int Average = 0;
									for(int i = 0; i < AverageFps.length; i++){
										Average += AverageFps[i];
									}
									Average = Average/FPSSampleSize;
									FPSText.setText("FPS: " + Average);
									//System.out.println("FPS: " + Average);
									FrameCount = 0;
								}
								FrameCount++;
							}
						}
					};
					Gameloop = new Thread(GameRun, "GameLoop");
					Gameloop.start();
					}else{
						FPSSampleSize = 30;
						AverageFps = new long [FPSSampleSize];
						FrameCount = 0;
						MainTimer.setDelay(UpdateTime);
						MainTimer.setInitialDelay(UpdateTime);
						MainTimer.start();
					}
					MenuTimer.stop();
				}
				if(Title.isOptionsButton() == true){
					Title.setOptionsButton(false);
					Cards.show(MainPanel, "Options");
				}
				if(Title.isHighscoreButton() == true){
					Title.setHighscoreButton(false);
					Cards.show(MainPanel, "HighScore");
				}
				if(Title.isExitButton() == true){
					System.exit(0);
				}
				if(Options.isBackButton() == true){
					Options.setBackButton(false);
					Cards.show(MainPanel, "Title");
				}
				if(HighScores.isBackButton() == true){
					HighScores.setBackButton(false);
					Cards.show(MainPanel, "Title");
				}
				repaint();
			}
		};
		MenuTimer = new Timer(10, MenuLoop);
		MenuTimer.start();
		
		ActionListener MainLoop = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				long Start = System.nanoTime();
				GameHolder.repaint();
				if(GameHolder.isGameRunning() == false){
					GameHolder.StopHolderThread();
					MainPanel.remove(GameHolder);
					MainPanel.revalidate();
					MainPanel.repaint();
					Cards.show(MainPanel, "Title");
					
					MenuTimer.start();
					MainTimer.stop();
				}
				long End = System.nanoTime();
				if(FrameCount < FPSSampleSize){
					AverageFps[FrameCount] = 1000000/(End - Start);
				}else{
					int Average = 0;
					for(int i = 0; i < AverageFps.length; i++){
						Average += AverageFps[i];
					}
					Average = Average/FPSSampleSize;
					//System.out.println("FPS: " + Average);
					FPSText.setText("FPS: " + Average);
					FrameCount = 0;
				}
				FrameCount++;
			}
		};
		MainTimer = new Timer(UpdateTime, MainLoop);
	}

	public static void main(String[] args){
		MainFrame Frame = new MainFrame("Cave Crawler", 750, 500);
		Frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Frame.setVisible(true);
	}
}
