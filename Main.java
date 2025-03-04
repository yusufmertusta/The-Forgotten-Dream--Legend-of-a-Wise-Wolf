package java_game;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main implements Serializable {

	public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {

			OpeningScreen openingScreen = new OpeningScreen();
			@Override
			public void run() {
				JFrame frame = new JFrame("The Forgotten Dream: Legend of a Wise Wolf ");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(false);	
				frame.add(openingScreen);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
        });
        }
}