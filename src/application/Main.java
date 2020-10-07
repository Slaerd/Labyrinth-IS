package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
    	 
        primaryStage.setTitle("File selector");
        primaryStage.setScene(new Scene(new VBox(), 300, 400));
        primaryStage.show();
        
        //  
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}

