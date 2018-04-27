package Menus.Components;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class TitleOption extends JButton{

	private JButton MainComponent;
	private boolean ButtonHover, ButtonClicked;
	private String MainText;
	
	public TitleOption(String MainText) {
		// TODO Auto-generated constructor stub
		this.MainText = MainText;
		this.MainComponent = this;
		this.setText(MainText);
		this.setForeground(Color.LIGHT_GRAY);
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorder(null);
		this.addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				MainComponent.setForeground(Color.GRAY);
				ButtonHover = true;
			}
			
		});
		this.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				ButtonClicked = true;
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	public String getMainText() {
		return MainText;
	}
	public void setMainText(String mainText) {
		MainText = mainText;
	}
	public boolean isButtonHover() {
		return ButtonHover;
	}
	public void setButtonHover(boolean Hover) {
		ButtonHover = Hover;
	}
	public boolean isButtonClicked() {
		return ButtonClicked;
	}
	public void setButtonClicked(boolean buttonClicked) {
		ButtonClicked = buttonClicked;
	}

}
