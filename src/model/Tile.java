package model;

public class Tile {
	public static final int FLOOR = 0;
	public static final int SPAWN = 1;
	public static final int WALL = 2;
	public static final int VOID = 3;
	
	public static final int TRAP = -1;
	public static final int BONUSACTION = 1;
	public static final int BONUSMOVEMENT = 2;
	
	private int playerNumber = -1;
	private int tileType;
	private int specialEvent = 0; //contains either traps or bonuses (bonus not implemented)
	private boolean shadowMode = false; //Tracks if a drag and drop motion is happening
	private boolean hoverMode = false;
	
	protected int x;
	protected int y;
	
	public Tile(int type, int x, int y){
		tileType = type;
		this.x = x;
		this.y = y;
	}
	
	public Tile(int type, int x, int y, int special) {
		tileType = type;
		this.x = x;
		this.y = y;
		specialEvent = special;
	}
	
	public void setType(int type) {
		this.tileType = type;
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
	
	public int getSpecial() {
		return specialEvent;
	}
	
	public void removeSpecial() {
		specialEvent = 0;
	}
	
	//////////////////////
	//// WALL METHODS ////
	//////////////////////
	
	public boolean isShadowed() {
		return shadowMode;
	}

	public void setShadow(boolean shadowMode) {
		this.shadowMode = shadowMode;
	}
	
	public boolean isHovered() {
		return hoverMode;
	}
	
	public void setHover(boolean hoverMode) {
		this.hoverMode = hoverMode;
	}
}
