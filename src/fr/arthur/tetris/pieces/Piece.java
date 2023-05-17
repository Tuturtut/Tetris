package fr.arthur.tetris.pieces;

import fr.arthur.tetris.Game;
import fr.arthur.tetris.Pieces;

import java.util.ArrayList;
import java.util.Collections;

public enum Piece {

    I("I", new int[][][] {
            {
                    {0, 0, 0, 0},
                    {1, 1, 1, 1},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0}
            },
            {
                    {0, 0, 1, 0},
                    {0, 0, 1, 0},
                    {0, 0, 1, 0},
                    {0, 0, 1, 0}
            },
            {
                    {0, 0, 0, 0},
                    {0, 0, 0, 0},
                    {1, 1, 1, 1},
                    {0, 0, 0, 0}
            },
            {
                    {0, 1, 0, 0},
                    {0, 1, 0, 0},
                    {0, 1, 0, 0},
                    {0, 1, 0, 0}
            }
    }, TileColor.LIGHT_BLUE, 1),

    J("J", new int[][][] {
            {
                    {1, 0, 0},
                    {1, 1, 1},
                    {0, 0, 0}
            },
            {
                    {0, 1, 1},
                    {0, 1, 0},
                    {0, 1, 0}
            },
            {
                    {0, 0, 0},
                    {1, 1, 1},
                    {0, 0, 1}
            },
            {
                    {0, 1, 0},
                    {0, 1, 0},
                    {1, 1, 0}
            }
    }, TileColor.DARK_BLUE, 1),

    L("L", new int[][][] {
            {
                    {0, 0, 1},
                    {1, 1, 1},
                    {0, 0, 0}
            },
            {
                    {0, 1, 0},
                    {0, 1, 0},
                    {0, 1, 1}
            },
            {
                    {0, 0, 0},
                    {1, 1, 1},
                    {1, 0, 0}
            },
            {
                    {1, 1, 0},
                    {0, 1, 0},
                    {0, 1, 0}
            }
    }, TileColor.ORANGE, 1),

    O("O", new int[][][] {
            {
                    {1, 1},
                    {1, 1}
            }
            // L'orientation reste la même pour O
    }, TileColor.YELLOW, 1),

    S("S", new int[][][] {
            {
                    {0, 1, 1},
                    {1, 1, 0},
                    {0, 0, 0}
            },
            {
                    {0, 1, 0},
                    {0, 1, 1},
                    {0, 0, 1}
            },
            {
                    {0, 0, 0},
                    {0, 1, 1},
                    {1, 1, 0}
            },
            {
                    {1, 0, 0},
                    {1, 1, 0},
                    {0, 1, 0}
            }
    }, TileColor.GREEN, 1),

    T("T", new int[][][] {
            {
                    {0, 1, 0},
                    {1, 1, 1},
                    {0, 0, 0}
            },
            {
                    {0, 1, 0},
                    {0, 1, 1},
                    {0, 1, 0}
            },
            {
                    {0, 0, 0},
                    {1, 1, 1},
                    {0, 1, 0}
            },
            {
                    {0, 1, 0},
                    {1, 1, 0},
                    {0, 1, 0}
            }
    }, TileColor.PURPLE, 1),

    Z("Z", new int[][][] {
            {
                    {1, 1, 0},
                    {0, 1, 1},
                    {0, 0, 0}
            },
            {
                    {0, 0, 1},
                    {0, 1, 1},
                    {0, 1, 0}
            },
            {
                    {0, 0, 0},
                    {1, 1, 0},
                    {0, 1, 1}
            },
            {
                    {0, 1, 0},
                    {1, 1, 0},
                    {1, 0, 0}
            }
    }, TileColor.RED, 1);

    private final String name;
    private int[][][] orientations;
    private int width;
    private int height;
    private final TileColor color;
    private final int spawnY;
    private int rotation; // Ajout d'une variable rotation


    Piece(String name, int[][][] orientations, TileColor color, int spawnY) {
        this.name = name;
        this.orientations = orientations;
        this.color = color;
        this.spawnY = spawnY;
        this.width = orientations[0][0].length;
        this.height = orientations[0].length;
        this.rotation = 0; // Initialisation de la rotation à 0
    }

    Piece(String name, int[][][] orientations, TileColor color) {
        this(name, orientations, color, 1);
    }

    public static ArrayList<Pieces> fillBundle() {
        ArrayList<Piece> pieceList = new ArrayList<>();
        Collections.addAll(pieceList, Piece.values());
        Collections.addAll(pieceList, Piece.values());
        ArrayList<Pieces> bundle = new ArrayList<>();
        for (Piece piece : pieceList) {
            bundle.add(new Pieces(piece));
        }
        return bundle;
    }


    public int[][] getCurrentOrientation() {
        int index = rotation % orientations.length;
        if (index < 0) {
            index += orientations.length;
        }
        return orientations[index];
    }

    public void rotateClockwise() {
        rotation++; // Incrémente la rotation de 90 degrés
    }

    public void rotateCounterClockwise() {
        rotation--; // Décrémente la rotation de 90 degrés
    }


    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[][] getCurrentPiece() {
        return getCurrentOrientation();
    }

    public void setCurrentPiece(int[][] newPiece) {
        orientations[0] = newPiece;
        width = newPiece[0].length;
        height = newPiece.length;
    }

    public TileColor getPieceColor() {
        return color;
    }

    public int getSpawnY() {
        return spawnY;
    }

    public int[][] getNextClockwiseRotation() {
        int[][] currentPiece = getCurrentPiece();
        int[][] rotatedPiece = new int[currentPiece[0].length][currentPiece.length];

        for (int i = 0; i < currentPiece.length; i++) {
            for (int j = 0; j < currentPiece[i].length; j++) {
                rotatedPiece[j][currentPiece.length - 1 - i] = currentPiece[i][j];
            }
        }

        return rotatedPiece;
    }

    public int[][] getNextCounterClockwiseRotation() {
        int[][] currentPiece = getCurrentPiece();
        int[][] rotatedPiece = new int[currentPiece[0].length][currentPiece.length];

        for (int i = 0; i < currentPiece.length; i++) {
            for (int j = 0; j < currentPiece[i].length; j++) {
                rotatedPiece[currentPiece[i].length - 1 - j][i] = currentPiece[i][j];
            }
        }
        return rotatedPiece;
    }



}