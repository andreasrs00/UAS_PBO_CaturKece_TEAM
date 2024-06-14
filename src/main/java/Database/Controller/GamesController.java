package Database.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.sql.*;

public class GamesController {

    @FXML
    private TextField startTimeField;

    @FXML
    private TextField endTimeField;

    @FXML
    private TextField gameStatusField;

    @FXML
    private Button createButton;

    @FXML
    private void initialize() {
        // Set initial values or perform any other initialization
    }

    @FXML
    private void createGame() {
        String startTime = startTimeField.getText();
        String endTime = endTimeField.getText();
        String gameStatus = gameStatusField.getText();

        // Simpan game ke database
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db_catur", "username", "password");
            PreparedStatement statement = conn.prepareStatement("INSERT INTO games (startTime, endTime, gameStatus) VALUES (?, ?, ?)");
            statement.setString(1, startTime);
            statement.setString(2, endTime);
            statement.setString(3, gameStatus);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                showSuccessMessage("Game created successfully!");
            } else {
                showErrorMessage("Failed to create game!");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorMessage("Database error occurred!");
        }
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

