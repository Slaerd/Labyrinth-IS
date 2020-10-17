package model;

public class Tile {
	public static final int FLOOR = 0;
	public static final int WALL = 1;
	public static final int VOID = 2;
	
	public static final int TRAP = -1;
	public static final int BONUSACTION = 1;
	public static final int BONUSMOVEMENT = 2;
	
	private int playerNumber = -1;
	private int tileType;
	private int specialEvent = 0;
	
	public int x;
	public int y;
	
	public Tile(int type, int x, int y){
		tileType = type;
		this.x = x;
		this.y = y;
	}
	
	public Tile(int type, int special) {
		tileType = type;
		specialEvent = special;
	}
	
	public int getType() {
		return tileType;
	}
	
	public int getPlayerNumber() {
		return playerNumber;
	}
	
	public void putPlayer(int n) {
		playerNumber = n;
	}
	public void removePlayer() {
		playerNumber = -1;
	}
	public void removeSpecial() {
		specialEvent = 0;
	}
	
	//////////////////////
	//// WALL METHODS ////
	//////////////////////
	
	
}
