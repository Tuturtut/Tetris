package fr.arthur.tetris.pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PieceBag {
    private List<Piece> pieceList;
    private List<Piece> bag;

    public PieceBag() {
        pieceList = new ArrayList<>();
        bag = new ArrayList<>();
        fillPieceList();
        fillBag();
    }

    private void fillPieceList() {
        // Ajoute toutes les pièces du jeu à la liste deux fois
        pieceList.add(Piece.I);
        pieceList.add(Piece.J);
        pieceList.add(Piece.L);
        pieceList.add(Piece.O);
        pieceList.add(Piece.S);
        pieceList.add(Piece.T);
        pieceList.add(Piece.Z);

        // Ajoute les autres pièces ici...

        // Mélange la liste des pièces
        Collections.shuffle(pieceList);
    }

    private void fillBag() {
        // Remplit le sac avec les pièces de la liste
        bag.addAll(pieceList);
    }

    public Piece getNextPiece() {
        if (bag.isEmpty()) {
            // Le sac est vide, on le remplit à nouveau
            fillBag();
        }

        // Sélectionne une pièce aléatoire dans le sac
        int randomIndex = new Random().nextInt(bag.size());
        Piece nextPiece = bag.get(randomIndex);

        // Retire la pièce sélectionnée du sac
        bag.remove(randomIndex);

        return nextPiece;
    }
}