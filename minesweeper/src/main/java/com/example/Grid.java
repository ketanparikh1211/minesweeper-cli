package com.example;

import java.util.Random;

public class Grid {
    private final int size;
    private final Square[][] squares;
    private static final int MIN_SIZE = 2;
    private static final int MAX_SIZE = 26; // Limited by A-Z row labels

    public Grid(int size) {
        if (size < MIN_SIZE) {
            throw new IllegalArgumentException("Grid size must be at least " + MIN_SIZE);
        }
        if (size > MAX_SIZE) {
            throw new IllegalArgumentException("Grid size cannot exceed " + MAX_SIZE);
        }
        
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
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IllegalArgumentException("Invalid grid coordinates: (" + row + ", " + col + ")");
        }
        return squares[row][col];
    }

    public void placeMines(int numMines) {
        if (numMines <= 0) {
            throw new IllegalArgumentException("Number of mines must be greater than 0");
        }
        
        int maxPossibleMines = size * size - 1; // At least one non-mine cell
        if (numMines > maxPossibleMines) {
            throw new IllegalArgumentException("Too many mines for grid size. Maximum allowed: " + maxPossibleMines);
        }
        
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
    
    public int getMineCount() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (squares[i][j].isMine()) {
                    count++;
                }
            }
        }
        return count;
    }
}
