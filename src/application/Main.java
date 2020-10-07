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
    	Pane root = new Pane();
    	App app = new App();
    	root.getChildren().add(app);
    	
        primaryStage.setTitle("A-maze-ing");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
        //  
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}

