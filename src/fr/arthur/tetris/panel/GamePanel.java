package fr.arthur.tetris.panel;

import fr.arthur.tetris.*;
import fr.arthur.tetris.pieces.TileColor;

import javax.swing.*;
import java.awt.*;


public class GamePanel extends Component {


    private DebugGraphics g;
    private int x = GameFrame.GRID_START_X * 20;
    private int y = GameFrame.GRID_START_Y * 20;

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
                    g.fillRect(x + i * 20, y + j * 20, 20, 20);
                }
                g.setColor(Color.GRAY);
                g.drawRect(x + i * 20, y + j * 20, 20, 20);
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
        drawHoldingPiece(g);
        drawGhostPiece(g);
        drawNextPieces(g);
        displayGrid(g);
        drawScore(g);
    }

    public void drawPiece(Graphics g) {
        // Foreach loop
        for (Tiles tiles: Game.getInstance().getCurrentPiece().getTiles()) {
            g.setColor(tiles.getColor());
            if (tiles.getY() >= 1) {
                g.fillRect(x + tiles.getX() * 20, y + tiles.getY() * 20, 20, 20);
            }
        }
    }

    public void drawGhostPiece(Graphics g) {
        // Clone current piece
        Pieces ghostPiece = new Pieces(Game.getInstance().getCurrentPiece());
        ghostPiece.setY(Game.getInstance().getGhostPieceY(ghostPiece));
        g.setColor(ghostPiece.getTileColor().getTransparentColor(ghostPiece.getColor(), 75));
        for (Tiles tiles : ghostPiece.getTiles()) {

            g.fillRect(x + tiles.getX() * 20, y + tiles.getY() * 20, 20, 20);
        }
    }

    public void drawHoldingPiece(Graphics g){
        Pieces holdingPiece = Game.getInstance().getHoldingPiece();
        if (holdingPiece != null) {
            for (Tiles tiles : holdingPiece.getTiles()) {
                if (!Game.getInstance().isHasSwapped()) {
                    g.setColor(tiles.getColor());
                } else {
                    g.setColor(TileColor.GREY.getColor());
                }
                g.fillRect( tiles.getX() * 20 - 50, tiles.getY() * 20, 20, 20);
            }
        }
    }

    public void drawNextPieces(Graphics g) {
        int x = Grid.getInstance().getWIDTH() * 20 + 20 + this.x;
        int y = 20 + this.y;
        for (Pieces piece : Game.getInstance().getNextPieces()) {
            for (Tiles tiles : piece.getTiles()) {
                g.setColor(tiles.getColor());
                g.fillRect(x + tiles.getX() * 20, y + tiles.getY() * 20, 20, 20);
            }
            y += 100;
        }

    }

    public void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Score: " + Game.getInstance().getScore(), x + 20, y + 20 + Grid.getInstance().getHEIGHT() * 20);
        g.drawString("Level: " + Game.getInstance().getLevel(), x + 20, y + 40 + Grid.getInstance().getHEIGHT() * 20);
        g.drawString("Lines: " + Game.getInstance().getTotalLines(), x+ 20, y + 60 + Grid.getInstance().getHEIGHT()* 20);

    }

    public void gameOver(Graphics g) {
        g.setColor(Color.BLACK);  // Couleur de l'arrière-plan
        g.fillRect(0, 0, GameFrame.WIDTH, GameFrame.HEIGHT);
        displayGrid(g);  // Affichage de la grille
        g.setColor(Color.WHITE);  // Couleur du texte
        g.drawString("Game Over", x + Grid.getInstance().getWIDTH() / 2, y + Grid.getInstance().getHEIGHT()/2);  // Affichage du texte au centre de la Grille
    }

}
