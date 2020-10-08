package model;

public class Player {
	private String name;
	private Position pos;
	private int action = 2;
	private int cible;
	private int bonusAction = 0;
	private int bonusMovement = 0;
	
	public Player(String name, int x, int y) {
		this.name = name;
		pos = new Position(x,y);
	}
	
	public void moveTo(int x, int y) {
		if(action != 0) {
				this.pos = new Position(x, y);
			
		}
	}
}

public class Position {
	protected int x;
	protected int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
