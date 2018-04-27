package Inventory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;

import character.Player;


@SuppressWarnings("serial")
public class Inventory extends JPanel {

	private Rectangle [][] tiles;
	private ArrayList<Item> items;
	private Item toolTipItem;
	private int MaxInventorySize;
	private Equipment MyEquipment;
	private int itemIndex;
	private ItemPool ip;
	private Player mainPlayer;
	
	public Inventory()
	{
		ip = new ItemPool();
		tiles = new Rectangle [6][6];
		setItems(new ArrayList<Item>());
		this.setSize(32*6,32*6);
		MaxInventorySize = 36;
		JPopupMenu popUp = new JPopupMenu();
   	 	JMenuItem deleteItem= new JMenuItem("Delete");
   	 	popUp.add(deleteItem);
	 	JMenuItem equipItem= new JMenuItem("Equip");
	 	popUp.add(equipItem);
	 	JMenuItem tip = new JMenuItem("Tool Tip");
	 	popUp.add(tip);
	 	JPanel parentComponent = this;
	 	JMenuItem potionMenu = new JMenuItem("Use Potion");
	 	JMenuItem stackCount = new JMenuItem("Stack Count");
	 	this.populateInventory();
		 this.addMouseListener(new MouseAdapter() {
		     @Override
		     public void mousePressed(MouseEvent e) {
		    	 if(SwingUtilities.isRightMouseButton(e))
		    	 {
		    		 //loop through the items n the inventory to check if an item is clicked on by the mouse
		    		 for(int i =0; i < getInvItems().size(); i++){
		    		 	//check for collision
		    		 	if(getInvItems().get(i).getGraphic().getCollisionBox().contains(e.getX(), e.getY())){
		    			 	//handle the item being pressed
		    		 		toolTipItem = getInvItems().get(i);
		    		 		if(toolTipItem.isPotion())
		    		 		{
		    		 			popUp.add(stackCount);
		    		 			popUp.add(potionMenu);
		    		 			popUp.remove(equipItem);
		    		 		}
		    		 		else{ popUp.remove(stackCount);popUp.remove(potionMenu); popUp.add(equipItem); }
		    		 		popUp.show(e.getComponent(), (int)e.getX(), (int)e.getY());
		    		 		itemIndex = i;
		    		 	}
		    	 	}
		    	 }
		     }
		  });
	 		deleteItem.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					removeFromInventory(itemIndex);
				}
		 	});
	 		equipItem.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					addFromInventory(itemIndex);
				}
		 	});
	 		tip.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(parentComponent, toolTipItem.toString(), "",JOptionPane.INFORMATION_MESSAGE);
				}
	 		});
	 		potionMenu.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					addFromInventory(itemIndex);
				}
	 		});
	 		stackCount.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(parentComponent,"Stack count:: " + toolTipItem.getStackCount(),"",JOptionPane.INFORMATION_MESSAGE);
				}
	 			
	 		});
	}
	
	private void populateInventory() {
		//TODO: give bad items
		Invadd(ip.specificItem(10));
		Invadd(ip.specificItem(10));
		Invadd(ip.specificItem(10));
		Invadd(ip.specificItem(34));
		Invadd(ip.specificItem(12));
		Invadd(ip.specificItem(22));
		Invadd(ip.specificItem(27));
	}

	public void setEquipment(Equipment e)
	{
		MyEquipment = e;
	}
	
    public boolean Invadd(Item item){
    	int didLoop =0;
    	int didif =0;
    	if(item.isStackable())
    	{
    		for(int i=0; i < getInvItems().size();i++)
    		{
    			didLoop = 1;
    			if(item.getName().equals(getInvItems().get(i).getName()))
    			{
    				didif =1;
    				getInvItems().get(i).setStackCount(getInvItems().get(i).getStackCount()+1);
    				return true;
    			}
    		}
    		if(didLoop ==0 || didif==0)
    		{
    			getInvItems().add(item);
    			this.add(getInvItems().get(getInvItems().size()-1));
    		}
    	}
    	else{
    		if( getInvItems().size() < MaxInventorySize){
    		getInvItems().add(item);
    		this.add(getInvItems().get(getInvItems().size()-1));
    		return true;
    		}
    	}
    	return false;
	}
    
	public void addFromInventory(int index){
		Item temp = new Item();
		if(getInvItems().get(index).getEquipable() == false){
			if(getInvItems().get(index).isStackable())
			{
				if(getInvItems().get(index).getStackCount()>1)
				{
					Potions p = (Potions) getInvItems().get(index);
					mainPlayer.setHealth(p.usePotion() + mainPlayer.getHealth());
					getInvItems().get(index).setStackCount(getInvItems().get(index).getStackCount() - 1);				
				}
				else{
					Potions p = (Potions) getInvItems().get(index);
					mainPlayer.setHealth(p.usePotion() + mainPlayer.getHealth());
					removeFromInventory(index);
				}
			}
			return;
		}
		if(MyEquipment.addEquipment((Item) getInvItems().get(index)) == true){
			getInvItems().remove(index);
		}else{
			//swap 
			temp = MyEquipment.swapEquipment((Item) getInvItems().get(index));
			removeFromInventory(index);
			this.Invadd(temp);
		}
		//Prints stats after potion use, item equip, or swap
		System.out.println("Health: "+ mainPlayer.getHealth() + "\nAttack: " + mainPlayer.getAttack() +
							 "\nDefense: "+ mainPlayer.getDefense());
		this.repaint();
		MyEquipment.repaint();
	}
	
	public Item removeFromInventory(int index)
	{
		if(getInvItems().get(index).isStackable() && getInvItems().get(index).getStackCount() > 1)
		{
			getInvItems().get(index).setStackCount(getInvItems().get(index).getStackCount() - 1);
		}
		Item temp = new Item();
		temp = getInvItems().get(index);
		getInvItems().remove(index);
		this.repaint();
		return temp;
	}
	
	public void deleteItem(int index)
	{
		getInvItems().remove(index);
	}
	
	public void paintComponent(Graphics g)
	{
		int f=0;
		super.paintComponent(g);
		for(int i=0;i<tiles.length;i++)
		{
			for(int h=0;h<tiles[i].length;h++)
			{
				g.setColor(Color.BLACK);
				g.drawRect((32*i),32*h,32,32);
				if(f<getInvItems().size())
				{
					getInvItems().get(f).getGraphic().setX(h*32);
					getInvItems().get(f).getGraphic().setY(i*32);
					g.drawImage(((Item) getInvItems().get(f)).getGraphic().getMainImage(),h*32,i*32,null);
					if(getInvItems().get(f).isStackable())
					{
						String str = "" + getInvItems().get(f).getStackCount() + "";
						g.setFont(new Font("Courier", Font.BOLD,32));
						g.setColor(Color.BLACK);
						g.drawString(str, 0, 0);
					}
					f++;
				}
			}
		}
	}

	public ArrayList<Item> getInvItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	
	public int getInvSize()
	{
		return MaxInventorySize;
	}

	public void setPlayer(Player mainPlayer) {
		this.mainPlayer = mainPlayer;
		
	}
}
