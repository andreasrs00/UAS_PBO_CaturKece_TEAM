package Main;

import pieces.Piece;

public class Move {

    private final int oldCol;
    private final int oldRow;
    private final int newCol;
    private final int newRow;

    private final Piece piece;
    private Piece capture;

    public Move(Board board, Piece piece, int newCol, int newRow) {
        this.oldCol = piece.getCol();
        this.oldRow = piece.getRow();
        this.newCol = newCol;
        this.newRow = newRow;

        this.piece = piece;
        this.capture = board.getPiece(newCol, newRow);
    }

    public int getOldCol() {
        return oldCol;
    }

    public int getOldRow() {
        return oldRow;
    }

    public int getNewCol() {
        return newCol;
    }

    public int getNewRow() {
        return newRow;
    }

    public void setCapture(Piece capture) {
        this.capture = capture;
    }

    public Piece getPiece() {
        return piece;
    }

    public Piece getCapture() {
        return capture;
    }
}
