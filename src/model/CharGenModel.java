package model;

public class CharGenModel {
	
	public final int nbPlayers = 3;
	
	String currentPlayer;
	int currentHead;
	int currentBody;
	int currentLegs;
	
	public CharGenModel() {
		this.currentPlayer = "1";
		this.currentHead = 1;
		this.currentBody = 1;
		this.currentLegs = 1;
	}
	
	public String getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void setCurrentPlayer(String newCurrentPlayer) {
		this.currentPlayer = newCurrentPlayer;
		System.out.println("Current player # : " + getCurrentPlayer());
	}
	
	//used to change the display of the middle of the headPane
	//atm we use numbers to differentiate head styles
	public int getCurrentHead() {
		return currentHead;
	}

	public void setCurrentHead(int newCurrentHead) {
		this.currentHead = newCurrentHead;
	}
	
	public void newHeadRightArrow() {
		this.currentHead = getCurrentHead() + 1;
		setCurrentHead(this.currentHead);
	}
	
	public void newHeadLeftArrow() {
		this.currentHead = getCurrentHead() - 1;
		setCurrentHead(this.currentHead);
	}
	
	//same for body
	public int getCurrentBody() {
		return currentBody;
	}

	public void setCurrentBody(int newCurrentBody) {
		this.currentBody = newCurrentBody;
	}
	
	public void newBodyRightArrow() {
		this.currentBody = getCurrentBody() + 1;
		setCurrentBody(this.currentBody);
	}
	
	public void newBodyLeftArrow() {
		this.currentBody = getCurrentBody() - 1;
		setCurrentBody(this.currentBody);
	}
	//same for legs
	public int getCurrentLegs() {
		return currentLegs;
	}

	public void setCurrentLegs(int newCurrentLegs) {
		this.currentLegs = newCurrentLegs;
	}
	
	public void newLegsRightArrow() {
		this.currentLegs = getCurrentLegs() + 1;
		setCurrentLegs(this.currentLegs);
	}
	
	public void newLegsLeftArrow() {
		this.currentLegs = getCurrentLegs() - 1;
		setCurrentLegs(this.currentLegs);
	}
	
	//adds one to the player number
	public void changePlayerNumber() {
		int tmp = Integer.parseInt(this.currentPlayer);
		tmp+=1;
		this.currentPlayer = Integer.toString(tmp);
	}

}
