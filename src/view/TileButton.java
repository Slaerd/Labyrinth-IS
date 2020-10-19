package view;

import java.util.ArrayList;

import controller.GameController;
import event.Listener;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
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
import javafx.scene.paint.Paint;
import model.Tile;

public class TileButton extends ListenerButton{
	
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
	public TileButton(int i, int j, GameController superController) {
		super(superController);
		this.controller = superController;
		
		this.setPadding(new Insets(0,0,0,0)); //Makes the flag fit in the button
		this.setPrefSize(50, 50);
		x = i;
		y = j;
		
		this.setOnMouseClicked(e->{
			
			if(e.getButton().equals(MouseButton.PRIMARY)){ //Tile clicking event
				controller.movePlayer(x,y);
			}
			
			if(e.getButton().equals(MouseButton.SECONDARY)) { //Remove dragNdrop flags

			}
		});
		
		initDragNDrop();
		
		controller.addListener(this);
		update();
	}
	
	private void setCustomStyle(int tileType) {
		switch(tileType) {
			case Tile.FLOOR:
				setFloorStyle();
				break;
			case Tile.WALL:
				setWallStyle();
				break;
			case Tile.VOID:
				setVoidStyle();
				break;
		}
	}
	private void setFloorStyle() {
		this.setStyle("-fx-background-color: #dddddd; -fx-border-color: Black");
		
		this.setOnMouseEntered(e->{
		});
		
		this.setOnMouseExited(e->{
		});
		
		this.setOnMousePressed(e->{
		});
	}
	
	private void setVoidStyle() {
		this.setStyle("-fx-background-color: #111111; -fx-border-color: Black");
		
		this.setOnMouseEntered(e->{
		});
		
		this.setOnMouseExited(e->{
		});
		
		this.setOnMousePressed(e->{
		});
	}
	
	private void setWallStyle() {
		
		this.setStyle("-fx-background-color: #3f3f3f; -fx-border-color: Black");
		
		this.setOnMouseEntered(e->{
			if(controller.getActionsLeft() > 0)
				this.setCursor(Cursor.OPEN_HAND);
		});
		
		this.setOnMouseExited(e->{
			this.setCursor(Cursor.DEFAULT);
		});
		
		this.setOnMousePressed(e->{
		});
		
	}
	
	private void setAccessibleStyle() {
		this.setStyle("-fx-background-color: #31BFFF; -fx-border-color: Black");
		
		this.setOnMouseEntered(e->{
			this.setStyle("-fx-background-color: #74D4FF; -fx-border-color: Black");
		});
		
		this.setOnMouseExited(e->{
			this.setStyle("-fx-background-color: #31BFFF; -fx-border-color: Black");
		});
		
		this.setOnMousePressed(e->{
			this.setStyle("-fx-background-color: #ABE5FF; -fx-border-color: Black; -fx-border-width: 3");
		});
	}

	private void initDragNDrop() {
		////////////////
		/// GRABBING ///
		////////////////
			
		this.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
            	if(controller.getTileType(x, y) == Tile.WALL && controller.getActionsLeft() > 0) {
            		controller.removeWallObject(x, y);
	                Dragboard db = TileButton.this.startDragAndDrop(TransferMode.ANY);
	                
	                ClipboardContent content = new ClipboardContent();
	                content.putString(Integer.toString(x) + "," + Integer.toString(y));
	                
	                db.setContent(content);
            	}
            }
        });
		
		this.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
            	TileButton.this.setCursor(Cursor.DEFAULT);
                if (!controller.isDropSuccess()) {
                	controller.restoreWall();
                }else {
                	controller.movedWall();
                }
            }
        });
		
		////////////////
		/// DROPPING ///
		////////////////
		
		this.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				if (event.getDragboard().hasString()) {
                	Dragboard db = event.getDragboard();
                	String wallObjectOrigin = (String) db.getContent(DataFormat.PLAIN_TEXT);
                    if(controller.setWallObjectShadow(x,y,wallObjectOrigin))
                    	event.acceptTransferModes(TransferMode.MOVE);
            	}
				TileButton.this.setCursor(Cursor.CLOSED_HAND);
            }
        });
        
        this.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
            	controller.unshadow();
            }
        });
        
        this.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
            	controller.shadowToWall();
            	event.setDropCompleted(false);	
            }
        });

	}
	
	public void update() {
		this.setTextFill(Color.GREEN);
		//this.setText(Integer.toString(x) + " " + Integer.toString(y));
		
		if(controller.isAccessible(x,y))
			setAccessibleStyle();
		else
			setCustomStyle(controller.getTileType(x, y));
				
		if(controller.getPlayer(x, y) != -1)
			this.setText(Integer.toString(controller.getPlayer(x, y))); 
		else
			this.setText("");
		
		if(controller.isShadowed(x, y))
			if(controller.getTileType(x,y) > 0 || controller.getPlayer(x, y) != -1)
				this.setStyle("-fx-background-color: RED; -fx-border-color: Black; -fx-opacity:0.8");
			else
				this.setStyle("-fx-background-color: #3f3f3f; -fx-border-color: Black; -fx-opacity:0.9");
		
	}
}
