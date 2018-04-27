package Inventory;

public class Potions extends Item{

	private int restoreAmount;
	public Potions(String imageName, String n, String description, int num) 
	{
		super(imageName, n, description, num);
		// TODO Auto-generated constructor stub
	}
	
	public void setRestoreAmount(int rs)
	{
		restoreAmount = rs;
	}
	
	public int getRestoreAmount()
	{
		return restoreAmount;
	}
	
	public String toString()
	{
		String s = this.name + ": " + this.desc + "\nHeals for: " + restoreAmount;
		return s;
	}
	
	public int usePotion()
	{
		return restoreAmount;
	}
}
