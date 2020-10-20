package model;

import java.util.ArrayList;

import event.Listener;

public class GameModel {
	private ArrayList<Player> playerList;
	private ArrayList<ArrayList<Tile>> laby;
	private ArrayList<Tile> accessible;
	private int turnPlayer = 0;
	private ArrayList<Listener> listenerList;
	
	private boolean gameState = false;
	
	private final int sizeX;
	private final int sizeY;
	
	//////////////
	//// WALL ////
	//////////////
	
	private ArrayList<ArrayList<Tile>> wallObjectList;
	private ArrayList<Tile> wallVisited;
	
	

	private ArrayList<Tile> wallObjectBuffer;
	
	public GameModel(String labyName) {
		laby = Laby.getLaby(labyName);
		sizeX = laby.size();
		sizeY = laby.get(0).size();
		
		wallObjectBuffer = new ArrayList<Tile>();
		wallObjectList = new ArrayList<ArrayList<Tile>>();
		wallVisited = new ArrayList<Tile>();
		generateWallObjectList();		
				
		playerList = new ArrayList<Player>();
		listenerList = new ArrayList<Listener>();
		templatePlayers(4);
		
		generateAccessibles(playerList.get(turnPlayer));
	}
	
	public int[] getLabSize() {
		int[] size = {sizeX,sizeY};
		return size;
	}
	private void printTile(Tile tile) {
		System.out.print("(" + tile.x + ", " + tile.y + ")");
	}
	
