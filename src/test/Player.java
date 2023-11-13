package test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Player {
	// position properties
	public Rectangle.Float hitbox;
	public float x, y;

	// movement state
	public boolean movingLeft, movingRight, jumping, falling;
	public float velocityY, velocityX;
	public float gravity = 0.2f;
	public float jumpStrength = 7f;
	public Map map;

	public Player() {
		this.x = GamePanel.WIDTH/2;
		this.y = 350;
		this.hitbox = new Rectangle.Float(x, y, 50, 50);
	}

	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.DARK_GRAY);
		g2d.fill(hitbox);
	}

	public void update() {
		updatePosition();
		CollisionHandler.handleCollision(this, map);
	}

	private void updatePosition() {
		if (!movingLeft && !movingRight) {
			velocityX = 0;
		}
		if (!jumping && !falling) {
			velocityY = 0;
		}
		// move left
		if (movingLeft && velocityX == 0) {
			velocityX = jumping ? -3f : -2.5f;
		}
		// move right
		if (movingRight) {
			velocityX = jumping ? 3f : 2.5f;
		}
		// jumping
		if (jumping) {
			if (velocityY == 0)
				velocityY = jumpStrength;
			velocityY -= gravity;
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
		hitbox.y = y -= (velocityY);
		hitbox.x = x += velocityX;
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
