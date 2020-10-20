package view;

import controller.GameController;
import javafx.scene.control.Button;

public class TrapButton extends Button{
	
	private int x;
	private int y;
	private GameController controller;
	
	public TrapButton(int x, int y, GameController controller) {
		super();
		
		if(controller.getTrapTile(x, y))
			this.setStyle("-fx-opacity: 0");
		else
			this.setStyle("-fx-background-color: White; -fx-border-color: Black");
		
		this.setOnMouseEntered(e->{
			if(!controller.getTrapTile(x, y))
				this.setStyle("-fx-background-color: Green; -fx-border-color: Black");
			controller.setTrapTile(x, y, true);
		});
	}
	
	
}
