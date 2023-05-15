package fr.arthur.tetris.pieces;

public class Sprite {

        private int[][] sprite;

        public Sprite(int[][] sprite) {
            this.sprite = sprite;
        }

        public int[][] getSprite() {
            return sprite;
        }

        public int getSpriteSize() {
            return sprite.length;
        }

        public int getSpriteValue(int x, int y) {
            return sprite[x][y];
        }

        public int[][] getFlippedSprite() {
            int[][] flippedSprite = new int[getSpriteSize()][getSpriteSize()];
            for (int x = 0; x < getSpriteSize(); x++) {
                for (int y = 0; y < getSpriteSize(); y++) {
                    flippedSprite[x][y] = sprite[y][x];
                }
            }
            return flippedSprite;
        }

        public int[][] getRotatedSprite() {
            int[][] rotatedSprite = new int[getSpriteSize()][getSpriteSize()];
            for (int x = 0; x < getSpriteSize(); x++) {
                for (int y = 0; y < getSpriteSize(); y++) {
                    rotatedSprite[x][y] = sprite[getSpriteSize() - y - 1][x];
                }
            }
            return rotatedSprite;
        }

}
