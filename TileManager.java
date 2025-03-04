package java_game;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class TileManager implements Serializable {

	GamePanel gp;
	Tile[] tile;
	public int mapTileNum[][];
	public Color groundColor;
	public List<Point> points = new ArrayList<>();; 
	private boolean collected;
	public List<String> tileImages = new ArrayList<>();

	public TileManager(GamePanel gp) {

		this.gp = gp;
		tile = new Tile[10];
		mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
		getTileImage();
		loadMap();
		setPoints();
	}

	public void setPoints() {
		points.clear(); 
		for (int col = 0; col < gp.maxScreenCol; col++) {
			for (int row = 1; row < gp.maxScreenRow; row++) {
				int tileNum = mapTileNum[col][row];
				int tileNumExecption = mapTileNum[col][row - 1];

				if ((tileNum == 1 || tileNum == 2 || tileNum == 3) && tileNumExecption != 4) { 
					int pointX = col * gp.tileSize;
					int pointY = (row) * gp.tileSize; 
					if (pointY - gp.tileSize >= 0) { 
						points.add(new Point(gp, pointX, pointY - gp.tileSize)); 
					}
				}
			}
		}
	}

	public void savePoints(String filename) {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
			out.writeObject(points);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadPoints(String filename) {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
			points = (ArrayList<Point>) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean finishLine(int characterX, int characterY) {
		int col = characterX / gp.tileSize;
		int row = characterY / gp.tileSize;
		if (row >= 0 && row < gp.maxScreenRow && col >= 0 && col < gp.maxScreenCol) {
			int tileNum = mapTileNum[col][row];
			return tileNum == 4;
		}
		return false;
	}
	
	public boolean isCharacterOnGround(int characterX, int characterY) {
		int col = characterX / gp.tileSize;
		int row = characterY / gp.tileSize + 1; 
		String pD = gp.player.direction;
		if (row >= 0 && row < gp.maxScreenRow && col >= 0 && col < gp.maxScreenCol) {
			int tileNum = mapTileNum[col][row];
			return tileNum == 1 || tileNum == 2 || tileNum == 3 || tileNum == 5 || tileNum == 6; 
		}

		gp.player.direction = pD;
		return false;
	}

	public void getTileImage() {
		tileImages.add("/game/tiles/cloud1.png");
		tileImages.add("/game/tiles/cloud2.png");
		tileImages.add("/game/tiles/cloud3.png");
		tileImages.add("/game/tiles/trans.png");

		try {
			tile[1] = new Tile(ImageIO.read(getClass().getResourceAsStream("/game/tiles/cloud2.png")), true,
					Color.DARK_GRAY 
			);
			tile[5] = new Tile(ImageIO.read(getClass().getResourceAsStream("/game/tiles/cloud1.png")), true,
					Color.DARK_GRAY
			);

			tile[6] = new Tile(ImageIO.read(getClass().getResourceAsStream("/game/tiles/cloud3.png")), true,
					Color.DARK_GRAY 
			);

			tile[0] = new Tile(ImageIO.read(getClass().getResourceAsStream("/game/tiles/trans.png")), false,
					Color.DARK_GRAY 
			);

			tile[2] = new Tile(ImageIO.read(getClass().getResourceAsStream("/game/tiles/cloud2.png")), true, Color.BLUE // Example
																														// color
																														// for
																														// non-solid
																														// tiles
			);
			tile[3] = new Tile(ImageIO.read(getClass().getResourceAsStream("/game/tiles/cloud2.png")), true,
					Color.RED
		
			);
			tile[4] = new Tile(ImageIO.read(getClass().getResourceAsStream("/game/tiles/gate.png")), false, Color.YELLOW
			
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Color getGroundColor(int characterX, int characterY) {
		int col = characterX / gp.tileSize;
		int row = characterY / gp.tileSize + 1;
		if (row >= 0 && row < gp.maxScreenRow && col >= 0 && col < gp.maxScreenCol) {
			int tileNum = mapTileNum[col][row];
			return tile[tileNum].color;
		}
		return null;
	}

	public void loadMap() {

		try {
			InputStream is = getClass().getResourceAsStream("/game/map/map.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			int col = 0;
			int row = 0;
			while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
				String line = br.readLine();

				while (col < gp.maxScreenCol) {
					String numbers[] = line.split(" ");

					int num = Integer.parseInt(numbers[col]);
					mapTileNum[col][row] = num;
					col++;
				}

				if (col == gp.maxScreenCol) {
					col = 0;
					row++;
				}
			}
			br.close();

		} catch (Exception e) {
		}
	}

	public void showPopup(String message, String title) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

	public void showFinishPopup() {
		String message = "Tebrikler! Zindana ulaştınız. CanisLupus'a ulaşmak üzeresiniz. .\nSkorunuz: " + gp.player.score+"\n Bu skor bilge kurdun size olan güvenini etkileyecek.";
		String title = "Bölüm sonu!";
		showPopup(message, title);
		System.exit(0); 
	}

	public void draw(Graphics2D g2) {

		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;

		while (col < gp.maxScreenCol && row < gp.maxScreenRow) {

			int tileNum = mapTileNum[col][row];
			g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
			g2.setColor(tile[tileNum].color);

			if (tile[tileNum].color == Color.RED || tile[tileNum].color == Color.BLUE)
				g2.fillRect(x, y, gp.tileSize, gp.tileSize / 5);
			col++;
			x += gp.tileSize;

			if (col == gp.maxScreenCol) {
				col = 0;
				x = 0;
				row++;
				y += gp.tileSize;
			}

		}

	}
}