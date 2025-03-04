package java_game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.text.html.HTMLDocument.Iterator;

public class Bullet extends Entity implements Serializable{

	private int x;
	private int y;
	private int speed;
	private int directionX;
	GamePanel gp;
	public Rectangle hitbox;
	private boolean isActive;
	public static List<Bullet> bullets = new ArrayList<>();
	public Color bulletColor;
	public transient BufferedImage bulletImage;
	public List<String> bulletImageP = new ArrayList<>();


	public Bullet(GamePanel gp, int worldX, int worldY, int playerDirectionX, List<Bullet> bullets, Color playerColor) {
		this.x = worldX+10;
		this.y = worldY+10;
		this.speed = 5;
		this.directionX = playerDirectionX;
		this.hitbox = new Rectangle(x, y, 10, 23);
		this.isActive = true;
		this.bulletColor = playerColor;
		loadBulletImage();
		bullets.add(this);
	}
	
	private void loadBulletImage() {
	    String imageName="/game/bullet/red_bullet.png";
	    if (bulletColor == Color.RED) {
	        imageName = "/game/bullet/red_bullet.png";
	    } else if (bulletColor == Color.BLUE) {
	    	imageName = "/game/bullet/Rock2.png";
	    }
	    try {
	    	bulletImage = ImageIO.read(getClass().getResource(imageName));           
	        bulletImageP.add(imageName);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public boolean hasHitSomething(Rectangle otherHitbox) {
		return hitbox.intersects(otherHitbox);
	}

	public void update() {
		if (directionX == 1)
			x += speed;
		if (directionX == -1)
			x -= speed;
		hitbox.setLocation(x, y);

	}


	public void draw(Graphics2D g2) {
		g2.drawImage(bulletImage, x+10, y+10, 44, 15, null);

	}
	public Rectangle getHitbox() {
		return hitbox;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isActive() {
		return isActive;
	}

}
