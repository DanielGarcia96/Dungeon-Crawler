package MainGraphicPackage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class AnimatedGraphic extends Graphic{

	private ArrayList <BufferedImage> MainImageArray;
	private int CurrentFrame, FrameRate;
	private boolean Paused, LoopOnce, LoopDone;
	
	/**
	 * Used to make a Multi-Frame animated graphic
	 * @param FileNameList
	 * @param x
	 * @param y
	 */
	public AnimatedGraphic(ArrayList <String> FileNameList, int x, int y, int FrameRate) {
		super(FileNameList.get(0), x, y);
		MainImageArray = new ArrayList <BufferedImage>();
		for(int i =0; i < FileNameList.size(); i++){
			try {
				MainImageArray.add((ImageIO.read(new File(FileNameList.get(i)))));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.FrameRate = FrameRate;
	}
	/**
	 * Used to make a single frame animated graphic
	 * Basically an Graphic
	 * @param FileNameList
	 * @param x
	 * @param y
	 */
	public AnimatedGraphic(String FileNameList, int x, int y) {
		super(FileNameList, x, y);
		MainImageArray = new ArrayList <BufferedImage>();
			try {
				MainImageArray.add((ImageIO.read(new File(FileNameList))));
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	/**
	 * Used to make a animated Graphic out of a directory
	 * @param DirectoryPath
	 * @param x
	 * @param y
	 * @param FrameRate
	 * @return
	 */
	public AnimatedGraphic(String DirectoryPath, int x, int y, int FrameRate){
		File RandomClutterDirectory;
		File [] RandomClutterFiles;
		MainImageArray = new ArrayList <BufferedImage>();
		//Find the Directory path
		RandomClutterDirectory = new File(System.getProperty("user.dir") + DirectoryPath);
		if(RandomClutterDirectory.isDirectory()){
			//add all files in directory to array
			RandomClutterFiles = RandomClutterDirectory.listFiles();
			for(int i = 0; i < RandomClutterFiles.length; i++){
				//if there is a sub directory throw an exception
				if(RandomClutterFiles[i].isDirectory()){
					try {
						throw new Exception("Animations Cannot have Sub-Directories!");
					} catch (Exception e) {
						e.printStackTrace();
						System.exit(1);
					}
				}
				//add Image to main array of images
				try {
					MainImageArray.add((ImageIO.read(RandomClutterFiles[i])));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			try {
				throw new Exception("You did not give a Directory Path");
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.exit(1);
		}
		if(MainImageArray.size() == 0){
			try {
				throw new Exception("No Images found!");
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.exit(1);
		}
		//set the frame rate
		this.FrameRate = FrameRate;
		this.setX(x);
		this.setY(y);
		//set super main m=image to the first file
		super.setMainImage(MainImageArray.get(0));
	}
	public void NextFrame(){
		if(CurrentFrame < MainImageArray.size() - 1){
			CurrentFrame++;
			LoopDone = false;
		}else{
			CurrentFrame = 0;
			LoopDone = true;
		}
		super.setMainImage(MainImageArray.get(CurrentFrame));
	}
	public int getFrameRate() {
		return FrameRate;
	}
	public void setFrameRate(int frameRate) {
		FrameRate = frameRate;
	}
	public boolean isPaused() {
		return Paused;
	}
	public void setPaused(boolean paused) {
		Paused = paused;
	}
	public boolean isLoopDone() {
		return LoopDone;
	}
	public void setLoopDone(boolean loopDone) {
		LoopDone = loopDone;
	}
	public boolean isLoopOnce() {
		return LoopOnce;
	}
	public void setLoopOnce(boolean loop) {
		LoopOnce = loop;
	}
	/**
	 * To change animations with a directory
	 * @param DirectoryPath
	 */
	public void setImages(String DirectoryPath){
		File RandomClutterDirectory;
		File [] RandomClutterFiles;
		MainImageArray = new ArrayList <BufferedImage>();
		//Find the Directory path
		RandomClutterDirectory = new File(System.getProperty("user.dir") + DirectoryPath);
		if(RandomClutterDirectory.isDirectory()){
			//add all files in directory to array
			RandomClutterFiles = RandomClutterDirectory.listFiles();
			for(int i = 0; i < RandomClutterFiles.length; i++){
				//if there is a sub directory throw an exception
				if(RandomClutterFiles[i].isDirectory()){
					try {
						throw new Exception("Animations Cannot have Sub-Directories!");
					} catch (Exception e) {
						e.printStackTrace();
						System.exit(1);
					}
				}
				//add Image to main array of images
				try {
					MainImageArray.add((ImageIO.read(RandomClutterFiles[i])));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			try {
				throw new Exception("You did not give a Directory Path");
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.exit(1);
		}
		if(MainImageArray.size() == 0){
			try {
				throw new Exception("No Images found!");
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.exit(1);
		}
		//set super main m=image to the first file
		super.setMainImage(MainImageArray.get(0));
	}
	/*
	//Had to add this function to change animations for the enemy
	public void setImages(ArrayList<String> images)  {
		MainImageArray.clear();
		try {
			this.setMainImage(ImageIO.read(new File(images.get(0))));
		} catch(IOException e)  {
			e.printStackTrace();
		}
		
		for(String s : images)  {
			try {
				MainImageArray.add((ImageIO.read(new File(s))));
			} catch (IOException e)  {
				e.printStackTrace();
			}
		}
	}
	*/
}
