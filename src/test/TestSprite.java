package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestSprite {
	public static void main(String[] args) {
		// test
		int index = 3;
		BufferedImage currentSprite = ImageManager.getSpriteAt(index,ImageManager.jumpSprite);
		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(currentSprite, 0,0,150,150,null);
				g.drawRect(50, 100, 40, 50);
			}
		};
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setBackground(Color.ORANGE);
		panel.setPreferredSize(new Dimension(200, 200));
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		
	}
}
