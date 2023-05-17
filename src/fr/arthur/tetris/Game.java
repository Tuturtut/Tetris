package fr.arthur.tetris;

import fr.arthur.tetris.pieces.Piece;

public class Game implements Runnable {

    private static Game INSTANCE;
    protected Thread gameThread;

    private boolean onGame;
    private final double FPS = 60;
    private final double GAME_SPEED = 1;

    public Pieces currentPiece;

    public Game() {
        this.currentPiece = Piece.getRandomPiece();
        this.currentPiece = new Pieces(Piece.I);
        this.onGame = true;
    }

    public static Game getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Game();
        }
        return INSTANCE;
    }

    public Pieces getCurrentPiece() {
        return currentPiece;
    }

    public void start() {
        startGameThread();
        SpeedUpdate speedUpdate = new SpeedUpdate();
        speedUpdate.startGameThread();

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    private boolean isOnGame() {
        return onGame;
    }

    @Override
    public void run(){
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int frames = 0;

        while (isOnGame()) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {
                draw();
                frames++;
                delta--;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS : " + frames);
                frames = 0;
                timer = 0;
            }
        }
    }

    private void draw() {
        GameFrame.getInstance().repaint();
    }

    public void getNewPiece() {
        currentPiece = Piece.getRandomPiece();
    }

    public class SpeedUpdate implements Runnable{

        @Override
        public void run() {
            double drawInterval = 1000000000 / GAME_SPEED;
            double delta = 0;
            long lastTime = System.nanoTime();
            long currentTime;
            long timer = 0;
            int frames = 0;

            while (isOnGame()) {
                currentTime = System.nanoTime();
                delta += (currentTime - lastTime) / drawInterval;
                timer += currentTime - lastTime;
                lastTime = currentTime;

                if (delta >= 1) {
                    update();
                    frames++;
                    delta--;
                }

                if (timer >= 1000000000) {
                    System.out.println("Game speed : " + frames);
                    frames = 0;
                    timer = 0;
                }
            }
        }

        private void update() {
            Grid.getInstance().dropDownPiece(currentPiece);
        }

        public void startGameThread() {
            gameThread = new Thread(this);
            gameThread.start();
        }

    }
}
