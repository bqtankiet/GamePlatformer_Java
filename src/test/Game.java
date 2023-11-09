package test;

import java.awt.Rectangle;

public class Game {

	public GamePanel panel;
	public Player player;
	public Map map;

	public Game() {
		this.player = new Player();
		this.map = Map.getSimpleMap();
		this.panel = new GamePanel(this);
		startGameLoop();
	}

	private void update() {
		player.map = map;
		this.player.update();
		// Test intersect with surface
//			if (player.hitbox.intersects(solid)) {
//				float dyIntersect = Math.abs(solid.y - (player.hitbox.y + player.hitbox.height));
//				player.hitbox.y = player.y -= dyIntersect;
//				player.velocityY = 0;
//				player.falling = false;
//			} 
	}

	public void startGameLoop() {
		new Thread(() -> {
			int frames = 0;
			double timePerFrame = 1_000_000_000 / 120;
			long fpsTimer = System.nanoTime();
			double delta = 0.0;
			long lastTime = System.nanoTime();
			long currentTime;

			while (true) {
				currentTime = System.nanoTime();
				delta += (currentTime - lastTime) / timePerFrame;
				lastTime = currentTime;

				if (delta >= 1) {
					delta--;
					update();
					panel.repaint();
					frames++;
				}

				if (System.nanoTime() - fpsTimer >= 1_000_000_000) {
//					System.out.println("FPS: " + frames);
					frames = 0;
					fpsTimer = System.nanoTime();
				}
			}
		}).start();
	}
}
