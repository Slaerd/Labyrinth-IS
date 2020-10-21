package model;

import java.util.ArrayList;
import java.util.Random;

import application.App;
import controller.GameController;
import event.Listener;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import view.TrapButton;

public class GameModel {
	private Random rand = new Random();
	private ArrayList<Player> playerList;
	private ArrayList<ArrayList<Tile>> laby;
	private ArrayList<Tile> accessible;
	private int turnPlayer = 0;
	
	private ArrayList<Listener> listenerList;
	
	private boolean gameState = false; //ongoing for false, done for true
	
	private final int sizeX;
	private final int sizeY;
	
	//////////////
	//// WALL ////
	//////////////
	
	private ArrayList<ArrayList<Tile>> wallObjectList;
	private ArrayList<Tile> wallVisited;
	
	private ArrayList<Tile> wallObjectBuffer; //Contains the wallObjects in an ongoing drag and drop motion
	
	//////////////
	//// TRAP ////
	//////////////
	
	private ArrayList<ArrayList<Boolean>> trap = new ArrayList<ArrayList<Boolean>>(); //trap shape to draw
	private Stage trapStage; //Trap QTE window
	private Timeline timeline;
	
	/**
	 * Creates the gameModel with the inputted laby
	 * @param labyName
	 */
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
		
