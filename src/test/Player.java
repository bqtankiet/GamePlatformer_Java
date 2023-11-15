package test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Player {
	// position properties
	public Rectangle.Float hitbox;
	public float x, y;
	public int size;
	// movement state
	public boolean movingLeft, movingRight, jumping, falling;
	public float velocityY, velocityX;
	public float gravity = 0.18f;
	public float jumpStrength = 8f;
	// other properties
	public Map map;
	public int numOfSprite;
	public BufferedImage image = null;
	public boolean imageOn = false;

	public Player() {
		this.x = GamePanel.WIDTH / 2;
		this.y = 350;
		this.size = 150;
		this.hitbox = new Rectangle.Float(x, y, 40, 80);
	}

	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.RED);
//		BufferedImage image = currentAnimation.getSubimage(currentIndex * 56, 0, 56, 56);
		if (imageOn) {
			g2d.drawImage(image, Math.round(x), Math.round(y), size, size, null);
		}
		g2d.draw(hitbox);
	}

	public void update() {
		updatePosition();
		CollisionHandler.handleCollision(this, map);
		updateAnimation();
	}

	int frames = 0;
	int delay = 15;
	int currentIndex = 0;

	private void updateAnimation() {
		// update animation index
		if (movingLeft || movingRight) {
			numOfSprite = 8;
		} else {
			numOfSprite = 6;
		}
		frames++;
		if (currentIndex >= numOfSprite - 1) {
			currentIndex = 0;
		}
		if (frames >= delay) {
			frames = 0;
			currentIndex++;
		}
		// update animation image base on animation index
		if (jumping) {
			image = ImageManager.getSpriteAt(6, ImageManager.jumpSprite);
		} else if (falling) {
			image = ImageManager.getSpriteAt(1, ImageManager.fallSprite);
		} else if (movingLeft || movingRight) {
			image = ImageManager.getSpriteAt(currentIndex, ImageManager.runSprite);
		} else {
			image = ImageManager.getSpriteAt(currentIndex, ImageManager.idleSprite);
		}
		if (movingLeft)
			image = ImageManager.flipHorizontal(image);
	}

	private void updatePosition() {
		if (!movingLeft && !movingRight) {
			velocityX = 0;
		}
		if (!jumping && !falling) {
			velocityY = 0;
		}
		// move left
		if (movingLeft) {
			velocityX = jumping || falling ? -2.5f : -2f;
		}
		// move right
		if (movingRight) {
			velocityX = jumping || falling ? 2.5f : 2f;
		}
		// jumping
		if (jumping) {
			if (velocityY == 0)
				velocityY = jumpStrength;
			velocityY -= gravity * 1.2;
			if (velocityY <= 0) {
				jumping = false;
				falling = true;
			}
		}
		// falling
		if (!CollisionHandler.stadingOnFloor(this, map) && !jumping && !falling) {
			falling = true;
		}
		if (falling) {
			if (velocityY > 0)
				velocityY = 0;
			velocityY += -gravity * 1.2;
		}
		y -= (velocityY);
		x += velocityX;
//		for (Rectangle solid : map.solidList) {
//			hitbox.x += velocityX / 2;
//			if (solid.intersects(hitbox)) {
//				hitbox.x -= velocityX / 2;
//				velocityX = 0;
//				break;
//			}
//			hitbox.x -= velocityX / 2;
//		}
//		map.scroll(-velocityX);

	}
}
