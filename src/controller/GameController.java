package controller;

import java.util.ArrayList;

import event.Listener;
import model.*;
import view.TileButton;

public class GameController implements Controller{
	private GameModel model;
	private ArrayList<TileButton> tiles;
	
	public GameController() {
		tiles = new ArrayList<TileButton>();
	}
	
	/**
	 * Returns the player number whose turn it is
	 * @return
	 */
	public int getTurnPlayer() {
		return model.getTurnPlayer();
	}
	
	/**
	 * moves player number n to x y
	 * @param n
	 * @param x
	 * @param y
	 */
	public void movePlayer(int n, int x, int y) {
		model.movePlayer(n, x, y);
	}

	public void addListener(Listener listener) {
		model.addListener(listener);
	}
	
	/**
	 * Returns the index of the player at x y, else -1
	 * @param x
	 * @param y
	 * @return
	 */
	public int getPlayer(int x, int y) {
		return model.getPlayer(x, y);
	}
	
	public void addModel(GameModel model) {
		this.model = model;
	}
	
	/**
	 * Checks if the current player can go to tile x y
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isAccessible(int x, int y) {
		return model.isAccessible(x,y);
	}
	
	public void nextTurn() {
		model.nextTurn();
	}
	
	/**
	 * Gives the remaining actions of the turn player
	 * @return
	 */
	public int getActionsLeft() {
		return model.getActionsLeft();
	}

	public int getTileType(int x, int y) {
		return model.getTileType(x, y);
	}

	public void removeWallObject(int x, int y) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isShadowed(int x, int y) {
		return model.isShadowed(x,y);
	}

	public void setWallObjectShadow(int x, int y, String wallObjectOrigin) {
		model.setWallObjectShadow(x,y,wallObjectOrigin);
		
	}
	
	public void unshadow() {
		model.unshadow();
	}
}
