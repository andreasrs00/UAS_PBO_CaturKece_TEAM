package Main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import pieces.*;

import java.util.ArrayList;

public class Board extends Pane {

    private int enPassantCol;
    private int enPassantRow;

    private final int tileSize = 85;  // Ubah ukuran jika perlu sesuai dengan ukuran gambar bidak
    private final int cols = 8;
    private final int rows = 8;

    private final ArrayList<Piece> pieceList = new ArrayList<>();
    private Piece selectedPiece;

    private final Input input;
    public final CheckScanner checkScanner;

    public int enPassantTile = -1;
    private boolean isWhiteToMove = true;
    private boolean isGameOver = false;

    private Timeline whiteTimer;
    private Timeline blackTimer;
    private int whiteTimeRemaining = 600; // 10 minutes in seconds
    private int blackTimeRemaining = 600; // 10 minutes in seconds

    private TimerPanel timerPanel;
    private final Canvas canvas;

    public Board() {
        this.setPrefSize(cols * tileSize, rows * tileSize);

        canvas = new Canvas(cols * tileSize, rows * tileSize);
        this.getChildren().add(canvas);

        input = new Input(this);
        checkScanner = new CheckScanner(this);

        addPieces();
        initializeTimers();

        // Start the timer for white
        whiteTimer.play();
    }

