package Light;

import java.awt.Point;
import java.util.ArrayList;

import MainGraphicPackage.Graphic;
import MainGraphicPackage.LightSource;
import MainGraphicPackage.Lightable;
import MainGraphicPackage.Visitable;
import Map.Clutter.Clutter;
/**
 * This Creates Game Light
 * ---Work in progress---
 * -Can be optimized-
 * @author Porter
 *
 */
public class LightingEngine {

	private ArrayList <Graphic> LightSourceList;
	public LightingEngine(ArrayList <Graphic> MainList) {
	//adds all light sources to a alight source list	
	LightSourceList = new ArrayList <Graphic>();
		for(int i = 0; i < MainList.size(); i++){
			if(MainList.get(i) instanceof LightSource){
				LightSourceList.add(MainList.get(i));
			}
		}
		//Set the light levels for all Tiles
		SetLightLevels(MainList);
	}
	private void SetLightLevels(ArrayList <Graphic> MainList){
		//Loop through mainlist
		for(int i = 0; i < MainList.size(); i++){
			if(MainList.get(i) instanceof Lightable){
				//Checks if tile is in view of the player
				if(isInView(MainList.get(i))){
					//if in view set light level to 100 and that it is visted
					((Lightable) MainList.get(i)).setLightLevel(100);
					if(MainList.get(i) instanceof Visitable){
						((Visitable) MainList.get(i)).setVisited(true);
					}
					//Make a gradual change between tiles
					if(DistanceView(MainList.get(i)) > 32){
						((Lightable) MainList.get(i)).setLightLevel(150);
					}
					if(DistanceView(MainList.get(i)) > 64){
						((Lightable) MainList.get(i)).setLightLevel(175);
					}
				}else if(MainList.get(i) instanceof Visitable){
					if(((Visitable) MainList.get(i)).isVisited() == true){
						//Grey tile if visited
						((Lightable) MainList.get(i)).setLightLevel(200);
					}else{
						//make tile black if haven't visited
						((Lightable) MainList.get(i)).setLightLevel(255);
					}
				}
				
			}
			
			//Override basic lighting for a brighter object look
			if(MainList.get(i) instanceof Clutter){
				Clutter Clutter1 = (Clutter) MainList.get(i);
				if(isInView(Clutter1)){
					Clutter1.setLightLevel(0);
					Clutter1.setVisited(true);
				}else if(Clutter1.isVisited() == true){
					Clutter1.setLightLevel(40);
				}else{
					Clutter1.setLightLevel(255);
				}
			}
			
		}
	}
	/**
	 * Checks if graphic is in view of any object in lightsource list
	 * @param g
	 * @return
	 */
	public boolean isInView(Graphic g){
		//loop through light source list
		for(int i = 0; i < LightSourceList.size(); i++){
			LightSource L1 = (LightSource) LightSourceList.get(i);
			//checks a box around the lightsource
			if(g.getX() < LightSourceList.get(i).getX() + LightSourceList.get(i).getMainImage().getWidth()/2 + L1.getLightRadius() 
					&& g.getX() > LightSourceList.get(i).getX() + LightSourceList.get(i).getMainImage().getWidth()/2 - L1.getLightRadius()  
					&& g.getY() < LightSourceList.get(i).getY() + LightSourceList.get(i).getMainImage().getHeight()/2 + L1.getLightRadius()  
					&& g.getY() > LightSourceList.get(i).getY() + LightSourceList.get(i).getMainImage().getHeight()/2 - L1.getLightRadius() ){
				return true;
			}
		}
		return false;
	}
	/**
	 * Finds the closest distance to a lightsource
	 * @param g
	 * @return
	 */
	private double DistanceView(Graphic g){
		//deault the closest distance to the first lightsource
		Point LightSourceCenter = new Point(LightSourceList.get(0).getX() + LightSourceList.get(0).getMainImage().getWidth()/2 , LightSourceList.get(0).getY() + LightSourceList.get(0).getMainImage().getHeight()/2);
		Point GraphicCenter = new Point((int)g.getCollisionBox().getCenterX(), (int)g.getCollisionBox().getCenterY());
		double ClosestDistance = LightSourceCenter.distance(GraphicCenter);
		//loop through the list of light sources
		for(int i = 0; i < LightSourceList.size(); i++){
			//find the closest distance between graphic and light source
			LightSourceCenter = new Point(LightSourceList.get(i).getX() + LightSourceList.get(i).getMainImage().getWidth()/2 , LightSourceList.get(i).getY() + LightSourceList.get(i).getMainImage().getHeight()/2);
			GraphicCenter = new Point((int)g.getCollisionBox().getCenterX(), (int)g.getCollisionBox().getCenterY());
			if(ClosestDistance > LightSourceCenter.distance(GraphicCenter)){
				ClosestDistance = LightSourceCenter.distance(GraphicCenter);
			}
		}
		return ClosestDistance;
	}

}
