package Setting;

import Database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import login.LoginController;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SettingController implements Initializable {
    @FXML
    private ComboBox<String> modeComboBox;

    @FXML
    private ComboBox<String> difficultyComboBox;

    @FXML
    private ComboBox<Integer> gameTimeComboBox;

    @FXML
    private ComboBox<String> themeComboBox;

    @FXML
    private Slider soundEffectSlider;

    @FXML
    private Slider musicSlider;

    @FXML
    private Button saveButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button muteSoundEffectButton;

    @FXML
    private Button muteMusicButton;

    @FXML
    private Label statusLabel;

    private int player_id;

    // Player for click sound
    private MediaPlayer clickSoundPlayer;
    private MediaPlayer musicPlayer;
    private boolean soundEffectMuted = false;
    private boolean musicMuted = false;

    SettingMain settingMain = new SettingMain();

    public void TakeId(){
        LoginController login = new LoginController();
        String username = "dewakipas";
        String password = "dewakipas";

        if (login.authenticate(username, password)) {
            player_id = login.getUserId();
            System.out.println("Player ID: " + player_id);

        } else {
            System.out.println("Authentication failed.");
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TakeId();
        // Initialize ComboBoxes
        modeComboBox.getItems().addAll("Against Computer", "Two Players");
        difficultyComboBox.getItems().addAll("Easy", "Medium", "Hard");
        gameTimeComboBox.getItems().addAll(6,15);
        themeComboBox.getItems().addAll("Classic", "Retro", "Modern");

        // Set default values
        modeComboBox.setValue("Against Computer");
        difficultyComboBox.setValue("Medium");
        gameTimeComboBox.setValue(15);
        themeComboBox.setValue("Classic");

        // Initialize Sliders
        soundEffectSlider.setValue(50);
        musicSlider.setValue(50);

        // Load click sound
        try {
            URL clickSoundUrl = getClass().getResource("/Setting/sound effect.wav");
            if (clickSoundUrl != null) {
                Media clickSound = new Media(clickSoundUrl.toExternalForm());
                clickSoundPlayer = new MediaPlayer(clickSound);
            } else {
                System.err.println("File sound effect.wav not found");
            }

            // Initialize music player
            URL musicUrl = getClass().getResource("/Setting/music.mp3");
            if (musicUrl != null) {
                Media music = new Media(musicUrl.toExternalForm());
                musicPlayer = new MediaPlayer(music);
                musicPlayer.setOnEndOfMedia(() -> musicPlayer.seek(Duration.ZERO));
                if (musicMuted) {
                    musicPlayer.setVolume(0);
                } else {
                    musicPlayer.setVolume(musicSlider.getValue() / 100.0);
                }
            } else {
                System.err.println("File music.mp3 not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set button actions with sound
        saveButton.setOnAction(this::handleSaveButton);
        exitButton.setOnAction(this::handleExitButton);
        muteSoundEffectButton.setOnAction(this::handleMuteSoundEffect);
        muteMusicButton.setOnAction(this::handleMuteMusic);

        // Set initial volume and start music
        if (musicPlayer != null) {
            musicPlayer.play();
        }

        // Play click sound on button press
        saveButton.setOnMousePressed(event -> playClickSound());
        exitButton.setOnMousePressed(event -> playClickSound());
        muteSoundEffectButton.setOnMousePressed(event -> playClickSound());
        muteMusicButton.setOnMousePressed(event -> playClickSound());

        // Handle music slider change
        musicSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (!musicMuted && musicPlayer != null) {
                musicPlayer.setVolume(newValue.intValue() / 100.0);
            }
        });
    }

    private void handleSaveButton(javafx.event.ActionEvent event) {
        System.out.println("Save button clicked");
        playClickSound();
        saveSettings();
    }

    private void handleExitButton(javafx.event.ActionEvent event) {
        System.out.println("Exit button clicked");
        playClickSound();

        // Mengambil stage saat ini
        Stage stage = (Stage) exitButton.getScene().getWindow();

        // Menutup stage (aplikasi) saat ini
        stage.close();
    }

    private void handleMuteSoundEffect(javafx.event.ActionEvent event) {
        System.out.println("Mute sound effect button clicked");
        playClickSound();
        soundEffectMuted = !soundEffectMuted; // Toggle mute state
        if (soundEffectMuted) {
            soundEffectSlider.setValue(0);
        } else {
            soundEffectSlider.setValue(50);
        }
        updateSoundEffectVolume();
    }

    private void handleMuteMusic(javafx.event.ActionEvent event) {
        System.out.println("Mute music button clicked");
        playClickSound();
        musicMuted = !musicMuted; // Toggle mute state
        if (musicPlayer != null) {
            if (musicMuted) {
                musicPlayer.setVolume(0);
            } else {
                musicPlayer.setVolume(musicSlider.getValue() / 100.0);
            }
        }
    }

    private void playClickSound() {
        if (clickSoundPlayer != null) {
            clickSoundPlayer.seek(clickSoundPlayer.getStartTime());
            clickSoundPlayer.play();
        }
    }

    private void saveSettings() {
        String mode = modeComboBox.getValue();
        String difficulty = difficultyComboBox.getValue();
        Integer gameTime = gameTimeComboBox.getValue();
        String theme = themeComboBox.getValue();
        double soundEffectVolume = soundEffectSlider.getValue();
        double musicVolume = musicSlider.getValue();

        System.out.println("Mode: " + mode);
        System.out.println("Difficulty: " + difficulty);
        System.out.println("Game Time: " + gameTime + " minute");
        System.out.println("Theme: " + theme);
        System.out.println("Sound Effect Volume: " + soundEffectVolume);
        System.out.println("Music Volume: " + musicVolume);
        System.out.println(player_id);
        boolean success = settingDatabase(player_id, mode, difficulty, gameTime, theme, soundEffectVolume, musicVolume);

        if (success) {
            System.out.println("Settings saved successfully");
        } else {
            System.out.println("Failed to save settings");
        }
    }

    private void updateSoundEffectVolume() {
        if (clickSoundPlayer != null) {
            if (soundEffectMuted) {
                clickSoundPlayer.setVolume(0);
            } else {
                clickSoundPlayer.setVolume(soundEffectSlider.getValue() / 100.0);
            }
        }
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    private boolean settingDatabase(int player_id, String mode, String difficulty, Integer gameTime, String theme, double soundEffectVolume, double musicVolume) {
        String query = "INSERT INTO setting (player_id, mode, difficulty, gameTime, theme, SoundEffectVolume, musicVolume) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, player_id); // Menggunakan player_id yang diatur di sini
            preparedStatement.setString(2, mode);
            preparedStatement.setString(3, difficulty);
            preparedStatement.setInt(4, gameTime);
            preparedStatement.setString(5, theme);
            preparedStatement.setDouble(6, soundEffectVolume);
            preparedStatement.setDouble(7, musicVolume);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
