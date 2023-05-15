package fr.arthur.tetris.pieces;


import fr.arthur.tetris.Pieces;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public enum Piece {

    I("I", new int[][]{
            //Flipped
            {0, 1, 0, 0},
            {0, 1, 0, 0},
            {0, 1, 0, 0},
            {0, 1, 0, 0}
    }),

    J("J", new int[][]{
            {0, 0, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 1, 0},
            {0, 1, 1, 0}
    }),

    L("L", new int[][]{
            {0, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 1, 0, 0},
            {0, 1, 1, 0}
    }),

    O("O", new int[][]{
            {0, 0, 0, 0},
            {0, 1, 1, 0},
            {0, 1, 1, 0},
            {0, 0, 0, 0}
    }),

    S("S", new int[][]{
            {0, 0, 0, 0},
            {0, 1, 1, 0},
            {1, 1, 0, 0},
            {0, 0, 0, 0}
    }),

    T("T", new int[][]{
            {0, 0, 0, 0},
            {1, 1, 1, 0},
            {0, 1, 0, 0},
            {0, 0, 0, 0}
    }),

    Z("Z", new int[][]{
            {0, 0, 0, 0},
            {1, 1, 0, 0},
            {0, 1, 1, 0},
            {0, 0, 0, 0}
    });

    public static final int SIZE = 4;

    public final String name;
    public final int[][] piece;
    public final int width;
    public final int height;


    Piece(String name, int[][] piece) {
        BufferedImage tempImage;
        this.name = name;
        this.piece = piece;
        this.width = getPieceWidth();
        this.height = getPieceHeight();
    }

    public static Pieces getRandomPiece() {
        return new Pieces(values()[(int) (Math.random() * values().length)], 0, 0);
    }

    private int getPieceHeight() {
        int height = 0;
        for (int i = 0; i < SIZE; i++) {
            int tempHeight = 0;
            for (int j = 0; j < SIZE; j++) {
                if (piece[j][i] == 1) {
                    tempHeight++;
                }
            }
            if (tempHeight > height) {
                height = tempHeight;
            }
        }
        return height;
    }

    private int getPieceWidth() {
        int width = 0;
        for (int i = 0; i < SIZE; i++) {
            int tempWidth = 0;
            for (int j = 0; j < SIZE; j++) {
                if (piece[i][j] == 1) {
                    tempWidth++;
                }
            }
            if (tempWidth > width) {
                width = tempWidth;
            }
        }
        return width;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[][] getPiece() {
        return piece;
    }

    public static Piece getPieceFromName(String name) {
        for (Piece piece : Piece.values()) {
            if (piece.getName().equals(name)) {
                return piece;
            }
        }
        return null;
    }
}
