package view;

import controller.GameController;
import javafx.scene.control.Button;

/**
 * 
 * @author Kevin Ratovo
 * Button to hover on to clear the traps
 */
public class TrapButton extends Button{
	
	private GameController controller;
	
	public TrapButton(int x, int y, GameController gameController) {
		super();
		this.controller = gameController;
		this.setPrefSize(40, 40);
		if(controller.getTrapTile(x, y))
			this.setStyle("-fx-opacity: 0");
		else
			this.setStyle("-fx-background-color: Orange");
		
		this.setOnMouseEntered(e->{
			if(!controller.getTrapTile(x, y))
				this.setStyle("-fx-background-color: Green");
			controller.setTrapTile(x, y, true);
			
			if(controller.isTrapShapeClear())
				controller.successTrap();
		});
	}
	
	
}
