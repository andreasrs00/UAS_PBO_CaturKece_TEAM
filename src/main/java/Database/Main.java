package Database;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Print the FXML resource path to debug
        System.out.println(getClass().getResource("/Register.fxml"));

        // Check if FXML file is found
        if (getClass().getResource("/Register.fxml") == null) {
            System.out.println("FXML file not found!");
            return;
        }

        // Load the FXML file
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/login/Register.fxml")));
        primaryStage.setTitle("User Authentication");

        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
