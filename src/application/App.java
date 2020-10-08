package application;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App{
	
	private Stage stage;
	
	//Scene List
	protected VBox labSelect = new VBox();
	protected FlowPane game; 
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
	
	
	public App(Stage stage) {
		this.stage = stage;
		//initUI();
		initLabSelect();
	}
	
	public void initUI() {
		VBox vpane = new VBox();
		vpane.getChildren().addAll(charGenButton, playButton);
		//this.getChildren().add(vpane);
	}
	
	public void initLabSelect() {
		labBackButton.setOnMouseClicked(e->{
			/*TODO 1 : Nathan | Met le nom du Pane du menu*/
			//stage.setScene();
		});
		
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
		
		labSelect.setSpacing(25);
		labSelect.setPadding(new Insets(50,50,50,50));
		labSelect.setAlignment(Pos.CENTER);
		labSelect.getChildren().addAll(sceneSwitchBox,lineOne,lineTwo);
	}
}
