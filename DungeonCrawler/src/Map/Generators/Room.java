/**
 * 
 */
package Map.Generators;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

/**
 * @author Porter
 *
 */
public class Room {

	private static final int MAXROOMSIZE = 4;
	private static final int MINROOMSIZE = 3;
	
	private int SizeX, SizeY, MaxRangeX, MaxRangeY;
	private Rectangle Room;
	private Point Center;
	private boolean InMap;
	/**
	 * Constructor to randomly generate a room
	 */
	public Room(int MaxRangeX, int MaxRangeY) {
		this.MaxRangeX = MaxRangeX;
		this.MaxRangeY = MaxRangeY;
		this.InMap = false;
		GenerateRandomRoom();
	}

	/**
	 * Generates a random room within class ranges
	 */
	public void GenerateRandomRoom(){
		Random RandomNumGen = new Random();
		//Set Random size for the room
		this.SizeX = RandomNumGen.nextInt(MAXROOMSIZE) + MINROOMSIZE;
		this.SizeY = RandomNumGen.nextInt(MAXROOMSIZE) + MINROOMSIZE;
		//makes a new room at a random point
		Room = new Rectangle(RandomNumGen.nextInt(MaxRangeX) + 1, RandomNumGen.nextInt(MaxRangeY) + 1, this.SizeX, this.SizeY);
		//Keeps trying to generate a room until one is vaild
		while(Room.getX() + Room.getWidth() > MaxRangeX - 1 || Room.getY() + Room.getHeight() > MaxRangeY - 1){
			this.SizeX = RandomNumGen.nextInt(MAXROOMSIZE) + MINROOMSIZE;
			this.SizeY = RandomNumGen.nextInt(MAXROOMSIZE) + MINROOMSIZE;
			Room = new Rectangle(RandomNumGen.nextInt(MaxRangeX) + 1, RandomNumGen.nextInt(MaxRangeY) + 1, this.SizeX, this.SizeY);
		}
		//Sets the center of the room
		//Center = new Point((int)((Room.getX() + Room.getWidth()) / 2), (int)((Room.getY() + Room.getHeight()) / 2));
		Center = new Point((int)Room.getCenterX(), (int) Room.getCenterY());
	}

	public Rectangle getRoom() {
		return Room;
	}

	public void setRoom(Rectangle room) {
		Room = room;
	}

	public boolean isInMap() {
		return InMap;
	}

	public void setInMap(boolean inMap) {
		InMap = inMap;
	}

	public Point getCenter() {
		return Center;
	}
}
