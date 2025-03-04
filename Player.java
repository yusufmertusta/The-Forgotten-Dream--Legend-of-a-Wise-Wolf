package java_game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

public class Player extends Entity implements Serializable {
	GamePanel gp;
	KeyHandler keyH;
	public boolean jumping = false;
	public int jumpSpeed = 22;
	private boolean canShoot = true;
	public boolean a = false;
	int initialX;
	int initialY;
	public Color playerColor;
	public int score = 0;
	public List<Bullet> bullets = new ArrayList<>();
	public int playerDirectionX = 1;

	public List<String> playerImages = new ArrayList<>();

	private transient SoundManager collect = new SoundManager("/game/sounds/collect.wav");;

	private static final int FRAME_RATE = 60; // Frames per second

	private long lastUpdateTime = System.nanoTime();

	public Player(GamePanel gp, KeyHandler keyH, int worldX, int worldY, int speed) {
		this.gp = gp;
		this.keyH = keyH;
		this.worldX = worldX;
		this.worldY = worldY;
		this.speed = speed;
		initialX = worldX;
		initialY = worldY;
		hitbox = new Rectangle(worldX, worldY, 10, 30);
	}

	public void checkPointCollision(List<Point> points) {
		Rectangle playerRect = getHitbox();
		Iterator<Point> iterator = points.iterator();
		while (iterator.hasNext()) {
			Point point = iterator.next();
			Rectangle pointRect = point.getBounds();
			if (playerRect.intersects(pointRect)) {
				iterator.remove();
				score += 5;
				collect.setVolume(0.3f);
				collect.play();
			}
		}
	}

	public Rectangle getHitbox() {
		return hitbox;
	}

	public void shoot() {
		Bullet bullet = new Bullet(gp, worldX, worldY, playerDirectionX, Bullet.bullets, playerColor);
		bullets.add(bullet);
	}

	public void updateBullets() {
		for (int i = bullets.size() - 1; i >= 0; i--) {
			Bullet bullet = bullets.get(i);
			bullet.update();

		}
	}

	public boolean jumpOK() {
		if (gp.tileM.isCharacterOnGround(worldX, worldY))
			return true;
		return false;
	}

	public void idleCheck() {
		if (gp.tileM.isCharacterOnGround(worldX, worldY))
			direction = "idle";
	}

	public void jump() {
		if (worldY > 0 && worldY < gp.screenHeigth) {
			gravity = 3;
			gp.cChecker.checkTile(this);

			jumping = true;
			worldY -= jumpSpeed;
			if (jumpSpeed > 0)
				jumpSpeed--;
			jumping = false;
			if (jumpSpeed <= 0 && jumpOK()) {
				jumping = false;

				jumpSpeed = 22;
				if (playerColor == Color.MAGENTA)
					jumpSpeed = 30;
			}
			gravity = 4;
		}
	}

	public void applyGravity() {
		direction = "gravity";
		gp.cChecker.checkTile(this);

		if (!jumping && !collisionOn) {
			worldY += gravity;
		} else if (jumpColl && !gravityColl) {
			worldY += gravity;
		}
		if (gp.tileM.isCharacterOnGround(worldX, worldY))
			direction = "idle";
		hitbox.setLocation(worldX, worldY);

	}

