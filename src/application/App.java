package application;


import controller.CharGenController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;

import java.io.FileInputStream;
import java.util.ArrayList;

import controller.*;
import view.ListenerButton;
import view.TileButton;
import model.CharGenModel;

public class App{
	
	private Stage primaryStage;
	private CharGenController myCharGenController = new CharGenController();
	
	
	//Scene List

	protected Scene gameScene; 

	protected VBox labSelect = new VBox();//root of the labSelectScene
	protected FlowPane game; 
	protected Scene labSelectScene;
	protected VBox mainMenuPane;//root of mainMenuScene
	protected Scene mainMenuScene;
	protected HBox charGenRoot = new HBox();//root of charGenScene
	protected Scene charGenScene;
	
	//MainMenu widgets
	public Button playButton = new Button("Play");
	public Button mazeCreationButton = new Button("Maze creation");
	public Button closeButton = new Button("Close");
	public Button charGenMenuButton = new Button("Character creation");
	
	//labSelect widget
	
	//charGen widgets
	private Button doneButton = new Button("DONE");
	private Button rightArrowHead = new Button(">");
	private Button leftArrowHead = new Button("<");
	private Button rightArrowBody = new Button(">");
	private Button leftArrowBody = new Button("<");
	private Button rightArrowLegs = new Button(">");
	private Button leftArrowLegs = new Button("<");
	//to switch between character customization
	private Label playerNumberL = new Label("Player #");
	private Button playerNumber = new Button(Integer.toString(myCharGenController.myModel.getCurrentPlayer()));
	//the image between the two arrows
	private ToggleButton imgHead = new ToggleButton("1");
	private ToggleButton imgBody = new ToggleButton("1");
	private ToggleButton imgLegs = new ToggleButton("1");
	
	private HBox headButtons = new HBox();
	private HBox bodyButtons = new HBox();
	private HBox legsButtons = new HBox();
	private HBox colorPlusPlayer = new HBox();
	
	private VBox characterDisplay = new VBox();
	private VBox playerNumberVBox = new VBox();
	private VBox pickersPlusDone = new VBox();
	
	private ColorPicker colorPicker = new ColorPicker();
	
	private Image head1 = new Image("file: images/head1.jpg");
	private ImageView head1IV = new ImageView(head1);
	private Image head2 = new Image("file: images.head2.jpg");
	private ImageView head2IV = new ImageView(head2);
	private StackPane headImages = new StackPane();
	
	private Image body1 = new Image("file: images/body1.jpg");
	private ImageView body1IV = new ImageView(body1);
	private Image body2 = new Image("file: images.body2.jpg");
	private ImageView body2IV = new ImageView(body2);
	private StackPane bodyImages = new StackPane();
	
	private Image legs1 = new Image("file: images/legs1.jpg");
	private ImageView legs1IV = new ImageView(legs1);
	private Image legs2 = new Image("file: images.legs2.jpg");
	private ImageView legs2IV = new ImageView(legs2);
	private StackPane legsImages = new StackPane();
	//Game stuff
	
	private GameController gameController;
	
	public App(Stage stage) {
		this.primaryStage = stage;
		gameController = new GameController();
		gameController.addModel(new GameModel());
		
		initGameUI();
		
		
		this.primaryStage = stage;
		initMainMenu();
		initLabSelect();
		initCharGen();

	}
	
	public void initMainMenu() {

		mainMenuPane = new VBox();
		mainMenuPane.getChildren().addAll(playButton, charGenMenuButton, mazeCreationButton, closeButton);
		mainMenuScene = new Scene(mainMenuPane);
		for(Node child : mainMenuPane.getChildren()) {
			((Button) child).setMaxWidth(Double.MAX_VALUE);
		}
		
		playButton.setOnMouseClicked(e->{
			primaryStage.setScene(labSelectScene);
			primaryStage.centerOnScreen();
		});
		
		closeButton.setOnMouseClicked(e->{
			Platform.exit();
		});
		charGenMenuButton.setOnMouseClicked(e->{
			
			primaryStage.setScene(charGenScene);
		});
		
	}
	
