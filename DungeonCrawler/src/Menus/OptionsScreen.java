package Menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import MainGraphicPackage.AnimatedGraphic;
import Menus.Components.OptionsDropDown;
import Menus.Components.OptionsToggles;
import Menus.Components.SoundControl;
import Menus.Components.TitleOption;

@SuppressWarnings("serial")
public class OptionsScreen extends JPanel{

	private AnimatedGraphic ButtonIcon, ButtonIcon2;
	private int TotalFrames;
	private boolean BackButton;
	private boolean BackButtonHover;
	private TitleOption Back;
	private SoundControl SP;
	private OptionsToggles OP;
	private OptionsDropDown ODD;
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		BackButton = Back.isButtonClicked();
		BackButtonHover = Back.isButtonHover();
		Graphics2D g2d = (Graphics2D)g;
		if(BackButtonHover){
			ButtonIcon.setX(Back.getX() - 32);
			ButtonIcon.setY(Back.getY() + Back.getHeight()/8);
			ButtonIcon2.setX((int) (Back.getX() + Back.getSize().getWidth()));
			ButtonIcon2.setY(Back.getY() + Back.getHeight()/8);
		}

		if(BackButtonHover){
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
	
	public OptionsScreen() {
		// TODO Auto-generated constructor stub
		this.setBackground(new Color(25, 23, 22));
		GridLayout Grid = new GridLayout(6,1);
		this.setLayout(Grid);
		this.ButtonIcon = new AnimatedGraphic("\\Images\\Clutter\\Torch", 0, 0, 5);
		this.ButtonIcon2 = new AnimatedGraphic("\\Images\\Clutter\\Torch", 0, 0, 5);
		
		SP = new SoundControl();
		this.add(SP);
		
		OP = new OptionsToggles();
		this.add(OP);
		
		ODD = new OptionsDropDown();
		this.add(ODD);
		Back = new TitleOption("Back");
		Back.setFont(new Font("Courier", Font.BOLD,32));
		this.add(Back);
		this.addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				Back.setForeground(Color.LIGHT_GRAY);
				Back.setButtonHover(false);
			}
			
		});
	}
	
	public boolean isBackButton() {
		return BackButton;
	}

	public void setBackButton(boolean backButton) {
		Back.setButtonClicked(false);
		Back.setButtonHover(false);
		Back.setForeground(Color.LIGHT_GRAY);
		BackButton = backButton;
	}

	public int getFrameRate() {
		return ODD.getFrameRate();
	}
	public int getDifficulty() {
		return ODD.getDifficulty();
	}
	public boolean isLightingEngineToggle() {
		return OP.isLightingEngineToggle();
	}
	public void setLightingEngineToggle(boolean lightingEngineToggle) {
		OP.setLightingEngineToggle(lightingEngineToggle);
	}
	public boolean isAnimationsToggle() {
		return OP.isAnimationsToggle();
	}
	public void setAnimationsToggle(boolean animationsToggle) {
		OP.setAnimationsToggle(animationsToggle);
	}
	public boolean isFPSCounterToggle() {
		return OP.isFPSCounterToggle();
	}
	public void setFPSCounterToggle(boolean fPSCounterToggle) {
		OP.setFPSCounterToggle(fPSCounterToggle);
	}

}