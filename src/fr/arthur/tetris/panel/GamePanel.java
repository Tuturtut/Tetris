package fr.arthur.tetris.panel;

import fr.arthur.tetris.GameFrame;
import fr.arthur.tetris.Grid;
import fr.arthur.tetris.Pieces;
import fr.arthur.tetris.pieces.Piece;

import java.awt.*;


public class GamePanel extends Component {

    public Graphics g;

    public GamePanel() {
        super();
        g = getGraphics();
        System.out.println(g);
    }

    public void displayGrid(Graphics g) {
        g.setColor(Color.GRAY);

        for (int i = 0; i < Grid.getInstance().getWidth(); i++) {
            for (int j = 0; j < Grid.getInstance().getHeight(); j++) {
                // Affiche un carré de 20x20 vide
                g.drawRect(i * 20, j * 20, 20, 20);

            }
        }
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GameFrame.WIDTH/2, GameFrame.HEIGHT);
        displayGrid(g);
    }

    public void drawPiece(Graphics g, Pieces currentPiece) {
        g.fillRect(currentPiece.getX() * 20, currentPiece.getY() * 20, 20, 20);
    }
}
