package application;


import controller.CharGenController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import controller.*;
import view.ImageLib;
import view.ListenerButton;
import view.ListenerLabel;
import view.TileButton;
import view.TrapButton;

public class App{
	public static final int WINDOWX = 1280;
	public static final int WINDOWY = 760;
	public static final Color beginningCharacterColor = Color.rgb(232, 237, 233);
	
	private Stage primaryStage;
	private CharGenController myCharGenController = new CharGenController();
	
	private String RGBOfBeginningCharacterColor = "rgb("+beginningCharacterColor.getRed()*255 +","+beginningCharacterColor.getGreen()*255+","+beginningCharacterColor.getBlue()*255+");";
	
	//Scene List

	protected VBox labSelect = new VBox();//root of the labSelectScene
	protected Scene gameScene; 
	protected Scene labSelectScene;
	protected Scene qteScene;
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
	private Button playerNumber = new Button(Integer.toString(myCharGenController.getCurrentPlayer()));
	//the image between the two arrows
	private ToggleButton imgHead = new ToggleButton("1");
	private ToggleButton imgBody = new ToggleButton("1");
	private ToggleButton imgLegs = new ToggleButton("1");
	
	private ColorPicker colorPicker = new ColorPicker();
	
	private StackPane headImages = new StackPane();
	
	private StackPane bodyImages = new StackPane();
	
	private StackPane legsImages = new StackPane();
	//Game stuff
	private GameController gameController;
	
	public App(Stage stage) throws FileNotFoundException {

		
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
		
		labSquare.setPrefSize(300,300);
		labRectangle.setPrefSize(300, 300);
		labCross.setPrefSize(300, 300);
		labSquare.setOnMouseClicked(e->{
			if(e.getButton().equals(MouseButton.PRIMARY)) {
				gameController = new GameController();
				gameController.addModel(new GameModel(Laby.SQUARE));
				
				initGameUI();
				primaryStage.setScene(gameScene);
			}
		});
		labRectangle.setOnMouseClicked(e->{
			if(e.getButton().equals(MouseButton.PRIMARY)) {
				gameController = new GameController();
				gameController.addModel(new GameModel(Laby.RECTANGLE));
				
				initGameUI();
				primaryStage.setScene(gameScene);
			}
		});
		labCross.setOnMouseClicked(e->{
			if(e.getButton().equals(MouseButton.PRIMARY)) {
				gameController = new GameController();
				gameController.addModel(new GameModel(Laby.CROSS));
				
				initGameUI();
				primaryStage.setScene(gameScene);
			}
		});
		
		sceneSwitchBox.setSpacing(3./4. * WINDOWX );
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
		labSelectScene = new Scene(root,WINDOWX,WINDOWY);
	}
	
	
	 private void initGameUI() {
		VBox root = new VBox();
		HBox labelZone = new HBox();
		GridPane laby = new GridPane();
		HBox buttonZone = new HBox();
		  
		ListenerLabel turnPlayer = new ListenerLabel("", gameController) {
			 public void update() {
				 if(gameController.isGameDone()) {
					 this.setStyle("-fx-text-fill: Green; -fx-font-size: 30");
					 this.setText(gameController.getTurnPlayerName() + " has won !");
				 }else
					 this.setText(gameController.getTurnPlayerName() + "'s turn !");
			 }
		};
		turnPlayer.setStyle("-fx-font-size: 30");
		  
		ListenerLabel actionsLeft = new ListenerLabel("", gameController) {
			 public void update() {
				 this.setText("Actions left : " + gameController.getActionsLeft());
			 }
		};
		actionsLeft.setStyle("-fx-font-size: 30");
		  
		ListenerButton kill = new ListenerButton("Kill", gameController) {
			public void update() {
				if(gameController.isTargetNear())
					this.setStyle("-fx-background-color: #FF0000; -fx-border-color: Black; -fx-text-fill: White");
				else
					this.setStyle("");
				this.setDisable(gameController.isTrapActive());
			}
		};
		
		kill.setPrefSize(120,80);
		kill.setOnMouseClicked(e->{
			if(e.getButton().equals(MouseButton.PRIMARY))
				gameController.kill();
		});
		
		ListenerButton pass = new ListenerButton("Pass", gameController) {
			public void update() {
				if(gameController.getActionsLeft() == 0)
					this.setStyle("-fx-background-color: #9CFF31; -fx-border-color: Black");
				else
					this.setStyle("");
				
				this.setDisable(gameController.isTrapActive());
			}
		};
		
		pass.setPrefSize(120, 80);
		pass.setOnMouseClicked(e -> {
			if(e.getButton().equals(MouseButton.PRIMARY))
				gameController.nextTurn();
		});
		
		Button backButton = new Button("Back");
		
		backButton.setOnMouseClicked(e->{
			if(e.getButton().equals(MouseButton.PRIMARY)) {
				primaryStage.setScene(mainMenuScene);
				primaryStage.centerOnScreen();
			}
		});
		
		int[] size = gameController.getLabSize();
		TileButton buffer;
		for(int i = 0; i < size[0]; i++) {
			for(int j = 0; j < size[1]; j++) {
				buffer = new TileButton(i, j, gameController);
				laby.add(buffer, i, j);
			}
		}
		
		labelZone.setSpacing(2./3.5 * WINDOWX);
		laby.setAlignment(Pos.CENTER);
		labelZone.setPadding(new Insets(0,10,0,10));
		buttonZone.setPadding(new Insets(10,10,10,100));
		
		root.getChildren().addAll(backButton,labelZone,laby,buttonZone);
		buttonZone.getChildren().addAll(pass,kill);
		labelZone.getChildren().addAll(turnPlayer, actionsLeft);
		
		//root.setAlignment(Pos.CENTER);
		gameScene = new Scene(root, 1280,720);
		gameController.notifyListeners();
	}
	 
