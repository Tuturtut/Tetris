package fr.arthur.tetris;

public class Grid {

    public static Grid INSTANCE;

    private Tiles[][] grid;
    private final int WIDTH;
    private final int HEIGHT;

    public static final int X_START = 3;
    public static final int Y_START = -1;

    public Grid() {
        this.WIDTH = 10;
        this.HEIGHT = 20;
        this.grid = new Tiles[WIDTH][HEIGHT];
    }

    public static Grid getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Grid();
        }
        return INSTANCE;
    }

    public boolean checkCollision(Pieces piece) {
        for (Tiles tile : piece.tiles) {
            if (tile.getY() >= HEIGHT) {
                return true;
            }
            if (tile.getX() < 0 || tile.getX() >= WIDTH) {
                return true;
            }
            if (grid[tile.getX()][tile.getY()] != null) {
                return true;
            }
        }
        return false;
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
        if (piece.getBottomY() <= HEIGHT) {
            piece.addY(1);
        } else {
            fixPiece(piece);
        }
    }

    public void fixPiece(Pieces piece) {
        for (Tiles tile : piece.tiles) {
            grid[tile.getX()][tile.getY()] = tile;
        }
        Game.getInstance().getNewPiece();
        addTiles(piece);
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
}
