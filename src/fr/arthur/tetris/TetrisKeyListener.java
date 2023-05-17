package fr.arthur.tetris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TetrisKeyListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            Game.getInstance().getCurrentPiece().moveLeft();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            Game.getInstance().getCurrentPiece().moveRight();
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            Game.getInstance().getCurrentPiece().moveDown();
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            Game.getInstance().getCurrentPiece().fastDrop();
        }

        if (e.getKeyCode() == KeyEvent.VK_X) {
            // Rotate clockwise
            Game.getInstance().getCurrentPiece().clockWiseRotation();
        }

        if (e.getKeyCode() == KeyEvent.VK_W) {
            // Rotate counter-clockwise
            Game.getInstance().getCurrentPiece().counterClockWiseRotation();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
