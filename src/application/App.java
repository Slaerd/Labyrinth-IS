package application;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;
import controller.*;
import view.ListenerButton;
import view.TileButton;

public class App{
	
	private Stage stage;
	
	//Scene List
	protected Scene labSelectScene; 
	protected Scene gameScene; 
	//MainMenu widgets
	public Button charGenButton = new Button("Character Generation");
	public Button playButton = new Button("Play");
	public Button mazeCreationButton = new Button("Maze creation");
	public Button closeButton = new Button("Close");
	
	//labSelect widgets
	private Button labBackButton = new Button("Back");
	private Button labSquare = new Button();
	private Button labRectangle = new Button();
	private Button labCross = new Button();
	private Label labelSquare = new Label("The Square");
	private Label labelRectangle = new Label("The Rectangle");
	private Label labelCross = new Label("The Cross");
	
	//Game stuff
	private GameController gameController;
	

	
	
	public App(Stage stage) {
		this.stage = stage;
		gameController = new GameController();
		gameController.addModel(new GameModel());
		
		//initUI();
		//initLabSelect();
		initGameUI();
		
		
	}
	
	public void initUI() {
		VBox vpane = new VBox();
		vpane.getChildren().addAll(charGenButton, playButton);
		//this.getChildren().add(vpane);
	}
	
	private void initLabSelect() {
		labBackButton.setOnMouseClicked(e->{
			/*TODO 1 : Nathan | Met le nom du Pane du menu*/
			//stage.setScene();
		});
		
		VBox root = new VBox();
		HBox sceneSwitchBox = new HBox();
		HBox lineOne = new HBox();
		VBox blockOne = new VBox();
		VBox blockTwo = new VBox();
		VBox lineTwo = new VBox();
		
		labSquare.setPrefSize(200,200);
		labRectangle.setPrefSize(200, 200);
		labCross.setPrefSize(200, 200);
		labSquare.setOnMouseClicked(e->{
			//TODO 1 : Kevin | Ajouter le setScene game
			if(e.getButton().equals(MouseButton.PRIMARY))
				System.out.println("Square");
			
		});
		labRectangle.setOnMouseClicked(e->{
			if(e.getButton().equals(MouseButton.PRIMARY))
				System.out.println("Rectangle");
		});
		labCross.setOnMouseClicked(e->{
			if(e.getButton().equals(MouseButton.PRIMARY))
				System.out.println("Cross");
		});
		
		sceneSwitchBox.setSpacing(700);
		sceneSwitchBox.getChildren().addAll(labBackButton,charGenButton);
		
		blockOne.getChildren().addAll(labSquare,labelSquare);
		blockTwo.getChildren().addAll(labRectangle,labelRectangle);
		
		lineOne.setAlignment(Pos.CENTER);
		lineOne.setSpacing(200);
		
		lineOne.getChildren().addAll(blockOne,blockTwo);
		lineTwo.getChildren().addAll(labCross,labelCross);
		
		lineTwo.setAlignment(Pos.CENTER);
		
		root.setSpacing(25);
		root.setPadding(new Insets(50,50,50,50));
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(sceneSwitchBox,lineOne,lineTwo);
		labSelectScene = new Scene(root,1000,600);
	}
	
	private void initGameUI() {
		BorderPane root = new BorderPane();
		GridPane laby = new GridPane();
		
		ListenerButton nextTurn = new ListenerButton("Pass",gameController) {
			public void update() {
				if(gameController.getActionsLeft() == 0)
					this.setStyle("-fx-background-color: #9CFF31; -fx-border-color: Black");
				else
					this.setStyle("");
					
			}
		};
		
		nextTurn.setPrefSize(100, 100);
		nextTurn.setOnMouseClicked(e -> {
			if(e.getButton().equals(MouseButton.PRIMARY))
				gameController.nextTurn();
		});
		gameController.addListener(nextTurn);
		
		TileButton buffer;
		for(int i = 0; i < Laby.SIZE; i++) {
			for(int j = 0; j < Laby.SIZE; j++) {
				buffer = new TileButton(i, j, gameController);
				laby.add(buffer, i, j);
			}
		}
		
		root.setCenter(laby);
		root.setBottom(nextTurn);
		gameScene = new Scene(root, 1000,600);
	}
}
