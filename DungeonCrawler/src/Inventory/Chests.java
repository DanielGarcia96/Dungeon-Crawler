package Inventory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class Chests extends JPanel{
	
	private Rectangle [] tiles;
	private ArrayList<Item> items;
	private Inventory chestInv;
	private int MaxChestSize;
	private ItemPool ip;
	private int numItems;
	private int itemIndex;
	private Random r;
	public Chests(Inventory inv)
	{
		items = new ArrayList<Item>();
		this.setSize(32*6,32*6);
		this.setOpaque(false);
		MaxChestSize=36;
		ip = new ItemPool();
		r = new Random();
		numItems = r.nextInt(3)+1;
		tiles = new Rectangle[numItems+1];
		chestInv = inv;
		this.populateChest(numItems);
		JPopupMenu popUp = new JPopupMenu();
   	 	JMenuItem removeChest= new JMenuItem("Add to inventory");
		popUp.add(removeChest);
		this.addMouseListener(new MouseAdapter() {
		     @Override
		     public void mousePressed(MouseEvent e) {
			     if(SwingUtilities.isRightMouseButton(e))
			     {
			    	 popUp.show(e.getComponent(),(int)e.getX(),(int)e.getY());
		    	 //loop through the items in the inventory to check if an item is clicked on by the mouse
		    	 for(int i =0; i < items.size(); i++){
		    		 //check for collision
		    		 if(items.get(i).getGraphic().getCollisionBox().contains(e.getX(), e.getY())){
		    			 //handle the item being pressed
		    			 itemIndex = i;

		    		 }
		    	 }
			     }
		     }
		  });
		removeChest.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				addFromChest(itemIndex);
			}
			
		});
	}
	
	public void populateChest(int i)
	{
		for(int g=0;g<i;g++)
		{
			items.add(ip.randomItem());
		}
		//Guarantees a potion will be in the chest
		int result = r.nextInt(100)%3;
		if(result == 0)
			items.add(ip.specificItem(0));//Regular potion
		else if(result == 1)
			items.add(ip.specificItem(9));//Greater potion
		else
			items.add(ip.specificItem(10));//Lesser potion
	}
	
	public void addFromChest(int index)
	{
		Item temp = new Item();
		if(chestInv.Invadd((Item) items.get(index)) == true){
			items.remove(index);
		}else{
			return;
		}
		this.repaint();
		chestInv.repaint();
	}
	
	public Item removeFromChest(int index)
	{
		Item temp = new Item();
		temp = items.get(index);
		items.remove(index);
		this.repaint();
		return temp;
	}
	
	public void paintComponent(Graphics g)
	{
		int f=0;
		super.paintComponent(g);
		for(int h=0;h<tiles.length;h++)
		{
			g.drawRect((32*h),32,32,32);
			if(f<items.size())
			{
				items.get(f).getGraphic().setX(h*32);
				items.get(f).getGraphic().setY(32);
				g.drawImage(((Item) items.get(f)).getGraphic().getMainImage(),h*32,32,null);
				f++;
			}
		}
	}
}