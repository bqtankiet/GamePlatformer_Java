package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	public static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
	public Game game;
	public Image gameBufferImage, screenBufferImage;
	public Graphics2D gameBufferG2D;
	public Graphics2D screenBufferGraphics;

	public GamePanel(Game game) {
		this.game = game;
		setBackground(new Color(37,22,19));
		addKeyListener(new MovingController(game));
		addKeyListener(new OtherController(game));
		setFocusable(true);
		requestFocusInWindow();
		showUp();
	}

	public void showUp()
	// show the panel on the frame
	{
		JFrame frame = new JFrame("Simple");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setVisible(true);
	}// close showUp()

	@Override
	public void addNotify()
	// wait for panel to be added to the JFrame
	{
		super.addNotify();
		startGameLoop();
	}// close addNotify()

	public void startGameLoop()
	// initialize and start the game thread
	{
		new Thread(() -> {
			int frames = 0;
			int updates = 0;
			double timePerFrame = 1_000_000_000.0 / 120;
			double timePerUpdate = 1_000_000_000.0 / 120;
			double deltaF = 0.0;
			double deltaU = 0.0;
			long fpsTimer = System.nanoTime();
			long lastTime = System.nanoTime();
			long currentTime;

			while (true) {
				currentTime = System.nanoTime();
				deltaF += (currentTime - lastTime) / timePerFrame;
				deltaU += (currentTime - lastTime) / timePerUpdate;
				lastTime = currentTime;

				if (deltaU >= 1) {
					game.update();
					updates++;
					deltaU--;
				}

				if (deltaF >= 1) {
//					gameRender();
//					paintScreen();
					repaint();
					frames++;
					deltaF--;
				}

				if (System.nanoTime() - fpsTimer >= 1_000_000_000) {
					System.out.println("FPS: " + frames + "| UPS:" + updates);
					frames = 0;
					updates = 0;
					fpsTimer = System.nanoTime();
				}
			}
		}).start();
	}// close startGameLoop()

	private void gameRender()
	// draw the current frame to an image buffer
	{
		if (gameBufferImage == null) { // create the buffer
			gameBufferImage = createImage(game.map.getWidth(), HEIGHT);
			if (gameBufferImage == null) { // after create if it still null -> return
				System.out.println("doubleBufferImage is null");
				return;
			} else if (gameBufferG2D == null)
				gameBufferG2D = (Graphics2D) gameBufferImage.getGraphics();
		}
		// clear back ground
		gameBufferG2D.setColor(Color.LIGHT_GRAY);
		gameBufferG2D.fillRect(0, 0, game.map.getWidth(), GamePanel.HEIGHT);
		// draw game elements
		game.map.draw(gameBufferG2D);
		game.player.draw(gameBufferG2D);
	}// close gameRender()

	public boolean showGrid = false;

	private void paintScreen()
	// actively render the buffer image to the screen
	{
		Graphics g;
		try {
			g = this.getGraphics();
			if ((g != null) && (gameBufferImage != null)) {
				if (screenBufferImage == null) { // using screenBufferImage to make slide scroll
					screenBufferImage = createImage(WIDTH, HEIGHT);
					screenBufferGraphics = (Graphics2D) screenBufferImage.getGraphics();
				}
				screenBufferGraphics.setColor(Color.WHITE);
				screenBufferGraphics.fillRect(0, 0, WIDTH, HEIGHT);
				int cameraX = (int) ((game.player.hitbox.x - WIDTH / 2));
				screenBufferGraphics.drawImage(gameBufferImage, -cameraX, 0, null);
				if (showGrid) {
					drawGrid(screenBufferGraphics);
				}
				g.drawImage(screenBufferImage, 0, 0, null);
			}
			g.dispose();
		} catch (Exception e) {
			System.out.println("GamePanel graphics context error: " + e);
		}
	} // close paintScreen()
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// render game
		if (gameBufferImage == null) { 
			gameBufferImage = createImage(game.map.getWidth(), HEIGHT);
			gameBufferG2D = (Graphics2D) gameBufferImage.getGraphics();
		}
		gameBufferG2D.setColor(new Color(37,22,19));
		gameBufferG2D.fillRect(0, 0, game.map.getWidth(), HEIGHT);
		game.map.draw(gameBufferG2D);
		game.player.draw(gameBufferG2D);
		if (showGrid) {
			drawGrid(gameBufferG2D);
		}
		// draw game
		int cameraX = (int) ((game.player.hitbox.x - WIDTH / 2));
		g.drawImage(gameBufferImage, -cameraX, 0, null);
	}

	public void drawGrid(Graphics g) {
		for (int r = 0; r <= GamePanel.HEIGHT / Map.TITLE_SIZE; r++) {
			for (int c = 0; c <= GamePanel.WIDTH / Map.TITLE_SIZE; c++) {
				g.setColor(Color.RED);
				int x = c * Map.TITLE_SIZE;
				int y = r * Map.TITLE_SIZE;
				g.drawRect(x, y, Map.TITLE_SIZE, Map.TITLE_SIZE);
				g.drawString("r" + r + "c" + c, x, y + 10);
			}
		}

	}

}
