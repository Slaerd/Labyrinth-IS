package controller;

import model.CharGenModel;
import model.PlayerGraphics;

import java.util.ArrayList;

import controller.Controller;
import javafx.scene.paint.Color;

public class CharGenController{

	private CharGenModel myModel = new CharGenModel();
	
	/*
	 * public void changePlayerNumber() {
	 * myModel.setCurrentPlayer(Integer.toString((Integer.parseInt(myModel.
	 * getCurrentPlayer()) + 1) % (myModel.nbPlayers+1))); }
	 * 
	 * public String getCurrentPlayer() { return
	 * myModel.player1Graphics.getCurrentPlayer(); }
	 */
	
	public void addPlayers() {
		myModel.addPlayers();
	}
	
	public void UpHeadNumber(PlayerGraphics pg) {
		myModel.newHeadRightArrow(pg);
	}
	 public void DownHeadNumber(PlayerGraphics pg) {
		 myModel.newHeadLeftArrow(pg);
	 }
	
	public int getCurrentHead(PlayerGraphics pg) {
		return myModel.getCurrentHead(pg);
	}
	
	public void UpBodyNumber(PlayerGraphics pg) {
		myModel.newBodyRightArrow(pg);
	}
	 public void DownBodyNumber(PlayerGraphics pg) {
		 myModel.newBodyLeftArrow(pg);
	 }
	
	public int getCurrentBody(PlayerGraphics pg) {
		return myModel.getCurrentBody(pg);
	}
	
	public void UpLegsNumber(PlayerGraphics pg) {
		myModel.newLegsRightArrow(pg);
	}
	 public void DownLegsNumber(PlayerGraphics pg) {
		 myModel.newLegsLeftArrow(pg);
	 }
	
	public int getCurrentLegs(PlayerGraphics pg) {
		return myModel.getCurrentLegs(pg);
	}
	
	public Color getCurrentColor(PlayerGraphics pg) {
		return myModel.getCurrentColor(pg);
	}
	
	public void setCurrentColor(PlayerGraphics pg, Color newColor) {
		myModel.setCurrentColor(pg, newColor);
	}
	
	public ArrayList<PlayerGraphics> getPlayers(){
		return myModel.getPlayers();
	}
	
	public int getCurrentPlayer() {
		return myModel.getCurrentPlayer();
	}
	
}
