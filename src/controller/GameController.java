package controller;

import event.Listener;
import model.*;

public class GameController implements Controller{
	private GameModel model;
	
	public GameController() {
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
	
	/**
	 * Returns the player number whose turn it is
	 * @return
	 */
	public String getTurnPlayerName() {
		return model.getTurnPlayerName();
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
	
	/**
	 * Gives the remaining actions of the turn player
	 * @return
	 */
	public int getActionsLeft() {
		return model.getActionsLeft();
	}
	
	/**
	 * getActionsLeft but for the player in a Tile x y
	 * @param x
	 * @param y
	 * @return
	 */
	public int getActionsLeft(int x, int y) {
		return model.getActionsLeft(x, y);
	}

	public int getTileType(int x, int y) {
		return model.getTileType(x, y);
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
	
	public void nextTurn() {
		model.nextTurn();
	}
	
	/**
	 * removes the wallObject at the tile x y from the labyrinth to Drag and Drop it
	 * @param x
	 * @param y
	 */
	public void removeWallObject(int x, int y) {
		model.removeWallObject(x,y);
	}
	
	/**
	 * Checks if a (x,y) tile is shadowing the wall movement
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isShadowed(int x, int y) {
		return model.isShadowed(x,y);
	}
	
	/**
	 * Creates the shadow of the wallObject as we move it around
	 * @param x
	 * @param y
	 * @param wallObjectOrigin original position of the wall as a string "x,y"
	 * @return
	 */
	public boolean drawWallObjectShadow(int x, int y, String wallObjectOrigin) {
		return model.drawWallObjectShadow(x,y,wallObjectOrigin);
		
	}
	
	/**
	 * removes all shadow tiles
	 */
	public void unshadow() {
		model.unshadow();
	}
	
	/**
	 * Restores the wall that was in a drag and drop motion
	 */
	public void restoreWall() {
		model.restoreWall();
	}
	
	/**
	 * Turns a shadowed wall into an actual wall
	 */
	public void shadowToWall() {
		model.shadowToWall();
	}
	
	/**
	 * Notifies the model that the DND motion was succesful
	 * @return
	 */
	public boolean isDropSuccess() {
		return model.isDropSuccess();
	}
	
	/**
	 * Acts as a "moveTo" for walls
	 */
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
	
	/**
	 * Notifies a WallObject it's being hovered on
	 * @param x
	 * @param y
	 */
	public void hoverWallObject(int x, int y) {
		model.hoverWallObject(x, y);
	}
	
	public boolean isHovered(int x, int y) {
		return model.isHovered(x, y);
	}
	
	/**
	 * removes any hover notifications
	 */
	public void unhover() {
		model.unhover();
	}
	
	public void notifyListeners() {
		model.notifyListeners();
	}

	//////////////
	//// TRAP ////
	//////////////
	
	/**
	 * returns the status of the tile x y of the trap shape
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean getTrapTile(int x, int y) {
		return model.getTrapTile(x, y);
	}
	
	/**
	 * sets the status of the tile x y of the trap shape
	 * @param x
	 * @param y
	 * @param b
	 */
	public void setTrapTile(int x, int y, boolean b) {
		model.setTrapTile(x, y, b);
		
	}
	
	/**
	 * returns if the trap QTE window is still there
	 * @return
	 */
	public boolean isTrapActive() {
		return model.isTrapActive();
	}
	
	/**
	 * Checks if a tile x y of the laby is trapped
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isTrapped(int x, int y) {
		return model.isTrapped(x, y);
	}
	
	/**
	 * Activates the trap QTE window
	 * @param x
	 * @param y
	 */
	public void triggerTrap(int x, int y) {
		model.triggerTrap(x, y, this);
		
	}
	
	/**
	 * returns if the trap shape was drawn fully
	 * @return
	 */
	public boolean isTrapShapeClear() {
		return model.isTrapShapeClear();
	}

	/**
	 * function to call to notify the trap was cleared
	 */
	public void successTrap() {
		model.successTrap();
	}
	
	public void addListener(Listener listener) {
		model.addListener(listener);
	}
	
	public void removeListener(Listener listener) {
		model.removeListener(listener);
	}
	
	public void addModel(GameModel model) {
		this.model = model;
	}
}
