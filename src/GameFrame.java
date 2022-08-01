import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameFrame extends JFrame {
	
	GameFrame() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
	   
	
		this.add(new GamePanel());
		this.setTitle("Snake by Giannis");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		ImageIcon icon = new ImageIcon("src/Images/snakeIcon.png");
		this.setIconImage(icon.getImage());
	}
}
