package Map.Generators;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class River{
	
	private ArrayList <Point> RiverPath;
	private ArrayList <Point> NewRiverPath;
	
	public River(Point A, Point B, int Displacement) {
		RiverPath = new ArrayList<Point>();
		NewRiverPath = new ArrayList<Point>();
		RiverPath.add(A);
		RiverPath.add(B);
		MidPointDisplacement(Displacement);
	}

	private void MidPointDisplacement(double Displacement){
		while(Displacement > 0){
			NewRiverPath = new ArrayList<Point>();
			for(int i = 0; i < RiverPath.size() - 1; i++){
				NewRiverPath.add(SubPoint(RiverPath.get(i), ClosestPointInRiverArray(RiverPath.get(i)), (int)Displacement));
			}
			RiverPath.addAll(NewRiverPath);
			Displacement--;
			Collections.sort(RiverPath, new Comparator<Point>() {
				   public int compare(Point p1, Point p2) {
					   if(p1.getY() < p2.getY()){
						   return -1;
					   }
					   if(p1.getY() > p2.getY()){
						   return 1;
					   }
					return 0;
				   }
				});
		}
		
		for(int i = 0; i < RiverPath.size() - 1; i++){
			ConnectRiverPoints(RiverPath.get(i), RiverPath.get(i + 1));
		}
		RiverPath.addAll(NewRiverPath);
			
	}
	private Point SubPoint(Point A, Point B, int Displacement){
		int MidX, MidY;
		Point MidPoint = new Point();
		Random RandomNum = new Random();
		if(RandomNum.nextInt(2) == 0){
			MidX = (int)(A.getX() + B.getX())/2 + RandomNum.nextInt(Displacement);
		}else{
			MidX = (int)(A.getX() + B.getX())/2 - RandomNum.nextInt(Displacement);
		}
		MidY = (int)(A.getY() + B.getY())/2;
		MidPoint.setLocation(MidX, MidY);
		return MidPoint;
	}
	
	private Point ClosestPointInRiverArray(Point Origin){
		Point Closest = RiverPath.get(0);
		double Distance  = Origin.distance(Closest);
		for(int i = 0; i < RiverPath.size(); i++){
			if(Distance > Origin.distance(RiverPath.get(i)) || Distance == 0){
				Closest = RiverPath.get(i);
				Distance  = Origin.distance(Closest);
			}
		}
		return Closest;
	}
	
	private void ConnectRiverPoints(Point A, Point B){
		Point NextPoint = A;
		Random R = new Random();
		while(NextPoint.distance(B) != 0){
			if(R.nextBoolean()){
			if(NextPoint.getX() < B.getX()){
				NewRiverPath.add(NextPoint);
				NextPoint = new Point((int)NextPoint.getX() + 1, (int)NextPoint.getY());
			}else if(NextPoint.getX() > B.getX()){
				NewRiverPath.add(NextPoint);
				NextPoint = new Point((int)NextPoint.getX() - 1, (int)NextPoint.getY());
			}
			}else{
			if(NextPoint.getY() < B.getY()){
				NewRiverPath.add(NextPoint);
				NextPoint = new Point((int)NextPoint.getX(), (int)NextPoint.getY() + 1);
			}else if(NextPoint.getY() > B.getY()){
				NewRiverPath.add(NextPoint);
				NextPoint = new Point((int)NextPoint.getX(), (int)NextPoint.getY() - 1);
			}
			}
		}
	}
	public ArrayList <Point> getRiverPathArrayList(){
		return RiverPath;
	}
}
