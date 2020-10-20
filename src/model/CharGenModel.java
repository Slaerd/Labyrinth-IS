package model;

import java.util.ArrayList;

import javafx.scene.paint.Color;

public class CharGenModel {
	
	public final int nbPlayers = 3;
	public final int nbHeads = 5;
	public final int nbBodies = 5;
	public final int nbLegs = 5;

	
	public int currentPlayer;
	
	public ArrayList<PlayerGraphics> players = new ArrayList<PlayerGraphics>();

	public CharGenModel() {
		this.currentPlayer = 1;
	}
	
	public int getCurrentPlayer() {
		return this.currentPlayer;
	}
	
	public void addPlayers() {
		for(int i = 0; i < nbPlayers; i ++) {
			players.add(new PlayerGraphics(i+1));	
		}
	}
	
	public void setCurrentPlayer(int newCurrentPlayer) {
		this.currentPlayer = newCurrentPlayer;
		System.out.println("Current player # : " + getCurrentPlayer());
	}
	
	public int getCurrentHead(PlayerGraphics pg) {
		return pg.getCurrentHead();
	}

	public void setCurrentHead(int newCurrentHead, PlayerGraphics pg) {
		pg.currentHead = newCurrentHead;
	}
	
	public void newHeadRightArrow(PlayerGraphics pg) {
		pg.currentHead = (getCurrentHead(pg) + 1)%nbHeads;
		setCurrentHead(pg.currentHead, pg);
	}
	
	public void newHeadLeftArrow(PlayerGraphics pg) {
		if(pg.getCurrentHead()==0) {
			setCurrentHead(0, pg);
		}else {
		pg.currentHead = Math.abs((getCurrentHead(pg) - 1)%nbHeads);
		setCurrentHead(pg.getCurrentHead(), pg);
		}
	}
	
	//same for body
	public int getCurrentBody(PlayerGraphics pg) {
		return pg.currentBody;
	}

	public void setCurrentBody(int newCurrentBody,PlayerGraphics pg) {
		pg.currentBody = newCurrentBody;
	}
	
	public void newBodyRightArrow(PlayerGraphics pg) {
		pg.currentBody = Math.abs((getCurrentBody(pg) + 1)%nbBodies);
		setCurrentBody(pg.currentBody, pg);
	}
	
	public void newBodyLeftArrow(PlayerGraphics pg) {
		pg.currentBody = Math.abs((getCurrentBody(pg) - 1)%nbBodies);
		setCurrentBody(pg.currentBody, pg);
	}
	//same for legs
	public int getCurrentLegs(PlayerGraphics pg) {
		return pg.currentLegs;
	}

	public void setCurrentLegs(int newCurrentLegs, PlayerGraphics pg) {
		pg.currentLegs = newCurrentLegs;
	}
	
	public void newLegsRightArrow(PlayerGraphics pg) {
		pg.currentLegs = (getCurrentLegs(pg) + 1)%nbLegs;
		setCurrentLegs(pg.currentLegs, pg);
	}
	
	public void newLegsLeftArrow(PlayerGraphics pg) {
		pg.currentLegs = (getCurrentLegs(pg) - 1)%nbLegs;
		setCurrentLegs(pg.currentLegs, pg);
	}
	
	public Color getCurrentColor(PlayerGraphics pg) {
		return pg.currentColor;
	}
	
	public void setCurrentColor(PlayerGraphics pg, Color newColor) {
		pg.currentColor = newColor;
	}
	
	public ArrayList<PlayerGraphics> getPlayers(){
		return this.players;
	}
	
	
	
}
