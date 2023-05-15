package fr.arthur.tetris;

import fr.arthur.tetris.panel.GamePanel;
import fr.arthur.tetris.panel.ScorePanel;
import fr.arthur.tetris.pieces.Piece;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public GamePanel gamePanel;

    public GameFrame() {
        super("Tetris");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        // Layout avec un espacement personnalisé
        this.setLayout(new GridLayout(1, 2, 10, 0));
        // Ajout de la partie ou le jeu se déroule et de la partie ou les scores sont affichés
        gamePanel = new GamePanel();
        this.add(gamePanel);
        this.add(new ScorePanel());

    }


    public void drawPiece(Pieces currentPiece) {
        gamePanel.drawPiece(gamePanel.g, currentPiece);
    }
}
