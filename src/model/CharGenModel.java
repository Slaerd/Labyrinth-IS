package model;

import java.util.ArrayList;

import javafx.scene.paint.Color;

public class CharGenModel {
	
	public final int nbPlayers = 4;
	public final int nbHeads = 2;
	public final int nbBodies = 2;
	public final int nbLegs = 2;

	
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
		if(this.players.get(this.getCurrentPlayer()).getCurrentHead()==1) {
			this.players.get(this.getCurrentPlayer()).setCurrentHead(2);
		}else {
			this.players.get(this.getCurrentPlayer()).setCurrentHead(1);
		}
		
	}
	
	public void newHeadLeftArrow(PlayerGraphics pg) {
		if(this.players.get(this.getCurrentPlayer()).getCurrentHead()==1) {
			this.players.get(this.getCurrentPlayer()).setCurrentHead(2);
		}else {
			this.players.get(this.getCurrentPlayer()).setCurrentHead(1);
		}
	}
	
	public int getCurrentBody(PlayerGraphics pg) {
		return pg.currentBody;
	}

	public void setCurrentBody(int newCurrentBody,PlayerGraphics pg) {
		pg.currentBody = newCurrentBody;
	}
	
	public void newBodyRightArrow(PlayerGraphics pg) {
		if(this.players.get(this.getCurrentPlayer()).getCurrentBody()==1) {
			this.players.get(this.getCurrentPlayer()).setCurrentBody(2);
		}else {
			this.players.get(this.getCurrentPlayer()).setCurrentBody(1);
		}
	}
	
	public void newBodyLeftArrow(PlayerGraphics pg) {
		if(this.players.get(this.getCurrentPlayer()).getCurrentBody()==1) {
			this.players.get(this.getCurrentPlayer()).setCurrentBody(2);
		}else {
			this.players.get(this.getCurrentPlayer()).setCurrentBody(1);
		}
	}

	public int getCurrentLegs(PlayerGraphics pg) {
		return pg.currentLegs;
	}

	public void setCurrentLegs(int newCurrentLegs, PlayerGraphics pg) {
		pg.currentLegs = newCurrentLegs;
	}
	
	public void newLegsRightArrow(PlayerGraphics pg) {
		if(this.players.get(this.getCurrentPlayer()).getCurrentLegs()==1) {
			this.players.get(this.getCurrentPlayer()).setCurrentLegs(2);
		}else {
			this.players.get(this.getCurrentPlayer()).setCurrentLegs(1);
		}
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
