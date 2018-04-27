package Inventory;

public class Armor extends Item{

	private int defense;
	
	public Armor(String imageName, String n, String description, int num)
	{
		super(imageName, n, description, num);
		// TODO Auto-generated constructor stub
	}
	
	public Armor() {
		// TODO Auto-generated constructor stub
	}

	public void setDefense(int d)
	{
		defense = d;
	}
	
	public int getDefense()
	{
		return defense;
	}
	
	public String toString()
	{
		String s = this.getDescriptor() + this.name + ": " + this.desc + "\nDefense: " + this.getItemDefense();
		return s;
	}
}
