package fr.arthur.tetris;

import fr.arthur.tetris.panel.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TetrisKeyListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Pieces currentPiece = Game.getInstance().getCurrentPiece();
        currentPiece.setPressedKey(true, e.getKeyCode());
//        if (!Game.getInstance().isGameOver())
//            currentPiece.pieceUpdate(e);
        if (e.getKeyCode() == KeyEvent.VK_R) {
            Game.getInstance().restart();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Pieces currentPiece = Game.getInstance().getCurrentPiece();
        currentPiece.setPressedKey(false, e.getKeyCode());
    }
}
