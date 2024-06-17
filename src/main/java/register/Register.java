package register;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Register extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Pastikan path ini benar
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/register/register.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Login and Signup");

            Scene scene = new Scene(root,960,720);
            scene.getStylesheets().add(getClass().getResource("/register/styles.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
