package application;


import controller.CharGenController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;
import controller.*;
import view.TileButton;
import model.CharGenModel;

public class App{
	
	private Stage primaryStage;
	private CharGenController myCharGenController = new CharGenController();
	private CharGenModel myCharGenModel;
	
	
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
	
	//labSelect widgets
	private Button labBackButton = new Button("Back");
	private Button charGenLabButton = new Button("Character creation");
	
	//Game stuff
	private GameController gameController;
	
	private Button labSquare = new Button();
	private Button labRectangle = new Button();
	private Button labCross = new Button();
	private Label labelSquare = new Label("The Square");
	private Label labelRectangle = new Label("The Rectangle");
	private Label labelCross = new Label("The Cross");
	
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
	private Button playerNumber = new Button("1");
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
	
	public App(Stage stage) {
		this.primaryStage = stage;
		gameController = new GameController();
		gameController.addModel(new GameModel());
		
		//initUI();
		//initLabSelect();
		//initGameUI();
		
		
		this.primaryStage = stage;
		initMainMenu();
		initLabSelect();
		initCharGen();
	}
	
	public void initMainMenu() {
		mainMenuPane = new VBox();
		mainMenuPane.getChildren().addAll(playButton, charGenMenuButton, mazeCreationButton, closeButton);
		mainMenuScene = new Scene(mainMenuPane);
		
		playButton.setOnMouseClicked(e->{
			primaryStage.setScene(labSelectScene);
		});
		
		closeButton.setOnMouseClicked(e->{
			Platform.exit();
		});
		charGenMenuButton.setOnMouseClicked(e->{
			myCharGenModel = new CharGenModel();
			primaryStage.setScene(charGenScene);
		});
		
	}
	
	private void initLabSelect() {
		labBackButton.setOnMouseClicked(e->{
			primaryStage.setScene(mainMenuScene);
		});
		
		charGenLabButton.setOnMouseClicked(e->{
			primaryStage.setScene(charGenScene);
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
	
	/*
	 * private void initGameUI() { BorderPane root = new BorderPane(); GridPane laby
	 * = new GridPane();
	 * 
	 * TileButton buffer; for(int i = 0; i < Laby.SIZE; i++) { for(int j = 0; j <
	 * Laby.SIZE; j++) { buffer = new TileButton(i, j, gameController);
	 * laby.add(buffer, i, j); } }
	 * 
	 * root.setCenter(laby); gameScene = new Scene(root, 1000,600);
	 * labSelect.setSpacing(25); labSelect.setPadding(new Insets(50,50,50,50));
	 * labSelect.setAlignment(Pos.CENTER);
	 * //labSelect.getChildren().addAll(sceneSwitchBox,lineOne,lineTwo);
	 * 
	 * labSelectScene = new Scene(labSelect); }
	 */
	
	public void initCharGen() {

		myCharGenModel = new CharGenModel();

		imgHead.setPrefWidth(60);
		imgBody.setPrefWidth(60);
		imgLegs.setPrefWidth(60);
		
		headButtons.getChildren().addAll(leftArrowHead, imgHead, rightArrowHead);
		bodyButtons.getChildren().addAll(leftArrowBody, imgBody, rightArrowBody);
		legsButtons.getChildren().addAll(leftArrowLegs, imgLegs, rightArrowLegs);
		characterDisplay.getChildren().addAll(headButtons, bodyButtons, legsButtons);
		playerNumberVBox.getChildren().addAll(playerNumber, playerNumberL);
		colorPlusPlayer.getChildren().addAll(colorPicker, playerNumberVBox);
		pickersPlusDone.getChildren().addAll(colorPlusPlayer, doneButton);
		charGenRoot.getChildren().addAll(characterDisplay, pickersPlusDone);
		
		charGenScene = new Scene(charGenRoot);
		
		doneButton.setOnMouseClicked(e->{
			primaryStage.setScene(mainMenuScene);
		});
		
		playerNumber.setOnMouseClicked(e->{
			myCharGenController.changePlayerNumber();
			playerNumber.setText(myCharGenController.getCurrentPlayer());
		});
		
		rightArrowHead.setOnMouseClicked(e->{
			myCharGenController.UpHeadNumber();
			imgHead.setText(Integer.toString(myCharGenController.getCurrentHead()));
		});
		
		leftArrowHead.setOnMouseClicked(e->{
			myCharGenController.DownHeadNumber();
			imgHead.setText(Integer.toString(myCharGenController.getCurrentHead()));
		});
		rightArrowBody.setOnMouseClicked(e->{
			myCharGenController.UpBodyNumber();
			imgBody.setText(Integer.toString(myCharGenController.getCurrentBody()));
		});
		
		leftArrowBody.setOnMouseClicked(e->{
			myCharGenController.DownBodyNumber();
			imgBody.setText(Integer.toString(myCharGenController.getCurrentBody()));
		});
		rightArrowLegs.setOnMouseClicked(e->{
			myCharGenController.UpLegsNumber();
			imgLegs.setText(Integer.toString(myCharGenController.getCurrentLegs()));
		});
		
		leftArrowLegs.setOnMouseClicked(e->{
			myCharGenController.DownLegsNumber();
			imgLegs.setText(Integer.toString(myCharGenController.getCurrentLegs()));
		});
	}
	
}
