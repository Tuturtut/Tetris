package fr.arthur.tetris;

public class Grid {

    public static Grid INSTANCE;

    private Tiles[][] grid;
    private final int width;
    private final int height;

    public Grid() {
        this.width = 10;
        this.height = 20;
        this.grid = new Tiles[width][height];
    }

    public static Grid getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Grid();
        }
        return INSTANCE;
    }

    public Tiles[][] getGrid() {
        return grid;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
