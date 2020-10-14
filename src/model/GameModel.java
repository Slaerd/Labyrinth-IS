package model;

import java.util.ArrayList;

public class GameModel {
	private ArrayList<Player> playerList;
	private ArrayList<ArrayList<Tile>> laby;
	public GameModel() {
		
	}
	
	public void movePlayer(int n, int x, int y) {
		Player currentPlayer = playerList.get(n);
		
		//distance check outside of recursive function
		int distance = Math.abs(x - currentPlayer.x) + Math.abs(y - currentPlayer.y);
		boolean minDistance = distance > 0;
		boolean maxDistance = distance <= currentPlayer.getMovement();
		
		
		if(isLegalMove(currentPlayer, x , y) && minDistance && maxDistance)
			laby.get(currentPlayer.x).get(currentPlayer.y).removePlayer();
			currentPlayer.moveTo(x, y);
			laby.get(x).get(y).putPlayer(n);
	}
	
	/**
	 * DOES NOT CHECK DISTANCE PLEASE DO IT IN THE MOVE FUNCTION
	 * @param p
	 * @param fX
	 * @param fY
	 * @return
	 */
	private boolean isLegalMove(Player p, int fX, int fY) {
		int travelX = fX - p.x;
		int travelY = fY - p.y;
		int travelXSign = travelX/Math.abs(travelX);
		int travelYSign = travelY/Math.abs(travelY);
		int total = Math.abs(travelX) + Math.abs(travelY);
		
		boolean type = laby.get(fX).get(fY).getType() == Tile.FLOOR;
		
		if (type) {
			if(total == 1)		//Tile is next to player and clear
				return true;
			else {
				if(travelX == 0)	//only vertical movement
					return isLegalMove(p, fX, fY - travelYSign);
				if(travelY == 0)	//only horizontal movement
					return isLegalMove(p, fX - travelXSign, fY);
									//else visit all the tiles
				return isLegalMove(p, fX - travelXSign, fY) || isLegalMove(p, fX, fY - travelYSign);
			}
		}
		return false;
	}	
	
}
