package java_game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

public class Enemy extends Entity implements Serializable {
	private static final long serialVersionUID = -472148433069044586L;
	                                              
	GamePanel gp;
	int enemyX, enemyY;
	int enemySpeed;
	Color enemyColor;
	String preDirection;
	public List<Enemy> allEnemies = new ArrayList<>();
	public boolean isAlive;
	int initialX;
	int initialY;
	public boolean damage;
	boolean running = true;
	private static final int FRAME_RATE = 60;
	public List<String> enemyImages = new ArrayList<>();

	private transient SoundManager damageSound = new SoundManager("/game/sounds/damage.wav");;

	private static final int DAMAGE_ANIMATION_DURATION = 60; 
	private int damageAnimationTimer = 0; 

	private long lastUpdateTime = System.nanoTime();

	public Enemy(GamePanel gp, int enemyX, int enemyY, int enemySpeed, String direction, Color enemyColor) {
		this.gp = gp;
		this.enemyX = enemyX;
		this.enemyY = enemyY;
		this.enemySpeed = enemySpeed;
		this.direction = direction;
		this.enemyColor = enemyColor;
		initialX = enemyX;
		initialY = enemyY;
		health = 3;
		hitbox = new Rectangle(enemyX+5, enemyY-5, gp.tileSize, gp.tileSize);
		if (enemyColor == Color.red)
			getEnemyImageRed();
		if (enemyColor == Color.blue)
			getEnemyImageBlue();
		if (enemyColor == Color.orange) {
			getEnemyImageOrange();
			health=999999999;
		}
		this.isAlive = true;
		allEnemies.add(this);
	}

	public int getHealth() {
		return health;
	}

	public void applyGravity() {
		preDirection = direction;
		direction = "gravity";
		gp.cChecker.checkTile(this);

		if (!collisionOn && !gp.tileM.isCharacterOnGround(enemyX, enemyY)) {
			enemyY += gravity;
		} else if (!gravityColl && !gp.tileM.isCharacterOnGround(enemyX, enemyY)) {
			enemyY += gravity;
		}
		direction = preDirection;
		hitbox.setLocation(enemyX-20, enemyY);

	}

