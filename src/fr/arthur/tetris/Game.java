package fr.arthur.tetris;

import fr.arthur.tetris.pieces.Piece;
import fr.arthur.tetris.pieces.PieceBag;

public class Game implements Runnable {

    private static Game INSTANCE;
    protected Thread gameThread;
    protected boolean isCancelled;

    SpeedUpdate speedUpdate;
    private boolean gameOver;
    private boolean onGame;
    private final double FPS = 240;
    private double gameSpeed;
    private int nextPiecesNumber = 5;
    private PieceBag pieceBag = new PieceBag(nextPiecesNumber);
    private Pieces currentPiece;
    private Pieces reservePiece;

    private boolean hasSwapped;
    private double lockDelay;
    private int score;
    private int totalLines;
    private int level;
    private int difficulty;

    public Game() {
        setCurrentPiece(getNextPiece(true));
        this.gameOver = false;
        this.onGame = true;
        this.speedUpdate = new SpeedUpdate();
        this.isCancelled = false;
        this.hasSwapped = false;
        this.score = 0;
        this.level = 1;
        this.totalLines = 0;
        this.lockDelay = 5;
        this.difficulty = 1;
        this.setGameSpeed();
    }

    private void setGameSpeed() {
        this.gameSpeed = level * difficulty;
    }

    public static Game getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Game();
        }
        return INSTANCE;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(int totalLines) {
        this.totalLines = totalLines;
        if (totalLines >= 10) {
            addLevel(1);
            setTotalLines(0);
            setGameSpeed();
        }
    }

    public int getLevel() {
        return level;
    }
    public void setLevel(int level){
        this.level = level;
    }

    public void addLevel(int level) {
        setLevel(getLevel() + level);
    }

    public void addTotalLines(int totalLines) {
        setTotalLines(getTotalLines() + totalLines);
    }

    public SpeedUpdate getSpeedUpdate() {
        return speedUpdate;
    }

    public Pieces getCurrentPiece() {
        return currentPiece;
    }

    public Pieces getNextPiece(boolean remove) {
        return pieceBag.getNextPiece(remove);
    }

    public void start() {
        startGameThread();
        this.speedUpdate.startGameThread();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public boolean isGameOver() {
        return gameOver;
    }
    public boolean isOnGame() {
        return onGame;
    }

    public void setPieceInReserve(Piece piece) {
        this.reservePiece = new Pieces(piece);
    }

    public void holdPiece() {
        if (!hasSwapped) {
            if (reservePiece == null) {
                getCurrentPiece().getPieceType().resetRotation();
                setPieceInReserve(getCurrentPiece().getPieceType());
                setCurrentPiece(getNextPiece(true));
            } else {

                Pieces temp = reservePiece;
                getCurrentPiece().getPieceType().resetRotation();
                setPieceInReserve(getCurrentPiece().getPieceType());
                setCurrentPiece(temp);
            }
            hasSwapped = true;
        }
    }

    public boolean hasSwapped() {
        return hasSwapped;
    }

    public void setHasSwapped(boolean hasSwapped) {
        this.hasSwapped = hasSwapped;
    }

    @Override
    public void run(){
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int frames = 0;

        while (onGame && !isCancelled) {
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

    public int getNextPiecesNumber() {
        return nextPiecesNumber;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setCurrentPiece(Pieces piece) {
        this.currentPiece = piece;
    }

    public void resetThread() {
        getSpeedUpdate().isThreadRunning = false;
        speedUpdate = new SpeedUpdate();
        speedUpdate.startGameThread();
    }

    public Pieces[] getNextPieces() {
        return pieceBag.getNextPieces();
    }

    public void restart() {
        clearThread();
        INSTANCE = new Game();
        Grid.getInstance().reset();
        INSTANCE.start();
    }

    private void clearThread() {
        // ARreter le thread
        this.isCancelled = true;

        getSpeedUpdate().isThreadRunning = false;
    }

    public int getGhostPieceY(Pieces ghostPiece) {
        int ghostPieceY = ghostPiece.getY();
        while (Grid.getInstance().canMove(ghostPiece, 0, 1)) {
            ghostPieceY++;
            ghostPiece.setY(ghostPieceY);
        }
        ghostPiece.setY(ghostPieceY);
        return ghostPieceY;
    }

    public void pauseGame() {
        // TODO: 20/05/2023 Pause game
    }

    public Pieces getHoldingPiece() {
        return reservePiece;
    }

    public boolean isHasSwapped() {
        return hasSwapped;
    }

    public void addScore(int score) {
        setScore(getScore() + score);
    }

    public int calculateScore(int linesRemoved) {
        return switch (linesRemoved) {
            case 1 -> 100 * level;
            case 2 -> 300 * level;
            case 3 -> 500 * level;
            case 4 -> 800 * level;
            default -> 0;
        };
    }


    public class SpeedUpdate implements Runnable{

        protected boolean isThreadRunning = true;

        private double delta;

        public boolean isThreadRunning() {
            return isThreadRunning;
        }

        public void setThreadRunning(boolean threadRunning) {
            isThreadRunning = threadRunning;
        }

        @Override
        public void run() {
            double drawInterval = 1000000000 / gameSpeed;
            this.delta = 0;
            long lastTime = System.nanoTime();
            long currentTime;
            long timer = 0;
            int frames = 0;

            while (!isGameOver() && isThreadRunning()) {
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
            Grid.getInstance().update();
        }

        public void startGameThread() {
            gameThread = new Thread(this);
            gameThread.start();
        }

    }

    public class PieceMovement{
        // Appel de la méthode updateMovement() de la currentPiece basé sur les fps

        public int mouvementSpeed = 1;
        public double speed = FPS / mouvementSpeed;

        public void updateMovement() {
            if (speed >= 1) {
                getCurrentPiece().updateMovement();
                speed--;
            }
        }

    }
}
