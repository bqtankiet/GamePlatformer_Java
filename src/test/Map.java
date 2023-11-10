package test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.plaf.SliderUI;

public class Map {

	public static final int TITLE_SIZE = 50;
	public ArrayList<Rectangle> solidList;

	public Map(ArrayList<Rectangle> solidList) {
		this.solidList = solidList;
	}

	public static Map getSimpleMap() {
		ArrayList<Rectangle> solidList = new ArrayList<>();
		Rectangle top = new Rectangle(0, 3 * TITLE_SIZE, GamePanel.WIDTH, TITLE_SIZE);
		Rectangle bottom = new Rectangle(0, 12 * TITLE_SIZE, GamePanel.WIDTH, TITLE_SIZE);
		Rectangle left = new Rectangle(0, 4 * TITLE_SIZE, TITLE_SIZE, (12 - 4) * TITLE_SIZE);
		Rectangle right = new Rectangle(27*TITLE_SIZE, 4 * TITLE_SIZE, TITLE_SIZE, (12 - 4) * TITLE_SIZE);
		Rectangle block = new Rectangle(GamePanel.WIDTH / 2, GamePanel.HEIGHT * 4 / 5 - 100, 200, TITLE_SIZE);
		solidList.add(bottom);
		solidList.add(top);
		solidList.add(left);
		solidList.add(right);
		solidList.add(block);
		return new Map(solidList);
	}

	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		for (Rectangle solid : solidList) {
			g2d.fill(solid);
		}
	}

	public void drawGrid(Graphics2D g2d) {
		for (int r = 0; r <= GamePanel.HEIGHT / TITLE_SIZE; r++) {
			for (int c = 0; c <= GamePanel.WIDTH / TITLE_SIZE; c++) {
				g2d.setColor(Color.RED);
				int x = c * TITLE_SIZE;
				int y = r * TITLE_SIZE;
				g2d.drawRect(x, y, TITLE_SIZE, TITLE_SIZE);
				g2d.drawString("r" + r + "c" + c, x, y + 10);
			}
		}

	}
}
