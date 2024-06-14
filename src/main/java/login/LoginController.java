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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    @FXML
    private Label errorMessageLabel;

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/db_catur";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "password";

    public void initialize() {

        animateTextFill();
        errorMessageLabel.setVisible(false); // Sembunyikan label pesan error pada awalnya
        loginButton.setOnAction(event -> handleLogin());
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

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (isLoginValid(username, password)) {
            // Login berhasil
            errorMessageLabel.setVisible(false);
            System.out.println("Login berhasil");
            // Anda bisa melakukan navigasi ke scene berikutnya atau tindakan lainnya di sini
        } else {
            // Login gagal
            errorMessageLabel.setText("Username atau password salah");
            errorMessageLabel.setVisible(true);
            System.out.println("login gagal");
        }
    }

    private boolean isLoginValid(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Jika ada hasil dari query, berarti login berhasil
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
