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

	private void updatePosition() {
		if (movingLeft) {
			x -= 2.5f;
		}
		if (movingRight) {
			x += 2.5f;
		}
		hitbox.x = x;
		hitbox.y = y;
	}

}
