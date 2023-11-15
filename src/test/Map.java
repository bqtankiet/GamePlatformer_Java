package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Map {

	public static final int TITLE_SIZE = 50;
	public ArrayList<Rectangle> solidList;
	public int cols = 40;
	public boolean imageOn = false;

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
		Rectangle right = new Rectangle((cols-1) * TITLE_SIZE, 4 * TITLE_SIZE, TITLE_SIZE, (12 - 4) * TITLE_SIZE);
		Rectangle block = new Rectangle(20 * TITLE_SIZE, 10 * TITLE_SIZE, 4 * TITLE_SIZE, TITLE_SIZE);
		solidList.add(bottom);
		solidList.add(top);
		solidList.add(left);
		solidList.add(right);
//		solidList.add(block);
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

	BufferedImage background = new BufferedImage(getWidth()-TITLE_SIZE, 766, BufferedImage.TYPE_INT_ARGB);
	Graphics backgroundG2D = background.getGraphics();
	
	int i = 0;
	public void draw(Graphics2D g2d) {
		if (i == 0) {
		backgroundG2D.drawImage(ImageManager.backgroundLayer1, TITLE_SIZE, 4*TITLE_SIZE,360*2+50,180*2+50, null);
		backgroundG2D.drawImage(ImageManager.backgroundLayer1, TITLE_SIZE+(360*2+50), 4*TITLE_SIZE,360*2+50,180*2+50, null);
		backgroundG2D.drawImage(ImageManager.backgroundLayer1, TITLE_SIZE+(360*2+50)*2, 4*TITLE_SIZE,360*2+50,180*2+50, null);
//		backgroundG2D.drawImage(ImageManager.backgroundLayer2, TITLE_SIZE, 4*TITLE_SIZE,360*2+50,180*2+50, null);
//		backgroundG2D.drawImage(ImageManager.backgroundLayer2, TITLE_SIZE+(360*2+50), 4*TITLE_SIZE,360*2+50,180*2+50, null);
//		backgroundG2D.drawImage(ImageManager.backgroundLayer2, TITLE_SIZE+(360*2+50)*2, 4*TITLE_SIZE,360*2+50,180*2+50, null);
		backgroundG2D.drawImage(ImageManager.backgroundLayer3, TITLE_SIZE, 4*TITLE_SIZE,360*2+50,180*2+50, null);
		backgroundG2D.drawImage(ImageManager.backgroundLayer3, TITLE_SIZE+(360*2+50), 4*TITLE_SIZE,360*2+50,180*2+50, null);
		backgroundG2D.drawImage(ImageManager.backgroundLayer3, TITLE_SIZE+(360*2+50)*2, 4*TITLE_SIZE,360*2+50,180*2+50, null);
		i = 1;
		}
		
		if(imageOn) {
		// draw image
		g2d.drawImage(background,0,0,null);
		g2d.drawImage(ImageManager.shop, 10*TITLE_SIZE, 8*TITLE_SIZE,200,200,null);
		g2d.drawImage(ImageManager.lamp, 15*TITLE_SIZE, 10*TITLE_SIZE-15,ImageManager.lamp.getWidth()*2,ImageManager.lamp.getHeight()*2,null);
		g2d.drawImage(ImageManager.fence_1, 20*TITLE_SIZE, 11*TITLE_SIZE+12,ImageManager.fence_1.getWidth()*2,ImageManager.fence_1.getHeight()*2,null);
		
		for (int i = 0; i < cols; i++) {
			g2d.drawImage(ImageManager.grassTile, i * TITLE_SIZE, 12*TITLE_SIZE, TITLE_SIZE, TITLE_SIZE, null);
			}
		} else {
			// draw solid
			g2d.setColor(Color.white);
			for (Rectangle solid : solidList) {
				g2d.fill(solid);
			}
		}
	}

	public int getWidth() {
		return cols * TITLE_SIZE;
	}
}
