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
			hitbox.x = x -= jumping ? 3.5f : 2.5f;
			collisionX();
		}

		if (movingRight) {
			hitbox.x = x += jumping ? 3.5f : 2.5f;
			collisionX();
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
			hitbox.y = y -= velocityY;
			if (collisionY()) {
				velocityY = 0;
				jumping = false;
				falling = true;
			}
		}
		if (!standingOnSolid() && !jumping && !falling) {
			falling = true;
		}
		if (falling) {
			if (velocityY > 0)
				velocityY = 0;
			velocityY -= gravity * 1.2;
			hitbox.y = y -= (velocityY);
			if (standingOnSolid()) {
				collisionY();
				velocityY = 0;
				falling = false;
			}
		}
	}

	private boolean standingOnSolid() {
		hitbox.y += 0.1;
		for (Rectangle solid : map.solidList) {
			if (hitbox.intersects(solid)) {
				hitbox.y -= 0.1;
				return true;
			}
		}
		hitbox.y -= 0.1;
		return false;
	}

	public Map map;

	public boolean collisionX() {
		for (Rectangle solid : map.solidList) {
			if (hitbox.intersects(solid)) {
				System.out.println("collison x");
				float dxIntersect = 0f;
				// intersect right side
				if (hitbox.x <= solid.x) {
					dxIntersect = Math.abs(solid.x - (hitbox.x + hitbox.width));
					hitbox.x = x -= dxIntersect;
					break;
				}
				// intersect left side
				if (hitbox.x >= solid.x) {
					dxIntersect = Math.abs((solid.x + solid.width) - hitbox.x);
					hitbox.x = x += dxIntersect;
					break;
				}
				return true;
			}
		}
		return false;
	}

	public boolean collisionY() {
		for (Rectangle solid : map.solidList) {
			if (hitbox.intersects(solid)) {
				System.out.println("collision y");
				float dyIntersect = 0f;
				// intersect top
				if (hitbox.y >= solid.y) {
					dyIntersect = Math.abs((solid.y + solid.height) - hitbox.y);
					hitbox.y = y += dyIntersect;
					break;
				}
				// intersect bottom
				if (hitbox.y <= solid.y) {
					dyIntersect = Math.abs(solid.y - (hitbox.y + hitbox.height));
					hitbox.y = y -= dyIntersect;
					break;
				}
				return true;
			}
		}
		return false;
	}
}
