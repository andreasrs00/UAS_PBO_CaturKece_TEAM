package pieces;

import Main.Board;
import javafx.scene.image.Image;

public class Knight extends Piece {

    public Knight(Board board, int col, int row, boolean isWhite) {
        super(board, col, row, isWhite, "Knight", 3, isWhite ? 0 : 1); // Adjust sprite initialization as per your design
    }

    @Override
    public boolean isValidMovement(int col, int row) {
        return Math.abs(col - this.col) * Math.abs(row - this.row) == 2;
    }

    @Override
    public boolean moveCollidesWithPiece(int col, int row) {
        // Implementation as before
        return false;
    }
}
