package test;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import test.Map;

public class Player {
	// position properties
	public Rectangle.Float hitbox;
	public float x, y;

	// movement properties
	public boolean movingLeft, movingRight, jumping, falling;
	public float velocityY, velocityX;
	public float gravity = 0.2f;
	public float jumpHeight = 2 * 50;
	public float jumpStrength = 6.5f;
	public Map map;

	public Player() {
		this.x = 400;
		this.y = 350;
//		this.size = 50;
		this.hitbox = new Rectangle.Float(x, y, 50, 50);
	}

	public void draw(Graphics2D g2d) {
		g2d.fill(hitbox);
	}

	public void update() {
		updatePosition();
		updateCollision();
		System.out.println("x: " + hitbox.x);
		System.out.println("y: " + hitbox.y);
	}

	private void updatePosition() {
		// move left
		if (movingLeft) {
			velocityX = jumping ? -3.5f : -2.5f;
		}
		// move right
		if (movingRight) {
			velocityX = jumping ? 3.5f : 2.5f;
		}
		// jumping
		if (jumping) {
			if (velocityY == 0)
				velocityY = jumpStrength;
			velocityY -= gravity;
			if (velocityY <= 0) {
				velocityY = 0;
				jumping = false;
				falling = true;
			}
		}
		// falling
		if (!stadingOnFloor() && !jumping && !falling) {
			falling = true;
		}
		if (falling) {
			if (velocityY > 0)
				velocityY = 0;
			velocityY += -gravity * 1.2;
		}
		// update after have velocity
		hitbox.x = x += velocityX;
		hitbox.y = y -= (velocityY);
		velocityX = 0;
	}

	private boolean stadingOnFloor() {
		for (Rectangle solid : map.solidList) {
			hitbox.y += 0.1f;
			if (hitbox.intersects(solid)) {
				hitbox.y -= 0.1f;
				return true;
			}
		}
		hitbox.y -= 0.1f;
		return false;
	}

	private void updateCollision() {
		for (Rectangle solid : map.solidList) {
			if (hitbox.intersects(solid)) {
				Rectangle2D rect = hitbox.createIntersection(solid);
				if (falling && hitbox.y + hitbox.height - 10 < solid.y) {
					hitbox.y = y -= (float) rect.getHeight();
					velocityY = 0;
					falling = false;
				} else if (jumping && hitbox.y > solid.y+solid.height - 10) {
					hitbox.y = y += (float) rect.getHeight();
					velocityY = 0;
					falling = true;
					jumping = false;
				} else if (movingRight) {
					hitbox.x = x -= (float) rect.getWidth();
				} else if (movingLeft) {
					hitbox.x = x += (float) rect.getWidth();
				}
			}
		}
	}

	public boolean collisionY() {
		for (Rectangle solid : map.solidList) {
			if (hitbox.intersects(solid)) {
				float dyIntersect = 0f;
				// intersect top
				if (hitbox.y >= solid.y) {
					dyIntersect = Math.abs((solid.y + solid.height) - hitbox.y);
					hitbox.y = y += dyIntersect;
					return true;
				}
				// intersect bottom
				if (hitbox.y <= solid.y) {
					dyIntersect = Math.abs(solid.y - (hitbox.y + hitbox.height));
					hitbox.y = y -= dyIntersect;
					return true;
				}
			}
		}
		return false;
	}
}
