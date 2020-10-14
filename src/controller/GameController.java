package controller;

import event.Listener;
import model.*;
import view.TileButton;

public class GameController {
	private GameModel model;
	
	public GameController() {
		
	}

	public int getTurnPlayer() {
		return model.getTurnPlayer();
	}

	public void movePlayer(int n, int x, int y) {
		model.movePlayer(n, x, y);
		
	}

	public void addListener(Listener listener) {
		model.addListener(listener);
		
	}

	public int getPlayer(int x, int y) {
		return model.getPlayer(x, y);
	}
	
	public void addModel(GameModel model) {
		this.model = model;
	}

	public boolean isAccessible(int x, int y) {
		
		return model.isAccessible(x,y);
	}
}
