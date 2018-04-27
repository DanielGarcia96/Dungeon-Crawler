package Map;

import java.util.ArrayList;

import MainGraphicPackage.Graphic;
import Map.Tile.Lava;
import Map.Tile.StairDown;
import Map.Tile.StairUp;
import Map.Tile.Tile;

public class MapThead extends Thread{

	private String ThreadName;
	private Thread T;
	private int [][] Map;
	private int Row, Row2, RowLength, MapTileSize;
	private ArrayList <Graphic> MapGraphic;
	
	private static int Tile_CaveTop = 0;
	private static int Tile_CaveRoom = 1;
	private static int Tile_CavePath = 2;
	private static int Tile_CaveWall = 3;
	private static int Tile_CaveWallUp = 4;
	private static int Tile_CaveWallRight = 5;
	private static int Tile_CaveWallDown = 6;
	private static int Tile_CaveWallLeft = 7;
	private static int Tile_CaveWallRightUpC = 8;
	private static int Tile_CaveWallRightDownC = 9;
	private static int Tile_CaveWallLeftDownC = 10;
	private static int Tile_CaveWallLeftUpC = 11;
	private static int Tile_CaveWallPUpDown = 12;
	private static int Tile_CaveWallPRightLeft = 13;
	private static int Tile_StairsDown = 14;
	private static int Tile_StairsUp = 15;
	public static int Tile_LavaStill = 16;
	public static int Tile_LavaPath = 17;
	public static int Tile_WaterStill = 18;
	
	public MapThead(String ThreadName, int [][] Map, int Row, int Row2, int RowLength) {
		this.ThreadName = ThreadName;
		this.Map = Map;
		this.Row = Row;
		this.Row2 = Row2;
		this.MapTileSize = 32;
		this.RowLength = RowLength;
		this.MapGraphic = new ArrayList <Graphic>();
	}

	public void start(){
		if(T == null){
			T = new Thread(this,ThreadName);
			T.start();
		}
	}
	public void run(){
		//synchronized(Map){
			UpdateMapGraphic();
		//}
	}
	public void UpdateMapGraphic(){
		for(int i = Row; i < Row2; i++){
			synchronized(Map[i]){
			for(int j = 0; j < RowLength; j++){
				if(Map[i][j] == Tile_CaveTop){
					MapGraphic.add(new Tile("Images\\Tiles\\Tile_CaveTop.png", j * this.MapTileSize, i * this.MapTileSize));
				}else if(Map[i][j] == Tile_CaveRoom){
					Tile TempTile = new Tile("Images\\Tiles\\Tile_CaveRoom.png", j * this.MapTileSize, i * this.MapTileSize);
					TempTile.setWalkable(true);
					MapGraphic.add(TempTile);
				}else if(Map[i][j] == Tile_CavePath){
					Tile TempTile = new Tile("Images\\Tiles\\Tile_CavePath.png", j * this.MapTileSize, i * this.MapTileSize);
					TempTile.setWalkable(true);
					MapGraphic.add(TempTile);
				}else if(Map[i][j] == Tile_CaveWall){
					MapGraphic.add(new Tile("Images\\Tiles\\Tile_CaveWall.png", j * this.MapTileSize, i * this.MapTileSize));
				}else if(Map[i][j] == Tile_CaveWallUp){
					MapGraphic.add(new Tile("Images\\Tiles\\Tile_CaveWallUp.png", j * this.MapTileSize, i * this.MapTileSize));
				}else if(Map[i][j] == Tile_CaveWallRight){
					MapGraphic.add(new Tile("Images\\Tiles\\Tile_CaveWallRight.png", j * this.MapTileSize, i * this.MapTileSize));
				}else if(Map[i][j] == Tile_CaveWallDown){
					MapGraphic.add(new Tile("Images\\Tiles\\Tile_CaveWallDown.png", j * this.MapTileSize, i * this.MapTileSize));
				}else if(Map[i][j] == Tile_CaveWallLeft){
					MapGraphic.add(new Tile("Images\\Tiles\\Tile_CaveWallLeft.png", j * this.MapTileSize, i * this.MapTileSize));
				}else if(Map[i][j] == Tile_CaveWallRightUpC){
					MapGraphic.add(new Tile("Images\\Tiles\\Tile_CaveWallRightUpC.png", j * this.MapTileSize, i * this.MapTileSize));
				}else if(Map[i][j] == Tile_CaveWallRightDownC){
					MapGraphic.add(new Tile("Images\\Tiles\\Tile_CaveWallRightDownC.png", j * this.MapTileSize, i * this.MapTileSize));
				}else if(Map[i][j] == Tile_CaveWallLeftDownC){
					MapGraphic.add(new Tile("Images\\Tiles\\Tile_CaveWallLeftDownC.png", j * this.MapTileSize, i * this.MapTileSize));
				}else if(Map[i][j] == Tile_CaveWallLeftUpC){
					MapGraphic.add(new Tile("Images\\Tiles\\Tile_CaveWallLeftUpC.png", j * this.MapTileSize, i * this.MapTileSize));
				}else if(Map[i][j] == Tile_CaveWallPUpDown){
					MapGraphic.add(new Tile("Images\\Tiles\\Tile_CaveWallPUpDown.png", j * this.MapTileSize, i * this.MapTileSize));
				}else if(Map[i][j] == Tile_CaveWallPRightLeft){
					MapGraphic.add(new Tile("Images\\Tiles\\Tile_CaveWallPRightLeft.png", j * this.MapTileSize, i * this.MapTileSize));
				}else if(Map[i][j] == Tile_StairsDown){
					StairDown TempTile = new StairDown("Images\\Tiles\\Tile_StairsDown.png", j * this.MapTileSize, i * this.MapTileSize);
					TempTile.setWalkable(true);
					MapGraphic.add(TempTile);
				}else if(Map[i][j] == Tile_StairsUp){
					StairUp TempTile = new StairUp("Images\\Tiles\\Tile_StairsUp.png", j * this.MapTileSize, i * this.MapTileSize);
					TempTile.setWalkable(true);
					MapGraphic.add(TempTile);
				}else if(Map[i][j] == Tile_LavaStill){
					Lava LavaTile = new Lava("Images\\Tiles\\Lava\\Tile_LavaStill.png", j * this.MapTileSize, i * this.MapTileSize);
					LavaTile.setLightRadius(64);
					MapGraphic.add(LavaTile);
				}else if(Map[i][j] == Tile_LavaPath){
					Lava LavaTile = new Lava("Images\\Tiles\\Lava\\Tile_LavaPath.png", j * this.MapTileSize, i * this.MapTileSize);
					LavaTile.setLightRadius(32);
					LavaTile.setWalkable(true);
					MapGraphic.add(LavaTile);
				}else if(Map[i][j] == Tile_WaterStill){
					Tile TempTile = new Tile("Images\\Tiles\\Tile_WaterStill1.png", j * this.MapTileSize, i * this.MapTileSize);
					TempTile.setWalkable(true);
					MapGraphic.add(TempTile);
				}
			}
			}
		}
	}
	public ArrayList<Graphic> getMapGraphic(){
		return MapGraphic;
	}
	public Thread getT(){
		return T;
	}

}
