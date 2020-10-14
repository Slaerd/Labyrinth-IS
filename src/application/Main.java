package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
    	App app = new App(primaryStage);
    	
        primaryStage.setTitle("A-maze-ing");
        primaryStage.setScene(app.mainMenuScene);
        primaryStage.show();
        
        //  
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}

