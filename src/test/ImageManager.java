package test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageManager {

	// sprite
	public static BufferedImage sprite;
	public static BufferedImage idleSprite;
	public static BufferedImage runSprite;
	public static BufferedImage jumpSprite;
	// background
	public static BufferedImage backgroundLayer3;
	public static BufferedImage backgroundLayer2;
	public static BufferedImage backgroundLayer1;
	// tile set
	public static BufferedImage tileSet;
	public static BufferedImage grassTile;
	// object
	public static BufferedImage shop;
	public static BufferedImage lamp;
	public static BufferedImage fence_1;

	public static final int SIZE = 56;

	static {
		try {
			loadBackgroundImg();
			loadSpriteImg();
			loadTileSetImg();
			loadObjectImg();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void loadObjectImg() throws IOException {
		shop = ImageIO.read(ImageManager.class.getResourceAsStream("/decorations/shop.png"));
		lamp = ImageIO.read(ImageManager.class.getResourceAsStream("/decorations/lamp.png"));
		fence_1 = ImageIO.read(ImageManager.class.getResourceAsStream("/decorations/fence_1.png"));
	}

	private static void loadTileSetImg() throws IOException {
		tileSet = ImageIO.read(ImageManager.class.getResourceAsStream("/sprite/oak_woods_tileset.png"));
		grassTile = tileSet.getSubimage(24, 0, 24, 24);
	}

	private static void loadSpriteImg() throws IOException {
		sprite = ImageIO.read(ImageManager.class.getResourceAsStream("/sprite/character.png"));
		idleSprite = sprite.getSubimage(0, 0, SIZE * 6, SIZE);
		runSprite = sprite.getSubimage(0, SIZE * 2, SIZE * 8, SIZE);
		jumpSprite = sprite.getSubimage(0, SIZE * 3, SIZE * 8, SIZE);
	}

	private static void loadBackgroundImg() throws IOException {
		backgroundLayer3 = ImageIO.read(ImageManager.class.getResourceAsStream("/background/background_layer_3.png"));
		backgroundLayer2 = ImageIO.read(ImageManager.class.getResourceAsStream("/background/background_layer_2.png"));
		backgroundLayer1 = ImageIO.read(ImageManager.class.getResourceAsStream("/background/background_layer_1.png"));
	}

	public static BufferedImage getIdleSprite(int i) {
		return idleSprite.getSubimage(i * SIZE, 0, SIZE, SIZE);
	}

	public static BufferedImage getRunSprite(int i) {
		return runSprite.getSubimage(i * SIZE, 0, SIZE, SIZE);
	}

	public static BufferedImage getSpriteAt(int i, BufferedImage sprites) {
		BufferedImage sprite = null;
		try {
			sprite = sprites.getSubimage(i * SIZE, 0, SIZE, SIZE);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Don't match sprite at " + i);
		}
		return sprite;
	}

}
