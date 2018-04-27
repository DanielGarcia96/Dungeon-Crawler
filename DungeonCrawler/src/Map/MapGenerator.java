package Map;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import MainGraphicPackage.Graphic;
import Map.Generators.Path;
import Map.Generators.River;
import Map.Generators.Room;

import javax.imageio.ImageIO;
/**
 * Generates a random tiled map
 * ---Bug---
 * - May run into infinite loop ??? Solved?
 * @author Porter
 *
 */
public class MapGenerator {
	
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
	
	private int MapTileSizeX, MapTileSizeY, MapTileSize;
	private int [][] MapTileArray;
	private ArrayList <Graphic> MapGraphic;
	private ArrayList <Room> RoomList;
	private ArrayList <Path> PathwayList; 
	
	public MapGenerator(double x, double y, int TileSize, int TotalRooms) {
		this.MapTileSizeX = (int) Math.ceil(x/TileSize);
		this.MapTileSizeY = (int) Math.ceil(y/TileSize);
		this.MapTileArray = new int[MapTileSizeY][MapTileSizeX];
		this.MapTileSize = TileSize;
		MapGraphic = new ArrayList <Graphic>();
		
		MapGenController(TotalRooms);
		
		SetRoomStairs();
		//PrintMapImage("Saved");
	}
	/**
	 * Controller for Map Generation
	 * @param TotalRooms
	 */
	public void MapGenController(int TotalRooms){
		//erase old map array
		MapGen0();
		//Generates Total number of rooms specified
		MapGenRoomList(TotalRooms);
		//adds all rooms into the map array
		for(int i = 0; i < RoomList.size(); i++){
			MapGenRoom(RoomList.get(i).getRoom());
		}
		//Generates all pathways connecting the rooms
		MapGenNewPathways(RoomList, Tile_CavePath);
		//Regenerate rooms so paths don't override them
		for(int i = 0; i < RoomList.size(); i++){
			MapGenRoom(RoomList.get(i).getRoom());
		}
		//Fix all walls for 3d effect
		FixWalls();
		//Generate all the rivers for the map
		MapGenRivers(2, 6);
	}
	/**
	 * Set all Values in MapTileArray to 0
	 */
	private void MapGen0(){
		for(int i = 0; i < MapTileSizeY; i++){
			for(int j = 0; j < MapTileSizeX; j++){
				MapTileArray[i][j] = 0;
			}
		}
	}
	/**
	 * Adds specified Total valid rooms to RoomList, be aware can cause infinite loop if given too many rooms
	 * @param TotalRooms
	 */
	private void MapGenRoomList(int TotalRooms){
		//int Limit = 0;
		//makes a new room arraylist
		RoomList = new ArrayList<Room>();
		RoomList.add(new Room(MapTileSizeX, MapTileSizeY));
		while(RoomList.size() < TotalRooms){
			/*
			//Makes a limit on how many passes, to save time
			if(Limit == TotalRooms * 25){
				System.out.println("Could not Generate: " + TotalRooms + " Only Generated: " + RoomList.size());
				break;
			}
			Limit++;
			*/
			RoomList.add(new Room(MapTileSizeX, MapTileSizeY));
			for(int i = 0; i < RoomList.size(); i++){
				boolean Flag = false;
				for(int j = 0; j < RoomList.size(); j++){
					if(RoomGreaterThanDistance(RoomList.get(i).getRoom(), RoomList.get(j).getRoom(), 2) == true && i != j){
						Flag = true;
					}
					if(RoomGreaterThanDistance(RoomList.get(i).getRoom(), RoomList.get(j).getRoom(), 2) == false && i != j){
						Flag = false;
						break;
					}
				}
				if(Flag == true){
					RoomList.get(i).setInMap(true);
				}
				//Stop infinite loop, by making at least 2 elements in array
				if(RoomList.size() == 1){
					RoomList.add(new Room(MapTileSizeX, MapTileSizeY));
				}
			}
			RemoveNonAddedRooms();
		}
	}
	/**
	 * Takes a rectangle and adds it to the map array
	 * @param NewRoom
	 */
	private void MapGenRoom(Rectangle NewRoom){
		for(int i = 0; i < MapTileSizeY; i++){
			for(int j = 0; j < MapTileSizeX; j++){
				if(NewRoom.contains(j, i)){
					MapTileArray[i][j] = 1;
				}
			}
		}
	}
	/**
	 * Connects two points in order to make a path to them
	 * WARNING Does not check if points are valid in Array
	 * @param One
	 * @param Two
	 */
	private void MapGenNewPathways(ArrayList <Room> InMapRoomList, int Tile_Number){
		PathwayList = new ArrayList <Path>();
		
		//Generate paths to all rooms that are in the map array
		for(int i = 0; i < InMapRoomList.size(); i++){
			if(i + 1 < InMapRoomList.size()){
				PathwayList.add(new Path(InMapRoomList.get(i).getCenter(),InMapRoomList.get(i+1).getCenter()));
			}
		}
		for(int i = 0; i < PathwayList.size(); i++){
			for(int j = 0; j < PathwayList.get(i).getPathway().size(); j++){
				MapTileArray[(int)PathwayList.get(i).getPathway().get(j).getY()][(int)PathwayList.get(i).getPathway().get(j).getX()] = Tile_Number;
			}
		}
	}
	/**
	 * Generates Rivers
	 * @param NumberRivers
	 * @param MaxBend
	 */
	private void MapGenRivers(int NumberRivers, int MaxBend){
		Random R = new Random();
		for(int j = 0; j < NumberRivers; j++){
			boolean TypeRiver = R.nextBoolean();
			int Displacement = R.nextInt(MaxBend);
			Point p1 = new Point(R.nextInt(MapTileSizeX - 2*Displacement) + Displacement, MapTileSizeY - 2);
			Point p2 = new Point(R.nextInt(MapTileSizeX - 2*Displacement) + Displacement, 1);
			River NewRiver = new River(p1,p2, Displacement);
			for(int i = 0; i < NewRiver.getRiverPathArrayList().size(); i++){
				if((int)NewRiver.getRiverPathArrayList().get(i).getX() > 0 && (int)NewRiver.getRiverPathArrayList().get(i).getX() < MapTileSizeX){
					if(MapTileArray[(int)NewRiver.getRiverPathArrayList().get(i).getY()][(int)NewRiver.getRiverPathArrayList().get(i).getX()] == Tile_CavePath ||
					MapTileArray[(int)NewRiver.getRiverPathArrayList().get(i).getY()][(int)NewRiver.getRiverPathArrayList().get(i).getX()] == Tile_CaveRoom	){
						if(TypeRiver == true){
							MapTileArray[(int)NewRiver.getRiverPathArrayList().get(i).getY()][(int)NewRiver.getRiverPathArrayList().get(i).getX()] = Tile_LavaPath;
						}else{
							MapTileArray[(int)NewRiver.getRiverPathArrayList().get(i).getY()][(int)NewRiver.getRiverPathArrayList().get(i).getX()] = Tile_WaterStill;
						}
					}
				}
			}
		}
	}
	/**
	 * Adds more map tiles to make it isometric
	 */
	private void FixWalls(){
		//Fix tiles around path, for a 3d look
				for(int i = 0; i < MapTileSizeY; i++){
					for(int j = 0; j < MapTileSizeX; j++){
						if(MapTileArray[i][j] == Tile_CaveTop){
							if(MatchAroundElement(j,i,Tile_CavePath) == true || MatchAroundElement(j,i,Tile_CaveRoom) == true){
								MapTileArray[i][j] = Tile_CaveTop;
								int [] biomearray = new int [2];
								biomearray[0] = Tile_CavePath;
								biomearray[1] = Tile_CaveRoom;
								boolean [] MapMatch = MatchAroundElementArrayLoctation(j,i,biomearray);
								if(MapMatch[1] == true){
									MapTileArray[i][j] = Tile_CaveWallRight;	
								}
								if(MapMatch[3] == true){
									MapTileArray[i][j] = Tile_CaveWallDown;	
								}
								if(MapMatch[4] == true){
									MapTileArray[i][j] = Tile_CaveWallUp;	
								}
								if(MapMatch[6] == true){
									MapTileArray[i][j] = Tile_CaveWallLeft;	
								}
								if(MapMatch[0] == true && MapMatch[1] == true && MapMatch[3] == true){
									MapTileArray[i][j] = Tile_CaveWallRightDownC;	
								}
								if(MapMatch[1] == true && MapMatch[2] == true && MapMatch[4] == true){
									MapTileArray[i][j] = Tile_CaveWallRightUpC;	
								}
								if(MapMatch[4] == true && MapMatch[6] == true && MapMatch[7] == true){
									MapTileArray[i][j] = Tile_CaveWallLeftUpC;;	
								}
								if(MapMatch[3] == true && MapMatch[5] == true && MapMatch[6] == true){
									MapTileArray[i][j] = Tile_CaveWallLeftDownC;	
								}
								if(MapMatch[3] == true && MapMatch[4] == true){
									MapTileArray[i][j] = Tile_CaveWallPUpDown;	
								}
								if(MapMatch[1] == true && MapMatch[6] == true){
									MapTileArray[i][j] = Tile_CaveWallPRightLeft;	
								}
							}
						}
					}
				}
	}	
	/**
	 * Save a map to a png file
	 * @param FileName
	 */
	public void PrintMapImage(String FileName){
		Graphic Tile0 = new Graphic("Images\\Tiles\\Tile_CaveTop.png");
		Graphic Tile1 = new Graphic("Images\\Tiles\\Tile_CaveRoom.png");
		Graphic Tile2 = new Graphic("Images\\Tiles\\Tile_CavePath.png");
		Graphic Tile3 = new Graphic("Images\\Tiles\\Tile_CaveWall.png");
		Graphic Tile4 = new Graphic("Images\\Tiles\\Tile_CaveWallUp.png");
		Graphic Tile5 = new Graphic("Images\\Tiles\\Tile_CaveWallRight.png");
		Graphic Tile6 = new Graphic("Images\\Tiles\\Tile_CaveWallDown.png");
		Graphic Tile7 = new Graphic("Images\\Tiles\\Tile_CaveWallLeft.png");
		Graphic Tile8 = new Graphic("Images\\Tiles\\Tile_CaveWallRightUpC.png");
		Graphic Tile9 = new Graphic("Images\\Tiles\\Tile_CaveWallRightDownC.png");
		Graphic Tile10 = new Graphic("Images\\Tiles\\Tile_CaveWallLeftDownC.png");
		Graphic Tile11 = new Graphic("Images\\Tiles\\Tile_CaveWallLeftUpC.png");
		Graphic Tile12 = new Graphic("Images\\Tiles\\Tile_CaveWallPUpDown.png");
		Graphic Tile13 = new Graphic("Images\\Tiles\\Tile_CaveWallPRightLeft.png");
		Graphic Tile14 = new Graphic("Images\\Tiles\\Tile_StairsDown.png");
		Graphic Tile15 = new Graphic("Images\\Tiles\\Tile_StairsUp.png");
		Graphic Tile16 = new Graphic("Images\\Tiles\\Lava\\Tile_LavaStill.png");
		Graphic Tile17 = new Graphic("Images\\Tiles\\Lava\\Tile_LavaPath.png");
		Graphic Tile18 = new Graphic("Images\\Tiles\\Tile_WaterStill1.png");
		
		BufferedImage Image = new BufferedImage(this.MapTileSizeX * this.MapTileSize,this.MapTileSizeY *this.MapTileSize,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = Image.createGraphics();
		for(int i = 0; i < MapTileSizeY; i++){
			for(int j = 0; j < MapTileSizeX; j++){
				if(MapTileArray[i][j] == Tile_CaveTop){
					g2d.drawImage(Tile0.getMainImage(), j * this.MapTileSize, i * this.MapTileSize, this.MapTileSize, this.MapTileSize, null);
				}else if(MapTileArray[i][j] == Tile_CaveRoom){
					g2d.drawImage(Tile1.getMainImage(), j * this.MapTileSize, i * this.MapTileSize, this.MapTileSize, this.MapTileSize, null);
				}else if(MapTileArray[i][j] == Tile_CavePath){
					g2d.drawImage(Tile2.getMainImage(), j * this.MapTileSize, i * this.MapTileSize, this.MapTileSize, this.MapTileSize, null);
				}else if(MapTileArray[i][j] == Tile_CaveWall){
					g2d.drawImage(Tile3.getMainImage(), j * this.MapTileSize, i * this.MapTileSize, this.MapTileSize, this.MapTileSize, null);
				}else if(MapTileArray[i][j] == Tile_CaveWallUp){
					g2d.drawImage(Tile4.getMainImage(), j * this.MapTileSize, i * this.MapTileSize, this.MapTileSize, this.MapTileSize, null);
				}else if(MapTileArray[i][j] == Tile_CaveWallRight){
					g2d.drawImage(Tile5.getMainImage(), j * this.MapTileSize, i * this.MapTileSize, this.MapTileSize, this.MapTileSize, null);
				}else if(MapTileArray[i][j] == Tile_CaveWallDown){
					g2d.drawImage(Tile6.getMainImage(), j * this.MapTileSize, i * this.MapTileSize, this.MapTileSize, this.MapTileSize, null);
				}else if(MapTileArray[i][j] == Tile_CaveWallLeft){
					g2d.drawImage(Tile7.getMainImage(), j * this.MapTileSize, i * this.MapTileSize, this.MapTileSize, this.MapTileSize, null);
				}else if(MapTileArray[i][j] == Tile_CaveWallRightUpC){
					g2d.drawImage(Tile8.getMainImage(), j * this.MapTileSize, i * this.MapTileSize, this.MapTileSize, this.MapTileSize, null);
				}else if(MapTileArray[i][j] == Tile_CaveWallRightDownC){
					g2d.drawImage(Tile9.getMainImage(), j * this.MapTileSize, i * this.MapTileSize, this.MapTileSize, this.MapTileSize, null);
				}else if(MapTileArray[i][j] == Tile_CaveWallLeftDownC){
					g2d.drawImage(Tile10.getMainImage(), j * this.MapTileSize, i * this.MapTileSize, this.MapTileSize, this.MapTileSize, null);
				}else if(MapTileArray[i][j] == Tile_CaveWallLeftUpC){
					g2d.drawImage(Tile11.getMainImage(), j * this.MapTileSize, i * this.MapTileSize, this.MapTileSize, this.MapTileSize, null);
				}else if(MapTileArray[i][j] == Tile_CaveWallPUpDown){
					g2d.drawImage(Tile12.getMainImage(), j * this.MapTileSize, i * this.MapTileSize, this.MapTileSize, this.MapTileSize, null);
				}else if(MapTileArray[i][j] == Tile_CaveWallPRightLeft){
					g2d.drawImage(Tile13.getMainImage(), j * this.MapTileSize, i * this.MapTileSize, this.MapTileSize, this.MapTileSize, null);
				}else if(MapTileArray[i][j] == Tile_StairsDown){
					g2d.drawImage(Tile14.getMainImage(), j * this.MapTileSize, i * this.MapTileSize, this.MapTileSize, this.MapTileSize, null);
				}else if(MapTileArray[i][j] == Tile_StairsUp){
					g2d.drawImage(Tile15.getMainImage(), j * this.MapTileSize, i * this.MapTileSize, this.MapTileSize, this.MapTileSize, null);
				}else if(MapTileArray[i][j] == Tile_LavaStill){
					g2d.drawImage(Tile16.getMainImage(), j * this.MapTileSize, i * this.MapTileSize, this.MapTileSize, this.MapTileSize, null);
				}else if(MapTileArray[i][j] == Tile_LavaPath){
					g2d.drawImage(Tile17.getMainImage(), j * this.MapTileSize, i * this.MapTileSize, this.MapTileSize, this.MapTileSize, null);
				}else if(MapTileArray[i][j] == Tile_WaterStill){
					g2d.drawImage(Tile18.getMainImage(), j * this.MapTileSize, i * this.MapTileSize, this.MapTileSize, this.MapTileSize, null);
				}
			}
		}
		File outputfile = new File(FileName + ".png");
	    try {
			ImageIO.write(Image, "png", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Tests to see if the distance between to rectangles is greater than given distance, and if is return true
	 * @param Rec1
	 * @param Rec2
	 * @param MaxDistance
	 * @return
	 */
	private boolean RoomGreaterThanDistance(Rectangle Rec1, Rectangle Rec2, double MaxDistance){
		if(Rec1.intersects(Rec2)){
			return false;
		}
		Point rec1P = new Point();
		rec1P.setLocation(Rec1.getX(), Rec1.getY());
		if(rec1P.distance(ClosestPointRec(rec1P, Rec2)) <= MaxDistance){
			return false;
		}
		rec1P.setLocation(Rec1.getX() + Rec1.getWidth(), Rec1.getY());
		if(rec1P.distance(ClosestPointRec(rec1P, Rec2)) <= MaxDistance){
			return false;
		}
		rec1P.setLocation(Rec1.getX(), Rec1.getY() + Rec1.getHeight());
		if(rec1P.distance(ClosestPointRec(rec1P, Rec2)) <= MaxDistance){
			return false;
		}
		rec1P.setLocation(Rec1.getX() + Rec1.getWidth(), Rec1.getY() + Rec1.getHeight());
		if(rec1P.distance(ClosestPointRec(rec1P, Rec2)) <= MaxDistance){
			return false;
		}
		return true;
	}
	/**
	 * Returns the closest Point in relation to the given rectangle
	 * @param Origin
	 * @param Rec1
	 * @return
	 */
	private Point ClosestPointRec(Point Origin, Rectangle Rec1){
		Point Closest = new Point((int)Rec1.getX(), (int)Rec1.getY());
		double Distance  = Origin.distance(Closest);
		
		if(Distance > Origin.distance(new Point((int)Rec1.getX() + (int)Rec1.getWidth(), (int)Rec1.getY()))){
			Closest = new Point((int)Rec1.getX() + (int)Rec1.getWidth(), (int)Rec1.getY());
			Distance  = Origin.distance(Closest);
		}
		if(Distance > Origin.distance(new Point((int)Rec1.getX(), (int)Rec1.getY() + (int)Rec1.getHeight()))){
			Closest = new Point((int)Rec1.getX(), (int)Rec1.getY() + (int)Rec1.getHeight());
			Distance  = Origin.distance(Closest);
		}
		if(Distance > Origin.distance(new Point((int)Rec1.getX() + (int)Rec1.getWidth(), (int)Rec1.getY() + (int)Rec1.getHeight()))){
			Closest = new Point((int)Rec1.getX() + (int)Rec1.getWidth(), (int)Rec1.getY() + (int)Rec1.getHeight());
			Distance  = Origin.distance(Closest);
		}
		return Closest;
	}
	/**
	 * Finds the closest path to the border of the array
	 * @param Origin
	 * @return
	 */
	private Point ClosestExit(Point Origin){
		Point Closest = new Point((int)Origin.getX(), 0);
		double Distance  = Origin.distance(Closest);
		if(Distance > Origin.distance(new Point(0, (int)Origin.getY()))){
			Closest = new Point(0, (int)Origin.getY());
			Distance  = Origin.distance(Closest);
		}
		if(Distance > Origin.distance(new Point((int)Origin.getX(), MapTileSizeY - 1))){
			Closest = new Point((int)Origin.getX(), MapTileSizeY - 1);
			Distance  = Origin.distance(Closest);
		}
		if(Distance > Origin.distance(new Point(MapTileSizeX - 1, (int)Origin.getY()))){
			Closest = new Point(MapTileSizeX - 1, (int)Origin.getY());
			Distance  = Origin.distance(Closest);
		}
		return Closest;
	}
	/**
	 * Goes through Array of rooms and removes them if they are not in the map
	 * Clean up Method
	 */
	private void RemoveNonAddedRooms(){ 
		for(int i = RoomList.size() - 1; i >= 0; i--){
			if(RoomList.get(i).isInMap() == false){
				RoomList.remove(i);
			}
		}
	}
	/**
	 * Checks if there is a match around a number in an 2d array
	 * returns true if there is a match and false if there isn't
	 * @param x_Value
	 * @param y_Value
	 * @param NumberToMatch
	 * @return
	 */
	public boolean MatchAroundElement(int x_Value, int y_Value, int NumberToMatch){ 
				//First Column Checking
				if(y_Value - 1 > -1 && x_Value - 1 > -1){
					if(MapTileArray[y_Value - 1][x_Value - 1] == NumberToMatch){ 
						return true;
					}
				}
				if(x_Value - 1 > -1){
					if(MapTileArray[y_Value][x_Value - 1] == NumberToMatch){ 
						return true;
					}
				}
				if(y_Value + 1 < MapTileSizeY && x_Value - 1 > -1){
					if(MapTileArray[y_Value + 1][x_Value - 1] == NumberToMatch){ 
						return true;
					}
				}
				//Second Column Checking
				if(y_Value - 1 > -1){
					if(MapTileArray[y_Value - 1][x_Value] == NumberToMatch){ 
						return true;
					}
				}
				if(y_Value + 1 < MapTileSizeY){
					if(MapTileArray[y_Value + 1][x_Value] == NumberToMatch){ 
						return true;
					}
				}
				//Third Column Checking
				if(y_Value - 1 > - 1 && x_Value + 1 < MapTileSizeX){
					if(MapTileArray[y_Value - 1][x_Value + 1] == NumberToMatch){ 
						return true;
					}
				}
				if(x_Value + 1 < MapTileSizeX){
					if(MapTileArray[y_Value][x_Value + 1] == NumberToMatch){ 
						return true;
					}
				}
				if(y_Value + 1 < MapTileSizeY && x_Value + 1 < MapTileSizeX){
					if(MapTileArray[y_Value + 1][x_Value + 1] == NumberToMatch){ 
						return true;
					}
				}
				return false;
	}
	/**
	 * Returns an array of locations of the number matched
	 * @param x_Value
	 * @param y_Value
	 * @param NumberToMatch
	 * @return
	 */
	public boolean [] MatchAroundElementLoctation(int x_Value, int y_Value, int NumberToMatch){ 
		boolean [] MatchTileLocation = new boolean[8];
		//First Column Checking
		if(y_Value - 1 > -1 && x_Value - 1 > -1){
			if(MapTileArray[y_Value - 1][x_Value - 1] == NumberToMatch){ 
				MatchTileLocation[0] = true;
			}
		}
		if(x_Value - 1 > -1){
			if(MapTileArray[y_Value][x_Value - 1] == NumberToMatch){ 
				MatchTileLocation[1] = true;
			}
		}
		if(y_Value + 1 < MapTileSizeY && x_Value - 1 > -1){
			if(MapTileArray[y_Value + 1][x_Value - 1] == NumberToMatch){ 
				MatchTileLocation[2] = true;
			}
		}
		//Second Column Checking
		if(y_Value - 1 > -1){
			if(MapTileArray[y_Value - 1][x_Value] == NumberToMatch){ 
				MatchTileLocation[3] = true;
			}
		}
		if(y_Value + 1 < MapTileSizeY){
			if(MapTileArray[y_Value + 1][x_Value] == NumberToMatch){ 
				MatchTileLocation[4] = true;
			}
		}
		//Third Column Checking
		if(y_Value - 1 > - 1 && x_Value + 1 < MapTileSizeX){
			if(MapTileArray[y_Value - 1][x_Value + 1] == NumberToMatch){ 
				MatchTileLocation[5] = true;
			}
		}
		if(x_Value + 1 < MapTileSizeX){
			if(MapTileArray[y_Value][x_Value + 1] == NumberToMatch){ 
				MatchTileLocation[6] = true;
			}
		}
		if(y_Value + 1 < MapTileSizeY && x_Value + 1 < MapTileSizeX){
			if(MapTileArray[y_Value + 1][x_Value + 1] == NumberToMatch){ 
				MatchTileLocation[7] = true;
			}
		}
		return MatchTileLocation;
}
	/**
	 * Returns an array of locations of the number matched
	 * @param x_Value
	 * @param y_Value
	 * @param NumbersToMatch
	 * @return
	 */
	private boolean [] MatchAroundElementArrayLoctation(int x_Value, int y_Value, int [] NumbersToMatch){ 
		boolean [] MatchTileLocation = new boolean[8];
		for(int i = 0; i < NumbersToMatch.length; i++){
			int NumberToMatch = NumbersToMatch[i];
		//First Column Checking
		if(y_Value - 1 > -1 && x_Value - 1 > -1){
			if(MapTileArray[y_Value - 1][x_Value - 1] == NumberToMatch){ 
				MatchTileLocation[0] = true;
			}
		}
		if(x_Value - 1 > -1){
			if(MapTileArray[y_Value][x_Value - 1] == NumberToMatch){ 
				MatchTileLocation[1] = true;
			}
		}
		if(y_Value + 1 < MapTileSizeY && x_Value - 1 > -1){
			if(MapTileArray[y_Value + 1][x_Value - 1] == NumberToMatch){ 
				MatchTileLocation[2] = true;
			}
		}
		//Second Column Checking
		if(y_Value - 1 > -1){
			if(MapTileArray[y_Value - 1][x_Value] == NumberToMatch){ 
				MatchTileLocation[3] = true;
			}
		}
		if(y_Value + 1 < MapTileSizeY){
			if(MapTileArray[y_Value + 1][x_Value] == NumberToMatch){ 
				MatchTileLocation[4] = true;
			}
		}
		//Third Column Checking
		if(y_Value - 1 > - 1 && x_Value + 1 < MapTileSizeX){
			if(MapTileArray[y_Value - 1][x_Value + 1] == NumberToMatch){ 
				MatchTileLocation[5] = true;
			}
		}
		if(x_Value + 1 < MapTileSizeX){
			if(MapTileArray[y_Value][x_Value + 1] == NumberToMatch){ 
				MatchTileLocation[6] = true;
			}
		}
		if(y_Value + 1 < MapTileSizeY && x_Value + 1 < MapTileSizeX){
			if(MapTileArray[y_Value + 1][x_Value + 1] == NumberToMatch){ 
				MatchTileLocation[7] = true;
			}
		}
		}
		return MatchTileLocation;
}
	/**
	 * Returns the Farthest room from the entry room
	 * @return
	 */
	public Room getBossRoom(){
		double FurthestDistance = 0;
		Room FurthestRoom = RoomList.get(0);
		for(int i = 1; i < RoomList.size(); i++){
			if(RoomList.get(0).getCenter().distance(RoomList.get(i).getCenter()) > FurthestDistance){
				FurthestDistance = RoomList.get(0).getCenter().distance(RoomList.get(i).getCenter());
				FurthestRoom = RoomList.get(i);
			}
		}
		return FurthestRoom;
	}
	/**
	 * Makes Stairs in the center of the first and boss room
	 */
	public void SetRoomStairs(){
		MapTileArray[(int)RoomList.get(0).getCenter().getY()][(int)RoomList.get(0).getCenter().getX()] = Tile_StairsUp;
		MapTileArray[(int)getBossRoom().getCenter().getY()][(int)getBossRoom().getCenter().getX()] = Tile_StairsDown;
	}
	public int getMapTileSizeY() {
		return MapTileSizeY;
	}
	public int getMapTileSizeX() {
		return MapTileSizeX;
	}
	public ArrayList <Room> getRoomList(){
		return RoomList;
	}
	public ArrayList<Path> getPathwayList() {
		return PathwayList;
	}
	public ArrayList<Graphic> getMapGraphicMultiThreaded(){
		//Empty Map Graphic for new images
		MapGraphic = new ArrayList <Graphic>();
		//Number of threads to spawn
		int NumThreads = 8;
		//Create a new threadlist
		ArrayList <MapThead> ThreadList = new ArrayList <MapThead>();
		//The amount of work to divide among the threads
		int Split = MapTileSizeY/NumThreads;
		//Add the threads and start them
		for(int i = 0; i < NumThreads; i++){
			if(i + 1 == NumThreads){
				ThreadList.add(new MapThead("Thread" + i, MapTileArray, i * Split, MapTileSizeY, MapTileSizeX));
			}else{
				ThreadList.add(new MapThead("Thread" + i, MapTileArray, i * Split, (i + 1)* Split, MapTileSizeX));
			}
			ThreadList.get(i).start();
		}
		//Make current thread wait until all threads are done, and when done add them to map graphic
		for(int i = 0; i < ThreadList.size(); i++){
			while(ThreadList.get(i).getT().isAlive() == true){}
			MapGraphic.addAll(ThreadList.get(i).getMapGraphic());
		}

		//UpdateMapImage();
		return MapGraphic;
	}
	/**
	 * Used to reset map graphic
	 * @param MapGraphic
	 */
	public void setMapGraphic(ArrayList<Graphic> MapGraphic){
		this.MapGraphic = MapGraphic;
	}
	/**
	 * Gives the element requested in the title, and -1 if out of bounds
	 * @param Elementx
	 * @param Elementy
	 * @return
	 */
	public int getMapTileArrayElement(int Elementx, int Elementy) {
		if(Elementx < MapTileSizeX){
			if(Elementy < MapTileSizeY){
				return MapTileArray[Elementy][Elementx];
			}
		}
		return -1;
	}

}