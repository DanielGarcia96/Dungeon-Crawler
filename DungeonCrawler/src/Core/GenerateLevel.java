package Core;

import java.util.ArrayList;

import Map.ClutterGeneration;
import MainGraphicPackage.Graphic;
import MainGraphicPackage.Visitable;
import Map.MapGenerator;
import character.Player;

/**
 * Could opmize the way graphics are made in clutter class
 * uses 32 instead of tile size fix it
 * @author Porter
 *
 */
public class GenerateLevel {

	private MapGenerator LevelMap;
	private ClutterGeneration Clutter;
	private ArrayList<Graphic> LevelGraphicList;
	private int PlayerExitX, PlayerExitY;
	
	public GenerateLevel() {
		//Make a new map full of tiles
		LevelMap = new MapGenerator(1920, 1080, 32, 15);
		//Fill map tiles with random clutter
		Clutter = new ClutterGeneration(LevelMap,32);
	}
	/**
	 * Returns an arraylist of new level graphics or old if specified
	 * @param Player1 the main character, set to null if new player
	 * @param NewLevel
	 * @return
	 */
	public ArrayList<Graphic> getLevelGraphicListController(Player Player1, Boolean NewLevel){
		//Generate all graphics for the level
		getLevelGraphicList();
		if(Player1 == null){
			//make a new player if non specified
			Player1 = new Player("Images/Characters/Player_Down/PlayerDown_1.png",(int) (LevelMap.getRoomList().get(0).getRoom().getCenterX() + 1) * 32, (int)LevelMap.getRoomList().get(0).getRoom().getCenterY() * 32);
		}else{
			//if it is a new level set player x and y to the stais going up in the next floor, to the left of them
			if(NewLevel == true){
				Player1.setX((int)(LevelMap.getRoomList().get(0).getRoom().getCenterX() + 1) * 32);
				Player1.setY((int)LevelMap.getRoomList().get(0).getRoom().getCenterY() * 32);
			}else{
				//set player to pervious coordinates
				Player1.setX(PlayerExitX);
				Player1.setY(PlayerExitY);
			}
		}
		LevelGraphicList.add(Player1);
		return LevelGraphicList;
	}
	private ArrayList<Graphic> getLevelGraphicList(){
		LevelGraphicList = new ArrayList <Graphic>();
		//Adds all graphics to arraylist
		LevelGraphicList.addAll(LevelMap.getMapGraphicMultiThreaded());
		//Reset map graphic for memory performance increase
		LevelMap.setMapGraphic(null);
		//reset all of map clutter items to be invisible
		for(int i = 0; i < Clutter.getMapClutterList().size(); i++){
			if(Clutter.getMapClutterList().get(i) instanceof Visitable){
				((Visitable)Clutter.getMapClutterList().get(i)).setVisited(false);
			}
		}
		LevelGraphicList.addAll(Clutter.getMapClutterList());
		return LevelGraphicList;
	}
	public void setLevelGraphicList(ArrayList<Graphic> GraphicList){
		this.LevelGraphicList = GraphicList;
	}
	public MapGenerator getLevelMap(){
		return LevelMap;
	}
	public int getPlayerExitX() {
		return PlayerExitX;
	}

	public void setPlayerExitX(int playerExitX) {
		PlayerExitX = playerExitX;
	}

	public int getPlayerExitY() {
		return PlayerExitY;
	}

	public void setPlayerExitY(int playerExitY) {
		PlayerExitY = playerExitY;
	}

}
