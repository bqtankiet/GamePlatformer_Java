package test;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Player {
	// position properties
	public Rectangle.Float hitbox;
	public float x, y;

	// movement properties
	public boolean movingLeft, movingRight, jumping, falling;

	public Player() {
		this.x = 400;
		this.y = 350;
		this.hitbox = new Rectangle.Float(x, y, 50, 50);
	}

	public void draw(Graphics2D g2d) {
		g2d.fill(hitbox);
	}

	public void update() {
		updatePosition();
	}

	float velocityY;
	float gravity = 0.2f;
	float jumpHeight = 2 * 50;
	float jumpStrength = 6.5f;

	private void updatePosition() {
		if (movingLeft) {
			x -= jumping ? 3.5f : 2.5f;
		}

		if (movingRight) {
			x += jumping ? 3.5f : 2.5f;
		}

		if (jumping) {
			if (velocityY == 0)
				velocityY = jumpStrength;
			velocityY -= gravity;
			if (velocityY <= 0) {
				velocityY = 0;
				jumping = false;
				falling = true;
			}
			y -= velocityY;
//			if (y >= 350) { // is touching surface
//				y = 350;
//				jumping = false;
//				velocityY = 0;
//			}
		}

		if (falling) {
			if (velocityY > 0)
				velocityY = 0;
			velocityY -= gravity;
			y -= (velocityY);
//			if (y >= 350) { // is touching surface
//				velocityY = 0;
//				y = 350;
//				falling = false;
//			}
		}
		hitbox.x = x;
		hitbox.y = y;
	}

}
