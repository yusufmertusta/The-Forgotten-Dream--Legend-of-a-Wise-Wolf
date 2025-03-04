package java_game;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Tile implements Serializable{

	public transient BufferedImage image;
	public boolean collision;
	public Color color;
	public int x;
	public int y;
	public int width;
	public int height;

	
	public Tile(BufferedImage image, boolean collision, Color color) {
		this.image = image;
		this.collision = collision;
		this.color = color;
	}

	public Rectangle getHitbox() {
		return new Rectangle(x, y, width, height);
	}
}
