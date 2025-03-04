package java_game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.*;

import javax.swing.JPanel;

public class SaveLoad {

	GamePanel gp;

	public SaveLoad(GamePanel gp) {
		this.gp = gp;
	}

	public void saveGame(String saveFile) {
		gp.savePanel(saveFile);
		gp.tileM.savePoints(saveFile);
	}

	public void loadGame(String saveFile) {
		gp.loadPanel(saveFile);
		gp.tileM.loadPoints(saveFile);
	}

}
