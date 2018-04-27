package Menus.Components;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class SoundControl extends JPanel{

	public SoundControl() {
		// TODO Auto-generated constructor stub
		GridLayout Grid = new GridLayout(2,3);
		this.setLayout(Grid);
		this.setBackground(new Color(25, 23, 22));
		JLabel MasterVolumeLabel = new JLabel("Master Volume: 50");
		MasterVolumeLabel.setForeground(Color.LIGHT_GRAY);
		MasterVolumeLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(MasterVolumeLabel);
		JLabel MusicVolumeLabel = new JLabel("Music Volume: 50");
		MusicVolumeLabel.setForeground(Color.LIGHT_GRAY);
		MusicVolumeLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(MusicVolumeLabel);
		JLabel SoundEffectLabel = new JLabel("Sound Effect Volume: 50");
		SoundEffectLabel.setForeground(Color.LIGHT_GRAY);
		SoundEffectLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(SoundEffectLabel);
		JSlider MasterVolume = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
		SetSlider(MasterVolume);
		this.add(MasterVolume);
		JSlider MusicVolume = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
		SetSlider(MusicVolume);
		this.add(MusicVolume);
		JSlider SoundEffectVolume = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
		SetSlider(SoundEffectVolume);
		this.add(SoundEffectVolume);
		
		MasterVolume.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				MasterVolumeLabel.setText("Master Volume: " + MasterVolume.getValue());
			}
		});
		MusicVolume.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				MusicVolumeLabel.setText("Music Volume: " + MusicVolume.getValue());
			}
		});
		SoundEffectVolume.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				SoundEffectLabel.setText("Sound Effect Volume: " + SoundEffectVolume.getValue());
			}
		});
	}

	public void SetSlider(JSlider Volume){
		Volume.setBackground(null);
		Volume.setPaintTicks(true);
		Volume.setMaximumSize(getMaximumSize());
		Volume.setPaintLabels(true);
	}
}