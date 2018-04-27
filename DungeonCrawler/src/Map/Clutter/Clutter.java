package Map.Clutter;

import java.util.ArrayList;

import MainGraphicPackage.AnimatedGraphic;
import MainGraphicPackage.LightSource;
import MainGraphicPackage.Lightable;
import MainGraphicPackage.Visitable;

public class Clutter extends AnimatedGraphic implements Lightable, Visitable, LightSource{
	
	private int LightLevel;
	private boolean Visited;
	private int LightRadius;
	
	public Clutter(ArrayList <String> ImageFileName, int x, int y, int FrameRate) {
		super(ImageFileName, x, y, FrameRate);
		this.LightLevel = 255;
		this.Visited = false;
		this.LightRadius = 0;
	}
	/**
	 * Used to make a single frame Clutter Object
	 * @param ImageFileName
	 * @param x
	 * @param y
	 */
	public Clutter(String ImageFileName, int x, int y) {
		super(ImageFileName, x, y);
		this.LightLevel = 255;
		this.Visited = false;
		this.LightRadius = 0;
	}
	
	public int getLightLevel() {
		return LightLevel;
	}

	public void setLightLevel(int lightLevel) {
		LightLevel = lightLevel;
	}

	public boolean isVisited() {
		return Visited;
	}

	public void setVisited(boolean visited) {
		Visited = visited;
	}

	@Override
	public int getLightRadius() {
		// TODO Auto-generated method stub
		return LightRadius;
	}

	@Override
	public void setLightRadius(int NewLightRadius) {
		LightRadius = NewLightRadius;
	}

}
