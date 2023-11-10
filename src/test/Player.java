package test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

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
		this.x = 400;
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
				jumping = false;
				falling = true;
			}
		}
		// falling
		if (!CollisionHandler.stadingOnFloor(this, map) && !jumping && !falling) {
			falling = true;
		}
		if (falling) {
			if(velocityY > 0) velocityY = 0;
			velocityY += -gravity * 1.2;
		}
		// update after have velocity
		hitbox.x = x += velocityX;
		hitbox.y = y -= (velocityY);
	}

//	private boolean stadingOnFloor() {
//		for (Rectangle solid : map.solidList) {
//			hitbox.y += 0.1f;
//			if (hitbox.intersects(solid)) {
//				hitbox.y -= 0.1f;
//				return true;
//			}
//		}
//		hitbox.y -= 0.1f;
//		return false;
//	}

//	private void updateCollision() {
//		for (Rectangle solid : map.solidList) {
//			boolean collisionDetected = hitbox.intersects(solid);
//
//			if (collisionDetected) {
//				Rectangle2D intersectRect = hitbox.createIntersection(solid);
//				float dx = (float) intersectRect.getWidth();
//				float dy = (float) intersectRect.getHeight();
//				if (falling && dy <= 10 && hitbox.y < solid.y) {
//					velocityY = 0;
//					falling = false;
//					dx = 0f;
//					hitbox.y = y -= dy;
//				}
//				if (jumping && dy <= 10 && hitbox.y > solid.y) {
//					velocityY = 0;
//					falling = true;
//					jumping = false;
//					dx = 0f;
//					hitbox.y = y += dy;
//				}
//				if (movingRight && dx != hitbox.width) {
//					velocityX = 0;
//					hitbox.x = x -= dx;
//				}
//				if (movingLeft && dx != hitbox.width) {
//					velocityX = 0;
//					hitbox.x = x += dx;
//				}
//			}
//		}
//	}
	
//	private void updateCollision() {
//	    for (Rectangle solid : map.solidList) {
//	        if (hitbox.intersects(solid)) {
//	            handleCollision(solid);
//	        }
//	    }
//	}
//
//	private void handleCollision(Rectangle solid) {
//	    Rectangle2D intersectRect = hitbox.createIntersection(solid);
//	    float dx = (float) intersectRect.getWidth();
//	    float dy = (float) intersectRect.getHeight();
//	    boolean isFallingCollision = falling && dy <= 10 && hitbox.y < solid.y;
//	    boolean isJumpingCollision = jumping && dy <= 10 && hitbox.y > solid.y;
//	    boolean isRightCollision = movingRight && dx != hitbox.width;
//	    boolean isLeftCollision = movingLeft && dx != hitbox.width;
//
//	    if (isFallingCollision) {
//	        handleFallingCollision(dy);
//	        dx = 0f;
//	    }
//
//	    if (isJumpingCollision) {
//	        handleJumpingCollision(dy);
//	        dx = 0f;
//	    }
//
//	    if (isRightCollision) {
//	        handleRightCollision(dx);
//	    }
//
//	    if (isLeftCollision) {
//	        handleLeftCollision(dx);
//	    }
//	}
//
//	private void handleFallingCollision(float dy) {
//	    velocityY = 0;
//	    falling = false;
//	    hitbox.y = y -= dy;
//	}
//
//	private void handleJumpingCollision(float dy) {
//	    velocityY = 0;
//	    falling = true;
//	    jumping = false;
//	    hitbox.y = y += dy;
//	}
//
//	private void handleRightCollision(float dx) {
//	    velocityX = 0;
//	    hitbox.x = x -= dx;
//	}
//
//	private void handleLeftCollision(float dx) {
//	    velocityX = 0;
//	    hitbox.x = x += dx;
//	}

}
