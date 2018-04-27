package Menus.Components;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class OptionsToggles extends JPanel{

	private boolean AnimationsToggle, LightingEngineToggle, FPSCounterToggle;
	public OptionsToggles() {
		// TODO Auto-generated constructor stub
		GridLayout Grid = new GridLayout(1,3);
		this.setLayout(Grid);
		JCheckBox AnimationsToggleButton = new JCheckBox("Animations");
		AnimationsToggleButton.setBackground(new Color(25, 23, 22));
		AnimationsToggleButton.setForeground(Color.LIGHT_GRAY);
		AnimationsToggleButton.setSelected(true);
		AnimationsToggle = AnimationsToggleButton.isSelected();
		this.add(AnimationsToggleButton);
		JCheckBox LightingEngineToggleButton = new JCheckBox("Lighting Engine");
		LightingEngineToggleButton.setBackground(new Color(25, 23, 22));
		LightingEngineToggleButton.setForeground(Color.LIGHT_GRAY);
		LightingEngineToggleButton.setSelected(true);
		LightingEngineToggle = LightingEngineToggleButton.isSelected();
		this.add(LightingEngineToggleButton);
		JCheckBox FPSCounterButton = new JCheckBox("FPS Counter");
		FPSCounterButton.setBackground(new Color(25, 23, 22));
		FPSCounterButton.setForeground(Color.LIGHT_GRAY);
		FPSCounterButton.setSelected(false);
		this.FPSCounterToggle = false;
		//FPSCounterButton.setEnabled(false);
		this.add(FPSCounterButton);
		
		AnimationsToggleButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setAnimationsToggle(AnimationsToggleButton.isSelected());
			}
		});
		LightingEngineToggleButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setLightingEngineToggle(LightingEngineToggleButton.isSelected());
			}
		});
		FPSCounterButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setFPSCounterToggle(FPSCounterButton.isSelected());
			}
		});
	}
	public boolean isLightingEngineToggle() {
		return LightingEngineToggle;
	}
	public void setLightingEngineToggle(boolean lightingEngineToggle) {
		LightingEngineToggle = lightingEngineToggle;
	}
	public boolean isAnimationsToggle() {
		return AnimationsToggle;
	}
	public void setAnimationsToggle(boolean animationsToggle) {
		AnimationsToggle = animationsToggle;
	}
	public boolean isFPSCounterToggle() {
		return FPSCounterToggle;
	}
	public void setFPSCounterToggle(boolean fPSCounterToggle) {
		FPSCounterToggle = fPSCounterToggle;
	}

}