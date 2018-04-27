package Core;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class ViewPort {
	private Rectangle View;
	/**
	 * Takes a starting x, starting y, width, height, minx, miny, maxx, maxy, and panel to add it too
	 * @param x starting x
	 * @param y
	 * @param width
	 * @param height
	 * @param MinX
	 * @param MinY
	 * @param MaxX
	 * @param MaxY
	 * @param Panel
	 */
	public ViewPort(int x, int y, int width, int height, int MinX, int MinY, int MaxX, int MaxY, JPanel Panel) {
		// TODO Auto-generated constructor stub
		setView(new Rectangle(x, y, width, height));
		Panel.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
				//int Speed = 10;
				/*
				if(e.getKeyCode() == KeyEvent.VK_LEFT){
					if(View.getX() > MinX){
						View.setLocation((int)View.getX() - Speed, (int)View.getY());
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_RIGHT){
					if(View.getX() + View.getWidth() < MaxX){
					View.setLocation((int)View.getX() + Speed, (int)View.getY());
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_DOWN){
					if(View.getY() + View.getHeight() < MaxY){
						View.setLocation((int)View.getX(), (int)View.getY() + Speed);
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_UP){
					if(View.getY() > MinY){
						View.setLocation((int)View.getX(), (int)View.getY() - Speed);
					}
				}
				*/
				//System.out.println(View.toString());
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	public boolean ViewportIntersection(Rectangle Rec){
		if(Rec.intersects(View)){
			return true;
		}
		return false;
	}
	public Rectangle getView() {
		return View;
	}
	public void setView(Rectangle view) {
		View = view;
	}
}
