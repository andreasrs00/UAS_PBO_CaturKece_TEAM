package login;

//import library javafx
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

//import package
import menu.MenuApplication;
import Database.DatabaseConnection;


//java.sql
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



    public void initialize() {

        errorMessageLabel.setVisible(false); // Sembunyikan label pesan error pada awalnya
        loginButton.setOnAction(event -> handleLogin());
    }





    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (isLoginValid(username, password)) {
            errorMessageLabel.setVisible(false);
            System.out.println("Login berhasil");
            openMenuPage();



        } else {
            errorMessageLabel.setText("Username atau password salah");
            errorMessageLabel.setVisible(true);
            System.out.println("login gagal");
            System.out.println("Username atau password salah");
        }
    }

    private void openMenuPage() {
        try {


            MenuApplication menuApp = new MenuApplication();
            Stage menuStage = new Stage();
            menuApp.start(menuStage);
            System.out.println(menuStage);

            Stage loginStage = (Stage) usernameField.getScene().getWindow();

            loginStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        }


        private boolean isLoginValid(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
