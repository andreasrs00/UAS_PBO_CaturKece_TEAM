package pieces;

import Main.Board;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public abstract class Piece {

    protected int col, row;
    protected int xPos, yPos;
    protected boolean isWhite;
    protected String name;
    protected boolean isFirstMove = true;
    protected Image sprite;
    protected Board board;

    // Path gambar pieces.png
    private static final Image piecesImage = new Image("pieces.png");
    protected static final int ORIGINAL_TILE_SIZE = 425; // Ukuran asli dari tile di sprite sheet

    public Piece(Board board, int col, int row, boolean isWhite, String name, int spriteX, int spriteY) {
        this.board = board;
        this.col = col;
        this.row = row;
        this.xPos = col * board.getTileSize();
        this.yPos = row * board.getTileSize();
        this.isWhite = isWhite;
        this.name = name;
        this.sprite = getSubImage(spriteX, spriteY);
    }

    public abstract boolean isValidMovement(int col, int row);

    public abstract boolean moveCollidesWithPiece(int col, int row);

    public void paint(GraphicsContext g2d) {
        g2d.drawImage(sprite, xPos, yPos, board.getTileSize(), board.getTileSize()); // Draw the sprite using GraphicsContext, scaling to TILE_SIZE
    }

    public void setCol(int col) {
        this.col = col;
        this.xPos = col * board.getTileSize();
    }

    // Getters and Setters (only necessary ones shown)
    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public void setFirstMove(boolean isFirstMove) {
        this.isFirstMove = isFirstMove;
    }

    public Board getBoard() {
        return board;
    }

    public Image getSprite() {
        return sprite;
    }

    public String getName() {
        return this.name;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    public boolean isWhite() {
        return this.isWhite;
    }

    // Method to get a sub-image from the pieces.png based on the piece type and color
    private Image getSubImage(int xPos, int yPos) {
        return new WritableImage(piecesImage.getPixelReader(), xPos * ORIGINAL_TILE_SIZE, yPos * ORIGINAL_TILE_SIZE, ORIGINAL_TILE_SIZE, ORIGINAL_TILE_SIZE);
    }
}