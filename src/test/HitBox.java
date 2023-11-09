package test;

import java.awt.Rectangle;

public class HitBox extends Rectangle.Float {
	private static final long serialVersionUID = 1L;

	public HitBox(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public boolean collisionRight(Map map) {
		for (Rectangle solid : map.solidList) {
			if (this.intersects(solid) && this.x > solid.x) {
				return true;
			}
		}
		return false;
	}

	public boolean collisionLeft(Map map) {
		for (Rectangle solid : map.solidList) {
			if (this.intersects(solid) && this.x < solid.x) {
				return true;
			}
		}
		return false;
	}

	public boolean collisionBottom(Map map) {
		for (Rectangle solid : map.solidList) {
			if (this.intersects(solid) && this.y < solid.y) {
				return true;
			}
		}
		return false;
	}
	
	public boolean collisionTop(Map map) {
		for (Rectangle solid : map.solidList) {
			if (this.intersects(solid) && this.y > solid.y) {
				return true;
			}
		}
		return false;
	}
}
