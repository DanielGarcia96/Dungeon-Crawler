package Map;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import MainGraphicPackage.Graphic;
import Map.Clutter.Chest;
import Map.Clutter.Clutter;
import Map.Generators.Path;
import Map.Generators.Room;

/**
 * Generates Random Clutter for an arraylist of rooms
 * @author Porter
 *
 */
public class ClutterGeneration {

	private static int Type_Room = 0;
	private static int Type_Path = 1;
	
	private static int UnkownFrameRate = 50;
	private static int TorchFrameRate = 10;
	
	private static int TorchLightLevel = 64;
	
	//private static int Tile_CavePath = 2;
	
	private MapGenerator LevelMap;
	private ArrayList <Graphic> MapClutterList;
	private int MapTileSize;

	public ClutterGeneration(MapGenerator LevelMap, int MapTileSize) {
		// TODO Auto-generated constructor stub
		MapClutterList = new ArrayList <Graphic>();
		this.LevelMap = LevelMap;
		this.MapTileSize = MapTileSize;
		
		ClutterGenController(Type_Path, "\\Images\\Clutter\\Floor", 1, 0, 0);
		ClutterGenController(Type_Path, "\\Images\\Clutter\\Unknown", 1, UnkownFrameRate, 0);
		ClutterGenController(Type_Room, "\\Images\\Clutter\\Torch", 20, TorchFrameRate, TorchLightLevel);
		AddChestsToRooms(LevelMap.getRoomList(), MapTileSize, 20);
		/*
		boolean MakeCaveEntrance = true;
		for(double i = LevelMap.getRoomList().get(0).getRoom().getX(); i < LevelMap.getRoomList().get(0).getRoom().getX() + LevelMap.getRoomList().get(0).getRoom().getWidth(); i++){
			if(LevelMap.MatchAroundElement((int)i, (int)LevelMap.getRoomList().get(0).getRoom().getY(), Tile_CavePath)){
				MakeCaveEntrance = false;
			}
		}
		if(MakeCaveEntrance == true){
			AddCaveEntrance(LevelMap.getRoomList(), MapTileSize);
		}
		*/
	}
	private void ClutterGenController(int Type,String FilePath, int PercentChance, int FrameRate, int LightLevel){
		File RandomClutterDirectory;
		File [] RandomClutterFiles;
		
		RandomClutterDirectory = new File(System.getProperty("user.dir") + FilePath);
		if(RandomClutterDirectory.isDirectory()){
			RandomClutterFiles = RandomClutterDirectory.listFiles();
			ArrayList <String> TempFileNames = new ArrayList<String>();
			for(int i = 0; i < RandomClutterFiles.length; i++){
				TempFileNames.add(RandomClutterFiles[i].getPath());
			}
			if(Type == Type_Path){
				AddAnimatedClutterToPaths(LevelMap.getPathwayList(), TempFileNames, PercentChance, FrameRate, LightLevel);
			}else if(Type == Type_Room){
				AddAnimatedClutterRooms(LevelMap.getRoomList(), TempFileNames, PercentChance, FrameRate, LightLevel);
			}
		}else{
			RandomClutterFiles = RandomClutterDirectory.listFiles();
			ArrayList <String> TempFileNames = new ArrayList<String>();
			TempFileNames.add(RandomClutterFiles[0].getPath());
			if(Type == Type_Path){
				AddAnimatedClutterToPaths(LevelMap.getPathwayList(), TempFileNames, PercentChance, FrameRate, LightLevel);
			}else if(Type == Type_Room){
				AddAnimatedClutterRooms(LevelMap.getRoomList(), TempFileNames, PercentChance, FrameRate, LightLevel);
			}
		}
	}
	/**
	 * Takes a roomlist and adds chests to them, and makes at least one chest
	 * @param RoomList
	 * @param MapTileSize
	 * @param PercentChance
	 */
	public void AddChestsToRooms(ArrayList <Room> RoomList, int MapTileSize, int PercentChance){ 
		int TotalChests = 0;
		Random newrandom = new Random();
		//Makes a random chance to put a chest in a room
		for(int i = 1; i < RoomList.size(); i++){
			if(newrandom.nextInt(100) <= PercentChance){
				int CluttterX = newrandom.nextInt(((int)RoomList.get(i).getRoom().getWidth() - 1) )* MapTileSize + (int)RoomList.get(i).getRoom().getX() * MapTileSize;
				int CluttterY = newrandom.nextInt(((int)RoomList.get(i).getRoom().getHeight() - 1) )* MapTileSize + (int)RoomList.get(i).getRoom().getY() * MapTileSize;
				if(VaildPoint(CluttterX, CluttterY) == true){
					Chest Chest1 = new Chest("Images\\Clutter\\chest.png", CluttterX,CluttterY);
					MapClutterList.add(Chest1);
					TotalChests++;
				}
			}
		}
		//make there at least be one room with a chest
		if(TotalChests == 0){
			int CluttterX, CluttterY;
			do{
			int RandomNumberRoom = newrandom.nextInt(RoomList.size());
			CluttterX = newrandom.nextInt(((int)RoomList.get(RandomNumberRoom).getRoom().getWidth() - 1) )* MapTileSize + (int)RoomList.get(RandomNumberRoom).getRoom().getX() * MapTileSize;
			CluttterY = newrandom.nextInt(((int)RoomList.get(RandomNumberRoom).getRoom().getHeight() - 1) )* MapTileSize + (int)RoomList.get(RandomNumberRoom).getRoom().getY() * MapTileSize;
			}while(VaildPoint(CluttterX, CluttterY) == false);
			Chest Chest1 = new Chest("Images\\Clutter\\chest.png", CluttterX,CluttterY);
			MapClutterList.add(Chest1);
		}
	}
	public void AddAnimatedClutterRooms(ArrayList <Room> RoomList, ArrayList <String> ClutterFileNamePath, int PercentChance, int FrameRate, int LightLevel){ 
		Random newrandom = new Random();
		//Makes a random chance to put a Torch in a room
		for(int i = 1; i < RoomList.size(); i++){
			if(newrandom.nextInt(100) <= PercentChance){
				int CluttterX = newrandom.nextInt(((int)RoomList.get(i).getRoom().getWidth() - 1) * MapTileSize) + (int)RoomList.get(i).getRoom().getX() * MapTileSize;
				int CluttterY = newrandom.nextInt(((int)RoomList.get(i).getRoom().getHeight() - 1) * MapTileSize) + (int)RoomList.get(i).getRoom().getY() * MapTileSize;
				if(VaildPoint(CluttterX, CluttterY) == true){
					MapClutterList.add(new Clutter(ClutterFileNamePath, CluttterX,CluttterY, FrameRate));
					((Clutter) MapClutterList.get(MapClutterList.size()-1)).setLightRadius(LightLevel);
				}
			}
		}
	}
	/**
	 * Takes a pathlist and adds Animated Clutter to them
	 * @param RoomList
	 * @param MapTileSize
	 * @param PercentChance
	 */
	public void AddAnimatedClutterToPaths(ArrayList <Path> PathList, ArrayList <String> ClutterFileNamePath, int PercentChance, int FrameRate, int LightLevel){ 
		Random newrandom = new Random();
		//Makes a random chance to put Animated Clutter in a room
		for(int i = 0; i < PathList.size(); i++){
			for(int j = 0; j < PathList.get(i).getPathway().size(); j++){
				if(newrandom.nextInt(100) <= PercentChance){
					int CluttterX = (int)PathList.get(i).getPathway().get(j).getX() * MapTileSize;
					int CluttterY = (int)PathList.get(i).getPathway().get(j).getY() * MapTileSize;
					if(VaildPoint(CluttterX, CluttterY) == true){
						MapClutterList.add(new Clutter(ClutterFileNamePath, CluttterX,CluttterY, FrameRate));
						((Clutter) MapClutterList.get(MapClutterList.size()-1)).setLightRadius(LightLevel);
					}
				}
			}
		}
	}
	/*
	public void AddCaveEntrance(ArrayList <Room> RoomList, int MapTileSize){ 
		int CluttterX = (((int)RoomList.get(0).getRoom().getWidth()/2) * MapTileSize) + (int)RoomList.get(0).getRoom().getX() * MapTileSize - 26;
		int CluttterY = (int)RoomList.get(0).getRoom().getY() * MapTileSize - 32;
		Clutter Chest = new Clutter("Images\\Clutter\\Clutter_CaveEnter.png", CluttterX,CluttterY);
		MapClutterList.add(Chest);
	}
	*/
	public boolean VaildPoint(int x, int y){
		int Tile_StairsDown = 14;
		int Tile_StairsUp = 15;
		int Element = LevelMap.getMapTileArrayElement(x/MapTileSize, y/MapTileSize);
		if(Element == Tile_StairsDown){
			return false;
		}
		if(Element == Tile_StairsUp){
			return false;
		}
		//Stop from spawning on top of each other
		for(int i = 0; i < MapClutterList.size(); i++){
			if(x == MapClutterList.get(i).getX() && y == MapClutterList.get(i).getY()){
				return false;
			}
		}
		return true;
	}
	public ArrayList <Graphic> getMapClutterList(){
		return MapClutterList;
	}

}