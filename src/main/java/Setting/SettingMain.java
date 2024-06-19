package Setting;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class  SettingMain extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Setting/Setting.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root,960,720);

        // Load CSS file if needed
        scene.getStylesheets().add(getClass().getResource("/Setting/Setting.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Chess Game Settings");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
