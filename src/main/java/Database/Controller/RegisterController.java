package Database.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button registerButton;

    @FXML
    private Label registerStatusLabel;

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/db_catur";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "password";

    public void initialize() {
        registerStatusLabel.setVisible(false);
        registerButton.setOnAction(event -> handleRegister());
    }

    private void handleRegister() {
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (password.equals(confirmPassword)) {
            if (registerUser(email, username, password)) {
                registerStatusLabel.setTextFill(Color.GREEN);
                registerStatusLabel.setText("Registration successful!");
            } else {
                registerStatusLabel.setTextFill(Color.RED);
                registerStatusLabel.setText("Registration failed. Please try again.");
            }
        } else {
            registerStatusLabel.setTextFill(Color.RED);
            registerStatusLabel.setText("Passwords do not match.");
        }
        registerStatusLabel.setVisible(true);
    }

    private boolean registerUser(String email, String username, String password) {
        String query = "INSERT INTO users (email, username, password) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
