package java_game;

import java.awt.Color;
import java.io.Serializable;
import java.util.List;

public class DataStorage implements Serializable {
	private static final long serialVersionUID = 1L;

	public int score;
	public int worldX, worldY;
	public boolean jumping;
	public String direction;
	public Color playerColor;
	public int playerDirectionX;
	public int jumpSpeed;
	public int gravity;
	public int speed;

	// public List<Enemy> enemies;
	//public List<Point> points;
	// public List<Bullet> bullets;

}
