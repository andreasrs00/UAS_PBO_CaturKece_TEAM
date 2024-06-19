package menu;

import Setting.SettingMain;
import Main.Main;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuController {

    @FXML
    private Button neonButton1;

    @FXML
    private Button neonButton2;

    @FXML
    private Button neonButton3;

    @FXML
    public void initialize() {
        // Apply hover transition only to neonButton2
        applyHoverTransition(neonButton2);

        // Set action for neonButton2
        neonButton2.setOnAction(event -> {
            // Open SettingMain
            openSettingMain();
        });

        // Set action for neonButton1
        neonButton1.setOnAction(event -> {
            // Open Main application
            openMain();
        });
    }

    private void applyHoverTransition(Button button) {
        button.setOnMouseEntered(event -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(400), button);
            scaleTransition.setToX(1.05);
            scaleTransition.setToY(1.05);
            scaleTransition.play();
        });

        button.setOnMouseExited(event -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(400), button);
            scaleTransition.setToX(1.0);
            scaleTransition.setToY(1.0);
            scaleTransition.play();
        });
    }

    private void openSettingMain() {
        // Launch SettingMain application
        SettingMain settingMain = new SettingMain();
        Stage stage = new Stage();
        try {
            settingMain.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openMain() {
        // Launch Main application
        Main mainApp = new Main();
        Stage stage = new Stage();
        try {
            mainApp.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