	private void templatePlayers(int n) {
		int playerNumber = 0;
		for(int i = 0; i < sizeX; i++) {
			for(int j = 0; j < sizeY; j++) {
				if(laby.get(i).get(j).getType() == Tile.SPAWN) {
					playerList.add(new Player("Player" + Integer.toString(playerNumber), i, j, playerNumber, (playerNumber + 1) % n));
					laby.get(i).get(j).putPlayer(playerNumber);
					playerNumber++;
				}
			}
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
			
		notifyListeners();
	}
	
	public void movedWall() {
		Player currentPlayer = playerList.get(turnPlayer);
		
		currentPlayer.spendAction();
		if(currentPlayer.getActions() > 0)
			generateAccessibles(currentPlayer);
		else
			accessible = new ArrayList<Tile>();
		
		notifyListeners();
	}
	
	private void generateAccessibles(Player p) {
		accessible = new ArrayList<Tile>();
		for(int x = 0; x < sizeX; x++) {
			for(int y = 0; y < sizeY; y++) {
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
		
		boolean type = laby.get(fX).get(fY).getType() < Tile.WALL;
		
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
	
	public int getPlayerInTile(int x, int y) {
		return laby.get(x).get(y).getPlayerNumber();
	}

	public boolean isAccessible(int x, int y) {
		return accessible.contains(laby.get(x).get(y));
	}
	
	public void nextTurn() {
		playerList.get(turnPlayer).regainActions();
		turnPlayer = (turnPlayer + 1) % playerList.size();
		generateAccessibles(playerList.get(turnPlayer));
		notifyListeners();
	}
	
	public int getActionsLeft() {
		return playerList.get(turnPlayer).getActions();
	}

	public int getTileType(int x, int y) {
		return laby.get(x).get(y).getType();
	}
	
	public void kill() {
		Player killer = playerList.get(turnPlayer);		
		Player dead = playerList.get(killer.target);

		
		dead.spendLife();

		
		if(dead.getLives() > 0) {
			laby.get(killer.x).get(killer.y).removePlayer();
			laby.get(dead.x).get(dead.y).removePlayer();
			
			dead.moveTo(dead.xSpawn, dead.ySpawn);
			killer.moveTo(killer.xSpawn, killer.ySpawn);
			
			laby.get(dead.xSpawn).get(dead.ySpawn).putPlayer(killer.target);
			laby.get(killer.xSpawn).get(killer.ySpawn).putPlayer(turnPlayer);
			
			nextTurn();
		}else {
			gameState = true;
		}
		
		notifyListeners();
	}
	
	///////////////////////////////////
	//// WALL MANIPULATION METHODS ////
	///////////////////////////////////
	
	/**
	 * Generates ArrayList of tiles of adjacent walls, useful for the drag n drop
	 */
	private void generateWallObjectList() {
		ArrayList<Tile> wallObjectBuffer;
		for(int i = 0; i < sizeX; i++) {
			for(int j = 0; j < sizeY; j++) {
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
				
				if(i + 1 < sizeX) {
					generateWallObject(i + 1, j, buffer);
				}
				if(j + 1 < sizeY) {
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
	
	public void hoverWallObject(int x, int y) {
		for(ArrayList<Tile> wallObject : wallObjectList) {
			if(wallObject.contains(laby.get(x).get(y))) {
				for(Tile tile : wallObject)
					tile.setHover(true);
				break;
			}
		}
		notifyListeners();
	}
	/**
	 * Sets shadow of the wallObject for the drag and drop motion
	 * <br> nX and nY are the current destination for the wallObject
	 * @param nX 
	 * @param nY
	 * @param wallObjectOrigin position of the wallObject before the dnd motion
	 * @return if the drop was valid
	 */
	public boolean setWallObjectShadow(int nX, int nY, String wallObjectOrigin) {
		String[] coordinates = wallObjectOrigin.split(",");
		boolean isDroppable = true;
		int oX = Integer.parseInt(coordinates[0]);
		int oY = Integer.parseInt(coordinates[1]);
		
		for(Tile tile : wallObjectBuffer){
			
			if(nX + (tile.x - oX) >= 0 && nX + (tile.x - oX) < sizeX
			&& nY + (tile.y - oY) >= 0 && nY + (tile.y - oY) < sizeY) {
				Tile newWall = laby.get(nX + (tile.x - oX)).get(nY + (tile.y - oY));
				newWall.setShadow(true);
				
				if(newWall.getType() != Tile.FLOOR || newWall.getPlayerNumber() != Player.NOPLAYER)
					isDroppable = false;
				
			}else {
				isDroppable = false;
			}
		}
		
		//System.out.println("OG Pos : " + oX + " " + oY);
		notifyListeners();
		return isDroppable;
	}
	
	public boolean isHovered(int x, int y) {
		return laby.get(x).get(y).isHovered();
	}
	public boolean isShadowed(int x, int y) {
		return laby.get(x).get(y).isShadowed();
	}
	
	public void unhover() {
		for(ArrayList<Tile> line : laby) {
			for(Tile tile : line)
				tile.setHover(false);
		}
		notifyListeners();
	}
	public void unshadow() {
		for(ArrayList<Tile> line : laby) {
			for(Tile tile : line)
				tile.setShadow(false);
		}
		notifyListeners();
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
		notifyListeners();
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
		
		if(!wallObjectBuffer.containsAll(newWallObject)) {
			wallObjectList.add(newWallObject);
			wallObjectBuffer = new ArrayList<Tile>();
		}
	}

	public void restoreWall() {
		for(Tile tile : wallObjectBuffer)
			laby.get(tile.x).get(tile.y).setType(Tile.WALL);
		
		wallObjectList.add(wallObjectBuffer);
		notifyListeners();
	}

	public boolean isDropSuccess() {
		return wallObjectBuffer.size() == 0;
	}

	public Player getPlayer(int i) {
		return playerList.get(i);
	}

	public String getTurnPlayerName() {
		return playerList.get(turnPlayer).getName();
	}
	
	public boolean isGameDone() {
		return gameState;
	}

	public boolean isTargetNear() {
		Player p = playerList.get(turnPlayer);
		if(p.x + 1 < sizeX) {
			if(laby.get(p.x + 1).get(p.y).getPlayerNumber() == p.target)
				return true;
		}
		if(p.y + 1 < sizeY) {
			if(laby.get(p.x).get(p.y + 1).getPlayerNumber() == p.target)
				return true;
		}
		if(p.x - 1 >= 0) {
			if(laby.get(p.x - 1).get(p.y).getPlayerNumber() == p.target)
				return true;
		}	
		if(p.y - 1 >= 0) {
			if(laby.get(p.x).get(p.y - 1).getPlayerNumber() == p.target)
				return true;
		}
		return false;
	}

	public void notifyListeners() {
		for(Listener listener : listenerList)
			listener.update();
	}
}
