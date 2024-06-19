package pieces;

import Main.Board;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Pawn extends Piece {

    public Pawn(Board board, int col, int row, boolean isWhite) {
        super(board, col, row, isWhite, "Pawn", 5, isWhite ? 0 : 1);// Ensure the path is correct
    }

    @Override
    public boolean isValidMovement(int col, int row) {
        int colorIndex = isWhite ? 1 : -1;

        // Move pawn 1 step
        if (this.col == col && row == this.row - colorIndex && board.getPiece(col, row) == null)
            return true;

        // Move pawn 2 steps on first move
        if (isFirstMove && this.col == col && row == this.row - colorIndex * 2 && board.getPiece(col, row) == null && board.getPiece(col, row + colorIndex) == null)
            return true;

        // Capture left
        if (col == this.col - 1 && row == this.row - colorIndex && board.getPiece(col, row) != null)
            return true;

        // Capture right
        if (col == this.col + 1 && row == this.row - colorIndex && board.getPiece(col, row) != null)
            return true;

        // En passant left
        if (board.getTileNum(col, row) == board.enPassantTile && col == this.col - 1 && row == this.row - colorIndex && board.getPiece(col, row + colorIndex) != null) {
            return true;
        }

        // En passant right
        if (board.getTileNum(col, row) == board.enPassantTile && col == this.col + 1 && row == this.row - colorIndex && board.getPiece(col, row + colorIndex) != null) {
            return true;
        }

        return false;
    }

    @Override
    public boolean moveCollidesWithPiece(int col, int row) {
        // Implement collision detection logic if necessary
        return false;
    }
}