package view;


import controller.GameController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import model.Player;
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
		
		/*this.setOnKeyPressed(e->{
			if(e.getCode().equals(KeyCode.F)) {
				if(controller.isHovered(x, y)) {
					controller.rotate(x,y);
					System.out.println("sus");
				}
				e.consume();
			}	
		});*/
		
		initDragNDrop();
	}
	
	private void setCustomStyle(int tileType) {
		switch(tileType) {
			case Tile.FLOOR:
				setFloorStyle();
				break;
			case Tile.SPAWN:
				setSpawnStyle();
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
	private void setSpawnStyle() {
		this.setStyle("-fx-background-color: #ffffff; -fx-border-color: Black");
		
		this.setOnMouseEntered(e->{
		});
		
		this.setOnMouseExited(e->{
		});
		
		this.setOnMousePressed(e->{
		});
	}
	private void setVoidStyle() {
		this.setStyle("-fx-opacity: 0");
		
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
			if(controller.getActionsLeft() > 0) {
				this.setCursor(Cursor.OPEN_HAND);
				controller.hoverWallObject(x, y);
			}
		});
		
		this.setOnMouseExited(e->{
			controller.unhover();
			this.setCursor(Cursor.DEFAULT);
		});
		
		this.setOnMousePressed(e->{
			this.setStyle("-fx-background-color: #7f7f7f; -fx-border-color: Blue");
			this.setCursor(Cursor.CLOSED_HAND);
		});
		
		this.setOnMouseReleased(e->{
			//this.setStyle("-fx-background-color: #3f3f3f; -fx-border-color: Black");
			this.setCursor(Cursor.DEFAULT);
		});
		
		if(controller.isHovered(x,y)) {
			this.setStyle("-fx-background-color: #5f5f5f; -fx-border-color: Black");
		}else {
			this.setStyle("-fx-background-color: #3f3f3f; -fx-border-color: Black");
		}
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
		
		this.setOnRotate(e->{
			System.out.println("this workming ?");
		});
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
            	//System.out.println("bruh");
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
            }
        });
        
        this.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
            	if (event.getDragboard().hasString()) {
            		controller.unshadow();
            	}
            }
        });
        
        this.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
            		controller.shadowToWall();
            }
        });

	}
	
	public void update() {
		
		this.setTextFill(Color.GREEN);
		
		if(controller.isAccessible(x,y))
			setAccessibleStyle();
		else
			setCustomStyle(controller.getTileType(x, y));
				
		if(controller.getPlayerInTile(x, y) != Player.NOPLAYER) {
			if(controller.getActionsLeft(x,y) == 0)
				this.setTextFill(Color.RED);
			//System.out.println("set player " + controller.getPlayerInTile(x, y) + "at " + x + " " + y);
			this.setText(Integer.toString(controller.getPlayerInTile(x, y))); 
		}else {
			this.setText("");
			this.setTextFill(Color.GREEN);
		}
		

		
		if(controller.isShadowed(x, y)) {
			if(controller.getTileType(x,y) > Tile.FLOOR || controller.getPlayerInTile(x, y) != Player.NOPLAYER)
				this.setStyle("-fx-background-color: RED; -fx-border-color: Black; -fx-opacity:0.8");
			else
				this.setStyle("-fx-background-color: #3f3f3f; -fx-border-color: Black; -fx-opacity:0.8");
		}
		
		if(controller.isTrapped(x,y) && controller.getPlayerInTile(x, y) != Player.NOPLAYER) {
			controller.triggerTrap(x,y);
		}
		
		this.setDisable(controller.isTrapActive());

		if(controller.isGameDone())
			this.setDisable(true);
		
		//this.setText(Integer.toString(x) + " " + Integer.toString(y));
		
	}
}
