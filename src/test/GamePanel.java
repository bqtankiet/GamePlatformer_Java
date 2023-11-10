package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	public static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
	public Game game;
	public MovingController controller;
	public Image doubleBufferImage;
	public Graphics2D doubleBufferG2D;

	public GamePanel(Game game) {
		this.game = game;
		setBackground(Color.GRAY);
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
		if (doubleBufferImage == null) { // create the buffer
			doubleBufferImage = createImage(WIDTH, HEIGHT);
			if (doubleBufferImage == null) { // after create if it still null -> return
				System.out.println("doubleBufferImage is null");
				return;
			} else
				doubleBufferG2D = (Graphics2D) doubleBufferImage.getGraphics();
		}
		// clear back ground
		doubleBufferG2D.setColor(Color.lightGray);
		doubleBufferG2D.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		// draw game elements
		game.player.draw(doubleBufferG2D);
		game.map.draw(doubleBufferG2D);
		if (showGrid) {
			game.map.drawGrid(doubleBufferG2D);
		}
	}// close gameRender()

	public boolean showGrid = false;

	private void paintScreen()
	// actively render the buffer image to the screen
	{
		Graphics g;
		try {
			g = this.getGraphics(); // get the panel's graphic context
			if ((g != null) && (doubleBufferImage != null)) {
				g.drawImage(doubleBufferImage, 0, 0, null);
			}
			Toolkit.getDefaultToolkit().sync(); // sync the display on some systems
			g.dispose();
		} catch (Exception e) {
			System.out.println("GamePanel graphics context error: " + e);
		}
	} // close paintScreen()

}