		for(Player p : playerList) {
			System.out.println("Target for " + p.getName() + " :" + p.target);
		}
		generateAccessibles(playerList.get(turnPlayer));
	}
	
	public int[] getLabSize() {
		int[] size = {sizeX,sizeY};
		return size;
	}
	
	/**
	 * Creates basic players, their targets are the next player in the list
	 * @param n number of players
	 */
	private void templatePlayers(int n) {
		int playerNumber = 0;
		for(int i = 0; i < sizeX; i++) {
			for(int j = 0; j < sizeY; j++) {
				if(laby.get(i).get(j).getType() == Tile.SPAWN) {
					playerList.add(new Player("Player" + Integer.toString(playerNumber), i, j, playerNumber, (playerNumber + 1) % n));
					//						  Playeri									(i,j)	number		 target
					laby.get(i).get(j).putPlayer(playerNumber);
					playerNumber++;
				}
			}
		}
	};
	
	/**
	 * Moves a player to Tile x y if he can move and it's clear
	 * @param x
	 * @param y
	 */
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
	
	/**
	 * Creates the accessible tiles for movement for the player p
	 * @param p
	 */
	private void generateAccessibles(Player p) {
		accessible = new ArrayList<Tile>();
		for(int x = 0; x < sizeX; x++) {
			for(int y = 0; y < sizeY; y++) {
				if(isLegalMove(p,x,y))
					accessible.add(laby.get(x).get(y));
			}
		}
	}
	
	/**
	 * Gives the accessible tiles for the turnPlayer
	 * @return
	 */
	public ArrayList<Tile> getAccessible(){
		return accessible;
	}
	
	/**
	 * Checks if player p can move to fX fY from its current position
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
	
	public Player getPlayer(int i) {
		return playerList.get(i);
	}

	public String getTurnPlayerName() {
		return playerList.get(turnPlayer).getName();
	}
	
	public int getPlayerInTile(int x, int y) {
		return laby.get(x).get(y).getPlayerNumber();
	}
	
	/**
	 * Check if a tile x y is accessible to the turnPlayer
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isAccessible(int x, int y) {
		return accessible.contains(laby.get(x).get(y));
	}
	
	/**
	 * Goes to the next player
	 */
	public void nextTurn() {
		Player p = playerList.get(turnPlayer);
		if(!p.isTrapped())		//if a player failed a trap this turn
			p.regainActions();	//don't restore his actions
		p.setTrapped(false);
		
		turnPlayer = (turnPlayer + 1) % playerList.size(); //next player becomes turnplayer
		if(playerList.get(turnPlayer).getActions() > 0)
			generateAccessibles(playerList.get(turnPlayer));	//generate his possible moves
		else
			accessible = new ArrayList<Tile>();
		
		notifyListeners();
	}
	
	/**
	 * gets actions left for the turnPlayer
	 * @return
	 */
	public int getActionsLeft() {
		return playerList.get(turnPlayer).getActions();
	}
	
	
	public int getActionsLeft(int x, int y) {
		return playerList.get(laby.get(x).get(y).getPlayerNumber()).getActions();
	}
	
	/**
	 * gets the tile x y's type
	 * @param x
	 * @param y
	 * @return
	 */
	public int getTileType(int x, int y) {
		return laby.get(x).get(y).getType();
	}
	
	public boolean isGameDone() {
		return gameState;
	}
	
	/**
	 * checks if the turnPlayer is near its target
	 * @return
	 */
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
	
	/**
	 * the turnPlayer kills his nearby target
	 */
	public void kill() {
		Player killer = playerList.get(turnPlayer);

		Player dead = playerList.get(killer.target);
		dead.setNumber(killer.target);
		dead.spendLife();
		
		if(dead.getLives() > 0) {
				respawn(killer);
				respawn(dead);
				
				nextTurn();
		}else {
			gameState = true;
		}
		
		notifyListeners();
	}
	
	/**
	 * Puts players back to their spawn after a kill
	 * if the spawn is occupied by a player, he respawns as well
	 * @param p
	 */
	private void respawn(Player p) {
		 	int spawnInvader = laby.get(p.xSpawn).get(p.ySpawn).getPlayerNumber();
			laby.get(p.x).get(p.y).removePlayer();
			
			if(spawnInvader != Player.NOPLAYER)
				respawn(playerList.get(spawnInvader));
			
			p.moveTo(p.xSpawn, p.ySpawn);
			laby.get(p.xSpawn).get(p.ySpawn).putPlayer(p.getNumber());
		
	}
	
	///////////////////////////////////
	//// WALL MANIPULATION METHODS ////
	///////////////////////////////////
	
	/**
	 * generates the list of wallObjects
	 * <br> see generateWallObject
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
	
	/**
	 * generates wallObjects at the tile x y if any
	 * <br> WallObjects are adjacent wall tiles, essentially tetris pieces
	 * <br> used for the drag and drop motion
	 * @param i
	 * @param j
	 * @param buffer will store the wallObject generated
	 */
	private void generateWallObject(int i, int j, ArrayList<Tile> buffer) {
		Tile currentTile = laby.get(i).get(j);
		if( !wallVisited.contains(currentTile) 				//if the tile wasn't already visited
			&& currentTile.getType() == Tile.WALL 			//and it's a wall
			&& buffer.size() != Laby.WALLOBJECTMAXSIZE) {	//and the wallObject isn't too big
			
				buffer.add(currentTile);					//add it to the wall object
				wallVisited.add(currentTile);				//add to visited tiles
				
				if(i + 1 < sizeX) {							//Then check adjacent tiles
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
	
	/**
	 * notifies wallObject at tile x y it's being hovered on
	 * @param x
	 * @param y
	 */
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
	 * Sets shadows of the wallObject for the drag and drop motion
	 * <br> nX and nY are the current destination for the wallObject
	 * @param nX 
	 * @param nY
	 * @param wallObjectOrigin position of the wallObject before the dnd motion
	 * @return if the drop was valid
	 */
	public boolean drawWallObjectShadow(int nX, int nY, String wallObjectOrigin) {
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
	
	/**
	 * undoes the hovering for all tiles
	 */
	public void unhover() {
		for(ArrayList<Tile> line : laby) {
			for(Tile tile : line)
				tile.setHover(false);
		}
		notifyListeners();
	}
	
	/**
	 * undoes the shadowing for all tiles
	 */
	public void unshadow() {
		for(ArrayList<Tile> line : laby) {
			for(Tile tile : line)
				tile.setShadow(false);
		}
		notifyListeners();
	}

	/**
	 * removes a wallObject at the tile x y from the labyrinth and the wallObject list
	 * @param x
	 * @param y
	 */
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
	
	/**
	 * Transforms the wall shadow into an actual wall
	 * <br> then add it to the wallObjectList
	 */
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
		
		if(!wallObjectBuffer.containsAll(newWallObject)) { //if the wall was not redropped to its original place
			wallObjectList.add(newWallObject);				//we add it
			wallObjectBuffer = new ArrayList<Tile>();
		}
	}
	
	/**
	 * function called to consume an action as you moved a wall
	 */
	public void movedWall() {
		Player currentPlayer = playerList.get(turnPlayer);
		
		currentPlayer.spendAction();
		if(currentPlayer.getActions() > 0)
			generateAccessibles(currentPlayer);
		else
			accessible = new ArrayList<Tile>();
		
		notifyListeners();
	}
	
	/**
	 * places back the picked up wall if the drag n drop gesture was a failure
	 */
	public void restoreWall() {
		for(Tile tile : wallObjectBuffer)
			laby.get(tile.x).get(tile.y).setType(Tile.WALL);
		
		wallObjectList.add(wallObjectBuffer);
		notifyListeners();
	}
	
	public boolean isDropSuccess() {
		return wallObjectBuffer.size() == 0; //If the buffer is empty, it means our drop was a success
	}
	
	//////////////
	//// TRAP ////
	//////////////
	
	/**
	 * Initializes the trap subwindow
	 * @param x
	 * @param y
	 * @param controller
	 */
	public void triggerTrap(int x, int y, GameController controller) {
		trapStage = new Stage();
		trapStage.initStyle(StageStyle.UNDECORATED);	//hides the stage outline
		trapStage.setOnCloseRequest(e->{
			e.consume();					//prevent closing
		});
		
		trapStage.setAlwaysOnTop(true);		//prevents unfocusing by clicking under
		
		trap = Laby.getTrap(rand.nextInt());
		VBox root = new VBox();				
		Label trapWarning = new Label("Draw the shape to escape the trap !");
		trapWarning.setStyle("-fx-font-size: 30");
		GridPane trapGrid = new GridPane();
		TrapButton buttonBuffer;
		for(int i = 0; i < trap.size(); i++) {
			for(int j = 0; j < trap.get(i).size(); j++) {
				buttonBuffer = new TrapButton(i,j,controller);
				trapGrid.add(buttonBuffer, i, j);
			}
		}
		
		Label timerLabel = new Label();
		int timerStart = 5;
		IntegerProperty seconds = new SimpleIntegerProperty(timerStart); //the label counts from 5 to 0

		
        timerLabel.textProperty().bind(seconds.asString());

        seconds.set(timerStart);
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(timerStart+1),
                new KeyValue(seconds, 0)));
        timeline.playFromStart();
        timeline.setOnFinished(e->{
			playerList.get(turnPlayer).setTrapped(true);	//When you hit 0, punish the current player
        	trapStage.close();								//close the window
        	trap = new ArrayList<ArrayList<Boolean>>();
        	notifyListeners();
        });
		

        root.getChildren().addAll(trapWarning,trapGrid,timerLabel);
        
        trapGrid.setAlignment(Pos.CENTER);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);
        trapStage.setScene(new Scene(root,App.WINDOWX/2,App.WINDOWY/2));

		trapStage.show();
		
		laby.get(x).get(y).removeSpecial();	//clear the trap
		
		notifyListeners();
	}
	
	/**
	 * checks if the trap shape was cleared by a player
	 * @return
	 */
	public boolean isTrapShapeClear() {
		for(ArrayList<Boolean> column : trap) {
			for(Boolean trapTile : column) {
				if(!trapTile)
					return false;
			}
		}
		return true;
	}

	/**
	 * checks if a tile of the trap shape was hovered on
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean getTrapTile(int x, int y) {
		return trap.get(x).get(y);
	}
	
	/**
	 * do or undo a hover on a tile of the trap shape
	 * @param x
	 * @param y
	 * @param b
	 */
	public void setTrapTile(int x, int y, boolean b) {
		trap.get(x).set(y,b);
	}
	
	/**
	 * check if a tile x y is trapped
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isTrapped(int x, int y) {
		return laby.get(x).get(y).getSpecial() == Tile.TRAP;
	}
	
	/**
	 * function to call when the trap is cleared
	 */
	public void successTrap() {
		timeline.stop();
		trapStage.close();
		trap = new ArrayList<ArrayList<Boolean>>();
		notifyListeners();
	}
	
	/**
	 * checks if the trap window is still here
	 * @return
	 */
	public boolean isTrapActive() {
		return trap.size() != 0; //if the trap shape is empty, it's not
	}
	
	public void notifyListeners() {
		for(Listener listener : listenerList)
			listener.update();
	}
	
	public void addListener(Listener listener) {
		listenerList.add(listener);
		
	}
	
	public void removeListener(Listener listener) {
		listenerList.remove(listener);
	}

}
