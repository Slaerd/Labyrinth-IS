package model;

public class PlayerGraphics {
	
	int playerNumber;
	
	public int currentHead;
	public int currentBody;
	public int currentLegs;
	
	public PlayerGraphics(int playerNumber) {
		this.playerNumber = playerNumber;
		this.currentHead = 1;
		this.currentBody = 1;
		this.currentLegs = 1;
	}
	
	public int getPlayerNumber() {
		return this.playerNumber;
	}
	
	public int getCurrentHead() {
		return this.currentHead;
	}
	
	public int getCurrentBody() {
		return this.currentBody;
	}
	
	public int getCurrentLegs() {
		return this.currentLegs;
	}
}
