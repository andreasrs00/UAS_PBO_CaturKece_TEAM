package Setting;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingController implements Initializable {

    @FXML
    private ComboBox<String> modeComboBox;

    @FXML
    private ComboBox<String> difficultyComboBox;

    @FXML
    private ComboBox<String> gameTimeComboBox;

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

    // Player for click sound
    private MediaPlayer clickSoundPlayer;
    private MediaPlayer musicPlayer;
    private boolean soundEffectMuted = false;
    private boolean musicMuted = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize ComboBoxes
        modeComboBox.getItems().addAll("Against Computer", "Two Players");
        difficultyComboBox.getItems().addAll("Easy", "Medium", "Hard");
        gameTimeComboBox.getItems().addAll("6 minutes", "15 minutes");
        themeComboBox.getItems().addAll("Classic", "Wooden", "Modern");

        // Set default values
        modeComboBox.setValue("Against Computer");
        difficultyComboBox.setValue("Medium");
        gameTimeComboBox.setValue("15 minutes");
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
        // Add exit functionality if needed
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
        String gameTime = gameTimeComboBox.getValue();
        String theme = themeComboBox.getValue();
        double soundEffectVolume = soundEffectSlider.getValue();
        double musicVolume = musicSlider.getValue();

        System.out.println("Mode: " + mode);
        System.out.println("Difficulty: " + difficulty);
        System.out.println("Game Time: " + gameTime);
        System.out.println("Theme: " + theme);
        System.out.println("Sound Effect Volume: " + soundEffectVolume);
        System.out.println("Music Volume: " + musicVolume);
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
}
