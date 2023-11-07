package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	public static final int WIDHT = 1000;
	public static final int HEIGHT = 600;
	public Game game;
	public MovingController controller;

	public GamePanel(Game game) {
		this.game = game;
		setPreferredSize(new Dimension(WIDHT, HEIGHT));
		setBackground(Color.GRAY);
		addKeyListener(controller = new MovingController(game));
		setFocusable(true);
		showUp();
	}

	public void showUp() {
		JFrame frame = new JFrame("Simple");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
//		// Set the JFrame to fullscreen
//        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        frame.setUndecorated(false); 
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		game.player.draw(g2d);
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 400, WIDHT, HEIGHT-400);
	}
	
	
}
