package com.example;

import java.util.Random;

public class Grid {
    private final int size;
    private final Square[][] squares;

    public Grid(int size) {
        this.size = size;
        this.squares = new Square[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                squares[i][j] = new Square();
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Square getSquare(int row, int col) {
        return squares[row][col];
    }

    public void placeMines(int numMines) {
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced < numMines) {
            int row = random.nextInt(size);
            int col = random.nextInt(size);
            if (!squares[row][col].isMine()) {
                squares[row][col].setMine(true);
                minesPlaced++;
            }
        }
    }

    public void calculateAdjacentMines() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!squares[i][j].isMine()) {
                    int count = 0;
                    for (int row = i - 1; row <= i + 1; row++) {
                        for (int col = j - 1; col <= j + 1; col++) {
                            if (row >= 0 && row < size && col >= 0 && col < size && squares[row][col].isMine()) {
                                count++;
                            }
                        }
                    }
                    squares[i][j].setAdjacentMines(count);
                }
            }
        }
    }
}