	public void initTrapStage() {

	}
	
	public void initCharGen() throws FileNotFoundException {
		ImageLib lib = new ImageLib();
		myCharGenController.addPlayers();
		
		Button applyColor = new Button("Apply Color");
		
		HBox colorPlusPlayer = new HBox();
		HBox characterDisplay = new HBox();
		VBox playerNumberVBox = new VBox();
		VBox pickersPlusDone = new VBox();
		
		VBox characterImages = new VBox();
		VBox leftArrows = new VBox();
		VBox rightArrows = new VBox();
		VBox colorPickers = new VBox();
		
		
		StackPane character = new StackPane();
		
		Pane paneOverImages = new Pane(); 
		
		paneOverImages.setStyle("-fx-backgrond-color : #42e5eb");
		paneOverImages.setOpacity(1);
		characterDisplay.setOpacity(0.1);
		
		ImageView head1IV = new ImageView(lib.head1);
		ImageView head2IV = new ImageView(lib.head2);
		ImageView body1IV = new ImageView(lib.body1);
		ImageView body2IV = new ImageView(lib.body2);
		ImageView legs1IV = new ImageView(lib.legs1);
		ImageView legs2IV = new ImageView(lib.legs2);
		
		head1IV.setId("1");
		head2IV.setId("2");
		head1IV.setDisable(true);
		head2IV.setDisable(true);
		head1IV.setFitHeight(100);
		head1IV.setFitWidth(100);
		head2IV.setFitHeight(100);
		head2IV.setFitWidth(100);
		
		body1IV.setId("1");
		body2IV.setId("2");
		body1IV.setDisable(true);
		body2IV.setDisable(true);
		body1IV.setFitHeight(100);
		body1IV.setFitWidth(100);
		body2IV.setFitHeight(100);
		body2IV.setFitWidth(100);
		
		legs1IV.setId("1");
		legs2IV.setId("2");
		legs1IV.setDisable(true);
		legs2IV.setDisable(true);
		legs1IV.setFitHeight(100);
		legs1IV.setFitWidth(100);
		legs2IV.setFitHeight(100);
		legs2IV.setFitWidth(100);
		
		//setting the default color on the character images
		System.out.println(beginningCharacterColor);
		imgHead.setStyle("-fx-background-color:" + RGBOfBeginningCharacterColor);
		imgBody.setStyle("-fx-background-color:" + RGBOfBeginningCharacterColor);
		imgLegs.setStyle("-fx-background-color:" + RGBOfBeginningCharacterColor);
		
		headImages.getChildren().addAll(head1IV, head2IV);
		bodyImages.getChildren().addAll(body1IV, body2IV);
		legsImages.getChildren().addAll(legs1IV, legs2IV);
		
		colorPickers.getChildren().addAll(colorPicker, applyColor);
		characterImages.getChildren().addAll(headImages,bodyImages,legsImages);
		leftArrows.getChildren().addAll(leftArrowHead, leftArrowBody, leftArrowLegs);
		rightArrows.getChildren().addAll(rightArrowHead, rightArrowBody, rightArrowLegs);
		characterDisplay.getChildren().addAll(leftArrows, characterImages, rightArrows);
		playerNumberVBox.getChildren().addAll(playerNumber, playerNumberL);
		colorPlusPlayer.getChildren().addAll(colorPickers, playerNumberVBox);
		pickersPlusDone.getChildren().addAll(colorPlusPlayer, doneButton);
		//character.getChildren().addAll( characterDisplay, paneOverImages);
		character.getChildren().addAll( paneOverImages, characterDisplay);
		charGenRoot.getChildren().addAll(character, pickersPlusDone);
		
		charGenScene = new Scene(charGenRoot);
		
		for(Node node : headImages.getChildren()) {
			if(node.getId()==Integer.toString(myCharGenController.getPlayers().get(myCharGenController.getCurrentPlayer()).getCurrentHead())) {
				node.setDisable(false);
			}else {
				node.setDisable(true);
			}
		}
		
		for(Node node : bodyImages.getChildren()) {
			if(node.getId()==Integer.toString(myCharGenController.getPlayers().get(myCharGenController.getCurrentPlayer()).getCurrentBody())) {
				node.setDisable(false);
			}else {
				node.setDisable(true);
			}
		}
		
		for(Node node : headImages.getChildren()) {
			if(node.getId()==Integer.toString(myCharGenController.getPlayers().get(myCharGenController.getCurrentPlayer()).getCurrentLegs())) {
				node.setDisable(false);
			}else {
				node.setDisable(true);
			}
		}
		
		
		applyColor.setOnMouseClicked(e->{
			if(playerNumber.getText().equals("1")) { 
				  myCharGenController.setCurrentColor(myCharGenController.getPlayers().get(0), colorPicker.getValue());
				  System.out.println(myCharGenController.getPlayers().get(0).getCurrentColor());
				  for(Node node : characterImages.getChildren()) {
				  node.setStyle("-fx-background-color: rgb(" + myCharGenController.getPlayers().get(0).getCurrentColor().getRed()*255
						  +","+myCharGenController.getPlayers().get(0).getCurrentColor().getGreen()*255
						  +","+myCharGenController.getPlayers().get(0).getCurrentColor().getBlue()*255
						  +");"
						  );
				  }
				  
			}else if(playerNumber.getText().equals("2")) {
				  myCharGenController.setCurrentColor(myCharGenController.getPlayers().get(1), colorPicker.getValue());
				  System.out.println(myCharGenController.getPlayers().get(1).getCurrentColor());
				  for(Node node : characterImages.getChildren()) {
					  node.setStyle("-fx-background-color: rgb(" + myCharGenController.getPlayers().get(1).getCurrentColor().getRed()*255
							  +","+myCharGenController.getPlayers().get(1).getCurrentColor().getGreen()*255
							  +","+myCharGenController.getPlayers().get(1).getCurrentColor().getBlue()*255
							  +");"
							  );
					  }
			  }else if(playerNumber.getText().equals("3")) {
				  myCharGenController.setCurrentColor(myCharGenController.getPlayers().get(2), colorPicker.getValue());
				  System.out.println(myCharGenController.getPlayers().get(2).getCurrentColor());
				  for(Node node : characterImages.getChildren()) {
					  node.setStyle("-fx-background-color: rgb(" + myCharGenController.getPlayers().get(2).getCurrentColor().getRed()*255
							  +","+myCharGenController.getPlayers().get(2).getCurrentColor().getGreen()*255
							  +","+myCharGenController.getPlayers().get(2).getCurrentColor().getBlue()*255
							  +");"
							  );
					  }
			  }
		});
		
		
		doneButton.setOnMouseClicked(e->{
			primaryStage.setScene(mainMenuScene);
			primaryStage.centerOnScreen();

		});
		
		
		playerNumber.setOnMouseClicked(e->{
			  if(playerNumber.getText().equals("1")) { 
				  playerNumber.setText("2");
				  colorPicker.setValue(myCharGenController.getPlayers().get(1).getCurrentColor());
				  if(myCharGenController.getPlayers().get(1).getCurrentColor() == null) {
					  imgHead.setStyle("-fx-background-color:" + RGBOfBeginningCharacterColor);
					  imgBody.setStyle("-fx-background-color:" + RGBOfBeginningCharacterColor);
					  imgLegs.setStyle("-fx-background-color:" + RGBOfBeginningCharacterColor);
				  }else {
					  for(Node node : characterImages.getChildren()) {
						  node.setStyle("-fx-background-color: rgb(" + myCharGenController.getPlayers().get(1).getCurrentColor().getRed()*255
								  +","+myCharGenController.getPlayers().get(1).getCurrentColor().getGreen()*255
								  +","+myCharGenController.getPlayers().get(1).getCurrentColor().getBlue()*255
								  +");"
								  );
					  }
				  }
				  imgHead.setText(Integer.toString(myCharGenController.getPlayers().get(1).getCurrentHead()));
				  imgBody.setText(Integer.toString(myCharGenController.getPlayers().get(1).getCurrentBody()));
				  imgLegs.setText(Integer.toString(myCharGenController.getPlayers().get(1).getCurrentLegs()));
			  }else if(playerNumber.getText().equals("2")) {
				  playerNumber.setText("3");
				  colorPicker.setValue(myCharGenController.getPlayers().get(2).getCurrentColor());
				  if(myCharGenController.getPlayers().get(2).getCurrentColor() == null) {
					  imgHead.setStyle("-fx-background-color:" + RGBOfBeginningCharacterColor);
					  imgBody.setStyle("-fx-background-color:" + RGBOfBeginningCharacterColor);
					  imgLegs.setStyle("-fx-background-color:" + RGBOfBeginningCharacterColor);
				  }else {
					  for(Node node : characterImages.getChildren()) {
						  node.setStyle("-fx-background-color: rgb(" + myCharGenController.getPlayers().get(2).getCurrentColor().getRed()*255
								  +","+myCharGenController.getPlayers().get(2).getCurrentColor().getGreen()*255
								  +","+myCharGenController.getPlayers().get(2).getCurrentColor().getBlue()*255
								  +");"
								  );
					  }
				  }
				  imgHead.setText(Integer.toString(myCharGenController.getPlayers().get(2).getCurrentHead())); 
				  imgBody.setText(Integer.toString(myCharGenController.getPlayers().get(2).getCurrentBody()));
				  imgLegs.setText(Integer.toString(myCharGenController.getPlayers().get(2).getCurrentLegs()));
			  }else if(playerNumber.getText().equals("3")) {
				  playerNumber.setText("1");
				  colorPicker.setValue(myCharGenController.getPlayers().get(0).getCurrentColor());
				  if(myCharGenController.getPlayers().get(2).getCurrentColor() == null) {
					  imgHead.setStyle("-fx-background-color:" + RGBOfBeginningCharacterColor);
					  imgBody.setStyle("-fx-background-color:" + RGBOfBeginningCharacterColor);
					  imgLegs.setStyle("-fx-background-color:" + RGBOfBeginningCharacterColor);
				  }else {
					  for(Node node : characterImages.getChildren()) {
						  node.setStyle("-fx-background-color: rgb(" + myCharGenController.getPlayers().get(0).getCurrentColor().getRed()*255
								  +","+myCharGenController.getPlayers().get(0).getCurrentColor().getGreen()*255
								  +","+myCharGenController.getPlayers().get(0).getCurrentColor().getBlue()*255
								  +");"
								  );
					  }
				  }
				  imgHead.setText(Integer.toString(myCharGenController.getPlayers().get(0).getCurrentHead()));
				  imgBody.setText(Integer.toString(myCharGenController.getPlayers().get(0).getCurrentBody()));
				  imgLegs.setText(Integer.toString(myCharGenController.getPlayers().get(0).getCurrentLegs()));
			  }
		});
		
		rightArrowHead.setOnMouseClicked(e->{
			if(playerNumber.getText().equals("1")) {
				myCharGenController.UpHeadNumber(myCharGenController.getPlayers().get(0));
				imgHead.setText(Integer.toString(myCharGenController.getCurrentHead(myCharGenController.getPlayers().get(0))));
			}else if(playerNumber.getText().equals("2")) {
				myCharGenController.UpHeadNumber(myCharGenController.getPlayers().get(1));
				imgHead.setText(Integer.toString(myCharGenController.getCurrentHead(myCharGenController.getPlayers().get(1))));
			}else if(playerNumber.getText().equals("3")) {
				myCharGenController.UpHeadNumber(myCharGenController.getPlayers().get(2));
				imgHead.setText(Integer.toString(myCharGenController.getCurrentHead(myCharGenController.getPlayers().get(2))));
			}
		});
		
		leftArrowHead.setOnMouseClicked(e->{
			if(playerNumber.getText().equals("1")) {
				myCharGenController.DownHeadNumber(myCharGenController.getPlayers().get(0));
				imgHead.setText(Integer.toString(myCharGenController.getCurrentHead(myCharGenController.getPlayers().get(0))));
			}else if(playerNumber.getText().equals("2")) {
				myCharGenController.DownHeadNumber(myCharGenController.getPlayers().get(1));
				imgHead.setText(Integer.toString(myCharGenController.getCurrentHead(myCharGenController.getPlayers().get(1))));
			}else if(playerNumber.getText().equals("3")) {
				myCharGenController.DownHeadNumber(myCharGenController.getPlayers().get(2));
				imgHead.setText(Integer.toString(myCharGenController.getCurrentHead(myCharGenController.getPlayers().get(2))));
			}
		});
		
		
		rightArrowBody.setOnMouseClicked(e->{
			if(playerNumber.getText().equals("1")) {
				myCharGenController.UpBodyNumber(myCharGenController.getPlayers().get(0));
				imgBody.setText(Integer.toString(myCharGenController.getCurrentBody(myCharGenController.getPlayers().get(0))));
			}else if(playerNumber.getText().equals("2")) {
				myCharGenController.UpBodyNumber(myCharGenController.getPlayers().get(1));
				imgBody.setText(Integer.toString(myCharGenController.getCurrentBody(myCharGenController.getPlayers().get(1))));
			}else if(playerNumber.getText().equals("3")) {
				myCharGenController.UpBodyNumber(myCharGenController.getPlayers().get(2));
				imgBody.setText(Integer.toString(myCharGenController.getCurrentBody(myCharGenController.getPlayers().get(2))));
			}
		});
		
		leftArrowBody.setOnMouseClicked(e->{
			if(playerNumber.getText().equals("1")) {
				myCharGenController.DownBodyNumber(myCharGenController.getPlayers().get(0));
				imgBody.setText(Integer.toString(myCharGenController.getCurrentBody(myCharGenController.getPlayers().get(0))));
			}else if(playerNumber.getText().equals("2")) {
				myCharGenController.DownBodyNumber(myCharGenController.getPlayers().get(1));
				imgBody.setText(Integer.toString(myCharGenController.getCurrentBody(myCharGenController.getPlayers().get(1))));
			}else if(playerNumber.getText().equals("3")) {
				myCharGenController.DownBodyNumber(myCharGenController.getPlayers().get(2));
				imgBody.setText(Integer.toString(myCharGenController.getCurrentBody(myCharGenController.getPlayers().get(2))));
			}
		});
		
		rightArrowLegs.setOnMouseClicked(e->{
			if(playerNumber.getText().equals("1")) {
				myCharGenController.UpLegsNumber(myCharGenController.getPlayers().get(0));
				imgLegs.setText(Integer.toString(myCharGenController.getCurrentLegs(myCharGenController.getPlayers().get(0))));
			}else if(playerNumber.getText().equals("2")) {
				myCharGenController.UpLegsNumber(myCharGenController.getPlayers().get(1));
				imgLegs.setText(Integer.toString(myCharGenController.getCurrentLegs(myCharGenController.getPlayers().get(1))));
			}else if(playerNumber.getText().equals("3")) {
				myCharGenController.UpLegsNumber(myCharGenController.getPlayers().get(2));
				imgLegs.setText(Integer.toString(myCharGenController.getCurrentLegs(myCharGenController.getPlayers().get(2))));
			}
		});
		
		leftArrowLegs.setOnMouseClicked(e->{
			if(playerNumber.getText().equals("1")) {
				myCharGenController.DownLegsNumber(myCharGenController.getPlayers().get(0));
				imgLegs.setText(Integer.toString(myCharGenController.getCurrentLegs(myCharGenController.getPlayers().get(0))));
			}else if(playerNumber.getText().equals("2")) {
				myCharGenController.DownLegsNumber(myCharGenController.getPlayers().get(1));
				imgLegs.setText(Integer.toString(myCharGenController.getCurrentLegs(myCharGenController.getPlayers().get(1))));
			}else if(playerNumber.getText().equals("3")) {
				myCharGenController.DownLegsNumber(myCharGenController.getPlayers().get(2));
				imgLegs.setText(Integer.toString(myCharGenController.getCurrentLegs(myCharGenController.getPlayers().get(2))));
			}
		});
	}
}
