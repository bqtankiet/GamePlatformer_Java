package test;

public class Game {

	public GamePanel panel;
	public Player player;
	public Map map;

	public Game() {
		this.player = new Player();
		this.map = new Map();
		this.map.getSimpleMap();
		this.player.map = map;
		this.panel = new GamePanel(this);
	}

	public void update() {
		this.player.update();
	}

	
}
