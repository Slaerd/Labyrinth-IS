package view;

import java.util.ArrayList;

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
		
		setFloorStyle();
		
		this.setOnMouseClicked(e->{
			
			if(e.getButton().equals(MouseButton.PRIMARY)){ //Tile clicking event
				controller.movePlayer(controller.getTurnPlayer(),x,y);
			}
			
			if(e.getButton().equals(MouseButton.SECONDARY)) { //Remove dragNdrop flags

			}
		});
		
		initDragNDrop();
		
		controller.addListener(this);
		update();
	}

	private void setFloorStyle() {
		this.setStyle("-fx-background-color: #dddddd; -fx-border-color: Black");
	}
	private void setWallStyle() {
		
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
            	
            	if(controller.getTileType(x, y) == Tile.WALL) {
	                Dragboard db = TileButton.this.startDragAndDrop(TransferMode.ANY);
	                
	                ClipboardContent content = new ClipboardContent();
	                content.putString(Integer.toString(x) + "," + Integer.toString(y));
	                db.setContent(content);
            	}
            }
        });
		
		this.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
            	if(controller.getTileType(x, y) == Tile.WALL) {
	                if (event.getTransferMode() == TransferMode.MOVE) {
	                	controller.removeWallObject(x, y);
	                }
            	}
            }
        });
		
		////////////////
		/// DROPPING ///
		////////////////
		
		this.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
            	if(controller.getTileType(x, y) == Tile.FLOOR && controller.getPlayer(x, y) == -1) { //only react if the tile isn't clicked nor flagged
	                if (event.getGestureSource() != TileButton.this &&
	                        event.getDragboard().hasString()) {
	                    event.acceptTransferModes(TransferMode.MOVE);
	                };
            	}
            }
        });
		
        this.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                 if (event.getDragboard().hasString()) {
                	 System.out.println("DragPos : " + x + " " + y);
                	 Dragboard db = event.getDragboard();
                	 String wallObjectOrigin = (String) db.getContent(DataFormat.PLAIN_TEXT);
                     controller.setWallObjectShadow(x,y,wallObjectOrigin);
                 }
            }
        });
        
        this.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
            	controller.unshadow();
            }
        });
        
        this.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
            	/*if(controller.isAccessible(x, y)) {
	                Dragboard db = event.getDragboard();
	                boolean success = false;
	                String key = (String) db.getContent(DataFormat.PLAIN_TEXT); //get the flagStand number from the dragboard
	                if ( Integer.parseInt(key) >= 0) {
	                   controller.putFlag(x, y,Integer.parseInt(key)); //put it onto the tile
	                   success = true;
	                }
	                event.setDropCompleted(success);
	             }*/
            	controller.unshadow();
            }
        });

	}
	
	public void update() {
		this.setTextFill(Color.GREEN);
		this.setText(Integer.toString(x) + " " + Integer.toString(y));
		
		String style = new String();
		int tileType = controller.getTileType(x, y);
		
		if(tileType == Tile.FLOOR)
			style = "-fx-background-color: #dddddd; -fx-border-color: Black";
		if(tileType == Tile.WALL) {
			style = "-fx-background-color: #3f3f3f; -fx-border-color: Black";
		}
		if(tileType == Tile.VOID) {
			style = "-fx-background-color: #111111; -fx-border-color: Black";
		}
				
		if(controller.getPlayer(x, y) != -1) {
			//this.setTextFill();
			this.setText(Integer.toString(controller.getPlayer(x, y))); 
		}
		/*else
			this.setText("");*/
		
		if(controller.isAccessible(x,y))
			setAccessibleStyle();
		else
			this.setStyle(style);
		
		if(controller.isShadowed(x, y))
			if(controller.getTileType(x,y) > 0 || controller.getPlayer(x, y) != -1)
				this.setStyle("-fx-background-color: RED; -fx-border-color: Black; -fx-opacity:0.8");
			else
				this.setStyle("-fx-background-color: #3f3f3f; -fx-border-color: Black; -fx-opacity:0.9");
		
	}
}
