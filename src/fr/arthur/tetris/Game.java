package fr.arthur.tetris;

import fr.arthur.tetris.pieces.Piece;

public class Game {

    public static Game INSTANCE;

    public Pieces currentPiece;

    public Game() {
        this.currentPiece = Piece.getRandomPiece();
    }

    public static Game getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Game();
        }
        return INSTANCE;
    }

    public Pieces getCurrentPiece() {
        return currentPiece;
    }

    public void start() {
        GameFrame gameFrame = new GameFrame();
        gameFrame.drawPiece(currentPiece);
    }
}