	public void getPlayerImage(Color color) {
		if (color == color.RED) {
			try {

				jump1 = ImageIO.read(getClass().getResourceAsStream("/game/pink/j_01.png"));
				playerImages.add("/game/pink/j_01.png");
				jump2 = ImageIO.read(getClass().getResourceAsStream("/game/pink/j_02.png"));
				playerImages.add("/game/pink/j_02.png");
				jump3 = ImageIO.read(getClass().getResourceAsStream("/game/pink/j_03.png"));
				playerImages.add("/game/pink/j_03.png");
				jump4 = ImageIO.read(getClass().getResourceAsStream("/game/pink/j_04.png"));
				playerImages.add("/game/pink/j_04.png");
				jump5 = ImageIO.read(getClass().getResourceAsStream("/game/pink/j_05.png"));
				playerImages.add("/game/pink/j_05.png");
				jump6 = ImageIO.read(getClass().getResourceAsStream("/game/pink/j_06.png"));
				playerImages.add("/game/pink/j_06.png");
				jump7 = ImageIO.read(getClass().getResourceAsStream("/game/pink/j_07.png"));
				playerImages.add("/game/pink/j_07.png");
				jump8 = ImageIO.read(getClass().getResourceAsStream("/game/pink/j_08.png"));
				playerImages.add("/game/pink/j_08.png");

				i1 = ImageIO.read(getClass().getResourceAsStream("/game/pink/i1.png"));
				playerImages.add("/game/pink/i1.png");
				i2 = ImageIO.read(getClass().getResourceAsStream("/game/pink/i2.png"));
				playerImages.add("/game/pink/i2.png");
				i3 = ImageIO.read(getClass().getResourceAsStream("/game/pink/i3.png"));
				playerImages.add("/game/pink/i3.png");
				i4 = ImageIO.read(getClass().getResourceAsStream("/game/pink/i4.png"));
				playerImages.add("/game/pink/i4.png");

				iL1 = ImageIO.read(getClass().getResourceAsStream("/game/pink/IL_01.png"));
				playerImages.add("/game/pink/IL_01.png");
				iL2 = ImageIO.read(getClass().getResourceAsStream("/game/pink/IL_02.png"));
				playerImages.add("/game/pink/IL_02.png");
				iL3 = ImageIO.read(getClass().getResourceAsStream("/game/pink/IL_03.png"));
				playerImages.add("/game/pink/IL_03.png");
				iL4 = ImageIO.read(getClass().getResourceAsStream("/game/pink/IL_04.png"));
				playerImages.add("/game/pink/IL_04.png");

				right1 = ImageIO.read(getClass().getResourceAsStream("/game/pink/w_01.png"));
				playerImages.add("/game/pink/w_01.png");
				right2 = ImageIO.read(getClass().getResourceAsStream("/game/pink/w_02.png"));
				playerImages.add("/game/pink/w_02.png");
				right3 = ImageIO.read(getClass().getResourceAsStream("/game/pink/w_03.png"));
				playerImages.add("/game/pink/w_03.png");
				right4 = ImageIO.read(getClass().getResourceAsStream("/game/pink/w_04.png"));
				playerImages.add("/game/pink/w_04.png");
				right5 = ImageIO.read(getClass().getResourceAsStream("/game/pink/w_05.png"));
				playerImages.add("/game/pink/w_05.png");
				right6 = ImageIO.read(getClass().getResourceAsStream("/game/pink/w_06.png"));
				playerImages.add("/game/pink/w_06.png");

				left1 = ImageIO.read(getClass().getResourceAsStream("/game/pink/wL_01.png"));
				playerImages.add("/game/pink/wL_01.png");
				left2 = ImageIO.read(getClass().getResourceAsStream("/game/pink/wL_02.png"));
				playerImages.add("/game/pink/wL_02.png");
				left3 = ImageIO.read(getClass().getResourceAsStream("/game/pink/wL_03.png"));
				playerImages.add("/game/pink/wL_03.png");
				left4 = ImageIO.read(getClass().getResourceAsStream("/game/pink/wL_04.png"));
				playerImages.add("/game/pink/wL_04.png");
				left5 = ImageIO.read(getClass().getResourceAsStream("/game/pink/wL_05.png"));
				playerImages.add("/game/pink/wL_05.png");
				left6 = ImageIO.read(getClass().getResourceAsStream("/game/pink/wL_06.png"));
				playerImages.add("/game/pink/wL_06.png");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (color == color.BLUE) {
			try {

				jump1 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_Jump_8_01.png"));
				playerImages.add("/game/blue/Dude_Monster_Jump_8_01.png");
				jump2 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_Jump_8_02.png"));
				playerImages.add("/game/blue/Dude_Monster_Jump_8_02.png");
				jump3 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_Jump_8_03.png"));
				playerImages.add("/game/blue/Dude_Monster_Jump_8_03.png");
				jump4 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_Jump_8_04.png"));
				playerImages.add("/game/blue/Dude_Monster_Jump_8_04.png");
				jump5 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_Jump_8_05.png"));
				playerImages.add("/game/blue/Dude_Monster_Jump_8_05.png");
				jump6 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_Jump_8_06.png"));
				playerImages.add("/game/blue/Dude_Monster_Jump_8_06.png");
				jump7 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_Jump_8_07.png"));
				playerImages.add("/game/blue/Dude_Monster_Jump_8_07.png");
				jump8 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_Jump_8_08.png"));
				playerImages.add("/game/blue/Dude_Monster_Jump_8_08.png");

				i1 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_Idle_4_01.png"));
				playerImages.add("/game/blue/Dude_Monster_Idle_4_01.png");
				i2 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_Idle_4_02.png"));
				playerImages.add("/game/blue/Dude_Monster_Idle_4_02.png");
				i3 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_Idle_4_03.png"));
				playerImages.add("/game/blue/Dude_Monster_Idle_4_03.png");
				i4 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_Idle_4_04.png"));
				playerImages.add("/game/blue/Dude_Monster_Idle_4_04.png");

				iL1 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_IdleLeft_4_01.png"));
				playerImages.add("/game/blue/Dude_Monster_IdleLeft_4_01.png");
				iL2 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_IdleLeft_4_02.png"));
				playerImages.add("/game/blue/Dude_Monster_IdleLeft_4_02.png");
				iL3 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_IdleLeft_4_03.png"));
				playerImages.add("/game/blue/Dude_Monster_IdleLeft_4_03.png");
				iL4 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_IdleLeft_4_04.png"));
				playerImages.add("/game/blue/Dude_Monster_IdleLeft_4_04.png");

				right1 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_Walk_6_01.png"));
				playerImages.add("/game/blue/Dude_Monster_Walk_6_01.png");
				right2 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_Walk_6_02.png"));
				playerImages.add("/game/blue/Dude_Monster_Walk_6_02.png");
				right3 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_Walk_6_03.png"));
				playerImages.add("/game/blue/Dude_Monster_Walk_6_03.png");
				right4 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_Walk_6_04.png"));
				playerImages.add("/game/blue/Dude_Monster_Walk_6_04.png");
				right5 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_Walk_6_05.png"));
				playerImages.add("/game/blue/Dude_Monster_Walk_6_05.png");
				right6 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_Walk_6_06.png"));
				playerImages.add("/game/blue/Dude_Monster_Walk_6_06.png");

				left1 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_WalkLeft_6_01.png"));
				playerImages.add("/game/blue/Dude_Monster_WalkLeft_6_01.png");
				left2 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_WalkLeft_6_02.png"));
				playerImages.add("/game/blue/Dude_Monster_WalkLeft_6_02.png");
				left3 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_WalkLeft_6_03.png"));
				playerImages.add("/game/blue/Dude_Monster_WalkLeft_6_03.png");
				left4 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_WalkLeft_6_04.png"));
				playerImages.add("/game/blue/Dude_Monster_WalkLeft_6_04.png");
				left5 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_WalkLeft_6_05.png"));
				playerImages.add("/game/blue/Dude_Monster_WalkLeft_6_05.png");
				left6 = ImageIO.read(getClass().getResourceAsStream("/game/blue/Dude_Monster_WalkLeft_6_06.png"));
				playerImages.add("/game/blue/Dude_Monster_WalkLeft_6_06.png");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (color == color.MAGENTA) {
			try {

				jump1 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_Jump_8_01.png"));
				playerImages.add("/game/owl/Owlet_Monster_Jump_8_01.png");
				jump2 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_Jump_8_02.png"));
				playerImages.add("/game/owl/Owlet_Monster_Jump_8_02.png");
				jump3 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_Jump_8_03.png"));
				playerImages.add("/game/owl/Owlet_Monster_Jump_8_03.png");
				jump4 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_Jump_8_04.png"));
				playerImages.add("/game/owl/Owlet_Monster_Jump_8_04.png");
				jump5 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_Jump_8_05.png"));
				playerImages.add("/game/owl/Owlet_Monster_Jump_8_05.png");
				jump6 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_Jump_8_06.png"));
				playerImages.add("/game/owl/Owlet_Monster_Jump_8_06.png");
				jump7 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_Jump_8_07.png"));
				playerImages.add("/game/owl/Owlet_Monster_Jump_8_07.png");
				jump8 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_Jump_8_08.png"));
				playerImages.add("/game/owl/Owlet_Monster_Jump_8_08.png");

				i1 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_Idle_4_01.png"));
				playerImages.add("/game/owl/Owlet_Monster_Idle_4_01.png");
				i2 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_Idle_4_02.png"));
				playerImages.add("/game/owl/Owlet_Monster_Idle_4_02.png");
				i3 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_Idle_4_03.png"));
				playerImages.add("/game/owl/Owlet_Monster_Idle_4_03.png");
				i4 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_Idle_4_04.png"));
				playerImages.add("/game/owl/Owlet_Monster_Idle_4_04.png");

				iL1 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_IdleLeft_4_01.png"));
				playerImages.add("/game/owl/Owlet_Monster_IdleLeft_4_01.png");
				iL2 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_IdleLeft_4_02.png"));
				playerImages.add("/game/owl/Owlet_Monster_IdleLeft_4_02.png");
				iL3 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_IdleLeft_4_03.png"));
				playerImages.add("/game/owl/Owlet_Monster_IdleLeft_4_03.png");
				iL4 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_IdleLeft_4_04.png"));
				playerImages.add("/game/owl/Owlet_Monster_IdleLeft_4_04.png");

				right1 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_Run_6_01.png"));
				playerImages.add("/game/owl/Owlet_Monster_Run_6_01.png");
				right2 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_Run_6_02.png"));
				playerImages.add("/game/owl/Owlet_Monster_Run_6_02.png");
				right3 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_Run_6_03.png"));
				playerImages.add("/game/owl/Owlet_Monster_Run_6_03.png");
				right4 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_Run_6_04.png"));
				playerImages.add("/game/owl/Owlet_Monster_Run_6_04.png");
				right5 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_Run_6_05.png"));
				playerImages.add("/game/owl/Owlet_Monster_Run_6_05.png");
				right6 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_Run_6_06.png"));
				playerImages.add("/game/owl/Owlet_Monster_Run_6_06.png");

				left1 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_RunLeft_6_01.png"));
				playerImages.add("/game/owl/Owlet_Monster_RunLeft_6_01.png");
				left2 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_RunLeft_6_02.png"));
				playerImages.add("/game/owl/Owlet_Monster_RunLeft_6_02.png");
				left3 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_RunLeft_6_03.png"));
				playerImages.add("/game/owl/Owlet_Monster_RunLeft_6_03.png");
				left4 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_RunLeft_6_04.png"));
				playerImages.add("/game/owl/Owlet_Monster_RunLeft_6_04.png");
				left5 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_RunLeft_6_05.png"));
				playerImages.add("/game/owl/Owlet_Monster_RunLeft_6_05.png");
				left6 = ImageIO.read(getClass().getResourceAsStream("/game/owl/Owlet_Monster_RunLeft_6_06.png"));
				playerImages.add("/game/owl/Owlet_Monster_RunLeft_6_06.png");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String checkFace() {
		if (direction == "right")
			return "R";
		if (direction == "left")
			return "L";
		return "";
	}

	public void resetToStartPosition() {
		worldX = initialX;
		worldY = initialY;
	}

	public void groundColorCollision() {
		if (gp.tileM.isCharacterOnGround(worldX, worldY))
			if ((playerColor != gp.tileM.getGroundColor(worldX, worldY + gp.tileSize / 2))
					&& gp.tileM.getGroundColor(worldX, worldY + gp.tileSize / 2) != Color.DARK_GRAY)
				gp.resetGame();
	}

	public void completeLevel() {
		if (gp.tileM.finishLine(worldX, worldY)) {
			gp.tileM.showFinishPopup();
		}
	}

	public void update() {
		applyGravity();
		checkFace();
		groundColorCollision();
		completeLevel();
		checkPointCollision(gp.tileM.points);
		if (keyH.spacePressed && canShoot && playerColor != Color.MAGENTA) {
			shoot();
			canShoot = false;
		}
		updateBullets();

		if (!keyH.spacePressed) {
			canShoot = true;
		}
		if (keyH.onePressed) {
			playerColor = Color.RED;
			getPlayerImage(playerColor);
			speed = 2;
		} else if (keyH.twoPressed) {
			playerColor = Color.BLUE;
			getPlayerImage(playerColor);
			speed = 2;
		} else if (keyH.threePressed) {
			playerColor = Color.MAGENTA;
			getPlayerImage(playerColor);
			jumpSpeed = 30;
			speed = 4;
			canShoot = false;
		}

		if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true
				|| keyH.jumpPressed == true) {

			if (keyH.jumpPressed) {
				direction = "jump";
			}
			if (keyH.jumpRight) {
				direction = "jumpRight";
			}
			if (keyH.jumpLeft) {
				direction = "jumpLeft";
			}
			if (keyH.leftPressed == true) {
				playerDirectionX = -1;
				direction = "left";
			}
			if (keyH.rightPressed == true) {
				playerDirectionX = 1;
				direction = "right";
			}
		}
		collisionOn = false;
		gp.cChecker.checkTile(this);
		boolean movementKeyPressed = keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed;

		boolean jumpKeyPressed = keyH.jumpPressed;

		if (jumpKeyPressed && !collisionOn) {
			jump();
		}
		long now = System.nanoTime();
		double elapsedTime = (now - lastUpdateTime) / 1e9;

		if (elapsedTime > (10.0 / FRAME_RATE)) {
			if (direction.equals("left") || direction.equals("right")) {
				spriteNumH++;
				if (spriteNumH > 6) {
					spriteNumH = 1;
				}
			}
			if (direction.equals("jump")) {
				spriteNumV++;
				if (spriteNumV > 8) {
					spriteNumV = 1;
				}
			}
			if (direction.equals("gravity")) {
				spriteNumV++;
				if (spriteNumV > 8) {
					spriteNumV = 1;
				}
			}

			if (direction.equals("idle")) {
				spriteNumIdle++;
				if (spriteNumIdle > 4) {
					spriteNumIdle = 1;
				}
			}

			lastUpdateTime = now;
		}

		if (!jumping && !collisionOn && movementKeyPressed) {
			if (keyH.leftPressed) {
				if (worldX - speed > 0)
					worldX -= speed;
			}
			if (keyH.rightPressed) {
				if (worldX + speed < gp.screenWidth - 49)
					worldX += speed;
			}
			hitbox.setLocation(worldX, worldY);
		}
	}

	private BufferedImage leftSprite() {
		BufferedImage[] leftSprites = { left1, left2, left3, left4, left5, left6 };
		return leftSprites[spriteNumH - 1];
	}

	private BufferedImage rightSprite() {
		BufferedImage[] rightSprites = { right1, right2, right3, right4, right5, right6 };
		return rightSprites[spriteNumH - 1];
	}

	private BufferedImage jumpSprite() {
		BufferedImage[] jumpSprites = { jump1, jump2, jump3, jump4, jump5, jump6, jump7, jump8 };
		return jumpSprites[spriteNumV - 1];
	}

	private BufferedImage idleSprite() {
		BufferedImage[] idleSprites = { i1, i2, i3, i4 };
		return idleSprites[spriteNumIdle - 1];
	}

	private BufferedImage idleLeftSprite() {
		BufferedImage[] idleLeftSprites = { iL1, iL2, iL3, iL4 };
		return idleLeftSprites[spriteNumIdle - 1];
	}

	public void draw(Graphics2D g2) {
		g2.setColor(playerColor);
		BufferedImage image = null;

		switch (direction) {
		case "left":
			playerDirectionX = -1;
			image = leftSprite();
			break;
		case "right":
			playerDirectionX = 1;
			image = rightSprite();
			break;
		case "jump":
			image = jumpSprite();
			break;
		case "gravity":
			image = jumpSprite();
			break;
		case "idle":
			if (playerDirectionX == -1)
				image = idleLeftSprite();
			else if (playerDirectionX == 1)
				image = idleSprite();
			break;
		}

		g2.drawImage(image, worldX - 17, worldY - 15, gp.tileSize, gp.tileSize, null);
		if (gp.canUseWildcard()) {
			g2.setColor(Color.green);
			g2.fillOval(worldX - 5, worldY - 30, 20, 20);
		} else {
			g2.setColor(Color.red);
			g2.fillOval(worldX - 5, worldY - 30, 20, 20);
		}
		if (gp.wildcardEnabled) {
			g2.setColor(Color.orange);
			g2.fillOval(worldX - 5, worldY - 30, 20, 20);
		}

		g2.setColor(Color.white);
		Font oldFont = g2.getFont();
		Font newFont = new Font(oldFont.getName(), Font.PLAIN, 20);
		g2.setFont(newFont);
		g2.drawString("Score: " + Integer.toString(score), 20, 30);
		try {
			for (Point point : gp.tileM.points) {
				point.draw(g2);
			}
			for (Bullet bullet : bullets) {
				bullet.draw(g2);
			}
		} catch (Exception e) {

		}

	}

}
