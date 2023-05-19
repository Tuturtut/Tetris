package fr.arthur.tetris;

import fr.arthur.tetris.pieces.PieceBag;

import java.util.ArrayList;
import java.util.Arrays;

public class Grid {

    public static Grid INSTANCE;

    private Tiles[][] grid;
    private final int WIDTH;
    private final int HEIGHT;

    public static final int X_START = 3;
    public static final int Y_START = 1;

    public Grid() {
        this.WIDTH = 10;
        this.HEIGHT = 22;
        this.grid = new Tiles[WIDTH][HEIGHT];
    }

    public static Grid getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Grid();
        }
        return INSTANCE;
    }

    // Check if the piece is colliding with the grid
    public boolean checkLateralGridCollision(Pieces piece) {
        for (Tiles tiles : piece.getTiles()){
            if (tiles.getX() < 0 || tiles.getX() >= WIDTH) {
                return true;
            }
        }
        return false;
    }

    public boolean checkGridCollisionBelow(Pieces piece) {
        for (Tiles tile : piece.getTiles()) {
            int nextY = tile.getY() + 1;
            if (nextY >= Grid.getInstance().getHEIGHT()) {
                Game.getInstance().resetThread();
                return false;
            } else if (Grid.getInstance().checkTileCollisionBelow(tile)){
                Game.getInstance().resetThread();
                return false;
            }
        }
        return true;
    }

    private boolean checkTileCollisionBelow(Pieces piece) {
        for (Tiles tile : piece.getTiles()) {
            if (checkTileCollisionBelow(tile)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkTileCollisionBelow(Tiles tile) {
        int nextY = tile.getY() + 1;
        if (nextY >= Grid.getInstance().getHEIGHT()) {
            Game.getInstance().resetThread();
            return false;
        } else return Grid.getInstance().isOccupied(tile.getX(), nextY);
    }

    public boolean isOccupied(int x, int y) {
        return grid[x][y] != null;
    }

    public Tiles[][] getGrid() {
        return grid;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public void dropDownPiece(Pieces piece) {
        if (checkGridCollisionBelow(piece)) {
            piece.addY(1);
        } else {
            fixPiece(piece);
        }
    }

    public void fixPiece(Pieces piece) {
        for (Tiles tile : piece.tiles) {
            grid[tile.getX()][tile.getY()] = tile;
        }
        addTiles(piece);
        if (checkSpawnCollision(Game.getInstance().getNextPiece(false))) {
            Game.getInstance().setGameOver(true);
        } else {
            Game.getInstance().resetThread();
            piece = Game.getInstance().getNextPiece(true);
            Game.getInstance().setCurrentPiece(piece);
            removeCompletedLines();
        }
    }

    public void addTiles(Pieces piece) {
        for (Tiles tile : piece.tiles) {
            grid[tile.getX()][tile.getY()] = tile;
        }
    }

    public void addTile(Tiles tile) {
        grid[tile.getX()][tile.getY()] = tile;
    }

    // return the bottom Y of the grid minus the height of the piece
    public int getBottomY(Pieces pieces) {
        return HEIGHT + 1 - pieces.getHeight();
    }

    public boolean checkTileCollision(Pieces pieces) {
        for (Tiles tile : pieces.getTiles()) {
            if (tile.getY() < 0) {
                return false;
            } else if (grid[tile.getX()][tile.getY()] != null) {
                return true;
            }
        }
        return false;
    }

    public boolean checkSpawnCollision(Pieces piece) {
        for (Tiles tile : piece.getTiles()) {
            if (grid[tile.getX()][tile.getY()] != null) {
                return true;
            }
        }
        return false;
    }

    public boolean checkRotationCollision(Pieces piece, int newX, int newY, int newWidth, int newHeight) {
        // Vérifier les limites du nouveau rectangle de la pièce après la rotation
        if (newX < 0 || newX + newWidth > WIDTH || newY < 0 || newY + newHeight > HEIGHT) {
            return true; // Collision avec les bords de la grille
        }

        // Vérifier les collisions avec les tuiles existantes dans la grille
        for (int x = newX; x < newX + newWidth; x++) {
            for (int y = newY; y < newY + newHeight; y++) {
                if (piece.getPiece()[x - newX][y - newY] != 0) {
                    System.out.println("x: " + x + " y: " + y);
                    if (grid[x][y] != null) {
                        return true; // Collision avec une tuile existante dans la grille
                    }
                }
            }
        }

        return false; // Pas de collision détectée
    }

    public void removeCompletedLines() {
        for (int y = HEIGHT - 1; y >= 0; y--) {
            if (isLineComplete(y)) {
                removeLine(y);
                y++; // Réévaluez la ligne actuelle après avoir supprimé une ligne
            }
        }
    }

    private void removeLine(int y) {
        for (int x = 0; x < WIDTH; x++) {
            grid[x][y] = null;
        }

        // Déplacer toutes les lignes au-dessus de la ligne supprimée vers le bas
        for (int i = y - 1; i >= 0; i--) {
            for (int x = 0; x < WIDTH; x++) {
                if (grid[x][i] != null) {
                    grid[x][i + 1] = grid[x][i];
                    grid[x][i] = null;
                }
            }
        }
    }

    private boolean isLineComplete(int y) {
        for (int x = 0; x < WIDTH; x++) {
            if (grid[x][y] == null) {
                return false;
            }
        }
        return true;
    }

    public void update() {
        dropDownPiece(Game.getInstance().getCurrentPiece());
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < HEIGHT; y++) {
            sb.append("|");
            for (int x = 0; x < WIDTH; x++) {
                if (grid[x][y] != null) {
                    sb.append(" X ");
                } else {
                    sb.append("   ");
                }
            }
            sb.append("|\n");
        }

        return sb.toString();
    }

    public void reset() {
        grid = new Tiles[WIDTH][HEIGHT];
    }
}
