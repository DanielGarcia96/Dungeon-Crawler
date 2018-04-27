package Combat;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import character.Player;

@SuppressWarnings("serial")
public class CombatFrame extends JFrame {
	private CombatFrame m;
	private CombatContent n;

	public CombatFrame(Player player) {
		super("Battle!");
		m = this;
		/*
		player.setMaxHP(Character.BASE_HEALTH);
		player.setMaxDef(Character.BASE_DEFENSE);
		player.setMaxAtt(Character.BASE_ATTACK);
		Mob mob = new Mob("Images/Characters/goblin.png", 0, 1);
		mob.setHealth(Character.BASE_HEALTH);
		mob.setDefense(Character.BASE_DEFENSE);
		n = new CombatContent(player, mob);
		*/
		m.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)  {
				if(n.getCanEnd()){
					m.dispose();
				}
			}
		});

		m.add(n, BorderLayout.CENTER);

		m.setSize(500,500);
		m.setLocationRelativeTo(null);
		m.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		m.setVisible(true);
	}
	
	public CombatContent getContent()  {
		return n;
	}
}