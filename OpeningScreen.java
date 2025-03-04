package java_game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OpeningScreen extends JPanel {
	private BufferedImage screen1, screen2, screen3;
	private BufferedImage currentScreen;
	private JButton nextButton;
	private int currentPage = 1;

	public OpeningScreen() {
		setPreferredSize(new Dimension(1200, 720));

		try {
			String screen1Path = "/game/screens/screen1.png";
			screen1 = ImageIO.read(getClass().getResource(screen1Path));
			String screen2Path = "/game/screens/screen2.png";
			screen2 = ImageIO.read(getClass().getResource(screen2Path));
			String screen3Path = "/game/screens/screen3.png";
			screen3 = ImageIO.read(getClass().getResource(screen3Path));
			currentScreen = screen1;
		} catch (IOException e) {
			e.printStackTrace();
		}

		setLayout(null);

		nextButton = new JButton();
		nextButton.setBounds(110, 560, 230, 100);
		nextButton.setOpaque(false);
		nextButton.setContentAreaFilled(false);
		nextButton.setBorderPainted(false);
		add(nextButton);

		// Butona tıklama olayı
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentPage < 4)
					currentPage++;

				if (currentPage == 4) {
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(OpeningScreen.this);
					frame.remove(OpeningScreen.this);
					JFrame window = new JFrame("The Forgotten Dream: Legend of a Wise Wolf");
					window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					window.setResizable(false);
					ImageIcon icon = new ImageIcon("/game/map/icon.png");
					window.setIconImage(icon.getImage());
					GamePanel gp = new GamePanel();
					window.add(gp);
					window.pack();
					window.setLocationRelativeTo(null);
					window.setVisible(true);
					gp.initialize();
					gp.startGameThread();
				}
				updateBackgroundImage();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (currentScreen != null) {
			g2.drawImage(currentScreen, 0, 0, 1200, 720, this);
		}
	}

	private void updateBackgroundImage() {
		switch (currentPage) {
		case 1:
			currentScreen = screen1;
			break;
		case 2:
			currentScreen = screen2;
			break;
		case 3:
			currentScreen = screen3;
			break;
		}
		if (currentPage == 4) {
			nextButton.setVisible(false);
		} else {
			nextButton.setVisible(true);
			if (currentPage == 1) {
				nextButton.setBounds(110, 560, 230, 100);
			} else if (currentPage == 2) {
				nextButton.setBounds(600, 540, 500, 100);
			}
		}
		repaint();
	}
}

