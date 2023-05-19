package fr.arthur.tetris;

import fr.arthur.tetris.pieces.Piece;
import fr.arthur.tetris.pieces.TileColor;

import java.awt.*;

public class Tiles {
    private Piece piece;
    private int x;
    private int y;
    private TileColor color;

    public Tiles(Piece piece, int x, int y) {
        this.piece = piece;
        this.x = x;
        this.y = y;
        this.color = piece.getPieceColor();
    }

    public Tiles(int x, int y, TileColor color) {
        this.piece = null;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public Color getColor() {
        return color.getColor();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int i) {
        this.y = i;
    }

    public void addY(int i) {
        this.y += i;
    }

    public void addX(int i) {
        this.x += i;
    }

    public void subX(int i) {
        this.x -= i;
    }

    public void subY(int i) {
        this.y -= i;
    }

    @Override
    public String toString() {
        return "Tiles{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