	private void initLabSelect() {
		
		Button labBackButton = new Button("Back");
		Button charGenLabButton = new Button("Character creation");
		Button labSquare = new Button();
		Button labRectangle = new Button();
		Button labCross = new Button();
		Label labelSquare = new Label("The Square");
		Label labelRectangle = new Label("The Rectangle");
		Label labelCross = new Label("The Cross");
		
		labBackButton.setOnMouseClicked(e->{
			primaryStage.setScene(mainMenuScene);
			primaryStage.centerOnScreen();

		});
		
		charGenLabButton.setOnMouseClicked(e->{
			primaryStage.setScene(charGenScene);
			primaryStage.centerOnScreen();

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
		sceneSwitchBox.getChildren().addAll(labBackButton,charGenLabButton);
		
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
	 
	
	public void initCharGen() {

		myCharGenController.addPlayers();
		
		head1IV.setId("1");
		head2IV.setId("2");
		head1IV.setDisable(true);
		head2IV.setDisable(true);
		
		body1IV.setId("1");
		body2IV.setId("2");
		body1IV.setDisable(true);
		body2IV.setDisable(true);
		
		legs1IV.setId("1");
		legs2IV.setId("2");
		legs1IV.setDisable(true);
		legs2IV.setDisable(true);
		/*
		 * imgHead.setPrefWidth(60); imgBody.setPrefWidth(60); imgLegs.setPrefWidth(60);
		 */
		
		headImages.getChildren().addAll(head1IV, head2IV);
		bodyImages.getChildren().addAll(body1IV, body2IV);
		legsImages.getChildren().addAll(legs1IV, legs2IV);
		
		//TODO transform characterDisplay de VBox de HBoxes à HBox de VBoxes
		headButtons.getChildren().addAll(leftArrowHead, headImages, rightArrowHead);
		bodyButtons.getChildren().addAll(leftArrowBody, bodyImages, rightArrowBody);
		legsButtons.getChildren().addAll(leftArrowLegs, legsImages, rightArrowLegs);
		characterDisplay.getChildren().addAll(headButtons, bodyButtons, legsButtons);
		playerNumberVBox.getChildren().addAll(playerNumber, playerNumberL);
		colorPlusPlayer.getChildren().addAll(colorPicker, playerNumberVBox);
		pickersPlusDone.getChildren().addAll(colorPlusPlayer, doneButton);
		charGenRoot.getChildren().addAll(characterDisplay, pickersPlusDone);
		
		charGenScene = new Scene(charGenRoot);
		
		for(Node node : headImages.getChildren()) {
			if(node.getId()==Integer.toString(myCharGenController.myModel.players.get(myCharGenController.myModel.currentPlayer).getCurrentHead())) {
				node.setDisable(false);
			}else {
				node.setDisable(true);
			}
		}
		
		for(Node node : bodyImages.getChildren()) {
			if(node.getId()==Integer.toString(myCharGenController.myModel.players.get(myCharGenController.myModel.currentPlayer).getCurrentBody())) {
				node.setDisable(false);
			}else {
				node.setDisable(true);
			}
		}
		
		for(Node node : headImages.getChildren()) {
			if(node.getId()==Integer.toString(myCharGenController.myModel.players.get(myCharGenController.myModel.currentPlayer).getCurrentHead())) {
				node.setDisable(false);
			}else {
				node.setDisable(true);
			}
		}
		
		
		doneButton.setOnMouseClicked(e->{
			primaryStage.setScene(mainMenuScene);
			primaryStage.centerOnScreen();

		});
		
		
		playerNumber.setOnMouseClicked(e->{
			  if(playerNumber.getText().equals("1")) { 
				  playerNumber.setText("2");
				  imgHead.setText(Integer.toString(myCharGenController.myModel.players.get(1).getCurrentHead()));
				  imgBody.setText(Integer.toString(myCharGenController.myModel.players.get(1).getCurrentBody()));
				  imgLegs.setText(Integer.toString(myCharGenController.myModel.players.get(1).getCurrentLegs()));
			  }else if(playerNumber.getText().equals("2")) {
				  playerNumber.setText("3");
				  imgHead.setText(Integer.toString(myCharGenController.myModel.players.get(2).getCurrentHead())); 
				  imgBody.setText(Integer.toString(myCharGenController.myModel.players.get(2).getCurrentBody()));
				  imgLegs.setText(Integer.toString(myCharGenController.myModel.players.get(2).getCurrentLegs()));
			  }else if(playerNumber.getText().equals("3")) {
				  playerNumber.setText("1");
				  imgHead.setText(Integer.toString(myCharGenController.myModel.players.get(0).getCurrentHead()));
				  imgBody.setText(Integer.toString(myCharGenController.myModel.players.get(0).getCurrentBody()));
				  imgLegs.setText(Integer.toString(myCharGenController.myModel.players.get(0).getCurrentLegs()));
			  }
		});
		
		rightArrowHead.setOnMouseClicked(e->{
			if(playerNumber.getText().equals("1")) {
				myCharGenController.UpHeadNumber(myCharGenController.myModel.players.get(0));
				imgHead.setText(Integer.toString(myCharGenController.getCurrentHead(myCharGenController.myModel.players.get(0))));
			}else if(playerNumber.getText().equals("2")) {
				myCharGenController.UpHeadNumber(myCharGenController.myModel.players.get(1));
				imgHead.setText(Integer.toString(myCharGenController.getCurrentHead(myCharGenController.myModel.players.get(1))));
			}else if(playerNumber.getText().equals("3")) {
				myCharGenController.UpHeadNumber(myCharGenController.myModel.players.get(2));
				imgHead.setText(Integer.toString(myCharGenController.getCurrentHead(myCharGenController.myModel.players.get(2))));
			}
		});
		
		leftArrowHead.setOnMouseClicked(e->{
			if(playerNumber.getText().equals("1")) {
				myCharGenController.DownHeadNumber(myCharGenController.myModel.players.get(0));
				imgHead.setText(Integer.toString(myCharGenController.getCurrentHead(myCharGenController.myModel.players.get(0))));
			}else if(playerNumber.getText().equals("2")) {
				myCharGenController.DownHeadNumber(myCharGenController.myModel.players.get(1));
				imgHead.setText(Integer.toString(myCharGenController.getCurrentHead(myCharGenController.myModel.players.get(1))));
			}else if(playerNumber.getText().equals("3")) {
				myCharGenController.DownHeadNumber(myCharGenController.myModel.players.get(2));
				imgHead.setText(Integer.toString(myCharGenController.getCurrentHead(myCharGenController.myModel.players.get(2))));
			}
		});
		
		
		rightArrowBody.setOnMouseClicked(e->{
			if(playerNumber.getText().equals("1")) {
				myCharGenController.UpBodyNumber(myCharGenController.myModel.players.get(0));
				imgBody.setText(Integer.toString(myCharGenController.getCurrentBody(myCharGenController.myModel.players.get(0))));
			}else if(playerNumber.getText().equals("2")) {
				myCharGenController.UpBodyNumber(myCharGenController.myModel.players.get(1));
				imgBody.setText(Integer.toString(myCharGenController.getCurrentBody(myCharGenController.myModel.players.get(1))));
			}else if(playerNumber.getText().equals("3")) {
				myCharGenController.UpBodyNumber(myCharGenController.myModel.players.get(2));
				imgBody.setText(Integer.toString(myCharGenController.getCurrentBody(myCharGenController.myModel.players.get(2))));
			}
		});
		
		leftArrowBody.setOnMouseClicked(e->{
			if(playerNumber.getText().equals("1")) {
				myCharGenController.DownBodyNumber(myCharGenController.myModel.players.get(0));
				imgBody.setText(Integer.toString(myCharGenController.getCurrentBody(myCharGenController.myModel.players.get(0))));
			}else if(playerNumber.getText().equals("2")) {
				myCharGenController.DownBodyNumber(myCharGenController.myModel.players.get(1));
				imgBody.setText(Integer.toString(myCharGenController.getCurrentBody(myCharGenController.myModel.players.get(1))));
			}else if(playerNumber.getText().equals("3")) {
				myCharGenController.DownBodyNumber(myCharGenController.myModel.players.get(2));
				imgBody.setText(Integer.toString(myCharGenController.getCurrentBody(myCharGenController.myModel.players.get(2))));
			}
		});
		
		rightArrowLegs.setOnMouseClicked(e->{
			if(playerNumber.getText().equals("1")) {
				myCharGenController.UpLegsNumber(myCharGenController.myModel.players.get(0));
				imgLegs.setText(Integer.toString(myCharGenController.getCurrentLegs(myCharGenController.myModel.players.get(0))));
			}else if(playerNumber.getText().equals("2")) {
				myCharGenController.UpLegsNumber(myCharGenController.myModel.players.get(1));
				imgLegs.setText(Integer.toString(myCharGenController.getCurrentLegs(myCharGenController.myModel.players.get(1))));
			}else if(playerNumber.getText().equals("3")) {
				myCharGenController.UpLegsNumber(myCharGenController.myModel.players.get(2));
				imgLegs.setText(Integer.toString(myCharGenController.getCurrentLegs(myCharGenController.myModel.players.get(2))));
			}
		});
		
		leftArrowLegs.setOnMouseClicked(e->{
			if(playerNumber.getText().equals("1")) {
				myCharGenController.DownLegsNumber(myCharGenController.myModel.players.get(0));
				imgLegs.setText(Integer.toString(myCharGenController.getCurrentLegs(myCharGenController.myModel.players.get(0))));
			}else if(playerNumber.getText().equals("2")) {
				myCharGenController.DownLegsNumber(myCharGenController.myModel.players.get(1));
				imgLegs.setText(Integer.toString(myCharGenController.getCurrentLegs(myCharGenController.myModel.players.get(1))));
			}else if(playerNumber.getText().equals("3")) {
				myCharGenController.DownLegsNumber(myCharGenController.myModel.players.get(2));
				imgLegs.setText(Integer.toString(myCharGenController.getCurrentLegs(myCharGenController.myModel.players.get(2))));
			}
		});
	}
}
