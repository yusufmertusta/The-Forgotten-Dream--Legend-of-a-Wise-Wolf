package java_game;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, Serializable {
	private static final long serialVersionUID = 8535737947996326134L;

	final int originalTileSize = 24;
	final int scale = 2;

	private transient SoundManager backgroundMusic;
	private transient Timer spawnTimer;
	private int spawnInterval = 5000; 

	public final int tileSize = originalTileSize * scale;
	final int maxScreenCol = 25;
	final int maxScreenRow = 15;
	final int screenWidth = tileSize * maxScreenCol;
	final int screenHeigth = tileSize * maxScreenRow;

	int FPS = 120;

	String playerImages = "player";
	String tileImages = "tile";
	String bulletImages = "bullet";
	String enemyImages = "enemy";

	public boolean wildcardEnabled = false;
	public Timer wildcardTimer;
	public long remainingTime;
	private long lastWildcardTime;

	public transient TileManager tileM = new TileManager(this);
	public transient KeyHandler keyH = new KeyHandler(this);
	private final Object enemiesLock = new Object();
	transient Thread gameThread;
	public transient CollisionChecker cChecker = new CollisionChecker(this);
	transient Player player = new Player(this, keyH, 20, 600, 2);
	public transient List<Enemy> enemies = new ArrayList<>();
	public boolean isPaused = false;
	private transient BufferedImage background;
	private transient Map<String, String> imagePaths = new HashMap<>();
	public transient SaveLoad saveLoad = new SaveLoad(this);
	public BufferedImage pauseScreen;

	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeigth() {
		return screenHeigth;
	}

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeigth));
		this.setBackground(Color.DARK_GRAY);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		try {
			String backgroundImagePath = "/game/map/Grassy_Mountains_Parallax_Background.png";
			background = ImageIO.read(getClass().getResource(backgroundImagePath));
			String pausePath = "/game/map/pause.png";
			pauseScreen = ImageIO.read(getClass().getResource(pausePath));
			imagePaths.put("background", backgroundImagePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		imagePaths.put(playerImages, playerImages);
		imagePaths.put(tileImages, tileImages);
		imagePaths.put(bulletImages, bulletImages);
		imagePaths.put(enemyImages, enemyImages);
		backgroundMusic = new SoundManager("/game/sounds/loop_2.wav");
		backgroundMusic.setVolume(0.3f);
		playBackgroundMusic();
		startSpawnTimer();
	}

	public void savePanel(String filename) {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
			out.writeObject(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadPanel(String filename) {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
			GamePanel gamePanel = (GamePanel) in.readObject();
			copyState(gamePanel);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void copyState(GamePanel loadedGamePanel) {
		this.tileM = loadedGamePanel.tileM;
		this.keyH = loadedGamePanel.keyH;
		this.player = loadedGamePanel.player;
		this.enemies = loadedGamePanel.enemies;
		this.isPaused = loadedGamePanel.isPaused;
		this.background = loadedGamePanel.background;
		this.imagePaths = loadedGamePanel.imagePaths;
		recreateSoundManager();
		startSpawnTimer();
	}

	public void startSpawnTimer() {
		spawnTimer = new Timer();
		spawnTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (!isPaused)
					spawnEnemy(enemies, 602, 10, 3); 
			}
		}, 5000, spawnInterval);
	}

	public void spawnEnemy(List<Enemy> enemies, int x, int y, int speed) {
		Random random = new Random();
		String direction = random.nextBoolean() ? "left" : "right";
		Color[] colors = { Color.RED, Color.orange, Color.BLUE };
		int colorIndex = random.nextInt(colors.length);
		Color color = colors[colorIndex];
		enemies.add(new Enemy(this, x, y, speed, direction, color));
	}

	public void initialize() {
		player.score = 0;
		tileM.points.clear();
		player.resetToStartPosition();
		player.playerColor = Color.red;
		player.getPlayerImage(player.playerColor);
		player.bullets.clear();
		tileM.setPoints();
		synchronized (enemiesLock) {

			if (enemies != null) {
				enemies.clear();
				enemies.add(new Enemy(this, 0, 50, 3, "left", Color.blue));
				enemies.add(new Enemy(this, 1128, 402, 3, "right", Color.red));
			} else {
				enemies = new ArrayList<>();
				enemies.add(new Enemy(this, 0, 50, 3, "left", Color.blue));
				enemies.add(new Enemy(this, 1128, 402, 3, "right", Color.red));
			}
		}
	}

	public boolean canUseWildcard() {
		 long currentTime = System.currentTimeMillis();
		    long timeSinceLastWildcard = currentTime - lastWildcardTime;
		    
		   if (timeSinceLastWildcard >= 6000) {
		        return true; 
		    } else {
		        return false; 
		    }
	}

	public void startWildcardTimer() {
		if (!wildcardEnabled) { 
			wildcardEnabled = true; 
			wildcardTimer = new Timer();
			wildcardTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					wildcardEnabled = false;
					wildcardTimer.cancel();
					lastWildcardTime = System.currentTimeMillis();
				}
			}, 6000); 
		}
	}

	private boolean isWildcardEnabled() {
		return wildcardEnabled;
	}

	public void playBackgroundMusic() {
		if (backgroundMusic != null) {
			backgroundMusic.loop(); 
		}
	}

	public void recreateSoundManager() {
		backgroundMusic = new SoundManager("/game/sounds/loop_2.wav");
		backgroundMusic.setVolume(0.3f);
		playBackgroundMusic();
	}

	public void drawPauseScreen(Graphics2D g2) {
		g2.drawImage(pauseScreen, 0, 0, getWidth(), getHeight(), null);
	}

	public void pauseGame() {
		System.out.println("oyun durdu");
		isPaused = true;
	}

	public void resumeGame() {
		System.out.println("oyun devam");
		isPaused = false;
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void resetGame() {

		initialize();
		repaint();
	}

	public void restartGame() {
		if (gameThread != null && gameThread.isAlive()) {
			gameThread.interrupt();
			gameThread = new Thread(this); 
			gameThread.start(); 
		} else {
			startGameThread();
		}
	}

	@Override
	public void run() {

		final double nsPerUpdate = 1000000000.0 / 65.0; 
		long lastUpdateTime = System.nanoTime();
		double delta = 0.0;

		while (gameThread != null) {
			long now = System.nanoTime();
			delta += (now - lastUpdateTime) / nsPerUpdate;
			lastUpdateTime = now;

			while (delta >= 1) {
				update(); 
				delta--;
			}

			repaint(); 

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
		}

	}

	public void updateBullets() {
		for (Bullet bullet : player.bullets) {
			bullet.update();
		}
	}

	public void update() {
		if (!isPaused) {
			player.update();
			updateBullets();

			boolean collisionDetected = false;
			synchronized (enemiesLock) {
				for (Enemy enemy : enemies) {
					enemy.update(); 
					Rectangle playerHitbox = player.getHitbox();
					Rectangle enemyHitbox = enemy.getHitbox();
					if (isWildcardEnabled())
						collisionDetected = false;
					else if ((playerHitbox != null && enemyHitbox != null && playerHitbox.intersects(enemyHitbox))) {
						System.out.println("Collision detected!");
						collisionDetected = true;
						break; 
					}
				}
			}
			if (collisionDetected) {
				resetGame();
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(background, 0, 0, getWidth(), getHeight(), null);
		tileM.draw(g2);
		player.draw(g2);
		Font oldFont = g2.getFont();
		Font newFont = new Font(oldFont.getName(), Font.BOLD, 20); 
		g2.setFont(newFont);
		g2.setColor(Color.darkGray); 
		String message = "Zindan Kapısı";
		int messageWidth = g2.getFontMetrics().stringWidth(message);
		int x = (getWidth() - messageWidth) / 2;
		g2.drawString(message, x, 30);
		g2.setFont(oldFont); 
		for (Enemy enemy : enemies) {
			g2.setColor(enemy.enemyColor);
			enemy.draw(g2);
		}
		if (isPaused) {
			drawPauseScreen(g2);
		}
		g2.dispose();

	}

}
