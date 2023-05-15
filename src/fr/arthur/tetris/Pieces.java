package fr.arthur.tetris;

import fr.arthur.tetris.pieces.Piece;

public class Pieces {
    public final String name;
    public final int[][] piece;
    public final int width;
    public final int height;
    public int x;
    public int y;

    public Pieces(String name, int[][] piece, int width, int height, int x, int y) {
        this.name = name;
        this.piece = piece;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public Pieces(Piece piece, int x, int y) {
        this.name = piece.getName();
        this.piece = piece.getPiece();
        this.width = piece.getWidth();
        this.height = piece.getHeight();
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public int[][] getPiece() {
        return piece;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
