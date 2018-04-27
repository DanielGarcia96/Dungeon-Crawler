package Inventory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import character.Player;

@SuppressWarnings("serial")
public class Equipment extends JPanel{
	
	private Item helmet;
	private Item chest;
	//private Item legs;
	private Item boots;
	private Item weapon;
	private Item shield;
	private Inventory playerInv;
	private Item unequipedItem;
	private ArrayList<Item> equippedArray;
	private Player player;
	
	public ArrayList<Item> getEquipmentArray(){
		//Get list of current equipment
		equippedArray = new ArrayList<Item>();
		
		if(helmet != null)
			equippedArray.add(helmet);
		if(boots != null)
			equippedArray.add(boots);
		if(weapon != null)
			equippedArray.add(weapon);
		if(shield != null)
			equippedArray.add(shield);
		if(chest != null)
			equippedArray.add(chest);
		
		return equippedArray;
	}
	
	public Equipment()
	{
		this.setSize(300,300);
		this.setOpaque(false);
		JPopupMenu popUp = new JPopupMenu();
   	 	JMenuItem unequipItem= new JMenuItem("Unequip");
   	 	popUp.add(unequipItem);
   	 	JMenuItem tip = new JMenuItem("ToolTip");
	 	popUp.add(tip);
	 	JPanel parentComponent = this;
		this.addMouseListener(new MouseAdapter() {
		     @Override
		     public void mousePressed(MouseEvent e) {
		    	 if(SwingUtilities.isRightMouseButton(e))
		    	 {
		    		if(helmet != null && helmet.getGraphic().getCollisionBox().contains(e.getX(), e.getY()))
		    		{
		    			popUp.show(e.getComponent(), (int)e.getX(),(int)e.getY());
		    			unequipedItem = helmet;
		    		}
		         	if(chest != null && chest.getGraphic().getCollisionBox().contains(e.getX(), e.getY()))
		         	{
		         		popUp.show(e.getComponent(), (int)e.getX(),(int)e.getY());
		         		unequipedItem = chest;
		         	}
		    	 	/*if(legs != null && legs.getGraphic().getCollisionBox().contains(e.getX(), e.getY()))
		    	 	{
		    	 		popUp.show(e.getComponent(), (int)e.getX(),(int)e.getY());
		    	 		unequipedItem = legs;
		    	 	}*/
		    	 	if(boots != null && boots.getGraphic().getCollisionBox().contains(e.getX(), e.getY()))
		    	 	{
		    	 		popUp.show(e.getComponent(), (int)e.getX(),(int)e.getY());
		    	 		unequipedItem = boots;
		    	 	}
		    	 	if(weapon != null && weapon.getGraphic().getCollisionBox().contains(e.getX(), e.getY()))
		    	 	{
		    	 		popUp.show(e.getComponent(), (int)e.getX(),(int)e.getY());
		    	 		unequipedItem = weapon;
		    	 	}
		    	 	if(shield != null && shield.getGraphic().getCollisionBox().contains(e.getX(), e.getY()))
		    	 	{
		    	 		popUp.show(e.getComponent(), (int)e.getX(),(int)e.getY());
		    	 		unequipedItem = shield;
		    	 	}
		    	 }
		     }
		  });
 		unequipItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean tf = unequip(unequipedItem);
				if(!tf)
				{
					JOptionPane.showMessageDialog(parentComponent, "Could not add to inventory, Inventory is full");
				}
			}
	 	});
 		tip.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(parentComponent, unequipedItem.toString());
			}
 		});
	}
	
	public void setInventory(Inventory i)
	{
		playerInv = i;
	}
	
	public boolean addEquipment(Item i)
	{
		//TODO
		if(i.isHelmet()){
			if(helmet == null){
				helmet = i;
				helmet.getGraphic().setX(64);
				helmet.getGraphic().setY(0);
				player.setDefense(player.getDefense() + helmet.getItemDefense());
				player.setMaxDef(player.getMaxDef() + helmet.getItemDefense());
			}else{
				return false;
			}
		}
		if(i.isBoots()){
			if(boots == null){
				boots=i;
				boots.getGraphic().setX(64);
				boots.getGraphic().setY(64);
				player.setDefense(player.getDefense() + boots.getItemDefense());
				player.setMaxDef(player.getMaxDef() + boots.getItemDefense());
			}else{
				return false;
			}
		}
		if(i.isShield())
			if(shield == null){
				shield = i;
				shield.getGraphic().setX(32);
				shield.getGraphic().setY(32);
				player.setDefense(player.getDefense() + shield.getItemDefense());
				player.setMaxDef(player.getMaxDef() + shield.getItemDefense());
			}else{
				return false;
			}
		if(i.isChest())
			if(chest == null){
				chest=i;
				chest.getGraphic().setX(64);
				chest.getGraphic().setY(32);
				player.setDefense(player.getDefense() + chest.getItemDefense());
				player.setMaxDef(player.getMaxDef() + chest.getItemDefense());
			}else{
				return false;
			}
		/*if(i.isLegs())
			if(legs == null){
				legs= i;
				legs.getGraphic().setX(64);
				legs.getGraphic().setY(64);
			}else{
				return false;
			}*/
		if(i.isWeapon())
			if(weapon == null){
				weapon =i;
				weapon.getGraphic().setX(96);
				weapon.getGraphic().setY(32);
				player.setAttack(player.getAttack() + weapon.getItemOffense());
				player.setMaxAtt(player.getMaxAtt() + weapon.getItemOffense());
			}else{
				return false;
			}
		return true;
	}
	
	public boolean unequip(Item i)
	{
		//TODO add dialogue box stating when you cannot remove items
		if(playerInv.getInvItems().size() < playerInv.getInvSize())
		{
			Item temp = new Item();
			if(i.isHelmet()){
				temp = helmet;
				helmet = null;
				playerInv.Invadd(temp);
				player.setDefense(player.getDefense() - temp.getItemDefense());
				player.setMaxDef(player.getMaxDef() - temp.getItemDefense());
			}
			if(i.isBoots()){
				temp = boots;
				boots = null;
				playerInv.Invadd(temp);
				player.setDefense(player.getDefense() - temp.getItemDefense());
				player.setMaxDef(player.getMaxDef() - temp.getItemDefense());
			}
			if(i.isShield())
			{
				temp = shield;
				shield = null;
				playerInv.Invadd(temp);
				player.setDefense(player.getDefense() - temp.getItemDefense());
				player.setMaxDef(player.getMaxDef() - temp.getItemDefense());
			}
			if(i.isChest())
			{
				temp = chest;
				chest = null;
				playerInv.Invadd(temp);
				player.setDefense(player.getDefense() - temp.getItemDefense());
				player.setMaxDef(player.getMaxDef() - temp.getItemDefense());
			}
			/*if(i.isLegs())
			{
				temp = legs;
				legs = null;
				playerInv.Invadd(temp);
			}*/
			if(i.isWeapon())
			{
				temp = weapon;
				weapon = null;
				player.setAttack(player.getAttack() - temp.getItemOffense());
				player.setMaxAtt(player.getMaxAtt() - temp.getItemOffense());
				playerInv.Invadd(temp);
			}
			//Prints stats after unequip
			System.out.println("Health: "+ player.getHealth() + "\nAttack: " + player.getAttack() +
								 "\nDefense: "+ player.getDefense());
			this.repaint();
			return true;
		}
		return false;
	}
	
	public Item swapEquipment(Item i)
	{
			Item temp = new Item();
			if(i.isHelmet()){
				temp = helmet;
				helmet = i;
				helmet.getGraphic().setX(64);
				helmet.getGraphic().setY(0);
				player.setDefense(player.getDefense() + (helmet.getItemDefense() - temp.getItemDefense()));
				player.setMaxDef(player.getMaxDef() + (helmet.getItemDefense() - temp.getItemDefense()));
				return temp;
			}
			if(i.isBoots()){
				temp = boots;
				boots = i;
				boots.getGraphic().setX(64);
				boots.getGraphic().setY(96);
				player.setDefense(player.getDefense() + (boots.getItemDefense() - temp.getItemDefense()));
				player.setMaxDef(player.getMaxDef() + (boots.getItemDefense() - temp.getItemDefense()));
				return temp;
			}
			if(i.isShield())
			{	
				temp = shield;
				shield = i;
				shield.getGraphic().setX(32);
				shield.getGraphic().setY(32);
				player.setDefense(player.getDefense() + (shield.getItemDefense() - temp.getItemDefense()));
				player.setMaxDef(player.getMaxDef() + (shield.getItemDefense() - temp.getItemDefense()));
				return temp;
			}
			if(i.isChest())
			{	
				temp = chest;
				chest = i;
				chest.getGraphic().setX(64);
				chest.getGraphic().setY(32);
				player.setDefense(player.getDefense() + (chest.getItemDefense() - temp.getItemDefense()));
				player.setMaxDef(player.getMaxDef() + (chest.getItemDefense() - temp.getItemDefense()));
				return temp;
			}
			/*if(i.isLegs())
			{	
				temp = legs;
				legs = i;
				legs.getGraphic().setX(64);
				legs.getGraphic().setY(64);
				return temp;
			}*/
			if(i.isWeapon())
			{	
				temp = weapon;
				weapon = i;
				weapon.getGraphic().setX(96);
				weapon.getGraphic().setY(32);
				player.setAttack(player.getAttack() + (weapon.getItemOffense() - temp.getItemOffense()));
				player.setMaxAtt(player.getMaxAtt() + (weapon.getItemOffense() - temp.getItemOffense()));
				return temp;
			}
		this.repaint();
		//should never hit this
		return null;
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.drawRect(64,0,32,32);
		if(helmet != null)
			g.drawImage(helmet.getGraphic().getMainImage(),64,0,null);
		g.drawRect(64, 32, 32, 32);
		if(chest!=null)
			g.drawImage(chest.getGraphic().getMainImage(), 64, 32, null);
		g.drawRect(32, 32, 32, 32);
		if(shield!=null)
			g.drawImage(shield.getGraphic().getMainImage(), 32, 32, null);
		g.drawRect(96, 32, 32, 32);
		if(weapon!=null)
			g.drawImage(weapon.getGraphic().getMainImage(), 96,32, null);
		g.drawRect(64,64,32,32);
		/*if(legs!=null)
			g.drawImage(legs.getGraphic().getMainImage(), 64, 64, null);*/
		//g.drawRect(64, 96, 32, 32);
		if(boots!=null)
			g.drawImage(boots.getGraphic().getMainImage(), 64, 64, null);
	}
	
	public int getDefenseSum()
	{
		int sum = 0;
		if(helmet!=null)
		sum += helmet.getItemDefense();
		if(chest!=null)
		sum += chest.getItemDefense();
		//if(legs!=null)
		//sum += legs.getItemDefense();
		if(boots!=null)
		sum += boots.getItemDefense();
		if(shield!=null)
		sum += shield.getItemDefense();
		return sum;
	}

	public void setPlayer(Player mainPlayer) {
		this.player = mainPlayer;
	}
}

