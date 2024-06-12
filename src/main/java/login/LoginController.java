package login;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label forgotPasswordLabel;

    @FXML
    private Label libraryLabel;

    @FXML
    private Label loginLabelKata;

    @FXML
    private Label loginLabel;

    @FXML
    private Label caturLabel;

    public void initialize() {
        animateTextFill();
    }

    private void animateTextFill() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(caturLabel.textFillProperty(), Color.RED)),
                new KeyFrame(Duration.seconds(1), new KeyValue(caturLabel.textFillProperty(), Color.GREEN)),
                new KeyFrame(Duration.seconds(2), new KeyValue(caturLabel.textFillProperty(), Color.BLUE)),
                new KeyFrame(Duration.seconds(3), new KeyValue(caturLabel.textFillProperty(), Color.RED))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
