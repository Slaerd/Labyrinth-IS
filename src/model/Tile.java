package model;

public class Tile {
	public static final int FLOOR = 0;
	public static final int WALL = 1;
	public static final int VOID = 2;
	
	public static final int TRAP = -1;
	public static final int BONUSACTION = 1;
	public static final int BONUSMOVEMENT = 2;
	
	private int playerNumber;
	private int tileType;
	private int specialEvent = 0;
	
	public Tile(int type){
		tileType = type;
		playerNumber = -1;
	}
	
	public Tile(int type, int special) {
		tileType = type;
		specialEvent = special;
	}
	
	public int getType() {
		return tileType;
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
}
