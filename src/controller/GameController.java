package controller;

import java.util.ArrayList;

import event.Listener;
import model.*;
import view.TileButton;

public class GameController implements Controller{
	private GameModel model;
	
	public GameController() {
	}
	
	/**
	 * Returns the player number whose turn it is
	 * @return
	 */
	public String getTurnPlayerName() {
		return model.getTurnPlayerName();
	}
	/**
	 * moves player number n to x y
	 * @param n
	 * @param x
	 * @param y
	 */
	public void movePlayer(int x, int y) {
		model.movePlayer(x, y);
	}

	public void addListener(Listener listener) {
		model.addListener(listener);
	}
	
	public void removeListener(Listener listener) {
		model.removeListener(listener);
	}
	
	/**
	 * Returns the index of the player at x y, else -1
	 * @param x
	 * @param y
	 * @return
	 */
	public int getPlayerInTile(int x, int y) {
		return model.getPlayerInTile(x, y);
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
		model.removeWallObject(x,y);
	}
	
	public boolean isShadowed(int x, int y) {
		return model.isShadowed(x,y);
	}

	public boolean setWallObjectShadow(int x, int y, String wallObjectOrigin) {
		return model.setWallObjectShadow(x,y,wallObjectOrigin);
		
	}
	
	public void unshadow() {
		model.unshadow();
	}
	
	public void restoreWall() {
		model.restoreWall();
	}
	public void shadowToWall() {
		model.shadowToWall();
	}
	
	public boolean isDropSuccess() {
		return model.isDropSuccess();
	}
	
	public void movedWall() {
		model.movedWall();
	}
	
	/**
	 * Returns true if the turnPlayer is right next to its target
	 * @return
	 */
	public boolean isTargetNear() {
		return model.isTargetNear();
	}
	
	public void kill() {
		model.kill();
	}
	
	public int[] getLabSize() {
		return model.getLabSize();
	}

	public boolean isGameDone() {
		return model.isGameDone();
	}
	
	public void hoverWallObject(int x, int y) {
		model.hoverWallObject(x, y);
	}
	
	public void notifyListeners() {
		model.notifyListeners();
	}

	public void unhover() {
		model.unhover();
	}

	public boolean isHovered(int x, int y) {
		return model.isHovered(x, y);
	}

	public boolean getTrapTile(int x, int y) {
		// TODO Auto-generated method stub
		return model.getTrapTile(x, y);
	}

	public void setTrapTile(int x, int y, boolean b) {
		model.setTrapTile(x, y, b);
		
	}

	public boolean isTrapped(int x, int y) {
		// TODO Auto-generated method stub
		return model.isTrapped(x, y);
	}

	public void triggerTrap(int x, int y) {
		model.triggerTrap(x, y, this);
		
	}

	public boolean isTrapShapeClear() {
		// TODO Auto-generated method stub
		return model.isTrapShapeClear();
	}

	public void closeTrapWindow() {
		model.closeTrapWindow();
		
	}

	/*public void rotate(int x, int y) {
		model.rotateLeft(x, y);
	}*/
}
