package Map.Generators;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Path {

	private ArrayList <Point> Pathway;
	public Path(Point A, Point B) {
		// TODO Auto-generated constructor stub
		GenerateNewPathway(A, B);
	}
	public void GenerateNewPathway(Point A, Point B){
		Pathway = new ArrayList <Point>();
		int Number = 0;
		Pathway.add(A);
		Random RandomGen = new Random();
		//Loop While points are not connected
		while(!Pathway.get(Number).equals(B)){
			//Random chance to start going horizontal first or vertical
			if(RandomGen.nextBoolean()){
				//Loop until point ones x is equal to point twos x
				while(Pathway.get(Number).getX() != B.getX()){
					//if less then add 1 else subtract 1
					if(Pathway.get(Number).getX() < B.getX()){
						Pathway.add(new Point((int)Pathway.get(Number).getX() + 1, (int)Pathway.get(Number).getY()));
						Number++;
						//A.setLocation(A.getX() + 1, A.getY());
					}
					if(Pathway.get(Number).getX() > B.getX()){
						Pathway.add(new Point((int)Pathway.get(Number).getX() - 1, (int)Pathway.get(Number).getY()));
						Number++;
						//MapTileArray[(int)A.getY()][(int)A.getX()] = 2;
						//A.setLocation(A.getX() - 1, A.getY());
					}
				}
			}else{
				while(Pathway.get(Number).getY() != B.getY()){
					if(Pathway.get(Number).getY() < B.getY()){
						Pathway.add(new Point((int)Pathway.get(Number).getX(), (int)Pathway.get(Number).getY() + 1));
						Number++;
						//MapTileArray[(int)A.getY()][(int)A.getX()] = 2;
						//A.setLocation(A.getX(), A.getY() + 1);
					}
					if(Pathway.get(Number).getY() > B.getY()){
						Pathway.add(new Point((int)Pathway.get(Number).getX(), (int)Pathway.get(Number).getY() - 1));
						Number++;
						//MapTileArray[(int)A.getY()][(int)A.getX()] = 2;
						//A.setLocation(A.getX(), A.getY() - 1);
					}
				}
			}
		}
	}
	public ArrayList <Point> getPathway() {
		return Pathway;
	}
	public void setPathway(ArrayList <Point> pathway) {
		Pathway = pathway;
	}

}
