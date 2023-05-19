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
        g.setColor(Color.BLACK);
        for (int i = 0; i < Grid.getInstance().getWIDTH(); i++) {
            for (int j = 0; j < Grid.getInstance().getHEIGHT(); j++) {
                int height = Grid.getInstance().getHEIGHT();
                if (j == height- height) {
                    continue;
                }
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
        if (Game.getInstance().isGameOver()) {
            gameOver(g);
        } else {
            drawGame(g);
        }

    }

    public void drawGame(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GameFrame.WIDTH, GameFrame.HEIGHT);
        drawPiece(g);
        drawNextPieces(g);
        displayGrid(g);
    }

    public void drawPiece(Graphics g) {
        // Foreach loop
        for (Tiles tiles: Game.getInstance().getCurrentPiece().getTiles()) {
            g.setColor(tiles.getColor());
            g.fillRect(tiles.getX() * 20, tiles.getY() * 20, 20, 20);
        }
    }

    public void drawNextPieces(Graphics g) {
        int x = Grid.getInstance().getWIDTH() * 20 + 20;
        int y = 20;
        for (Pieces piece : Game.getInstance().getNextPieces()) {
            for (Tiles tiles : piece.getTiles()) {
                g.setColor(tiles.getColor());
                g.fillRect(x + tiles.getX() * 20, y + tiles.getY() * 20, 20, 20);
            }
            y += 100;
        }

    }

    public void gameOver(Graphics g) {
        g.setColor(Color.BLACK);  // Couleur de l'arrière-plan
        g.fillRect(0, 0, GameFrame.WIDTH, GameFrame.HEIGHT);
        displayGrid(g);  // Affichage de la grille
        g.setColor(Color.WHITE);  // Couleur du texte
        g.drawString("Game Over", Grid.getInstance().getWIDTH() / 2, Grid.getInstance().getHEIGHT()/2);  // Affichage du texte au centre de la Grille
    }

}
