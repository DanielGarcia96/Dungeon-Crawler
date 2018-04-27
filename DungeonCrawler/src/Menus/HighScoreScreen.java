package Menus;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import HighScore.Highscore;
import MainGraphicPackage.AnimatedGraphic;
import Menus.Components.TitleOption;

@SuppressWarnings("serial")
public class HighScoreScreen extends JPanel{
	private AnimatedGraphic ButtonIcon, ButtonIcon2;
	private int TotalFrames;
	private TitleOption Back;
	private boolean BackButton;
	private boolean BackButtonHover;
	
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
	
	public HighScoreScreen(){
		this.setBackground(new Color(25, 23, 22));
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.ButtonIcon = new AnimatedGraphic("\\Images\\Clutter\\Torch", 0, 0, 5);
		this.ButtonIcon2 = new AnimatedGraphic("\\Images\\Clutter\\Torch", 0, 0, 5);
		
		Highscore HTable = new Highscore();
		HTable.ReadHighScoreFile("Data\\HighScores.bin");
		String TextA = "HighScore Table\n\n";
		for(int i = 0; i < HTable.getUserList().size(); i++){
			TextA += HTable.getUserList().get(i).getRank() + ") ";
			TextA += HTable.getUserList().get(i).getName() + " ";
			TextA += HTable.getUserList().get(i).getScore()+ "\n";
		}
		//JTextArea HighScoreArea = new JTextArea(TextA);
		JTextPane HighScoreArea = new JTextPane();
		HighScoreArea.setText(TextA);
		HighScoreArea.setFont(new Font("Courier", Font.BOLD,16));
		StyledDocument doc = HighScoreArea.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		HighScoreArea.setEditable(false);
		HighScoreArea.setBackground(new Color(25, 23, 22));
		HighScoreArea.setForeground(Color.LIGHT_GRAY);
		this.add(HighScoreArea);
		
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
}
