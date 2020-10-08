package model;

public class Tile {
	public static final int FLOOR = 0;
	public static final int WALL = 1;
	public static final int VOID = 2;
	
	public static final int TRAP = -1;
	public static final int BONUSACTION = 1;
	public static final int BONUSMOVEMENT = 2;
	
	private String playerName = "";
	private int tileType;
	private int specialEvent = 0;
	
	public Tile(int type){
		tileType = type;
	}
	
	public Tile(int type, int special) {
		tileType = type;
		specialEvent = special;
	}
	
	public void putPlayer(String name) {
		playerName = name;
	}
	public void removePlayer(String name) {
		playerName = "";
	}
	public void removeSpecial() {
		specialEvent = 0;
	}
}
