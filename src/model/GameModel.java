package model;

import java.util.ArrayList;

import event.Listener;

public class GameModel {
	private ArrayList<Player> playerList;
	private ArrayList<ArrayList<Tile>> laby;
	private ArrayList<Tile> accessible;
	private int turnPlayer = 0;
	
	//////////////
	//// WALL ////
	//////////////
	
	private ArrayList<ArrayList<Tile>> wallObjectList;
	private ArrayList<Tile> wallVisited;
	
	
	private ArrayList<Listener> listenerList;
	
	public GameModel() {
		laby = Laby.getSquare();
		wallObjectList = new ArrayList<ArrayList<Tile>>();
		wallVisited = new ArrayList<Tile>();
		generateWallObjectList();
		
		
		/*for(ArrayList<Tile> wallObject : wallObjectList) {
			System.out.print("[");
			for(Tile tile : wallObject)
				printTile(tile);
			System.out.print("]\n");
		}*/
			
				
		playerList = new ArrayList<Player>();
		listenerList = new ArrayList<Listener>();
		templatePlayers();
		
		generateAccessibles(playerList.get(turnPlayer));
	}
	
	private void printTile(Tile tile) {
		System.out.print("(" + tile.x + ", " + tile.y + ")");
	}
	/**
	 * Generates ArrayList of tiles of adjacent walls, useful for the drag n drop
	 */
	private void generateWallObjectList() {
		for(int i = 0; i < Laby.SIZE; i++) {
			ArrayList<Tile> wallObject = new ArrayList<Tile>();
			for(int j = 0; j < Laby.SIZE; j++) {
				if(laby.get(i).get(j).getType() == Tile.WALL)
					generateWallObject(i, j, wallObject);
			}
			
			if(wallObject.size() != 0)
				wallObjectList.add(wallObject);
		}
	}
	
	private void generateWallObject(int i, int j, ArrayList<Tile> buffer) {
		Tile currentTile = laby.get(i).get(j);
		if( !wallVisited.contains( currentTile ) ) {
			buffer.add(currentTile);
			wallVisited.add(currentTile);
			
			printTile(currentTile);
			System.out.print("\n");
			if(j + 1 < Laby.SIZE ) {
				if(laby.get(i).get(j + 1).getType() == Tile.WALL && !wallVisited.contains(laby.get(i).get(j + 1)))
					generateWallObject(i, j + 1, buffer);
			}
			if(i + 1 < Laby.SIZE) {
				if(laby.get(i + 1).get(j).getType() == Tile.WALL && !wallVisited.contains(laby.get(i + 1).get(j)))
					generateWallObject(i + 1, j, buffer);
			}
			if(j - 1 >= 0) {
				if(laby.get(i).get(j - 1).getType() == Tile.WALL && !wallVisited.contains(laby.get(i).get(j - 1)))
					generateWallObject(i, j - 1, buffer);
			}
			if(i - 1 >= 0) {
				if(laby.get(i - 1).get(j).getType() == Tile.WALL && !wallVisited.contains(laby.get(i - 1).get(j)))
					generateWallObject(i - 1, j, buffer);
			}
		}
	}
	
	private void templatePlayers() {
		for(int i = 0; i < 4; i++) {
			playerList.add(new Player("bruh" + Integer.toString(i),i,0));
			laby.get(i).get(0).putPlayer(i);
		}
	};
	
	public void movePlayer(int n, int x, int y) {
		Player currentPlayer = playerList.get(n);
		
		if(accessible.contains(laby.get(x).get(y))) {
			laby.get(currentPlayer.x).get(currentPlayer.y).removePlayer();
			currentPlayer.moveTo(x, y);
			laby.get(x).get(y).putPlayer(n);
			currentPlayer.spendAction();
		}
		
		if(currentPlayer.getActions() > 0)
			generateAccessibles(currentPlayer);
		else
			accessible = new ArrayList<Tile>();
			
		for(Listener l : listenerList)
			l.update();
	}
	
	public void moveWall() {
		
	}
	
	private void generateAccessibles(Player p) {
		accessible = new ArrayList<Tile>();
		for(int x = 0; x < Laby.SIZE; x++) {
			for(int y = 0; y < Laby.SIZE; y++) {
				if(isLegalMove(p,x,y))
					accessible.add(laby.get(x).get(y));
			}
		}
	}
	
	public ArrayList<Tile> getAccessible(){
		return accessible;
	}
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
				 && distance <= p.getMovement() 
				 && laby.get(fX).get(fY).getPlayerNumber() == -1) {
			if(distance == 1)		//Tile is next to player and clear
				return true;
			else {
				if(fX == p.x)	//only vertical movement
					return isLegalMove(p, fX, fY - (fY - p.y) / Math.abs(fY - p.y)); //sign of fY - p.y
				if(fY == p.y)	//only horizontal movement
					return isLegalMove(p, fX - (fX - p.x) / Math.abs(fX - p.x), fY);
									//else visit all the tiles
				return isLegalMove(p, fX - (fX - p.x) / Math.abs(fX - p.x), fY) || isLegalMove(p, fX, fY - (fY - p.y) / Math.abs(fY - p.y));
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
	
	public void removeListener(Listener listener) {
		listenerList.remove(listener);
	}
	
	public int getPlayer(int x, int y) {
		return laby.get(x).get(y).getPlayerNumber();
	}

	public boolean isAccessible(int x, int y) {
		return accessible.contains(laby.get(x).get(y));
	}
	
	public void nextTurn() {
		playerList.get(turnPlayer).regainActions();
		turnPlayer = (turnPlayer + 1) % playerList.size();
		generateAccessibles(playerList.get(turnPlayer));
		for(Listener l : listenerList)
			l.update();
	}
	
	public int getActionsLeft() {
		return playerList.get(turnPlayer).getActions();
	}

	public int getTileType(int x, int y) {
		return laby.get(x).get(y).getType();
	}
	
}
