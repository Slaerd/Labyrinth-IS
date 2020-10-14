package view;

import controller.GameController;
import event.Listener;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;

public class TileButton extends Button implements Listener{
	
	private int x; //Coordinates of the corresponding tiles
	private int y;
	private GameController controller;
	
	/**
	 * Buttons that make up the grid on the play view
	 * 
	 * @param label
	 * @param i line of the matching tile
	 * @param j column of the matching column
	 * @param controller
	 */
	public TileButton(int i, int j,GameController controller) {
		super();
		
		this.setPadding(new Insets(0,0,0,0)); //Makes the flag fit in the button
		this.setPrefSize(25, 25);
		x = i;
		y = j;
		
		this.controller = controller;
		
		initStyle();
		
		this.setOnMouseClicked(e->{
			
			if(e.getButton().equals(MouseButton.PRIMARY)){ //Tile clicking event
				controller.movePlayer(controller.getTurnPlayer(),x,y);
			}
			
			if(e.getButton().equals(MouseButton.SECONDARY)) { //Remove dragNdrop flags

			}
		});
		
		controller.addListener(this);
		update();
	}

	/**
	 * Custom style of buttons
	 */
	private void initStyle() {
		
		this.setStyle("-fx-background-color: #3f3f3f; -fx-border-color: Black");
		
		
		/*this.setOnMouseEntered(e->{
			if(!controller.isTileClicked(x, y))
				this.setStyle("-fx-background-color: #5a5a5a; -fx-border-color: Black");
		});
		
		this.setOnMouseExited(e->{
			if(!controller.isTileClicked(x, y))
				this.setStyle("-fx-background-color: #3f3f3f; -fx-border-color: Black");
		});
		
		this.setOnMousePressed(e->{
			if(!controller.isTileClicked(x, y) && e.getButton().equals(MouseButton.PRIMARY))
				this.setStyle("-fx-background-color: #ffffff; -fx-border-color: Green; -fx-border-width: 3");
		});*/
		
	}

	
	public void update() {
		this.setTextFill(Color.GREEN);
		if(controller.getPlayer(x, y) != -1)
			this.setText(Integer.toString(controller.getPlayer(x, y))); 
		else
			this.setText("");
		
		if(controller.isAccessible(x,y))
			this.setStyle("-fx-background-color: #31BFFF; -fx-border-color: Black");
		else
			this.setStyle("-fx-background-color: #3f3f3f; -fx-border-color: Black");
			
		
	}
}
