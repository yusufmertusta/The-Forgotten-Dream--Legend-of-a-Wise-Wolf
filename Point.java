package java_game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Point implements Serializable {
	private static final long serialVersionUID = -5407174829853788672L;

	GamePanel gp;
	private int x;
	private int y;
	private boolean collected;
	public transient BufferedImage point;

	public Point(GamePanel gp, int x, int y) {
		this.gp = gp;
		this.x = x;
		this.y = y;
		this.collected = false;
		try {
			this.point = ImageIO.read(getClass().getResourceAsStream("/game/tiles/point.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		out.writeObject(x);
		out.writeObject(y);
		if (gp != null) {
			out.writeObject(true); 
			out.writeObject(gp.toString()); 
		} else {
			out.writeObject(false); 
		}

	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
	    gp = (GamePanel) in.readObject(); 
		x = (int) in.readObject();
		y = (int) in.readObject();
	}

	public void draw(Graphics2D g2) {
		if (!collected) {

			g2.drawImage(point, x, y, gp.tileSize * 2 / 3, gp.tileSize * 2 / 3, null);

		}
	}

	public boolean isCollected() {
		return collected;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, gp.tileSize / 2, gp.tileSize / 2);
	}

	public void collect() {
		this.collected = true;
	}
}