    public void setTimerPanel(TimerPanel timerPanel) {
        this.timerPanel = timerPanel;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public int getTileSize() {
        return tileSize;
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public void setSelectedPiece(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
    }

    public Piece getPiece(int col, int row) {
        for (Piece piece : pieceList) {
            if (piece.getCol() == col && piece.getRow() == row) {
                return piece;
            }
        }
        return null;
    }

    public ArrayList<Piece> getPieceList() {
        return pieceList;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public void makeMove(Move move) {
        if (move.getPiece().getName().equals("Pawn")) {
            movePawn(move);
        } else if (move.getPiece().getName().equals("King")) {
            moveKing(move);
        }

        // Update the piece's position
        move.getPiece().setCol(move.getNewCol());
        move.getPiece().setRow(move.getNewRow());
        move.getPiece().setXPos(move.getNewCol() * tileSize);
        move.getPiece().setYPos(move.getNewRow() * tileSize);
        move.getPiece().setFirstMove(false);

        // Capture the opponent's piece if applicable
        capture(move.getCapture());

        // Toggle turn
        isWhiteToMove = !isWhiteToMove;

        // Switch timers
        if (isWhiteToMove) {
            blackTimer.stop();
            whiteTimer.play();
        } else {
            whiteTimer.stop();
            blackTimer.play();
        }

        // Update game state
        updateGameState();
        draw();
    }

    private void moveKing(Move move) {
        if (Math.abs(move.getPiece().getCol() - move.getNewCol()) == 2) {
            Piece rook;
            if (move.getPiece().getCol() < move.getNewCol()) {
                rook = getPiece(7, move.getPiece().getRow());
                rook.setCol(5);
            } else {
                rook = getPiece(0, move.getPiece().getRow());
                rook.setCol(3);
            }
            rook.setXPos(rook.getCol() * tileSize);
        }
    }

    private void movePawn(Move move) {
        int colorIndex = move.getPiece().isWhite() ? 1 : -1;

        if (getTileNum(move.getNewCol(), move.getNewRow()) == enPassantTile) {
            move.setCapture(getPiece(move.getNewCol(), move.getNewRow() + colorIndex));
        }
        if (Math.abs(move.getPiece().getRow() - move.getNewRow()) == 2) {
            enPassantTile = getTileNum(move.getNewCol(), move.getNewRow() + colorIndex);
        } else {
            enPassantTile = -1;
        }

        colorIndex = move.getPiece().isWhite() ? 0 : 7;
        if (move.getNewRow() == colorIndex) {
            promotePawn(move);
        }
    }

    private void promotePawn(Move move) {
        pieceList.add(new Queen(this, move.getNewCol(), move.getNewRow(), move.getPiece().isWhite()));
        capture(move.getPiece());
    }

    public void capture(Piece piece) {
        pieceList.remove(piece);
    }

    public boolean isValidMove(Move move) {
        if (isGameOver) {
            return false;
        }
        if (move.getPiece().isWhite() != isWhiteToMove) {
            return false;
        }
        if (sameTeam(move.getPiece(), move.getCapture())) {
            return false;
        }
        if (!move.getPiece().isValidMovement(move.getNewCol(), move.getNewRow())) {
            return false;
        }
        if (move.getPiece().moveCollidesWithPiece(move.getNewCol(), move.getNewRow())) {
            return false;
        }
        if (checkScanner.isKingChecked(move)) {
            return false;
        }
        return true;
    }

    public boolean sameTeam(Piece p1, Piece p2) {
        if (p1 == null || p2 == null) {
            return false;
        }
        return p1.isWhite() == p2.isWhite();
    }

    public int getTileNum(int col, int row) {
        return row * rows + col;
    }

    public Piece findKing(boolean isWhite) {
        for (Piece piece : pieceList) {
            if (isWhite == piece.isWhite() && piece.getName().equals("King")) {
                return piece;
            }
        }
        return null;
    }

    public void addPieces() {
        // Adding white pieces
        pieceList.add(new Rook(this, 0, 7, true));
        pieceList.add(new Knight(this, 1, 7, true));
        pieceList.add(new Bishop(this, 2, 7, true));
        pieceList.add(new Queen(this, 3, 7, true));
        pieceList.add(new King(this, 4, 7, true));
        pieceList.add(new Bishop(this, 5, 7, true));
        pieceList.add(new Knight(this, 6, 7, true));
        pieceList.add(new Rook(this, 7, 7, true));

        for (int i = 0; i < 8; i++) {
            pieceList.add(new Pawn(this, i, 6, true));
        }


        // Adding black pieces
        pieceList.add(new Rook(this, 0, 0, false));
        pieceList.add(new Knight(this, 1, 0, false));
        pieceList.add(new Bishop(this, 2, 0, false));
        pieceList.add(new Queen(this, 3, 0, false));
        pieceList.add(new King(this, 4, 0, false));
        pieceList.add(new Bishop(this, 5, 0, false));
        pieceList.add(new Knight(this, 6, 0, false));
        pieceList.add(new Rook(this, 7, 0, false));

        for (int i = 0; i < 8; i++) {
            pieceList.add(new Pawn(this, i, 1, false));
        }

        draw();
    }

    public int getEnPassantCol() {
        return enPassantCol;
    }

    public int getEnPassantRow() {
        return enPassantRow;
    }

    public void setEnPassantTile(int col, int row) {
        this.enPassantCol = col;
        this.enPassantRow = row;
    }

    public void clearEnPassantTile() {
        this.enPassantCol = -1;
        this.enPassantRow = -1;
    }

    private void updateGameState() {
        Piece king = findKing(isWhiteToMove);
        if (checkScanner.isGameOver(king)) {
            if (checkScanner.isKingChecked(new Move(this, king, king.getCol(), king.getRow()))) {
                System.out.println(isWhiteToMove ? "Black Wins!" : "White Wins!");
            } else {
                System.out.println("Stalemate!");
            }
            isGameOver = true;
            whiteTimer.stop();
            blackTimer.stop();
        }
    }

    private void draw() {
        GraphicsContext g2d = canvas.getGraphicsContext2D();
        g2d.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw board
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                g2d.setFill((c + r) % 2 == 0 ? Color.rgb(227, 198, 181) : Color.rgb(157, 105, 53));
                g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
            }
        }

        // Highlight valid moves
        if (selectedPiece != null) {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (isValidMove(new Move(this, selectedPiece, c, r))) {
                        g2d.setFill(Color.rgb(68, 180, 57, 0.75));
                        g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
                    }
                }
            }
        }

        // Draw pieces
        for (Piece piece : pieceList) {
            piece.paint(g2d);
        }
    }

    private void initializeTimers() {
        whiteTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (isWhiteToMove && !isGameOver) {
                whiteTimeRemaining--;
                if (whiteTimeRemaining <= 0) {
                    isGameOver = true;
                    whiteTimer.stop();
                    blackTimer.stop();
                    System.out.println("Black Wins on Time!");
                }
                if (timerPanel != null) {
                    timerPanel.updateWhiteTimer(formatTime(whiteTimeRemaining));
                }
            }
        }));
        whiteTimer.setCycleCount(Timeline.INDEFINITE);

        blackTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (!isWhiteToMove && !isGameOver) {
                blackTimeRemaining--;
                if (blackTimeRemaining <= 0) {
                    isGameOver = true;
                    whiteTimer.stop();
                    blackTimer.stop();
                    System.out.println("White Wins on Time!");
                }
                if (timerPanel != null) {
                    timerPanel.updateBlackTimer(formatTime(blackTimeRemaining));
                }
            }
        }));
        blackTimer.setCycleCount(Timeline.INDEFINITE);
    }

    private String formatTime(int seconds) {
        int mins = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", mins, secs);
    }

    public void repaint() {
        draw();
    }
}
