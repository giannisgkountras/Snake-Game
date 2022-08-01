import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;
import java.util.Random;
import javax.sound.sampled.*;


public class GamePanel extends JPanel implements ActionListener{
	static final int SCREEN_WIDTH =600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 20;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 50;
	final int x[] = new int [GAME_UNITS];
	final int y[] = new int [GAME_UNITS];
	int bodyParts = 6;
	int applesEaten = 0;
	int appleX;
	int appleY;
	char direction = 'R'; 
	boolean running;
	Timer timer;
	Random random;
	Graphics d;
    //MUSIC

	File mainMenuMusic = new File ("src/Music/mainGame.wav");
	//	AudioInputStream streamMenuMusic = AudioSystem.getAudioInputStream(menuMusic);
		AudioInputStream streamMenu = AudioSystem.getAudioInputStream(mainMenuMusic);
		Clip clipMenu = AudioSystem.getClip();
		
		
	
	
	GamePanel() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
		
	}
	/*public void mainMenu(Graphics d) {
		d.setColor(new Color (0,0,150));
		d.setFont(new Font ("Courier New",Font.PLAIN, 30));
		FontMetrics metrics3 = getFontMetrics(d.getFont());
		d.drawString("Welcome to my snake game!\nMove the snake with the arrow keys.\n(press any key to play)", (SCREEN_WIDTH - metrics3.stringWidth("Welcome to my snake game!\nMove the snake with the arrow keys.\n(press any key to play")), (SCREEN_HEIGHT/2));
	}*/
	public void startGame() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		newApple();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
		clipMenu.open(streamMenu);
		clipMenu.start();
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			draw(g);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void draw(Graphics g) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if(running) {
				for (int i = 0 ; i < SCREEN_HEIGHT/UNIT_SIZE;i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);			//GRID
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
				}
			
			g.setColor(Color.red);
			g.fillOval(appleX,appleY, UNIT_SIZE, UNIT_SIZE);
			
			for (int i = 0; i < bodyParts; i++) {
				if(i==0) {
					g.setColor(new Color(0,102,204));
					g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
				}
				else {
					g.setColor(new Color(0,76,153));
					g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);	
				}
			}
		}
		else {
			gameOver(g);
		}
	}
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	public void move() {
		for (int i = bodyParts; i > 0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch (direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}
	public void checkApple() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if ((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			newApple();
			takeAppleMusic();
		}
		
	}
	public void checkCollisions() {
		
		//head - body collision
		for (int i = bodyParts; i>0;i--) {
			if ( (x[0] == x[i]) && (y[0] == y[i]) ) {
				running = false;
			
			}
		}
		
		//snake - border collision
		if (x[0] < 0) {
			x[0] = SCREEN_WIDTH;
		}
		if (x[0] > SCREEN_WIDTH) {
			x[0] = 0;
		}
		if (y[0] < 0) {
			y[0] = SCREEN_HEIGHT;
		}
		if (y[0] > SCREEN_HEIGHT) {
			y[0] = 0;
		}
		
		if(!running) {
			timer.stop();
		}
	}
	public void gameOver(Graphics g) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		clipMenu.stop();
		gameOverMusic();
		g.setColor(Color.red);
		g.setFont(new Font ("Impact",Font.ITALIC, 75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("GAME OVER", (SCREEN_WIDTH - metrics.stringWidth("GAME OVER") )/2,SCREEN_HEIGHT/2);
		
		g.setColor(new Color (150,0,0));
		g.setFont(new Font ("Courier New",Font.PLAIN, 30));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Score: "+applesEaten*10, (SCREEN_WIDTH - metrics2.stringWidth("Score: "+applesEaten*10) )/2,SCREEN_HEIGHT/2+100);
	}	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			try {
				checkApple();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			checkCollisions();
			}
		repaint();
	}
	public void takeAppleMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		File takeApple = new File ("src/Music/takeApple.wav");
	//	AudioInputStream streamMenuMusic = AudioSystem.getAudioInputStream(menuMusic);
		AudioInputStream streamTakeApple = AudioSystem.getAudioInputStream(takeApple);
		Clip clipApple = AudioSystem.getClip();
		clipApple.open(streamTakeApple);
		clipApple.start();	
	}
	public void gameOverMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		
		File takeApple = new File ("src/Music/gameOverMusic.wav");
	//	AudioInputStream streamMenuMusic = AudioSystem.getAudioInputStream(menuMusic);
		AudioInputStream streamTakeApple = AudioSystem.getAudioInputStream(takeApple);
		Clip clipApple = AudioSystem.getClip();
		clipApple.open(streamTakeApple);
		clipApple.start();
		
	}
	//public void mainGameMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		
		
		
		
	//}
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			
			case KeyEvent.VK_LEFT:
				if (direction !='R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (direction !='L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if (direction !='D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if (direction !='U') {
					direction = 'D';
				}
				break;
				
				
			}
		}
	}

}