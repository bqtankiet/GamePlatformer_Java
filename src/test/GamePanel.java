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
	public MovingController controller;
	public Image gameBufferImage, screenBufferImage;
	public Graphics2D gameBufferG2D;
	public Graphics screenBufferGraphics;

	public GamePanel(Game game) {
		this.game = game;
		addKeyListener(controller = new MovingController(game));
		addKeyListener(new OtherController(game));
		setFocusable(true);
		requestFocus();
		showUp();
	}

	public void showUp()
	// show the panel on the frame
	{
		JFrame frame = new JFrame("Simple");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		// Set the JFrame to full screen
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
			double timePerFrame = 1_000_000_000 / 120;
			long fpsTimer = System.nanoTime();
			double delta = 0.0;
			long lastTime = System.nanoTime();
			long currentTime;

			while (true) {
				currentTime = System.nanoTime();
				delta += (currentTime - lastTime) / timePerFrame;
				lastTime = currentTime;

				if (delta >= 1) {
					delta--;
					frames++;
					game.update();
					gameRender();
					paintScreen();
				}

				if (System.nanoTime() - fpsTimer >= 1_000_000_000) {
					System.out.println("FPS: " + frames);
					frames = 0;
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
			} else
				gameBufferG2D = (Graphics2D) gameBufferImage.getGraphics();
		}
		// clear back ground
		gameBufferG2D.setColor(Color.lightGray);
		gameBufferG2D.fillRect(0, 0, game.map.getWidth(), GamePanel.HEIGHT);
		// draw game elements
		game.player.draw(gameBufferG2D);
		game.map.draw(gameBufferG2D);
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
					screenBufferGraphics = screenBufferImage.getGraphics();
				}
				screenBufferGraphics.setColor(Color.WHITE);
				screenBufferGraphics.fillRect(0, 0, WIDTH, HEIGHT);
				int cameraX = (int) ((game.player.hitbox.x - WIDTH / 2));
//				if (cameraX <= 0) {
//					cameraX = 0;
//				}
//				if(cameraX + WIDTH >= game.map.getWidth()) {
//					cameraX = game.map.getWidth() - WIDTH;
//				}
				screenBufferGraphics.drawImage(gameBufferImage, -cameraX, 0, null);
				if (showGrid) {
					drawGrid(screenBufferGraphics);
				}
				g.drawImage(screenBufferImage, 0, 0, null);
//				g2d.drawImage(dbScrImg,0, 0, null);
			}
			g.dispose();
		} catch (Exception e) {
			System.out.println("GamePanel graphics context error: " + e);
		}
	} // close paintScreen()

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
