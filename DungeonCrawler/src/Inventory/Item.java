package Inventory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import MainGraphicPackage.Graphic;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class Item extends JComponent{
	
	private int rarity;
	private int id;
	protected String desc;
	private boolean equipable;
	protected String name;
	private Graphic item;
	private boolean helmet;
	private boolean armor;
	private boolean shield;
	private boolean chest;
	private boolean legs;
	private boolean boots;
	private boolean weapon;
	private String descriptor;
	private boolean p;
	private int itemDefense;
	private int itemOffense;
	private boolean stackable;
	private int stackCount;
	
	public Item(String imageName, String n, String description, int num)
	{
		stackCount = 1;
		item = new Graphic(imageName);
		itemDefense = 0;
		itemOffense = 0;
		name = n;
		desc = description;
		id = num;
		stackable = false;
	}

	public Item() {
		
	}
	
	public Graphic getGraphic()
	{
		return item;
	}
	
	public void setEquipable(boolean tf)
	{
		equipable = tf;
	}
	
	public boolean getEquipable()
	{
		return equipable;
	}

	public int getId()
	{
		return id;
	}
	
	public String getDescription()
	{
		return desc;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setRarity(int r)
	{
		rarity = r;
	}
	
	public int getRarity()
	{
		return rarity;
	}

	public boolean isWeapon() {
		return weapon;
	}

	public void setIsWeapon(boolean weapon) {
		this.weapon = weapon;
	}

	public boolean isBoots() {
		return boots;
	}

	public void setIsBoots(boolean boots) {
		this.boots = boots;
	}

	public boolean isLegs() {
		return legs;
	}

	public void setLegs(boolean legs) {
		this.legs = legs;
	}

	public boolean isChest() {
		return chest;
	}

	public void setChest(boolean chest) {
		this.chest = chest;
	}

	public boolean isShield() {
		return shield;
	}

	public void setShield(boolean shield) {
		this.shield = shield;
	}

	public boolean isHelmet() {
		return helmet;
	}

	public void setHelmet(boolean helmet) {
		this.helmet = helmet;
	}
	
	public String getDescriptor()
	{
		return descriptor;
	}
	
	public void setDescriptor(String s)
	{
		descriptor = s;
	}

	public boolean isArmor() {
		return armor;
	}

	public void setArmor(boolean armor) {
		this.armor = armor;
	}

	public boolean isPotion() {
		return p;
	}

	public void setPotion(boolean p) {
		this.p = p;
	}

	public int getItemDefense() {
		return itemDefense;
	}

	public void setItemDefense(int itemDefense) {
		this.itemDefense = itemDefense;
	}

	public int getItemOffense() {
		return itemOffense;
	}

	public void setItemOffense(int itemOffense) {
		this.itemOffense = itemOffense;
	}

	public boolean isStackable() {
		return stackable;
	}

	public void setStackable(boolean stackable) {
		this.stackable = stackable;
	}

	public int getStackCount() {
		return stackCount;
	}

	public void setStackCount(int stackCount) {
		this.stackCount = stackCount;
	}
}

