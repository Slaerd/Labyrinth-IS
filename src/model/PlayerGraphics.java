package model;

import javafx.scene.paint.Color;

public class PlayerGraphics {
	
	int playerNumber;
	
	public int currentHead;
	public int currentBody;
	public int currentLegs;
	
	public Color currentColor;
	
	
	public PlayerGraphics(int playerNumber) {
		this.playerNumber = playerNumber;
		this.currentHead = 1;
		this.currentBody = 1;
		this.currentLegs = 1;
	}
	
	public void setCurrentHead(int newHead) {
		this.currentHead = newHead;
	}
	
	public int getPlayerNumber() {
		return this.playerNumber;
	}
	
	public void setCurrentBody(int newBody) {
		this.currentBody = newBody;
	}
	
	public void setCurrentLegs(int newLegs) {
		this.currentLegs = newLegs;
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
	
	public Color getCurrentColor() {
		return this.currentColor;
	}
}
