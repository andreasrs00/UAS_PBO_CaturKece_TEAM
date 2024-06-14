package menu;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
        applyHoverTransition(neonButton1);
        applyHoverTransition(neonButton2);
        applyHoverTransition(neonButton3);
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
}