	public void getEnemyImageBlue() {
		try {

			e1 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_blue_08.png"));
			enemyImages.add("/game/wolf/wolf_run_1.png");
			e2 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_blue_09.png"));
			enemyImages.add("/game/wolf/wolf_run_2.png");
			e3 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_blue_10.png"));
			enemyImages.add("/game/wolf/wolf_run_3.png");
			e4 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_blue_11.png"));
			enemyImages.add("/game/wolf/wolf_run_4.png");
			e5 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_blue_12.png"));
			enemyImages.add("/game/wolf/wolf_run_5.png");

			eL1 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_blue_left_08.png"));
			enemyImages.add("/game/wolf/wolf_runLeft_1.png");

			eL2 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_blue_left_09.png"));
			enemyImages.add("/game/wolf/wolf_runLeft_2.png");

			eL3 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_blue_left_10.png"));
			enemyImages.add("/game/wolf/wolf_runLeft_3.png");

			eL4 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_blue_left_11.png"));
			enemyImages.add("/game/wolf/wolf_runLeft_4.png");

			eL5 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_blue_left_12.png"));
			enemyImages.add("/game/wolf/wolf_runLeft_5.png");

			eD1 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_blue_15.png"));
			enemyImages.add("/game/wolf/wolf_damage_1.png");
			eD2 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_blue_16.png"));
			enemyImages.add("/game/wolf/wolf_damage_2.png");
			eD3 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_blue_17.png"));
			enemyImages.add("/game/wolf/wolf_damage_3.png");
			eD4 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_blue_18.png"));
			enemyImages.add("/game/wolf/wolf_damage_4.png");

			eLD1 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_blue_left_16.png"));
			enemyImages.add("/game/wolf/wolf_damageLeft_1.png");
			eLD2 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_blue_left_17.png"));
			enemyImages.add("/game/wolf/wolf_damageLeft_2.png");
			eLD3 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_blue_left_18.png"));
			enemyImages.add("/game/wolf/wolf_damageLeft_3.png");
			eLD4 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_blue_left_19.png"));
			enemyImages.add("/game/wolf/wolf_damageLeft_4.png");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getEnemyImageRed() {
		try {

			e1 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_red_08.png"));
			enemyImages.add("/game/wolf/wolf_run_1.png");
			e2 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_red_09.png"));
			enemyImages.add("/game/wolf/wolf_run_2.png");
			e3 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_red_10.png"));
			enemyImages.add("/game/wolf/wolf_run_3.png");
			e4 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_red_11.png"));
			enemyImages.add("/game/wolf/wolf_run_4.png");
			e5 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_red_12.png"));
			enemyImages.add("/game/wolf/wolf_run_5.png");

			eL1 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_red_left_08.png"));
			enemyImages.add("/game/wolf/wolf_runLeft_1.png");

			eL2 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_red_left_09.png"));
			enemyImages.add("/game/wolf/wolf_runLeft_2.png");

			eL3 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_red_left_10.png"));
			enemyImages.add("/game/wolf/wolf_runLeft_3.png");

			eL4 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_red_left_11.png"));
			enemyImages.add("/game/wolf/wolf_runLeft_4.png");

			eL5 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_red_left_12.png"));
			enemyImages.add("/game/wolf/wolf_runLeft_5.png");

			eD1 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_red_15.png"));
			enemyImages.add("/game/wolf/wolf_damage_1.png");
			eD2 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_red_16.png"));
			enemyImages.add("/game/wolf/wolf_damage_2.png");
			eD3 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_red_17.png"));
			enemyImages.add("/game/wolf/wolf_damage_3.png");
			eD4 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_red_18.png"));
			enemyImages.add("/game/wolf/wolf_damage_4.png");

			eLD1 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_red_left_16.png"));
			enemyImages.add("/game/wolf/wolf_damageLeft_1.png");
			eLD2 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_red_left_17.png"));
			enemyImages.add("/game/wolf/wolf_damageLeft_2.png");
			eLD3 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_red_left_18.png"));
			enemyImages.add("/game/wolf/wolf_damageLeft_3.png");
			eLD4 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_red_left_19.png"));
			enemyImages.add("/game/wolf/wolf_damageLeft_4.png");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getEnemyImageOrange() {
		try {

			e1 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_orange_08.png"));
			enemyImages.add("/game/wolf/wolf_run_1.png");
			e2 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_orange_09.png"));
			enemyImages.add("/game/wolf/wolf_run_2.png");
			e3 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_orange_10.png"));
			enemyImages.add("/game/wolf/wolf_run_3.png");
			e4 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_orange_11.png"));
			enemyImages.add("/game/wolf/wolf_run_4.png");
			e5 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_orange_12.png"));
			enemyImages.add("/game/wolf/wolf_run_5.png");

			eL1 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_orange_left_08.png"));
			enemyImages.add("/game/wolf/wolf_runLeft_1.png");

			eL2 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_orange_left_09.png"));
			enemyImages.add("/game/wolf/wolf_runLeft_2.png");

			eL3 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_orange_left_10.png"));
			enemyImages.add("/game/wolf/wolf_runLeft_3.png");

			eL4 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_orange_left_11.png"));
			enemyImages.add("/game/wolf/wolf_runLeft_4.png");

			eL5 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_orange_left_12.png"));
			enemyImages.add("/game/wolf/wolf_runLeft_5.png");

			eD1 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_orange_15.png"));
			enemyImages.add("/game/wolf/wolf_damage_1.png");
			eD2 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_orange_16.png"));
			enemyImages.add("/game/wolf/wolf_damage_2.png");
			eD3 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_orange_17.png"));
			enemyImages.add("/game/wolf/wolf_damage_3.png");
			eD4 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_orange_18.png"));
			enemyImages.add("/game/wolf/wolf_damage_4.png");

			eLD1 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_orange_left_16.png"));
			enemyImages.add("/game/wolf/wolf_damageLeft_1.png");
			eLD2 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_orange_left_17.png"));
			enemyImages.add("/game/wolf/wolf_damageLeft_2.png");
			eLD3 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_orange_left_18.png"));
			enemyImages.add("/game/wolf/wolf_damageLeft_3.png");
			eLD4 = ImageIO.read(getClass().getResourceAsStream("/game/wolf/wolf_orange_left_19.png"));
			enemyImages.add("/game/wolf/wolf_damageLeft_4.png");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getDamage() {
		boolean damaged = false; 
		for (Bullet bullet : Bullet.bullets) {
			if (bullet.bulletColor != enemyColor) {
				if (bullet.isActive() && bullet.hitbox.intersects(hitbox)) {
					health--;
					damaged = true;
					damageSound.play();
					bullet.bulletImage = null;
					bullet.setActive(false); 
				}
			} else if (bullet.bulletColor == enemyColor) {
				if (bullet.isActive() && bullet.hitbox.intersects(hitbox)) {
					health++;
					damageSound.play();
					damaged = true;
					bullet.bulletImage = null;
					bullet.setActive(false);
				}
			}
			if (damaged) {
				damage = true;
				running = false; 
				spriteNumD = 1; 
			} else {
				damage = false;
				running = true;
			} 
			if (health <= 0) {
				if (isAlive)
					gp.player.score += 10;
				isAlive = false; 
				hitbox = new Rectangle(0, 0, 0, 0); 
			}
		}
	}

	public List<Enemy> getAllEnemies() {
		return allEnemies;
	}

	public void resetToStartPosition() {

		enemyX = initialX;
		enemyY = initialY;

		health = 3; 
		isAlive = true;
		hitbox = new Rectangle(enemyX, enemyY, gp.tileSize, gp.tileSize); // Reset hitbox
	}

	public Rectangle getHitbox() {
		return hitbox;
	}

	public void setHitbox(Rectangle hitbox) {
		this.hitbox = hitbox;
	}

	private BufferedImage enemyLeftSprite() {
		BufferedImage[] enemyLeftSprites = { eL1, eL2, eL3, eL4, eL5 };
		return enemyLeftSprites[spriteNum - 1];
	}

	private BufferedImage enemyRightSprite() {
		BufferedImage[] enemyRightSprites = { e1, e2, e3, e4, e5 }; 
		return enemyRightSprites[spriteNum - 1];
	}

	private BufferedImage enemyRightDamageSprite() {
		BufferedImage[] enemyRightSprites = { eD1, eD2, eD3, eD4 }; 
		int index = spriteNum - 1;
		if (index >= 0 && index < enemyRightSprites.length) {
			return enemyRightSprites[index];
		} else {
			
			return enemyRightSprites[0];
		}
	}

	private BufferedImage enemyLeftDamageSprite() {
		BufferedImage[] enemyLeftDamageSprites = { eLD1, eLD2, eLD3, eLD4 }; 
		int index = spriteNumD - 1; 
		if (index >= 0 && index < enemyLeftDamageSprites.length) {
			return enemyLeftDamageSprites[index];
		} else {
			return enemyLeftDamageSprites[0];
		}
	}

	public void update() {
		applyGravity();

		getDamage();

		if (!isAlive) {
			allEnemies.remove(this); 
			return;
		}

		long now = System.nanoTime();
		double elapsedTime = (now - lastUpdateTime) / 1e9; 

		if (elapsedTime > (10.0 / FRAME_RATE)) {
			if (direction.equals("left") || direction.equals("right")) {
				if (!damage) {
					spriteNum++;
					if (spriteNum > 5) {
						spriteNum = 1; 
					}
				}
			}
			if (damage) {
				damageAnimationTimer++;
				if (damageAnimationTimer >= DAMAGE_ANIMATION_DURATION) {
					spriteNumD++;

					damage = false;
					running = true;
					damageAnimationTimer = 0; 
				}
			}

			lastUpdateTime = now;
		}

		if (direction == "left") {

			if (enemyX - enemySpeed > 0 && gp.tileM.isCharacterOnGround(enemyX, enemyY) && !collisionOn)
				enemyX -= enemySpeed; 
			if (enemyX - (enemySpeed + 0.3) < 0)
				direction = "right";
		}
		if (direction == "right") {
			if (enemyX + enemySpeed < gp.screenWidth - 25 && gp.tileM.isCharacterOnGround(enemyX, enemyY)
					&& !collisionOn)
				enemyX += enemySpeed; 
			if (!(enemyX + enemySpeed < gp.screenWidth - 25))
				direction = "left";
		}

	}
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
	}
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
	}

	public void draw(Graphics2D g2) {

		BufferedImage image = null;

		switch (direction) {
		case "left":
			if (damage) {
				image = enemyLeftDamageSprite();
			} else if (running)
				image = enemyRightSprite(); 
			break;
		case "right":
			if (damage) {
				image = enemyRightDamageSprite();
			} else if (running)
				image = enemyLeftSprite(); 
			break;
		case "gravity":
			image = eFall1;
			break;
		}

		if (isAlive) {

			Font oldFont = g2.getFont();
			Font newFont = new Font(oldFont.getName(), Font.BOLD, 20); 
			g2.setFont(newFont);

			g2.drawImage(image, enemyX - gp.tileSize, enemyY - gp.tileSize + 20, gp.tileSize * 2, gp.tileSize * 2,
					null);
			if(enemyColor!=Color.orange)
			g2.drawString(Integer.toString(health), enemyX, enemyY - 5);
		}
	}

}
