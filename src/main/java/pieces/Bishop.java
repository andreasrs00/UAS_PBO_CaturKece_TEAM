package pieces;

import Main.Board;

public class Bishop extends Piece {

    public Bishop(Board board, int col, int row, boolean isWhite) {
        super(board, col, row, isWhite, "Bishop", 2, isWhite ? 0 : 1); // Adjust sprite initialization as per your design
    }

    @Override
    public boolean isValidMovement(int col, int row) {
        return Math.abs(this.col - col) == Math.abs(this.row - row);
    }

    public boolean moveCollidesWithPiece(int col, int row) {

        //atas kiri
        if (this.col > col && this.row > row)
            for (int i = 1; i < Math.abs(this.col - col); i++)
                if (board.getPiece(this.col - i, this.row - i) != null)
                    return true;
        //atas kanan
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


        return false;
    }
}
