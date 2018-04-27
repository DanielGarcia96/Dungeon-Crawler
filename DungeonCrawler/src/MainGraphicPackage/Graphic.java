package MainGraphicPackage;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * Main class for all graphics in game
 * @author Porter
 *
 */
public class Graphic {

	private int x, y, width, height;
	private BufferedImage MainImage;
	private Rectangle CollisionBox;
	public Graphic() {
		
	}
	/**
	 * Constructor for Filename and an x and y
	 * @param FileName Name of image to use
	 * @param x X Coordinate
	 * @param y Y Coordinate
	 */
	public Graphic(String ImageFileName, int x, int y) {
		this.x = x;
		this.y = y;
		try {
			this.setMainImage(ImageIO.read(new File(ImageFileName)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.CollisionBox = new Rectangle(x, y, MainImage.getWidth(), MainImage.getHeight());
		width = (int) CollisionBox.getWidth();
		height = (int) CollisionBox.getHeight();
		
		//Makes Graphic Render Faster
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice device = env.getDefaultScreenDevice();
	    GraphicsConfiguration config = device.getDefaultConfiguration();
	    BufferedImage buffy = config.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
	    Graphics2D g2d = (Graphics2D) buffy.getGraphics();
	    g2d.drawImage(MainImage, 0, 0, null);
	    g2d.dispose();
	}
	/**
	 * Constructor with just a file name, defaults x and y to 0,0
	 * @param FileName Name of image to use
	 */
	public Graphic(String ImageFileName) {
		try {
			this.setMainImage(ImageIO.read(new File(ImageFileName)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.CollisionBox = new Rectangle(x, y, MainImage.getWidth(), MainImage.getHeight());
		width = (int) CollisionBox.getWidth();
		height = (int) CollisionBox.getHeight();
	}
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		setCollisionBox(new Rectangle(this.x, this.y, this.width, this.height));
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		setCollisionBox(new Rectangle(this.x, this.y, this.width, this.height));
	}

	public Rectangle getCollisionBox() {
		return CollisionBox;
	}

	public void setCollisionBox(Rectangle collisionBox) {
		CollisionBox = collisionBox;
	}

	public BufferedImage getMainImage() {
		return MainImage;
	}

	public void setMainImage(BufferedImage mainImage) {
		MainImage = mainImage;
		this.CollisionBox = new Rectangle(x, y, MainImage.getWidth(), MainImage.getHeight());
		width = (int) CollisionBox.getWidth();
		height = (int) CollisionBox.getHeight();
	}

}
