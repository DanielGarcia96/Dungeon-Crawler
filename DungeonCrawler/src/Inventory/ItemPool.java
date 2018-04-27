package Inventory;

import java.util.ArrayList;
import java.util.Random;

public class ItemPool {

	private Random randomNumber=new Random();
	
	public ItemPool()
	{
		
	}
	
	public Item randomItem(){
		int Number = randomNumber.nextInt(38);
		if(Number==0){
			Potions potion1 = new Potions("Images\\Inventory\\Potions\\ruby.png","Health Potion", "A healing potion",00);
			potion1.setRestoreAmount(50);
			potion1.setEquipable(false);
			potion1.setPotion(true);
			potion1.setStackable(true);
			return potion1;
		}
		if(Number ==1){
			Offensive axe1 = new Offensive("Images\\Inventory\\Weapons\\executioner_axe1.png","Executioner's Axe", "Strong right-hand weapon", 01);
			axe1.setItemOffense(25);
			axe1.setEquipable(true);
			axe1.setIsWeapon(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(axe1,Number);
			axe1.setStackable(false);
			return axe1;
		}
		if(Number == 2)
		{
			Offensive axe2 = new Offensive("Images\\Inventory\\Weapons\\hand_axe1.png", "Small Axe", "A simple hand axe", 02);
			axe2.setItemOffense(10);
			axe2.setEquipable(true);
			axe2.setIsWeapon(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(axe2,Number);
			return axe2;
		}
		if(Number == 3)
		{
			Offensive staff1 = new Offensive("Images\\Inventory\\Weapons\\staff02.png","Wooden Staff", "Simple staff, smashing", 03);
			staff1.setItemOffense(5);
			staff1.setEquipable(true);
			staff1.setIsWeapon(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(staff1,Number);
			return staff1;
		}
		
		if(Number == 4)
		{
			Armor leatherchest1 = new Armor("Images\\Inventory\\Chests\\leather_armour3.png","Leather armor", "Durable, but not very protective",04);
			leatherchest1.setItemDefense(10);
			leatherchest1.setEquipable(true);
			leatherchest1.setChest(true);
			leatherchest1.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(leatherchest1,Number);
			return leatherchest1;
		}
		if(Number == 5)
		{
			Armor helmet1 = new Armor("Images\\Inventory\\Helmets\\wizard_hat2.png","Wizard Hat", "Does not offer much in the way of defense",05);
			helmet1.setItemDefense(2);
			helmet1.setEquipable(true);
			helmet1.setHelmet(true);
			helmet1.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(helmet1,Number);
			return helmet1;
		}
		if(Number == 6)
		{
			Armor helmet2 = new Armor("Images\\Inventory\\Helmets\\green_mask.png","Strange green mask","Strange mask from a strange place",06);
			helmet2.setItemDefense(5);
			helmet2.setEquipable(true);
			helmet2.setHelmet(true);
			helmet2.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(helmet2,Number);
			return helmet2;
		}
		if(Number==7)
		{
			Armor shield1 = new Armor("Images\\Inventory\\Shields\\shield2_kite.png","Large kite-shield","Greatly boosts the defense of the user",07);
			shield1.setItemDefense(10);
			shield1.setEquipable(true);
			shield1.setShield(true);
			shield1.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(shield1,Number);
			return shield1;
		}
		if(Number ==8)
		{
			Armor boot1 = new Armor("Images\\Inventory\\Boots\\boots1_brown.png","Brown leather boots","Protects the wearers feet from harm",8);
			boot1.setItemDefense(4);
			boot1.setEquipable(true);
			boot1.setIsBoots(true);
			boot1.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(boot1,Number);
			return boot1;
		}
		if(Number==9){
			Potions potion2 = new Potions("Images\\Inventory\\Potions\\magenta.png","Greater Healing Potion", "A potent healing potion",9);
			potion2.setRestoreAmount(75);
			potion2.setEquipable(false);
			potion2.setPotion(true);
			potion2.setStackable(true);
			return potion2;
		}
		if(Number==10){
			Potions potion3 = new Potions("Images\\Inventory\\Potions\\pink.png","Lesser Healing Potion", "A weak healing potion",10);
			potion3.setRestoreAmount(25);
			potion3.setEquipable(false);
			potion3.setPotion(true);
			potion3.setStackable(true);
			return potion3;
		}
		if(Number ==11)
		{
			Armor boot2 = new Armor("Images\\Inventory\\Boots\\boots2_jackboots.png","Brown Fur Boots","Keeps one's feet rather warm",11);
			boot2.setItemDefense(2);
			boot2.setEquipable(true);
			boot2.setIsBoots(true);
			boot2.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(boot2,Number);
			return boot2;
		}
		if(Number ==12)
		{
			Armor boot3 = new Armor("Images\\Inventory\\Boots\\boots3_stripe.png","Leather Sandals","Straps of leather woven together",12);
			boot3.setItemDefense(1);
			boot3.setEquipable(true);
			boot3.setIsBoots(true);
			boot3.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(boot3,Number);
			return boot3;
		}
		if(Number ==13)
		{
			Armor boot4 = new Armor("Images\\Inventory\\Boots\\boots4_green.png","Strange Green boots","Strange boots from a strange place",13);
			boot4.setItemDefense(5);
			boot4.setEquipable(true);
			boot4.setIsBoots(true);
			boot4.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(boot4,Number);
			return boot4;
		}
		if(Number == 14)
		{
			Armor cloak1 = new Armor("Images\\Inventory\\Chests\\cloak2.png","Silk Cloak", "Every traveler needs a nice cloak",14);
			cloak1.setItemDefense(2);
			cloak1.setEquipable(true);
			cloak1.setChest(true);
			cloak1.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(cloak1,Number);
			return cloak1;
		}
		if(Number == 15)
		{
			Armor chest3= new Armor("Images\\Inventory\\Chests\\leather_armour1.png","Hard Leather Chest Piece", "A standard chest piece worn by mercenaries",15);
			chest3.setItemDefense(8);
			chest3.setEquipable(true);
			chest3.setChest(true);
			chest3.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(chest3,Number);
			return chest3;
		}
		if(Number == 16)
		{
			Armor chest4= new Armor("Images\\Inventory\\Chests\\leather_armour2.png","Flimsy Red Chest Plate", "The dollar store version of a chest piece",15);
			chest4.setItemDefense(5);
			chest4.setEquipable(true);
			chest4.setChest(true);
			chest4.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(chest4,Number);
			return chest4;
		}
		if(Number == 17)
		{
			Armor chest5= new Armor("Images\\Inventory\\Chests\\plate_mail1.png","Plate Mail", "The armor of a true warrior",17);
			chest5.setItemDefense(15);
			chest5.setEquipable(true);
			chest5.setChest(true);
			chest5.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(chest5,Number);
			return chest5;
		}
		if(Number == 18)
		{
			Armor chest6= new Armor("Images\\Inventory\\Chests\\robe1.png","Blue Robe", "A thin wool robe",18);
			chest6.setItemDefense(2);
			chest6.setEquipable(true);
			chest6.setChest(true);
			chest6.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(chest6,Number);
			return chest6;
		}
		if(Number == 19)
		{
			Armor chest7 = new Armor("Images\\Inventory\\Chests\\troll_leather_armour.png","Strange Green Armor", "A strange chest piece from a strange place",19);
			chest7.setItemDefense(10);
			chest7.setEquipable(true);
			chest7.setChest(true);
			chest7.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(chest7,Number);
			return chest7;
		}
		if(Number == 20)
		{
			Armor chest8= new Armor("Images\\Inventory\\Chests\\scale_mail2.png","Chain Mail", "Protects the wearer from being stabbed to death",20);
			chest8.setItemDefense(5);
			chest8.setEquipable(true);
			chest8.setChest(true);
			chest8.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(chest8,Number);
			return chest8;
		}
		if(Number == 21)
		{
			Armor helmet3= new Armor("Images\\Inventory\\Helmets\\cap2.png","Red Hood", "Once belonged to a little girl", 21);
			helmet3.setItemDefense(2);
			helmet3.setEquipable(true);
			helmet3.setHelmet(true);
			helmet3.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(helmet3,Number);
			return helmet3;
		}
		if(Number == 22)
		{
			Armor helmet4= new Armor("Images\\Inventory\\Helmets\\elven_leather_helm.png","Bard's Hat", "This musician's hat won't help the adventurer much", 22);
			helmet4.setItemDefense(1);
			helmet4.setEquipable(true);
			helmet4.setHelmet(true);
			helmet4.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(helmet4,Number);
			return helmet4;
		}
		if(Number == 23)
		{
			Armor helmet5= new Armor("Images\\Inventory\\Helmets\\helmet1_visored.png","Old Knight's Helm", "Some long passed knight's helmet", 23);
			helmet5.setItemDefense(4);
			helmet5.setEquipable(true);
			helmet5.setHelmet(true);
			helmet5.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(helmet5,Number);
			return helmet5;
		}
		if(Number == 24)
		{
			Armor helmet6= new Armor("Images\\Inventory\\Helmets\\helmet2_etched.png","Modern Knight's Helm", "A strong helm", 24);
			helmet6.setItemDefense(6);
			helmet6.setEquipable(true);
			helmet6.setHelmet(true);
			helmet6.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(helmet6,Number);
			return helmet6;
		}
		if(Number == 25)
		{
			Armor helmet7= new Armor("Images\\Inventory\\Helmets\\helmet3.png","Elven Helmet", "An ancient elven helmet, beautifuly crafted", 25);
			helmet7.setItemDefense(8);
			helmet7.setEquipable(true);
			helmet7.setHelmet(true);
			helmet7.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(helmet7,Number);
			return helmet7;
		}
		if(Number == 26)
		{
			Armor helmet8= new Armor("Images\\Inventory\\Helmets\\helmet4_visor.png","Jouster's Helm", "This impressive looking helm offers little in actual protection", 26);
			helmet8.setItemDefense(3);
			helmet8.setEquipable(true);
			helmet8.setHelmet(true);
			helmet8.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(helmet8,Number);
			return helmet8;
		}
		if(Number == 27)
		{
			Armor shield2= new Armor("Images\\Inventory\\Shields\\buckler1.png","Wooden Buckler", "This wooden shiled will likely give you a splinter", 27);
			shield2.setItemDefense(2);
			shield2.setEquipable(true);
			shield2.setShield(true);
			shield2.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(shield2,Number);
			return shield2;
		}
		if(Number == 28)
		{
			Armor shield3= new Armor("Images\\Inventory\\Shields\\buckler2.png","Ornate Shield","The shield of a king's guard", 28);
			shield3.setItemDefense(6);
			shield3.setEquipable(true);
			shield3.setShield(true);
			shield3.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(shield3,Number);
			return shield3;
		}
		if(Number == 29)
		{
			Armor shield4= new Armor("Images\\Inventory\\Shields\\buckler3.png","Elven Shield","An ancient elven shield", 29);
			shield4.setItemDefense(6);
			shield4.setEquipable(true);
			shield4.setShield(true);
			shield4.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(shield4,Number);
			return shield4;
		}
		if(Number == 30)
		{
			Armor shield5= new Armor("Images\\Inventory\\Shields\\large_shield1.png","Small Kite Shield","A large shield wielded by knights", 30);
			shield5.setItemDefense(8);
			shield5.setEquipable(true);
			shield5.setShield(true);
			shield5.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(shield5,Number);
			return shield5;
		}
		if(Number == 31)
		{
			Offensive weapon3 = new Offensive("Images\\Inventory\\Weapons\\bardiche1.png","Bardiche", "A long scythe-like weapon", 31);
			weapon3.setItemOffense(10);
			weapon3.setEquipable(true);
			weapon3.setIsWeapon(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(weapon3,Number);
			weapon3.setStackable(false);
			return weapon3;
		}
		if(Number == 32)
		{
			Offensive weapon4 = new Offensive("Images\\Inventory\\Weapons\\bullwhip.png","Whip", "A sturdy whip", 32);
			weapon4.setItemOffense(8);
			weapon4.setEquipable(true);
			weapon4.setIsWeapon(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(weapon4,Number);
			weapon4.setStackable(false);
			return weapon4;
		}
		if(Number == 33)
		{
			Offensive weapon5 = new Offensive("Images\\Inventory\\Weapons\\club.png","Wooden Club", "A wooden club used for bashing", 33);
			weapon5.setItemOffense(6);
			weapon5.setEquipable(true);
			weapon5.setIsWeapon(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(weapon5,Number);
			weapon5.setStackable(false);
			return weapon5;
		}
		if(Number == 34)
		{
			Offensive weapon6 = new Offensive("Images\\Inventory\\Weapons\\dagger.png","Small Dagger", "One should probably replace this quickly", 34);
			weapon6.setItemOffense(3);
			weapon6.setEquipable(true);
			weapon6.setIsWeapon(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(weapon6,Number);
			weapon6.setStackable(false);
			return weapon6;
		}
		if(Number == 35)
		{
			Offensive weapon7 = new Offensive("Images\\Inventory\\Weapons\\double_sword.png","Double Bladed Sword", "One must be wary when using a dual sided weapon", 35);
			weapon7.setItemOffense(15);
			weapon7.setEquipable(true);
			weapon7.setIsWeapon(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(weapon7,Number);
			weapon7.setStackable(false);
			return weapon7;
		}
		if(Number == 36)
		{
			Offensive weapon8 = new Offensive("Images\\Inventory\\Weapons\\eveningstar1.png","Evening Star", "A golden spiked ball used to crush and penetrate armor", 36);
			weapon8.setItemOffense(13);
			weapon8.setEquipable(true);
			weapon8.setIsWeapon(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(weapon8,Number);
			weapon8.setStackable(false);
			return weapon8;
		}
		if(Number == 37)
		{
			Offensive weapon9 = new Offensive("Images\\Inventory\\Weapons\\scythe1.png","Scythe", "Who would use a farming tool as a weapon?", 37);
			weapon9.setItemOffense(10);
			weapon9.setEquipable(true);
			weapon9.setIsWeapon(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(weapon9,Number);
			weapon9.setStackable(false);
			return weapon9;
		}
		if(Number == 38)
		{
			Offensive weapon10 = new Offensive("Images\\Inventory\\Weapons\\giant_club.png","Giant Club", "This Giant's club is GIANT", 38);
			weapon10.setItemOffense(12);
			weapon10.setEquipable(true);
			weapon10.setIsWeapon(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(weapon10,Number);
			weapon10.setStackable(false);
			return weapon10;
		}
		return null;
	}	
	
	public Item specificItem(int Number){
		if(Number==0){
			Potions potion1 = new Potions("Images\\Inventory\\Potions\\ruby.png","Health Potion", "A healing potion",00);
			potion1.setRestoreAmount(50);
			potion1.setEquipable(false);
			potion1.setPotion(true);
			potion1.setStackable(true);
			return potion1;
		}
		if(Number ==1){
			Offensive axe1 = new Offensive("Images\\Inventory\\Weapons\\executioner_axe1.png","Executioner's Axe", "Strong right-hand weapon", 01);
			axe1.setItemOffense(25);
			axe1.setEquipable(true);
			axe1.setIsWeapon(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(axe1,Number);
			axe1.setStackable(false);
			return axe1;
		}
		if(Number == 2)
		{
			Offensive axe2 = new Offensive("Images\\Inventory\\Weapons\\hand_axe1.png", "Small Axe", "A simple hand axe", 02);
			axe2.setItemOffense(10);
			axe2.setEquipable(true);
			axe2.setIsWeapon(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(axe2,Number);
			return axe2;
		}
		if(Number == 3)
		{
			Offensive staff1 = new Offensive("Images\\Inventory\\Weapons\\staff02.png","Wooden Staff", "Simple staff, smashing", 03);
			staff1.setItemOffense(5);
			staff1.setEquipable(true);
			staff1.setIsWeapon(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(staff1,Number);
			return staff1;
		}
		
		if(Number == 4)
		{
			Armor leatherchest1 = new Armor("Images\\Inventory\\Chests\\leather_armour3.png","Leather armor", "Durable, but not very protective",04);
			leatherchest1.setItemDefense(10);
			leatherchest1.setEquipable(true);
			leatherchest1.setChest(true);
			leatherchest1.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(leatherchest1,Number);
			return leatherchest1;
		}
		if(Number == 5)
		{
			Armor helmet1 = new Armor("Images\\Inventory\\Helmets\\wizard_hat2.png","Wizard Hat", "Does not offer much in the way of defense",05);
			helmet1.setItemDefense(2);
			helmet1.setEquipable(true);
			helmet1.setHelmet(true);
			helmet1.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(helmet1,Number);
			return helmet1;
		}
		if(Number == 6)
		{
			Armor helmet2 = new Armor("Images\\Inventory\\Helmets\\green_mask.png","Strange green mask","Strange mask from a strange place",06);
			helmet2.setItemDefense(5);
			helmet2.setEquipable(true);
			helmet2.setHelmet(true);
			helmet2.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(helmet2,Number);
			return helmet2;
		}
		if(Number==7)
		{
			Armor shield1 = new Armor("Images\\Inventory\\Shields\\shield2_kite.png","Large kite-shield","Greatly boosts the defense of the user",07);
			shield1.setItemDefense(10);
			shield1.setEquipable(true);
			shield1.setShield(true);
			shield1.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(shield1,Number);
			return shield1;
		}
		if(Number ==8)
		{
			Armor boot1 = new Armor("Images\\Inventory\\Boots\\boots1_brown.png","Brown leather boots","Protects the wearers feet from harm",8);
			boot1.setItemDefense(4);
			boot1.setEquipable(true);
			boot1.setIsBoots(true);
			boot1.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(boot1,Number);
			return boot1;
		}
		if(Number==9){
			Potions potion2 = new Potions("Images\\Inventory\\Potions\\magenta.png","Greater Healing Potion", "A potent healing potion",9);
			potion2.setRestoreAmount(75);
			potion2.setEquipable(false);
			potion2.setPotion(true);
			potion2.setStackable(true);
			return potion2;
		}
		if(Number==10){
			Potions potion3 = new Potions("Images\\Inventory\\Potions\\pink.png","Lesser Healing Potion", "A weak healing potion",10);
			potion3.setRestoreAmount(25);
			potion3.setEquipable(false);
			potion3.setPotion(true);
			potion3.setStackable(true);
			return potion3;
		}
		if(Number ==11)
		{
			Armor boot2 = new Armor("Images\\Inventory\\Boots\\boots2_jackboots.png","Brown Fur Boots","Keeps one's feet rather warm",11);
			boot2.setItemDefense(2);
			boot2.setEquipable(true);
			boot2.setIsBoots(true);
			boot2.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(boot2,Number);
			return boot2;
		}
		if(Number ==12)
		{
			Armor boot3 = new Armor("Images\\Inventory\\Boots\\boots3_stripe.png","Leather Sandals","Straps of leather woven together",12);
			boot3.setItemDefense(1);
			boot3.setEquipable(true);
			boot3.setIsBoots(true);
			boot3.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(boot3,Number);
			return boot3;
		}
		if(Number ==13)
		{
			Armor boot4 = new Armor("Images\\Inventory\\Boots\\boots4_green.png","Strange Green boots","Strange boots from a strange place",13);
			boot4.setItemDefense(5);
			boot4.setEquipable(true);
			boot4.setIsBoots(true);
			boot4.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(boot4,Number);
			return boot4;
		}
		if(Number == 14)
		{
			Armor cloak1 = new Armor("Images\\Inventory\\Chests\\cloak2.png","Silk Cloak", "Every traveler needs a nice cloak",14);
			cloak1.setItemDefense(2);
			cloak1.setEquipable(true);
			cloak1.setChest(true);
			cloak1.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(cloak1,Number);
			return cloak1;
		}
		if(Number == 15)
		{
			Armor chest3= new Armor("Images\\Inventory\\Chests\\leather_armour1.png","Hard Leather Chest Piece", "A standard chest piece worn by mercenaries",15);
			chest3.setItemDefense(8);
			chest3.setEquipable(true);
			chest3.setChest(true);
			chest3.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(chest3,Number);
			return chest3;
		}
		if(Number == 16)
		{
			Armor chest4= new Armor("Images\\Inventory\\Chests\\leather_armour2.png","Flimsy Red Chest Plate", "The dollar store version of a chest piece",15);
			chest4.setItemDefense(5);
			chest4.setEquipable(true);
			chest4.setChest(true);
			chest4.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(chest4,Number);
			return chest4;
		}
		if(Number == 17)
		{
			Armor chest5= new Armor("Images\\Inventory\\Chests\\plate_mail1.png","Plate Mail", "The armor of a true warrior",17);
			chest5.setItemDefense(15);
			chest5.setEquipable(true);
			chest5.setChest(true);
			chest5.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(chest5,Number);
			return chest5;
		}
		if(Number == 18)
		{
			Armor chest6= new Armor("Images\\Inventory\\Chests\\robe1.png","Blue Robe", "A thin wool robe",18);
			chest6.setItemDefense(2);
			chest6.setEquipable(true);
			chest6.setChest(true);
			chest6.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(chest6,Number);
			return chest6;
		}
		if(Number == 19)
		{
			Armor chest7 = new Armor("Images\\Inventory\\Chests\\troll_leather_armour.png","Strange Green Armor", "A strange chest piece from a strange place",19);
			chest7.setItemDefense(10);
			chest7.setEquipable(true);
			chest7.setChest(true);
			chest7.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(chest7,Number);
			return chest7;
		}
		if(Number == 20)
		{
			Armor chest8= new Armor("Images\\Inventory\\Chests\\scale_mail2.png","Chain Mail", "Protects the wearer from being stabbed to death",20);
			chest8.setItemDefense(5);
			chest8.setEquipable(true);
			chest8.setChest(true);
			chest8.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(chest8,Number);
			return chest8;
		}
		if(Number == 21)
		{
			Armor helmet3= new Armor("Images\\Inventory\\Helmets\\cap2.png","Red Hood", "Once belonged to a little girl", 21);
			helmet3.setItemDefense(2);
			helmet3.setEquipable(true);
			helmet3.setHelmet(true);
			helmet3.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(helmet3,Number);
			return helmet3;
		}
		if(Number == 22)
		{
			Armor helmet4= new Armor("Images\\Inventory\\Helmets\\elven_leather_helm.png","Bard's Hat", "This musician's hat won't help the adventurer much", 22);
			helmet4.setItemDefense(1);
			helmet4.setEquipable(true);
			helmet4.setHelmet(true);
			helmet4.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(helmet4,Number);
			return helmet4;
		}
		if(Number == 23)
		{
			Armor helmet5= new Armor("Images\\Inventory\\Helmets\\helmet1_visored.png","Old Knight's Helm", "Some long passed knight's helmet", 23);
			helmet5.setItemDefense(4);
			helmet5.setEquipable(true);
			helmet5.setHelmet(true);
			helmet5.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(helmet5,Number);
			return helmet5;
		}
		if(Number == 24)
		{
			Armor helmet6= new Armor("Images\\Inventory\\Helmets\\helmet2_etched.png","Modern Knight's Helm", "A strong helm", 24);
			helmet6.setItemDefense(6);
			helmet6.setEquipable(true);
			helmet6.setHelmet(true);
			helmet6.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(helmet6,Number);
			return helmet6;
		}
		if(Number == 25)
		{
			Armor helmet7= new Armor("Images\\Inventory\\Helmets\\helmet3.png","Elven Helmet", "An ancient elven helmet, beautifuly crafted", 25);
			helmet7.setItemDefense(8);
			helmet7.setEquipable(true);
			helmet7.setHelmet(true);
			helmet7.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(helmet7,Number);
			return helmet7;
		}
		if(Number == 26)
		{
			Armor helmet8= new Armor("Images\\Inventory\\Helmets\\helmet4_visor.png","Jouster's Helm", "This impressive looking helm offers little in actual protection", 26);
			helmet8.setItemDefense(3);
			helmet8.setEquipable(true);
			helmet8.setHelmet(true);
			helmet8.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(helmet8,Number);
			return helmet8;
		}
		if(Number == 27)
		{
			Armor shield2= new Armor("Images\\Inventory\\Shields\\buckler1.png","Wooden Buckler", "This wooden shiled will likely give you a splinter", 27);
			shield2.setItemDefense(2);
			shield2.setEquipable(true);
			shield2.setShield(true);
			shield2.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(shield2,Number);
			return shield2;
		}
		if(Number == 28)
		{
			Armor shield3= new Armor("Images\\Inventory\\Shields\\buckler2.png","Ornate Shield","The shield of a king's guard", 28);
			shield3.setItemDefense(6);
			shield3.setEquipable(true);
			shield3.setShield(true);
			shield3.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(shield3,Number);
			return shield3;
		}
		if(Number == 29)
		{
			Armor shield4= new Armor("Images\\Inventory\\Shields\\buckler3.png","Elven Shield","An ancient elven shield", 29);
			shield4.setItemDefense(6);
			shield4.setEquipable(true);
			shield4.setShield(true);
			shield4.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(shield4,Number);
			return shield4;
		}
		if(Number == 30)
		{
			Armor shield5= new Armor("Images\\Inventory\\Shields\\large_shield1.png","Small Kite Shield","A large shield wielded by knights", 30);
			shield5.setItemDefense(8);
			shield5.setEquipable(true);
			shield5.setShield(true);
			shield5.setArmor(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(shield5,Number);
			return shield5;
		}
		if(Number == 31)
		{
			Offensive weapon3 = new Offensive("Images\\Inventory\\Weapons\\bardiche1.png","Bardiche", "A long scythe-like weapon", 31);
			weapon3.setItemOffense(10);
			weapon3.setEquipable(true);
			weapon3.setIsWeapon(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(weapon3,Number);
			weapon3.setStackable(false);
			return weapon3;
		}
		if(Number == 32)
		{
			Offensive weapon4 = new Offensive("Images\\Inventory\\Weapons\\bullwhip.png","Whip", "A sturdy whip", 32);
			weapon4.setItemOffense(8);
			weapon4.setEquipable(true);
			weapon4.setIsWeapon(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(weapon4,Number);
			weapon4.setStackable(false);
			return weapon4;
		}
		if(Number == 33)
		{
			Offensive weapon5 = new Offensive("Images\\Inventory\\Weapons\\club.png","Wooden Club", "A wooden club used for bashing", 33);
			weapon5.setItemOffense(6);
			weapon5.setEquipable(true);
			weapon5.setIsWeapon(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(weapon5,Number);
			weapon5.setStackable(false);
			return weapon5;
		}
		if(Number == 34)
		{
			Offensive weapon6 = new Offensive("Images\\Inventory\\Weapons\\dagger.png","Small Dagger", "One should probably replace this quickly", 34);
			weapon6.setItemOffense(3);
			weapon6.setEquipable(true);
			weapon6.setIsWeapon(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(weapon6,Number);
			weapon6.setStackable(false);
			return weapon6;
		}
		if(Number == 35)
		{
			Offensive weapon7 = new Offensive("Images\\Inventory\\Weapons\\double_sword.png","Double Bladed Sword", "One must be wary when using a dual sided weapon", 35);
			weapon7.setItemOffense(15);
			weapon7.setEquipable(true);
			weapon7.setIsWeapon(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(weapon7,Number);
			weapon7.setStackable(false);
			return weapon7;
		}
		if(Number == 36)
		{
			Offensive weapon8 = new Offensive("Images\\Inventory\\Weapons\\eveningstar1.png","Evening Star", "A golden spiked ball used to crush and penetrate armor", 36);
			weapon8.setItemOffense(13);
			weapon8.setEquipable(true);
			weapon8.setIsWeapon(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(weapon8,Number);
			weapon8.setStackable(false);
			return weapon8;
		}
		if(Number == 37)
		{
			Offensive weapon9 = new Offensive("Images\\Inventory\\Weapons\\scythe1.png","Scythe", "Who would use a farming tool as a weapon?", 37);
			weapon9.setItemOffense(10);
			weapon9.setEquipable(true);
			weapon9.setIsWeapon(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(weapon9,Number);
			weapon9.setStackable(false);
			return weapon9;
		}
		if(Number == 38)
		{
			Offensive weapon10 = new Offensive("Images\\Inventory\\Weapons\\giant_club.png","Giant Club", "This Giant's club is GIANT", 38);
			weapon10.setItemOffense(12);
			weapon10.setEquipable(true);
			weapon10.setIsWeapon(true);
			Number = randomNumber.nextInt(5);
			randomDescriptor(weapon10,Number);
			weapon10.setStackable(false);
			return weapon10;
		}
		//TODO ADD PANTS
		return null;
	}	
	
	
	public void randomDescriptor(Item i, int number)
	{
		if(number ==0)
			i.setDescriptor("");
		if(number ==1)
		{
			i.setDescriptor("Terrible ");
			if(i.isArmor())
			{
				Armor temp = (Armor) i;
				temp.setItemDefense(temp.getItemDefense()-4);
				if(temp.getItemDefense() <= 0)
					temp.setItemDefense(1);
				i = temp;
			}
			if(i.isWeapon())
			{
				Offensive temp = (Offensive) i;
				temp.setItemOffense(temp.getItemOffense() - 4);
				if(temp.getItemOffense() <= 0)
					temp.setItemOffense(1);
				i = temp;
			}
		}
		if(number ==2)
		{
			i.setDescriptor("Strong ");
			if(i.isArmor())
			{
				Armor temp = (Armor) i;
				temp.setItemDefense(temp.getItemDefense() + 3);
				i = temp;
			}
			if(i.isWeapon())
			{
				Offensive temp = (Offensive) i;
				temp.setItemOffense(temp.getItemOffense() + 3);
				i = temp;
			}
		}
		if(number ==3)
		{
			i.setDescriptor("Weak ");
			if(i.isArmor())
			{
				Armor temp = (Armor) i;
				temp.setItemDefense(temp.getItemDefense() - 2);
				if(temp.getItemDefense() <= 0)
					temp.setItemDefense(1);
				i = temp;
			}
			if(i.isWeapon())
			{
				Offensive temp = (Offensive) i;
				temp.setItemOffense(temp.getItemOffense() - 2);
				if(temp.getItemOffense() <= 0)
					temp.setItemOffense(1);
				i = temp;
			}
		}
		if(number ==4)
		{
			i.setDescriptor("Slightly above average ");
			if(i.isArmor())
			{
				Armor temp = (Armor) i;
				temp.setItemDefense(temp.getItemDefense() + 1);
				i = temp;
			}
			if(i.isWeapon())
			{
				Offensive temp = (Offensive) i;
				temp.setItemOffense(temp.getItemOffense() +1);
				i = temp;
			}
		}
	}
}
