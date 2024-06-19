package login;

//import library javafx
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Setting.SettingController;

//import package
import menu.MenuApplication;
import Database.DatabaseConnection;

//java.sql
import java.sql.Connection;
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
    private Label errorMessageLabel;

    private int userId;
    private String username;
    private String password;

    public void initialize() {
        errorMessageLabel.setVisible(false); // Sembunyikan label pesan error pada awalnya
        loginButton.setOnAction(event -> handleLogin());
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (authenticate(username, password)) {
            errorMessageLabel.setVisible(false);
            System.out.println("Login berhasil");
            openMenuPage();
        } else {
            errorMessageLabel.setText("Username atau password salah");
            errorMessageLabel.setVisible(true);
            System.out.println("Login gagal");
        }
    }

    public boolean authenticate(String username, String password) {
        String query = "SELECT id FROM users WHERE username = ? AND password = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getInt("id");
                setUserId(userId);
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void openMenuPage() {
        try {
            MenuApplication menuApp = new MenuApplication();
            Stage menuStage = new Stage();
            menuApp.start(menuStage);

            Stage loginStage = (Stage) usernameField.getScene().getWindow();
            loginStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
