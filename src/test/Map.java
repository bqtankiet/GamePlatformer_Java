package test;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.plaf.SliderUI;

public class Map {

	public ArrayList<Rectangle> solidList;

	public Map(ArrayList<Rectangle> solidList) {
		this.solidList = solidList;
	}

	public static Map getSimpleMap() {
		ArrayList<Rectangle> solidList = new ArrayList<>();
		Rectangle surface = new Rectangle(0, 400, GamePanel.WIDHT, GamePanel.HEIGHT - 400);
		Rectangle block = new Rectangle(GamePanel.WIDHT - 200, 350, 200, 50);
		Rectangle block1 = new Rectangle(200, 300, 300, 50);
		solidList.add(surface);
		solidList.add(block);
		solidList.add(block1);
		return new Map(solidList);
	}

	public void draw(Graphics2D g2d) {
		for (Rectangle solid : solidList) {
			g2d.fill(solid);
		}
	}
}
