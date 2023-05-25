package fr.arthur.tetris;

import fr.arthur.tetris.pieces.Piece;
import fr.arthur.tetris.pieces.TileColor;

import java.awt.*;
import java.awt.event.KeyEvent;
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
    private boolean leftPressed;
    private int leftPressedCount;
    private boolean rightPressed;
    private int rightPressedCount;
    private boolean downPressed;
    private int downPressedCount;


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

    public Pieces(Piece piece) {
        this(piece, Grid.X_START, Grid.Y_START - piece.getSpawnY());
    }

    public Pieces(Pieces currentPiece) {
        this(currentPiece.piece, currentPiece.x, currentPiece.y);
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

    private void subY(int i) {
        this.y -= i;
        this.bottomY -= i;
        for (Tiles tile : tiles) {
            tile.subY(i);
        }
    }

    public void clockWiseRotation() {


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

    public void softDrop() {
        if (Grid.getInstance().checkGridCollisionBelow(this)) {
            addY(1);
            Game.getInstance().addScore(1);
        }
    }

    public void hardDrop() {
        while (!Grid.getInstance().checkLateralGridCollision(this) && Grid.getInstance().checkGridCollisionBelow(this)) {
            addY(1);
            Game.getInstance().addScore(2);
        }
        Grid.getInstance().fixPiece(this);
    }

    public void pieceUpdate(KeyEvent e) {
        Pieces currentPiece = Game.getInstance().getCurrentPiece();

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            currentPiece.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            currentPiece.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            currentPiece.softDrop();
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            currentPiece.hardDrop();
        } else if (e.getKeyCode() == KeyEvent.VK_X) {
            currentPiece.clockWiseRotation();
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            currentPiece.counterClockWiseRotation();
        } else if (e.getKeyCode() == KeyEvent.VK_C) {
            Game.getInstance().holdPiece();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Game.getInstance().pauseGame();
        }
    }

    @Override
    public String toString() {
        return "Pieces{" +
                "name='" + name + '\'' +
                ", piece=" + piece +
                ", width=" + width +
                ", height=" + height +
                ", x=" + x +
                ", y=" + y +
                ", bottomY=" + bottomY +
                ", color=" + color +
                ", tiles=" + tiles +
                ", spawnY=" + spawnY +
                '}';
    }

    public void setColor(TileColor color) {
        this.color = color;
    }

    public TileColor getTileColor() {
        return color;
    }

    public Piece getPieceType() {
        return piece;
    }

    public void setPressedKey(boolean isPressing, int keyCode) {
        if (keyCode == KeyEvent.VK_LEFT) {
            leftPressed = isPressing;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            rightPressed = isPressing;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            downPressed = isPressing;
        }

        if (isPressing) {
            if (keyCode == KeyEvent.VK_SPACE) {
                hardDrop();
            } else if (keyCode == KeyEvent.VK_X) {
                clockWiseRotation();
            } else if (keyCode == KeyEvent.VK_W) {
                counterClockWiseRotation();
            } else if (keyCode == KeyEvent.VK_C) {
                Game.getInstance().holdPiece();
            } else if (keyCode == KeyEvent.VK_ESCAPE) {
                Game.getInstance().pauseGame();
            }
        }

    }

    public void updateMovement() {
        if (leftPressed) {
            moveLeft();
        } else if (rightPressed) {
            moveRight();
        } else if (downPressed) {
            softDrop();
        }
    }
}
