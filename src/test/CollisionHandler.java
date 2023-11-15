package test;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class CollisionHandler {

	public static void handleCollision(Player player, Map map) {
		player.hitbox.x = player.x + player.size/3;
		player.hitbox.y = player.y + (player.size - player.hitbox.height);
		for (Rectangle solid : map.solidList) {
			if (player.hitbox.intersects(solid)) {
				handleCollision(player, solid);
			}
		}
		player.hitbox.x = player.x + player.size/3;
		player.hitbox.y = player.y + (player.size - player.hitbox.height);

	}

	public static boolean stadingOnFloor(Player player, Map map) {
		for (Rectangle solid : map.solidList) {
			player.hitbox.y += 0.1f;
			if (player.hitbox.intersects(solid)) {
				player.hitbox.y -= 0.1f;
				return true;
			}
		}
		player.hitbox.y -= 0.1f;
		return false;
	}

	private static void handleCollision(Player player, Rectangle solid) {
		Rectangle2D intersectRect = player.hitbox.createIntersection(solid);
		float dx = (float) intersectRect.getWidth();
		float dy = (float) intersectRect.getHeight();
		boolean isFallingCollision = player.falling && dy <= 15 && player.hitbox.y < solid.y;
		boolean isJumpingCollision = player.jumping && dy <= 10 && player.hitbox.y > solid.y;
		boolean isRightCollision = player.movingRight && dx != player.hitbox.width;
		boolean isLeftCollision = player.movingLeft && dx != player.hitbox.width;

		if (isFallingCollision) {
			handleFallingCollision(player, dy);
			dx = 0f;
		}

		if (isJumpingCollision) {
			handleJumpingCollision(player, dy);
			dx = 0f;
		}

		if (isRightCollision) {
			handleRightCollision(player, dx);
		}

		if (isLeftCollision) {
			handleLeftCollision(player, dx);
		}
	}

	private static void handleFallingCollision(Player player, float dy) {
		player.velocityY = 0;
		player.falling = false;
		player.hitbox.y = player.y -= dy;
	}

	private static void handleJumpingCollision(Player player, float dy) {
		player.velocityY = 0;
		player.falling = true;
		player.jumping = false;
		player.hitbox.y = player.y += dy;
	}

	private static void handleRightCollision(Player player, float dx) {
		player.velocityX = 0;
		player.hitbox.x = player.x -= dx;
	}

	private static void handleLeftCollision(Player player, float dx) {
		player.velocityX = 0;
		player.hitbox.x = player.x += dx;
	}

}
