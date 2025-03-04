package java_game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Timer;

public class Entity implements Serializable{
	private static final long serialVersionUID = -5443274418948468006L;

	public int gravity = 3;

	public int health;

	public int worldX, worldY;
	public int speed;
	public transient BufferedImage jump1, jump2, jump3, jump4, jump5, jump6, jump7, jump8;
	public transient BufferedImage i1, i2, i3, i4, down5, down6;
	public transient BufferedImage e1, e2, e3, e4, e5, eL1, eL2, eL3, eL4, eL5, eD1, eD2, eD3, eD4, eLD1, eLD2, eLD3, eLD4;
	public transient BufferedImage iL1, iL2, iL3, iL4,eFall1,eFall2,eFallLeft1,eFallLeft2;
	public transient BufferedImage left1, left2, left3, left4, left5, left6, left7, left8, left9, left10;
	public transient BufferedImage right1, right2, right3, right4, right5, right6, right7, right8, right9, right10;
	public String direction="right";

	public int spriteCounter = 0;
	public int spriteNumIdle = 1;
	public int spriteNumH = 1;
	public int spriteNumV = 1;
	public int spriteNum = 1;
	public int spriteNumD = 1;


	public boolean jumping = false;
	public Rectangle hitbox; 
	public boolean collisionOn = false;
	public boolean jumpColl = false;
	public boolean gravityColl = false;

	public int getGravity() {
		return gravity;
	}

	public void setGravity(int gravity) {
		this.gravity = gravity;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getWorldX() {
		return worldX;
	}

	public void setWorldX(int worldX) {
		this.worldX = worldX;
	}

	public int getWorldY() {
		return worldY;
	}

	public void setWorldY(int worldY) {
		this.worldY = worldY;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Rectangle getHitbox() {
		return hitbox;
	}

	public void setHitbox(Rectangle hitbox) {
		this.hitbox = hitbox;
	}

	public boolean isCollisionOn() {
		return collisionOn;
	}

	public void setCollisionOn(boolean collisionOn) {
		this.collisionOn = collisionOn;
	}

}
