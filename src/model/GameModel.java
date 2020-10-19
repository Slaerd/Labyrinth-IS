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
	private ArrayList<Tile> wallObjectBuffer;
	
	public GameModel() {
		laby = Laby.getSquare();
		wallObjectBuffer = new ArrayList<Tile>();
		wallObjectList = new ArrayList<ArrayList<Tile>>();
		wallVisited = new ArrayList<Tile>();
		generateWallObjectList();
		
		/*System.out.println("//////////////////////////\n"
						+  "//// WALL OBJECT LIST ////\n"
						+  "//////////////////////////\n");
		for(ArrayList<Tile> wallObject : wallObjectList) {
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
		ArrayList<Tile> wallObjectBuffer;
		for(int i = 0; i < Laby.SIZE; i++) {
			for(int j = 0; j < Laby.SIZE; j++) {
				wallObjectBuffer = new ArrayList<Tile>();
				generateWallObject(i, j, wallObjectBuffer);
				if(wallObjectBuffer.size() != 0) {
					wallObjectList.add(wallObjectBuffer);
				}
			}	
		}
	}
	
	private void generateWallObject(int i, int j, ArrayList<Tile> buffer) {
		Tile currentTile = laby.get(i).get(j);
		if( !wallVisited.contains(currentTile) 
			&& currentTile.getType() == Tile.WALL 
			&& buffer.size() != Laby.WALLOBJECTMAXSIZE) {
			
				buffer.add(currentTile);
				wallVisited.add(currentTile);
				
				if(i + 1 < Laby.SIZE) {
					generateWallObject(i + 1, j, buffer);
				}
				if(j + 1 < Laby.SIZE) {
					generateWallObject(i, j + 1, buffer);
				}
				if(i - 1 >= 0) {
					generateWallObject(i - 1, j, buffer);
				}	
				if(j - 1 >= 0) {
					generateWallObject(i, j - 1, buffer);
				}
				
							
		}
	}
	
	private void templatePlayers() {
		for(int i = 0; i < 2; i++) {
			playerList.add(new Player("bruh" + Integer.toString(i),i,0));
			laby.get(i).get(0).putPlayer(i);
		}
	};
	
	public void movePlayer(int x, int y) {
		Player currentPlayer = playerList.get(turnPlayer);
		
		if(accessible.contains(laby.get(x).get(y))) {
			laby.get(currentPlayer.x).get(currentPlayer.y).removePlayer();
			currentPlayer.moveTo(x, y);
			laby.get(x).get(y).putPlayer(turnPlayer);
			currentPlayer.spendAction();
		}
		
		if(currentPlayer.getActions() > 0)
			generateAccessibles(currentPlayer);
		else
			accessible = new ArrayList<Tile>();
			
		for(Listener l : listenerList)
			l.update();
	}
	
	public void movedWall() {
		Player currentPlayer = playerList.get(turnPlayer);
		
		currentPlayer.spendAction();
		if(currentPlayer.getActions() > 0)
			generateAccessibles(currentPlayer);
		else
			accessible = new ArrayList<Tile>();
		
		for(Listener listener : listenerList)
			listener.update();
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
	
	
	///////////////////////////////////
	//// WALL MANIPULATION METHODS ////
	///////////////////////////////////
	
	/**
	 * Sets shadow of the wallObject for the drag and drop motion
	 * <br> nX and nY are the current destination for the wallObject
	 * @param nX 
	 * @param nY
	 * @param wallObjectOrigin position of the wallObject before the dnd motion
	 */
	public boolean setWallObjectShadow(int nX, int nY, String wallObjectOrigin) {
		String[] coordinates = wallObjectOrigin.split(",");
		boolean isDroppable = true;
		int oX = Integer.parseInt(coordinates[0]);
		int oY = Integer.parseInt(coordinates[1]);
		
		for(Tile tile : wallObjectBuffer){
			
			if(nX + (tile.x - oX) >= 0 && nX + (tile.x - oX) < Laby.SIZE 
			&& nY + (tile.y - oY) >= 0 && nY + (tile.y - oY) < Laby.SIZE) {
				Tile newWall = laby.get(nX + (tile.x - oX)).get(nY + (tile.y - oY));
				newWall.setShadow(true);
				if(newWall.getType() > 0 || newWall.getPlayerNumber() != -1)
					isDroppable = false;
			}else {
				isDroppable = false;
			}
		}
		
		//System.out.println("OG Pos : " + oX + " " + oY);
		for(Listener listener : listenerList)
			listener.update();
		return isDroppable;
	}

	public boolean isShadowed(int x, int y) {
		return laby.get(x).get(y).isShadowed();
	}

	public void unshadow() {
		for(ArrayList<Tile> line : laby) {
			for(Tile tile : line)
				tile.setShadow(false);
		}
		for(Listener listener : listenerList)
			listener.update();
	}

	public void removeWallObject(int x, int y) {
		
		for(int i = 0; i < wallObjectList.size(); i++){
			if(wallObjectList.get(i).contains(laby.get(x).get(y))){
				
				wallObjectBuffer = wallObjectList.get(i);
				
				for(Tile tile : wallObjectList.get(i))
					tile.setType(Tile.FLOOR);
				
				wallObjectList.remove(i);
				break;
			}
		}
		for(Listener listener : listenerList)
			listener.update();
	}
	
	public void shadowToWall() {
		ArrayList<Tile> newWallObject = new ArrayList<Tile>();
		for(ArrayList<Tile> line : laby) {
			for(Tile tile : line) {
				if(tile.isShadowed()) {
					tile.setType(Tile.WALL);
					newWallObject.add(tile);
				}
			}
		}
		wallObjectList.add(newWallObject);
		
		wallObjectBuffer = new ArrayList<Tile>();	
	}

	public void restoreWall() {
		for(Tile tile : wallObjectBuffer)
			laby.get(tile.x).get(tile.y).setType(Tile.WALL);
		
		wallObjectList.add(wallObjectBuffer);
	}

	public boolean isDropSuccess() {
		return wallObjectBuffer.size() == 0;
	}
}
