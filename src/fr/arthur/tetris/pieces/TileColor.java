package fr.arthur.tetris.pieces;

import java.awt.*;

public enum TileColor {
    LIGHT_BLUE(new Color(0, 255, 255)),
    DARK_BLUE(new Color(0, 0, 255)),
    ORANGE(new Color(255, 165, 0)),
    YELLOW(new Color(255, 255, 0)),
    GREEN(new Color(0, 255, 0)),
    PURPLE(new Color(128, 0, 128)),
    RED(new Color(255, 0, 0)),
    GREY(new Color(128, 128, 128));

    private final Color color;

    TileColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    // Couleur avec moins d'opacité
    public Color getTransparentColor(Color color, int opacity) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
    }
}
