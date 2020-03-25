package Program3GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Class that starts the gui. When application run, this is the class that is run and contains the main function.
 * Created as part of intellij template for fx projects.
 *
 * @author Rizwan Chowdhury (Edited)
 * @author Tin Fung (Edited)
 */
public class Main extends Application {

    /**
     * starts the gui. note this function was edited by Rizwan and Tin not created by them
     * @param primaryStage the main stage that will be shown to user
     * @throws Exception throws I/O exception if fxml file not found
     * @author Rizwan Chowdhury (edited)
     * @author Tin Fung (edited)
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Program3GUI.fxml"));
        primaryStage.setTitle("Tuition Manager");
        Scene primaryScene = new Scene(root, 500, 400);
        primaryScene.getStylesheets().add(getClass().getResource("Program3GUI.css").toExternalForm());
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }


    /**
     * main function that will run the entire program by triggering start function.
     * note this function was edited by Rizwan and Tin not created by them.
     * @param args arguments passed into the main function
     * @author Rizwan Chowdhury (edited)
     * @author Tin Fung (edited)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
