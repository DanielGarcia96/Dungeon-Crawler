package Inventory;

public class Offensive extends Item{

	private int damage;
	public Offensive(String imageName, String n, String description, int num) 
	{
		super(imageName, n, description, num);
		// TODO Auto-generated constructor stub
	}
	
	public Offensive() {
		// TODO Auto-generated constructor stub
	}

	public void setDamage(int d)
	{
		damage = d;
	}
	
	public int getDamage()
	{
		return damage;
	}
	
	public String toString()
	{
		String s = this.getDescriptor() + this.name + ": " + this.desc + "\nDamage: " + this.getItemOffense();
		return s;
	}

}

