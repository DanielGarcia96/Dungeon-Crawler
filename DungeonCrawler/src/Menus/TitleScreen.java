package Menus;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import MainGraphicPackage.AnimatedGraphic;
import MainGraphicPackage.Graphic;
import Map.MapGenerator;
import Map.Tile.Tile;
import Menus.Components.TitleOption;

@SuppressWarnings("serial")
public class TitleScreen extends JPanel{

	private AnimatedGraphic ButtonIcon, ButtonIcon2;
	private int TotalFrames;
	private boolean StartButton, OptionsButton, HighscoreButton, ExitButton;
	private boolean StartButtonHover, OptionsButtonHover, HighscoreButtonHover, ExitButtonHover;
	private TitleOption Start, Options, Highscore, Exit;
	private ArrayList <Graphic> BackGroundGraphics;
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		StartButtonHover = Start.isButtonHover(); OptionsButtonHover = Options.isButtonHover(); ExitButtonHover = Exit.isButtonHover(); HighscoreButtonHover = Highscore.isButtonHover();
		StartButton = Start.isButtonClicked(); OptionsButton = Options.isButtonClicked(); ExitButton = Exit.isButtonClicked(); HighscoreButton = Highscore.isButtonClicked();
		
		Graphics2D g2d = (Graphics2D)g;
		//Horizontal Effect
		for(int i = 0; i < BackGroundGraphics.size(); i++){
			if(BackGroundGraphics.get(i) instanceof Tile){
				if(((Tile)BackGroundGraphics.get(i)).isVisited() == true){
					g2d.drawImage(BackGroundGraphics.get(i).getMainImage(), BackGroundGraphics.get(i).getX(), BackGroundGraphics.get(i).getY(), null);
				}else{
					((Tile)BackGroundGraphics.get(i)).setVisited(true);
					break;
				}
			}
		}
		if(StartButtonHover){
			ButtonIcon.setX(Start.getX() - 32);
			ButtonIcon.setY(Start.getY() + Start.getHeight()/8);
			ButtonIcon2.setX((int) (Start.getX() + Start.getSize().getWidth()));
			ButtonIcon2.setY(Start.getY() + Start.getHeight()/8);
		}
		if(OptionsButtonHover){
			ButtonIcon.setX(Options.getX() - 32);
			ButtonIcon.setY(Options.getY() + Options.getHeight()/8);
			ButtonIcon2.setX((int) (Options.getX() + Options.getSize().getWidth()));
			ButtonIcon2.setY(Options.getY() + Options.getHeight()/8);
		}
		if(HighscoreButtonHover){
			ButtonIcon.setX(Highscore.getX() - 32);
			ButtonIcon.setY(Highscore.getY() + Highscore.getHeight()/8);
			ButtonIcon2.setX((int) (Highscore.getX() + Highscore.getSize().getWidth()));
			ButtonIcon2.setY(Highscore.getY() + Highscore.getHeight()/8);
		}
		if(ExitButtonHover){
			ButtonIcon.setX(Exit.getX() - 32);
			ButtonIcon.setY(Exit.getY() + Exit.getHeight()/8);
			ButtonIcon2.setX((int) (Exit.getX() + Exit.getSize().getWidth()));
			ButtonIcon2.setY(Exit.getY() + Exit.getHeight()/8);
		}
		if(StartButtonHover || OptionsButtonHover || ExitButtonHover || HighscoreButtonHover){
			g2d.drawImage(ButtonIcon.getMainImage(), ButtonIcon.getX(), ButtonIcon.getY(), null);
			g2d.drawImage(ButtonIcon2.getMainImage(), ButtonIcon2.getX(), ButtonIcon2.getY(), null);
		}
		if(TotalFrames % ButtonIcon.getFrameRate() == 0){
			ButtonIcon.NextFrame();
		}
		if(TotalFrames % ButtonIcon2.getFrameRate() == 0){
			ButtonIcon2.NextFrame();
		}
		if(TotalFrames == 10000){
			TotalFrames = 0;
		}else{
			TotalFrames++;
		}
	}

	
	public TitleScreen() {
		// TODO Auto-generated constructor stub
		this.setBackground(new Color(25, 23, 22));
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.ButtonIcon = new AnimatedGraphic("/Images/Clutter/Torch", 0, 0, 5);
		this.ButtonIcon2 = new AnimatedGraphic("/Images/Clutter/Torch", 0, 0, 5);
		JLabel TitleBar = new JLabel("Cave Crawler");
		TitleBar.setForeground(new Color(200,200,200));
		Font CustomFont = null;
		try {
			CustomFont = Font.createFont(Font.TRUETYPE_FONT, new File("Font/JFRocSol.ttf"));
		} catch (FontFormatException | IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Font/JFRocSol.ttf")));
		} catch (FontFormatException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		CustomFont = CustomFont.deriveFont(Font.TRUETYPE_FONT, 48);
		TitleBar.setFont(CustomFont);
		//TitleBar.setFont(new Font("Courier", Font.BOLD,48));
		TitleBar.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(Box.createRigidArea(new Dimension(0,100)));
		this.add(TitleBar);
		Start = new TitleOption("Start Game");
		Start.setFont(new Font("Courier", Font.BOLD,32));
		this.add(Box.createRigidArea(new Dimension(0,15)));
		this.add(Start);
		this.add(Box.createRigidArea(new Dimension(0,5)));
		Highscore = new TitleOption("Highscores");
		Highscore.setFont(new Font("Courier", Font.BOLD,32));
		this.add(Highscore);
		this.add(Box.createRigidArea(new Dimension(0,5)));
		Options = new TitleOption("Options");
		Options.setFont(new Font("Courier", Font.BOLD,32));
		this.add(Options);
		this.add(Box.createRigidArea(new Dimension(0,5)));
		Exit = new TitleOption("Exit");
		Exit.setFont(new Font("Courier", Font.BOLD,32));
		this.add(Exit);
		
		MapGenerator Map = new MapGenerator(1920, 1080, 32, 15);
		BackGroundGraphics = Map.getMapGraphicMultiThreaded();

		this.addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				Start.setForeground(Color.LIGHT_GRAY);
				Options.setForeground(Color.LIGHT_GRAY);
				Highscore.setForeground(Color.LIGHT_GRAY);
				Exit.setForeground(Color.LIGHT_GRAY);
				Start.setButtonHover(false);
				Options.setButtonHover(false);
				Highscore.setButtonHover(false);
				Exit.setButtonHover(false);
			}
			
		});
	}
	public boolean isStartButton() {
		return StartButton;
	}

	public void setStartButton(boolean startButton) {
		Start.setButtonClicked(false);
		Start.setButtonHover(false);
		Start.setForeground(Color.LIGHT_GRAY);
		StartButton = startButton;
	}

	public boolean isOptionsButton() {
		return OptionsButton;
	}

	public void setOptionsButton(boolean optionsButton) {
		Options.setButtonClicked(false);
		Options.setButtonHover(false);
		Options.setForeground(Color.LIGHT_GRAY);
		OptionsButton = optionsButton;
	}

	public boolean isExitButton() {
		return ExitButton;
	}

	public void setExitButton(boolean exitButton) {
		Exit.setButtonClicked(false);
		Exit.setButtonClicked(false);
		Exit.setForeground(Color.LIGHT_GRAY);
		ExitButton = exitButton;
	}


	public boolean isHighscoreButton() {
		return HighscoreButton;
	}


	public void setHighscoreButton(boolean highscoreButton) {
		Highscore.setButtonClicked(false);
		Highscore.setButtonHover(false);
		Highscore.setForeground(Color.LIGHT_GRAY);
		HighscoreButton = highscoreButton;
	}

}
