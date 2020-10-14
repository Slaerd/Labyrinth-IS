package controller;

import model.CharGenModel;

public class CharGenController {

	CharGenModel myModel = new CharGenModel();
	
	public void changePlayerNumber() {
		myModel.setCurrentPlayer(Integer.toString((Integer.parseInt(myModel.getCurrentPlayer()) + 1) % (myModel.nbPlayers+1)));
	}
	
	public String getCurrentPlayer() {
		return myModel.getCurrentPlayer();
	}
	
	public void UpHeadNumber() {
		myModel.newHeadRightArrow();
	}
	 public void DownHeadNumber() {
		 myModel.newHeadLeftArrow();
	 }
	
	public int getCurrentHead() {
		return myModel.getCurrentHead();
	}
	
	public void UpBodyNumber() {
		myModel.newBodyRightArrow();
	}
	 public void DownBodyNumber() {
		 myModel.newBodyLeftArrow();
	 }
	
	public int getCurrentBody() {
		return myModel.getCurrentBody();
	}
	
	public void UpLegsNumber() {
		myModel.newLegsRightArrow();
	}
	 public void DownLegsNumber() {
		 myModel.newLegsLeftArrow();
	 }
	
	public int getCurrentLegs() {
		return myModel.getCurrentLegs();
	}
}
