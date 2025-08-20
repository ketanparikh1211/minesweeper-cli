package com.example;

import java.util.Scanner;

public class Game {
    private final Grid grid;
    private boolean gameOver;

    public Game(int size, int numMines) {
        this.grid = new Grid(size);
        grid.placeMines(numMines);
        grid.calculateAdjacentMines();
        this.gameOver = false;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (!gameOver) {
            displayGrid();
            System.out.print("Select a square to reveal (e.g. A1): ");
            String input = scanner.nextLine();
            if (isValidInput(input)) {
                int row = input.charAt(0) - 'A';
                int col = Integer.parseInt(input.substring(1)) - 1;
                if (row >= 0 && row < grid.getSize() && col >= 0 && col < grid.getSize()) {
                    reveal(row, col);
                    if (checkWin()) {
                        System.out.println("Congratulations, you have won the game!");
                        gameOver = true;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid square.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid square.");
            }
        }
        scanner.close();
    }

    private boolean isValidInput(String input) {
        if (input.length() < 2) {
            return false;
        }
        char rowChar = Character.toUpperCase(input.charAt(0));
        if (rowChar < 'A' || rowChar >= 'A' + grid.getSize()) {
            return false;
        }
        try {
            int col = Integer.parseInt(input.substring(1));
            if (col < 1 || col > grid.getSize()) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private void reveal(int row, int col) {
        Square square = grid.getSquare(row, col);
        if (square.isRevealed()) {
            return;
        }
        square.setRevealed(true);
        if (square.isMine()) {
            System.out.println("Oh no, you detonated a mine! Game over.");
            gameOver = true;
        } else if (square.getAdjacentMines() == 0) {
            for (int i = row - 1; i <= row + 1; i++) {
                for (int j = col - 1; j <= col + 1; j++) {
                    if (i >= 0 && i < grid.getSize() && j >= 0 && j < grid.getSize()) {
                        reveal(i, j);
                    }
                }
            }
        }
    }

    public Grid getGrid() {
        return grid;
    }

    public boolean checkWin() {
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                if (!grid.getSquare(i, j).isMine() && !grid.getSquare(i, j).isRevealed()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void displayGrid() {
        System.out.print("  ");
        for (int i = 1; i <= grid.getSize(); i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < grid.getSize(); i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < grid.getSize(); j++) {
                Square square = grid.getSquare(i, j);
                if (square.isRevealed()) {
                    if (square.isMine()) {
                        System.out.print("* ");
                    } else {
                        System.out.print(square.getAdjacentMines() + " ");
                    }
                } else {
                    System.out.print("_ ");
                }
            }
            System.out.println();
        }
    }
}
