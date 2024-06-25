import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
	static final int screen_width = 800;
	static final int screen_height = 800;
	static final int unit_size = 18;
	static final int Game_unit = (screen_width * screen_height / unit_size);
	static final int Delay = 40;

	final int x[] = new int[Game_unit];
	final int y[] = new int[Game_unit];
	int bodyparts = 6;
	int Appleseaten;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;

	Random random;

	GameFrame frame;

	MyKeyAdapter key = new MyKeyAdapter();

	public GamePanel() {
		random = new Random();

		this.setPreferredSize(new Dimension(screen_height, screen_width));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.addKeyListener(key);

		StartGame();

	}

	public void StartGame() {
		newApples();
		running = true;
		timer = new Timer(Delay, this);
		timer.start();

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);

	}

	public void draw(Graphics g) {
		if (running) {/*
						 * 
						 * for(int i=0;i<screen_height/unit_size;i++) { //Grid
						 * g.drawLine(i*unit_size, 0, i*unit_size, screen_height);
						 * g.drawLine(0, i*unit_size, screen_width, i*unit_size);
						 * 
						 * }
						 */
			g.setColor(Color.red); // Apples
			g.fillOval(appleX, appleY, unit_size, unit_size);

			for (int i = 0; i < bodyparts; i++) { // SnakeBody
				if (i == 0) { // Snake head
					g.setColor(Color.GREEN);
					g.fillRect(x[i], y[i], unit_size, unit_size);

				} else { // Snake body(the rest)
					g.setColor(new Color(45, 180, 0));
					g.fillRect(x[i], y[i], unit_size, unit_size);

				}

			}
			g.setColor(Color.blue);
			g.setFont(new Font("ink free", Font.BOLD, 75));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("score:" + Appleseaten, (screen_width - metrics.stringWidth("score:" + Appleseaten)) / 2,
					g.getFont().getSize());

		}

		else {

			Gameover(g);

		}
	}

	public void newApples() {
		appleX = random.nextInt((int) screen_width / unit_size) * unit_size;
		appleY = random.nextInt((int) screen_height / unit_size) * unit_size;

	}

	public void move() {
		for (int i = bodyparts; i > 0; i--) { // body parts equal each other equal head
			x[i] = x[i - 1]; // rest of the body follow the head
			y[i] = y[i - 1]; // loop mechanism: bodyPart coordinates equal each other equal head ,take a
								// direction then repaint then draw then
		}

		switch (direction) { // directions (x,y coordinates)

			case 'U':
				y[0] = y[0] - unit_size;
				break;

			case 'D':
				y[0] = y[0] + unit_size;

				break;
			case 'L':
				x[0] = x[0] - unit_size;
				break;
			case 'R':
				x[0] = x[0] + unit_size;
				break;

		}

	}

	public void Checkapple() {
		if ((x[0] == appleX) && (y[0] == appleY)) {
			Appleseaten++;
			bodyparts++;
			newApples();

		}

	}

	public void Checkcollisions() {
		// checks if head collides with body
		for (int i = bodyparts; i > 0; i--) {
			if ((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;

			}

		}
		// check if head touch the boarder
		// check if head touch left boarder
		if (x[0] < 0) {
			running = false;
		}
		// check if head touch right boarder
		if (x[0] > screen_width) {
			running = false;
		}
		// check if head touch upward boarder
		if (y[0] < 0) {
			running = false;
		}
		// check if head touch downward boarder
		if (y[0] > screen_height) {
			running = false;
		}
		if (!running) {

			timer.stop();
		}

	}

	public void Gameover(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("ink free", Font.BOLD, 75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game over", (screen_width - metrics.stringWidth("Game over")) / 2, screen_height / 2);
		g.setColor(Color.blue);
		g.setFont(new Font("ink free", Font.BOLD, 75));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("score:" + Appleseaten, (screen_width - metrics1.stringWidth("score:" + Appleseaten)) / 2,
				g.getFont().getSize());

		// ****Restart
		// Restart Button
		JButton button = new JButton("Restart");
		this.setLayout(null);
		button.setLocation((screen_height / 2) - 50, screen_height - 100);
		button.setSize(100, 100);
		button.setBorder(new EmptyBorder(5, 15, 5, 15));
		button.setBackground(Color.white);
		button.setForeground(Color.black);
		button.setFont(new Font("Calibri", Font.PLAIN, 14));

		this.add(button);
		// Action of the Restart Button
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				button.setVisible(false);
				// random = new Random();

				Appleseaten = 0;
				for (int i = bodyparts; i >= 0; i--) { // make array content zeros
					x[i] = 0;
					y[i] = 0;
				}
				bodyparts = 6;
				direction = 'R';
				reinitialize_panel();
				StartGame();

			}
		});
	}

	void reinitialize_panel() {
		this.setPreferredSize(new Dimension(screen_height, screen_width));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.requestFocus();
		this.addKeyListener(key);

	}

	public class MyKeyAdapter extends KeyAdapter {

		public void keyPressed(KeyEvent e) {

			switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					if (direction != 'R') {
						direction = 'L';

					}
					break;
				case KeyEvent.VK_RIGHT:
					if (direction != 'L') {
						direction = 'R';

					}
					break;
				case KeyEvent.VK_UP:
					if (direction != 'D') {
						direction = 'U';

					}
					break;
				case KeyEvent.VK_DOWN:
					if (direction != 'U') {
						direction = 'D';

					}
					break;

			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (running) {
			move();
			Checkapple();
			Checkcollisions();

		}
		repaint();

	}

}
