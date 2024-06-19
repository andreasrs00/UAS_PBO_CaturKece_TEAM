package Setting;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import login.LoginController;

import java.io.IOException;

public class  SettingMain extends Application {

    private int player_id;
    @Override
    public void start(Stage primaryStage) throws IOException {
        LoginController login = new LoginController();

//        System.out.println(player_id);

        // Contoh username dan password

        SettingController setting = new SettingController();
        setting.setPlayer_id(player_id);
        setPlayer_id(player_id);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Setting/Setting.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root,960,720);
        scene.getStylesheets().add(getClass().getResource("/Setting/Setting.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chess Game Settings");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public int getPlayerId(){
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }
}
