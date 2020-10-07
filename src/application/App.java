package application;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class App extends HBox{

	//MainMenu widgets
	public Button charGenButton = new Button("Character Generation");
	public Button playButton = new Button("Play");
	public Button mazeCreationButton = new Button("Maze creation");
	public Button closeButton = new Button("Close");
	
	public App() {
		initUI();
	}
	
	public void initUI() {
		VBox vpane = new VBox();
		vpane.getChildren().addAll(charGenButton, playButton);
		this.getChildren().add(vpane);
	}
}
