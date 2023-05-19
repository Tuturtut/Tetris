package fr.arthur.tetris;

import fr.arthur.tetris.panel.GamePanel;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private static GameFrame INSTANCE;

    public static final int WIDTH = 400;
    public static final int HEIGHT = 600;

    public GamePanel gamePanel;

    public GameFrame() {
        super("Tetris");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        // Layout avec un espacement personnalisé
        this.setLayout(new GridLayout(1, 2, 10, 0));
        // Ajout de la partie ou le jeu se déroule et de la partie ou les scores sont affichés
        gamePanel = new GamePanel();
        this.add(gamePanel);
        this.setVisible(true);
        SwingUtilities.invokeLater(() -> gamePanel.requestFocusInWindow());
    }

    public static GameFrame getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameFrame();
        }
        return INSTANCE;
    }

    public void repaint() {
        gamePanel.repaint();

    }
}
