package Main;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class TimerPanel extends BorderPane {

    private Label whiteTimerLabel;
    private Label blackTimerLabel;

    public TimerPanel() {
        this.setStyle("-fx-background-color: lightgray;");
        this.setPrefHeight(50);

        whiteTimerLabel = createTimerLabel();
        blackTimerLabel = createTimerLabel();

        HBox hbox = new HBox(20, whiteTimerLabel, blackTimerLabel);
        hbox.setAlignment(Pos.CENTER);
        this.setCenter(hbox);
    }

    private Label createTimerLabel() {
        Label label = new Label("10:00");
        label.setFont(Font.font("Arial", 18));
        return label;
    }

    public void updateWhiteTimer(String time) {
        whiteTimerLabel.setText(time);
    }

    public void updateBlackTimer(String time) {
        blackTimerLabel.setText(time);
    }
}
