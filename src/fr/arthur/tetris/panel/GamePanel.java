package fr.arthur.tetris.panel;

import fr.arthur.tetris.*;

import javax.swing.*;
import java.awt.*;


public class GamePanel extends Component {


    private DebugGraphics g;

    public GamePanel() {
        super();
        TetrisKeyListener keyListener = new TetrisKeyListener();
        this.addKeyListener(keyListener);
        this.setVisible(true);
        this.setFocusable(true);
    }

    public void displayGrid(Graphics g) {
        for (int i = 0; i < Grid.getInstance().getWIDTH(); i++) {
            for (int j = 0; j < Grid.getInstance().getHEIGHT(); j++) {
                if (Grid.getInstance().getGrid()[i][j] != null) {
                    g.setColor(Grid.getInstance().getGrid()[i][j].getColor());
                    g.fillRect(i * 20, j * 20, 20, 20);
                }
                g.setColor(Color.GRAY);
                g.drawRect(i * 20, j * 20, 20, 20);
            }
        }
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GameFrame.WIDTH/2, GameFrame.HEIGHT);
        drawPiece(g);
        displayGrid(g);
    }

    public void drawPiece(Graphics g) {
        // Foreach loop
        for (Tiles tiles: Game.getInstance().getCurrentPiece().getTiles()) {
            g.setColor(tiles.getColor());
            g.fillRect(tiles.getX() * 20, tiles.getY() * 20, 20, 20);
        }
    }
}
