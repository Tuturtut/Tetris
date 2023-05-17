package fr.arthur.tetris;

import fr.arthur.tetris.pieces.Piece;
import fr.arthur.tetris.pieces.TileColor;

import java.awt.*;
import java.util.ArrayList;

public class Pieces {
    private final String name;
    private final Piece piece;
    private int width;
    private int height;
    public int x;
    public int y;
    public int bottomY;
    public TileColor color;
    public ArrayList<Tiles> tiles = new ArrayList<>();
    public int spawnY;


    public Pieces(Piece piece, int x, int y) {
        this.name = piece.getName();
        this.piece = piece;
        this.width = piece.getWidth();
        this.height = piece.getHeight();
        this.x = x;
        this.y = y;
        this.color = piece.getPieceColor();
        this.bottomY = y + height;
        this.spawnY = piece.getSpawnY();

        addTiles();
    }



    public Pieces(Piece value) {
        this(value, Grid.X_START, Grid.Y_START - value.getSpawnY());
    }

    private void addTiles() {
        int[][] piece = this.piece.getCurrentPiece();
        // Ajoute les Tiles dans l'ArrayList en gardant le sens de la pièce
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[i].length; j++) {
                if (piece[i][j] == 1) {
                    tiles.add(new Tiles(this.piece, x + j, y + i));
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public int[][] getPiece() {
        return piece.getCurrentPiece();
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

    public int getBottomY() {
        return bottomY;
    }

    public TileColor getPieceColor() {
        return color;
    }

    public Color getColor() {
        return color.getColor();
    }

    public ArrayList<Tiles> getTiles() {
        return tiles;
    }

    public void addX(int i) {
        this.x += i;
        for (Tiles tile : tiles) {
            tile.addX(i);
        }
    }

    public void subX(int i) {
        this.x -= i;
        for (Tiles tile : tiles) {
            tile.subX(i);
        }
    }

    public void addY(int i) {
        this.y += i;
        this.bottomY += i;
        for (Tiles tile : tiles) {
            tile.addY(i);
        }
    }


    public void setY(int newY) {
        int deltaY = newY - y; // Calculer la différence entre la nouvelle valeur et l'ancienne valeur de y

        // Mettre à jour les propriétés
        y = newY;
        bottomY = bottomY + deltaY;

        // Mettre à jour les coordonnées des tuiles
        for (Tiles tile : tiles) {
            tile.addY(deltaY);
        }
    }

    public void clockWiseRotation() {

        System.out.println("clockWiseRotation");

        int[][] rotatedPiece = this.piece.getNextClockwiseRotation();
        int newWidth = rotatedPiece[0].length;
        int newHeight = rotatedPiece.length;
        int newX = x - (newWidth - width) / 2;
        int newY = y - (newHeight - height) / 2;

        if (Grid.getInstance().checkRotationCollision(this, newX, newY, newWidth, newHeight)) {
            return; // La rotation entraîne une collision, on ne fait rien
        }

        this.piece.rotateClockwise();
        this.width = newWidth;
        this.height = newHeight;
        this.x = newX;
        this.y = newY;
        this.bottomY = newY + height;

        tiles.clear();
        addTiles();
    }


    public void counterClockWiseRotation() {

        System.out.println("counterClockWiseRotation");

        int[][] rotatedPiece = this.piece.getNextCounterClockwiseRotation();
        int newWidth = rotatedPiece[0].length;
        int newHeight = rotatedPiece.length;
        int newX = x - (newWidth - width) / 2;
        int newY = y - (newHeight - height) / 2;

        // Appel d'une méthode de Grid pour vérifier si la rotation est possible
        if (Grid.getInstance().checkRotationCollision(this, newX, newY, newWidth, newHeight)) {
            return; // La rotation entraîne une collision, on ne fait rien
        }


        this.piece.rotateCounterClockwise();
        this.width = newWidth;
        this.height = newHeight;
        this.x = newX;
        this.y = newY;
        this.bottomY = newY + height;

        tiles.clear();
        addTiles();
    }


    public void moveLeft() {
        subX(1);
        if (Grid.getInstance().checkLateralGridCollision(this)) {
            addX(1); // Annule le mouvement s'il y a une collision
        } else if (Grid.getInstance().checkTileCollision(this)){
            addX(1);
        }

    }

    public void moveRight() {
        addX(1);
        if (Grid.getInstance().checkLateralGridCollision(this)) {
            subX(1); // Annule le mouvement s'il y a une collision
        } else if (Grid.getInstance().checkTileCollision(this)){
            subX(1);
        }
    }

    public void moveDown() {
        if (Grid.getInstance().checkGridCollisionBelow(this)) {
            addY(1);
        }
    }



    private void subY(int i) {
        this.y -= i;
        this.bottomY -= i;
        for (Tiles tile : tiles) {
            tile.subY(i);
        }
    }

    public void fastDrop() {
        while (!Grid.getInstance().checkLateralGridCollision(this) && Grid.getInstance().checkGridCollisionBelow(this)) {
            addY(1);
        }
        Grid.getInstance().fixPiece(this);
    }

}
