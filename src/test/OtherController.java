package test;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

public class OtherController implements KeyListener {
	public Game game;

	public OtherController(Game game) {
		this.game = game;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_G -> {
			game.panel.showGrid = !game.panel.showGrid;
		}
		case KeyEvent.VK_ESCAPE -> {
			int option = JOptionPane.showConfirmDialog(null, "Exit");
			if (option == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
		case KeyEvent.VK_ENTER -> {
		}
		case KeyEvent.VK_O -> {
			game.player.imageOn = !game.player.imageOn;
			game.map.imageOn = !game.map.imageOn;
		}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
