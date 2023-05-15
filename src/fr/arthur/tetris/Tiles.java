package fr.arthur.tetris;

import fr.arthur.tetris.pieces.Piece;

public class Tiles {
    private Piece piece;
    private int x;
    private int y;

    public Tiles(Piece piece, int x, int y) {
        this.piece = piece;
        this.x = x;
        this.y = y;
    }

    public Tiles(int x, int y) {
        this.piece = null;
        this.x = x;
        this.y = y;
    }
}
