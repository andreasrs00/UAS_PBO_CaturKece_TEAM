package pieces;

import Main.Board;
import javafx.scene.image.Image;

public class Queen extends Piece {

    public Queen(Board board, int col, int row, boolean isWhite) {
        super(board, col, row, isWhite, "Queen", 1, isWhite ? 0 : 1); // Adjust sprite initialization as per your design
    }

    @Override
    public boolean isValidMovement(int col, int row) {
        return this.col == col || this.row == row || Math.abs(this.col - col) == Math.abs(this.row - row);
    }

    public boolean moveCollidesWithPiece(int col, int row) {
        if (this.col == col || this.row == row) {
            //kiri
            if (this.col > col)
                for (int c = this.col - 1; c > col; c--)
                    if (board.getPiece(c, this.row) != null)
                        return true;
            //kanan
            if (this.col < col)
                for (int c = this.col + 1; c < col; c++)
                    if (board.getPiece(c, this.row) != null)
                        return true;
            //atas
            if (this.row > row)
                for (int r = this.row - 1; r > row; r--)
                    if (board.getPiece(this.col, r) != null)
                        return true;
            //bawah
            if (this.row < row)
                for (int r = this.row + 1; r < row; r++)
                    if (board.getPiece(this.col, r) != null)
                        return true;
        } else {
            //atas kanan
            if (this.col > col && this.row > row)
                for (int i = 1; i < Math.abs(this.col - col); i++)
                    if (board.getPiece(this.col - i, this.row - i) != null)
                        return true;
            //atas kiri
            if (this.col < col && this.row > row)
                for (int i = 1; i < Math.abs(this.col - col); i++)
                    if (board.getPiece(this.col + i, this.row - i) != null)
                        return true;

            //bawah kiri
            if (this.col > col && this.row < row)
                for (int i = 1; i < Math.abs(this.col - col); i++)
                    if (board.getPiece(this.col - i, this.row + i) != null)
                        return true;
            //bawah kanan
            if (this.col < col && this.row < row)
                for (int i = 1; i < Math.abs(this.col - col); i++)
                    if (board.getPiece(this.col + i, this.row + i) != null)
                        return true;

        }
        return false;
    }
}



