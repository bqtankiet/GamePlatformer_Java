package test;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MovingController implements KeyListener {
	public Game game;
	public boolean leftPressed, rightPressed, spacePressed;

	public MovingController(Game game) {
		this.game = game;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_LEFT -> {
			game.player.movingLeft = leftPressed = true;
			game.player.movingRight = false;
		}
		case KeyEvent.VK_RIGHT -> {
			game.player.movingRight = rightPressed = true;
			game.player.movingLeft = false;
		}
		case KeyEvent.VK_SPACE -> {
			if (!game.player.falling && !spacePressed) {
				game.player.jumping = spacePressed = true;
				game.player.falling = false;
			}
		}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_LEFT -> {
			game.player.movingLeft = leftPressed = false;
			game.player.movingRight = rightPressed;
		}
		case KeyEvent.VK_RIGHT -> {
			game.player.movingRight = rightPressed = false;
			game.player.movingLeft = leftPressed;
		}
		case KeyEvent.VK_SPACE -> {
			game.player.jumping = spacePressed = false;
			game.player.falling = true;
		}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
