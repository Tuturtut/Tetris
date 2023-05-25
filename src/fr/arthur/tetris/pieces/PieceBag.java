package fr.arthur.tetris.pieces;

import fr.arthur.tetris.Pieces;

import java.util.ArrayList;
import java.util.Collections;

public class PieceBag {

    private int size;
    private ArrayList<Pieces> bag = new ArrayList<>();

    public PieceBag(int nextPiecesNumber) {
        this.size = nextPiecesNumber;
        this.bag = new ArrayList<>();
        this.addAllPiecesToBag();
    }

    private void addAllPiecesToBag() {
        // TODO: 20/05/2023 Fix rotation spawn bug
        ArrayList<Pieces> tempBag = new ArrayList<>();
        for (Piece piece : Piece.values()) {
            tempBag.add(new Pieces(piece));
        }
        // Shuffle this bag
        Collections.shuffle(tempBag);

        // Add all pieces to the bag
        this.bag.addAll(tempBag);
    }

    public Pieces getNextPiece(boolean remove) {
        if (this.bag.size() <= this.size) {
            this.addAllPiecesToBag();
        }


        Pieces nextPiece = this.bag.get(0);
        if (remove)
            this.bag.remove(0);
        return nextPiece;
    }

    public Pieces[] getNextPieces() {
        Pieces[] nextPieces = new Pieces[this.size];
        for (int i = 0; i < this.size; i++) {
            nextPieces[i] = bag.get(i);
        }
        return nextPieces;
    }

    public void clear(){
        this.bag.clear();
    }
}
