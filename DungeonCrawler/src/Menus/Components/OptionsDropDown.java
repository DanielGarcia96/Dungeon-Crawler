package Menus.Components;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class OptionsDropDown extends JPanel{

	int FrameRate, Difficulty;
	
	public OptionsDropDown() {
		GridLayout Grid = new GridLayout(2,3);
		this.setLayout(Grid);
		this.setBackground(new Color(25, 23, 22));
		
		JLabel Label1 = new JLabel("Framerate:");
		Label1.setForeground(Color.LIGHT_GRAY);
		this.add(Label1);
		JLabel Label2 = new JLabel("Difficulty:");
		Label2.setForeground(Color.LIGHT_GRAY);
		this.add(Label2);
		JLabel Label3 = new JLabel("Framerate:");
		Label3.setForeground(Color.LIGHT_GRAY);
		this.add(Label3);
		
		String [] BoxOptions = {"30 Fps", "60 Fps", "120 Fps", "Unlimited"};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox FrameRateBoxHolder = new JComboBox(BoxOptions);
		FrameRateBoxHolder.setBackground(new Color(25, 23, 22));
		FrameRateBoxHolder.setForeground(Color.LIGHT_GRAY);
		FrameRateBoxHolder.setSelectedIndex(0);
		this.FrameRate = 1000/30;
		this.add(FrameRateBoxHolder);
		
		String [] BoxOptions2 = {"Baby", "Easy", "Medium", "Hard", "Impossible", "Death"};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox DifficultyHolder = new JComboBox(BoxOptions2);
		DifficultyHolder.setBackground(new Color(25, 23, 22));
		DifficultyHolder.setForeground(Color.LIGHT_GRAY);
		DifficultyHolder.setSelectedIndex(2);
		Difficulty = 2;
		this.add(DifficultyHolder);
		
		String [] BoxOptions3 = {"?", "??", "???"};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox BoxHolder2 = new JComboBox(BoxOptions3);
		BoxHolder2.setBackground(new Color(25, 23, 22));
		BoxHolder2.setForeground(Color.LIGHT_GRAY);
		this.add(BoxHolder2);
		
		FrameRateBoxHolder.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(FrameRateBoxHolder.getSelectedIndex() == 0){
					FrameRate = 1000/30;
				}else if(FrameRateBoxHolder.getSelectedIndex() == 1){
					FrameRate = 1000/60;
				}else if(FrameRateBoxHolder.getSelectedIndex() == 2){
					FrameRate = 1000/120;
				}else if(FrameRateBoxHolder.getSelectedIndex() == 3){
					FrameRate = -1;
				}
			}
		});
		DifficultyHolder.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
					Difficulty = DifficultyHolder.getSelectedIndex();
			}
		});
	}

	public int getFrameRate() {
		return FrameRate;
	}

	public int getDifficulty() {
		return Difficulty;
	}
}
