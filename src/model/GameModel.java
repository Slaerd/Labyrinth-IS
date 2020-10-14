package model;

import java.util.ArrayList;

import event.Listener;

public class GameModel {
	private ArrayList<Player> playerList;
	private ArrayList<ArrayList<Tile>> laby;
	private int turnPlayer;
	
	private ArrayList<Listener> listenerList;
	public GameModel() {
		laby = Laby.getSquare();
		turnPlayer = 0;
		playerList = new ArrayList<Player>();
		templatePlayers();
		listenerList = new ArrayList<Listener>();
		
	}
	
	private void templatePlayers() {
		for(int i = 0; i < 4; i++) {
			playerList.add(new Player("bruh",i,0));
		}
	};
	public void movePlayer(int n, int x, int y) {
		Player currentPlayer = playerList.get(n);
		
		//distance check outside of recursive function
		
		if(laby.get(x).get(y).getPlayerNumber() == -1) {
			if(isLegalMove(currentPlayer, x , y)) {
				laby.get(currentPlayer.x).get(currentPlayer.y).removePlayer();
				currentPlayer.moveTo(x, y);
				laby.get(x).get(y).putPlayer(n);
				for(Listener l : listenerList)
					l.update();
			}
		}
	}
	
	/*private void generateAccessibles() {
		for(int i = 0; i <)
	}*/
	
	/**
	 * DOES NOT CHECK DISTANCE PLEASE DO IT IN THE MOVE FUNCTION
	 * @param p
	 * @param fX
	 * @param fY
	 * @return
	 */
	private boolean isLegalMove(Player p, int fX, int fY) {
		
		int distance = Math.abs(fX - p.x) + Math.abs(fY - p.y);
		
		boolean type = laby.get(fX).get(fY).getType() == Tile.FLOOR;
		
		if (type && distance > 0 
				&& distance <= p.getMovement()) {
			if(distance == 1)		//Tile is next to player and clear
				return true;
			else {
				if(fY != p.y)	//only vertical movement
					return isLegalMove(p, fX, fY - (fY - p.y) / Math.abs(fY - p.y)); //sign of fY - p.y
				if(fX != p.x)	//only horizontal movement
					return isLegalMove(p, fX - (fX - p.x) / Math.abs(fX - p.x), fY);
									//else visit all the tiles
				return isLegalMove(p, fX - (fX - p.x) / Math.abs(fX - p.x), fY) || isLegalMove(p, fX, fY - fY - (fY - p.y) / Math.abs(fY - p.y));
			}
		}
		return false;
	}

	public int getTurnPlayer() {
		return turnPlayer;
	}

	public void addListener(Listener listener) {
		listenerList.add(listener);
		
	}

	public int getPlayer(int x, int y) {
		return laby.get(x).get(y).getPlayerNumber();
	}
	
}
