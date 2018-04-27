package Map.Tile;

import MainGraphicPackage.LightSource;

public class Lava extends Tile implements LightSource{

	private int LightRadius;
	
	public Lava(String ImageFileName, int x, int y) {
		super(ImageFileName, x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getLightRadius() {
		// TODO Auto-generated method stub
		return LightRadius;
	}

	@Override
	public void setLightRadius(int NewLightRadius) {
		// TODO Auto-generated method stub
		LightRadius = NewLightRadius;
	}

}
