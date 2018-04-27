package Map.Clutter;

import java.util.ArrayList;

import Inventory.Chests;

public class Chest extends Clutter {

	private Chests ChestContent;
	
	public Chest(ArrayList<String> ImageFileName, int x, int y, int FrameRate) {
		super(ImageFileName, x, y, FrameRate);
		// TODO Auto-generated constructor stub
	}

	public Chest(String ImageFileName, int x, int y) {
		super(ImageFileName, x, y);
		// TODO Auto-generated constructor stub
	}

	public Chests getChestContent() {
		return ChestContent;
	}

	public void setChestContent(Chests chestContent) {
		ChestContent = chestContent;
	}

}