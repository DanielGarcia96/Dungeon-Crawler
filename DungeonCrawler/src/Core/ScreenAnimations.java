package Core;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class ScreenAnimations {
	private JPanel Screen;
	private boolean LoadingAnimationOutReset, LoadingAnimationInReset;
	private boolean LoadingAnimationOut, LoadingAnimationIn;
	private boolean TOut, TIn;
	private int LoadingSize;
	private ArrayList<Rectangle> SquareList;
	
	public ScreenAnimations(){
		LoadingAnimationOutReset = true; 
		LoadingAnimationInReset = true;
		TOut = false;
		TIn = false;
	}
	public ArrayList<Rectangle> LoadingAnimationIn(JPanel Screen){
		SquareList = new ArrayList<Rectangle>();
			if(LoadingSize == 0 && LoadingAnimationInReset == true){
				LoadingSize = 0;
				LoadingAnimationInReset = false;
			}
			Ellipse2D.Double circle = new Ellipse2D.Double(Screen.getWidth()/2 - LoadingSize/2, Screen.getHeight()/2 - LoadingSize/2, LoadingSize, LoadingSize);
			for(int y = 0; y < Screen.getHeight(); y += 32){
				for(int x = 0; x < Screen.getWidth(); x += 32){
					if(!circle.intersects(x, y, 32, 32)){
						SquareList.add(new Rectangle(x, y, 32, 32));
					}
				}
			}
			if(Screen.getWidth() > Screen.getHeight()){
				LoadingSize += Screen.getWidth()/30;
				if(LoadingSize > Screen.getWidth() + Screen.getWidth()/2){
					TIn = true;
				}
				if(LoadingSize > Screen.getWidth() + Screen.getWidth()/2 + Screen.getWidth()/2){
					LoadingAnimationIn = false;
					LoadingAnimationInReset = true;
					LoadingSize = 0;
				}
			}else{
				LoadingSize += Screen.getHeight()/30;
				if(LoadingSize > Screen.getHeight() + Screen.getHeight()/2){
					TIn = true;
				}
				if(LoadingSize > Screen.getHeight() + Screen.getHeight()/2 + Screen.getWidth()/2){
					LoadingAnimationIn = false;
					LoadingAnimationInReset = true;
					LoadingSize = 0;
				}
			}
				return SquareList;
	}
	public ArrayList<Rectangle> LoadingAnimationOut(JPanel Screen){
		SquareList = new ArrayList<Rectangle>();
			if(LoadingSize == 0 && LoadingAnimationOutReset == true){
				if(Screen.getWidth() > Screen.getHeight()){
					LoadingSize = Screen.getWidth();
				}else{
					LoadingSize = Screen.getHeight();
				}
				LoadingAnimationOutReset = false;
			}
			Ellipse2D.Double circle = new Ellipse2D.Double(Screen.getWidth()/2 - LoadingSize/2, Screen.getHeight()/2 - LoadingSize/2, LoadingSize, LoadingSize);
			for(int y = 0; y < Screen.getHeight(); y += 32){
				for(int x = 0; x < Screen.getWidth(); x += 32){
					if(!circle.intersects(x, y, 32, 32)){
						SquareList.add(new Rectangle(x, y, 32, 32));
					}
				}
			}
			if(Screen.getWidth() > Screen.getHeight()){
				LoadingSize -= Screen.getWidth()/30;
				if(LoadingSize < -Screen.getWidth()/2){
					TOut = true;
				}
				if(LoadingSize < -Screen.getWidth()/2 -Screen.getWidth()/2){
					setLoadingAnimationOut(false);
					LoadingAnimationOutReset = true;
					LoadingSize = 0;
					LoadingAnimationInReset = true;
					LoadingAnimationIn = true;
				}
			}else{
				LoadingSize -= Screen.getHeight()/30;
				if(LoadingSize <= -Screen.getHeight()/2){
					TOut = true;
				}
				if(LoadingSize < -Screen.getHeight()/2-Screen.getHeight()/2){
					setLoadingAnimationOut(false);
					LoadingAnimationOutReset = true;
					LoadingSize = 0;
					LoadingAnimationInReset = true;
					LoadingAnimationIn = true;
				}
			}
			return SquareList;
	}
	public JPanel getScreen() {
		return Screen;
	}
	public void setScreen(JPanel screen) {
		Screen = screen;
	}
	public boolean isLoadingAnimationOut() {
		return LoadingAnimationOut;
	}
	public void setLoadingAnimationOut(boolean loadingAnimationOut) {
		LoadingAnimationOut = loadingAnimationOut;
	}
	public boolean isLoadingAnimationIn() {
		return LoadingAnimationIn;
	}
	public void setLoadingAnimationIn(boolean IsLoadingAnimationIn) {
		LoadingAnimationIn = IsLoadingAnimationIn;
	}
	public boolean isTOut() {
		return TOut;
	}
	public boolean isTIn() {
		return TIn;
	}
}
