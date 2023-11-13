package test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Map {

	public static final int TITLE_SIZE = 50;
	public ArrayList<Rectangle> solidList;
	public int cols = 35;

	public Map(ArrayList<Rectangle> solidList) {
		this.solidList = solidList;
	}

	public Map() {
	}

	public void getSimpleMap() {
		ArrayList<Rectangle> solidList = new ArrayList<>();
		Rectangle top = new Rectangle(0, 3 * TITLE_SIZE, (cols) * TITLE_SIZE, TITLE_SIZE);
		Rectangle bottom = new Rectangle(0, 12 * TITLE_SIZE, (cols) * TITLE_SIZE, TITLE_SIZE);
		Rectangle left = new Rectangle(0, 4 * TITLE_SIZE, TITLE_SIZE, (12 - 4) * TITLE_SIZE);
		Rectangle right = new Rectangle((cols - 1) * TITLE_SIZE, 4 * TITLE_SIZE, TITLE_SIZE, (12 - 4) * TITLE_SIZE);
		Rectangle block = new Rectangle(20 * TITLE_SIZE, 10 * TITLE_SIZE, 4 * TITLE_SIZE, TITLE_SIZE);
		solidList.add(bottom);
		solidList.add(top);
		solidList.add(left);
		solidList.add(right);
		solidList.add(block);
		this.solidList = solidList;
	}
	
	public void getSimpleMap1() {
		ArrayList<Rectangle> solidList = new ArrayList<>();
		Rectangle top = new Rectangle(0, 3 * TITLE_SIZE, (cols) * TITLE_SIZE, TITLE_SIZE);
		Rectangle bottom = new Rectangle(0, 12 * TITLE_SIZE, (cols) * TITLE_SIZE, TITLE_SIZE);
		Rectangle left = new Rectangle(0, 4 * TITLE_SIZE, TITLE_SIZE, (12 - 4) * TITLE_SIZE);
		Rectangle right = new Rectangle((cols - 1) * TITLE_SIZE, 4 * TITLE_SIZE, TITLE_SIZE, (12 - 4) * TITLE_SIZE);
		Rectangle block = new Rectangle(20 * TITLE_SIZE, 10 * TITLE_SIZE, 4 * TITLE_SIZE, TITLE_SIZE);
		Rectangle block1 = new Rectangle(13 * TITLE_SIZE, 9 * TITLE_SIZE, 4 * TITLE_SIZE, TITLE_SIZE);
		solidList.add(bottom);
		solidList.add(top);
		solidList.add(left);
		solidList.add(right);
		solidList.add(block);
		solidList.add(block1);
		this.solidList = solidList;
	}

	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		for (Rectangle solid : solidList) {
			g2d.fill(solid);
		}
	}

	public void scroll(float deltaX) {
		for (Rectangle solid : solidList) {
			solid.x += deltaX;
		}
	}

	public int getWidth() {
		return cols * TITLE_SIZE;
	}
}
