package Main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pieces.Piece;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Chess Game");
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(5));

        Board board = new Board();
        TimerPanel timerPanel = new TimerPanel();
//        Piece piece = new Piece(board, "file:pieces.png");
//        PlayerProfilePanel whitePlayerProfile = new PlayerProfilePanel("Player 1 (White)", new Image("path/to/white_player_avatar.png"));
//        PlayerProfilePanel blackPlayerProfile = new PlayerProfilePanel("Player 2 (Black)", new Image("path/to/black_player_avatar.png"));

        // Set up the layout
        root.setTop(timerPanel);
        root.setCenter(board);

        // Add profile panels to the sides
//        root.setLeft(whitePlayerProfile);
//        root.setRight(blackPlayerProfile);

        // Link the timerPanel to the board so it can update the timers
        board.setTimerPanel(timerPanel);

        primaryStage.setScene(new Scene(root, 700, 800));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
