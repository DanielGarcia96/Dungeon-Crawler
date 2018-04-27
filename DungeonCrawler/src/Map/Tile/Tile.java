package Map.Tile;

import MainGraphicPackage.Graphic;
import MainGraphicPackage.Lightable;
import MainGraphicPackage.Visitable;

public class Tile extends Graphic implements Lightable, Visitable{

	private int LightLevel;
	private boolean Walkable, Visited;
	
	public Tile(String ImageFileName, int x, int y) {
		super(ImageFileName, x, y);
		this.LightLevel = 255;
		this.Walkable = false;
		this.Visited = false;
	}

	public int getLightLevel() {
		return LightLevel;
	}

	public void setLightLevel(int lightLevel) {
		LightLevel = lightLevel;
	}

	public boolean isWalkable() {
		return Walkable;
	}

	public void setWalkable(boolean walkable) {
		Walkable = walkable;
	}

	public boolean isVisited() {
		return Visited;
	}

	public void setVisited(boolean visited) {
		Visited = visited;
	}

}
