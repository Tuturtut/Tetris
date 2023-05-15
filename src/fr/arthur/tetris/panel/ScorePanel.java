package fr.arthur.tetris.panel;

import fr.arthur.tetris.GameFrame;

import java.awt.*;

public class ScorePanel extends Component {

        public ScorePanel() {
            super();
        }

        public void paint(Graphics g) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, GameFrame.WIDTH/2, GameFrame.HEIGHT);
        }
}
