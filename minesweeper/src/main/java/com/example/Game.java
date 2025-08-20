package com.example;

import java.util.Scanner;

public class Game {
    private final Grid grid;
    private boolean gameOver;
    private int flagsPlaced;

    public Game(int size, int numMines) {
        this.grid = new Grid(size);
        grid.placeMines(numMines);
        grid.calculateAdjacentMines();
        this.gameOver = false;
        this.flagsPlaced = 0;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        displayInstructions();
        
        while (!gameOver) {
            displayGrid();
            displayStats();
            System.out.print("Enter your move (or type 'help' for instructions): ");
            String input = scanner.nextLine().trim();
            
            // Handle exit command
            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit")) {
                System.out.println("Are you sure you want to quit? (y/n)");
                String confirm = scanner.nextLine().trim();
                if (confirm.equalsIgnoreCase("y") || confirm.equalsIgnoreCase("yes")) {
                    System.out.println("Thanks for playing!");
                    gameOver = true;
                    continue;
                } else {
                    continue;
                }
            }
            
            // Handle help command
            if (input.equalsIgnoreCase("help")) {
                displayInstructions();
                continue;
            }
            
            // Handle flag command (e.g., "f A1" or "flag A1")
            if (input.length() > 2 && (input.toLowerCase().startsWith("f ") || input.toLowerCase().startsWith("flag "))) {
                String coords = input.substring(input.indexOf(' ') + 1).trim();
                if (isValidInput(coords)) {
                    int row = Character.toUpperCase(coords.charAt(0)) - 'A';
                    int col = Integer.parseInt(coords.substring(1)) - 1;
                    toggleFlag(row, col);
                } else {
                    System.out.println("Invalid coordinate. Please enter a valid square.");
                }
                continue;
            }
            
            // Handle reveal command
            if (isValidInput(input)) {
                int row = Character.toUpperCase(input.charAt(0)) - 'A';
                int col = Integer.parseInt(input.substring(1)) - 1;
                
                // Check if the coordinate is within the grid bounds
                if (row >= 0 && row < grid.getSize() && col >= 0 && col < grid.getSize()) {
                    // Check if the square is flagged
                    if (grid.getSquare(row, col).isFlagged()) {
                        System.out.println("That square is flagged. Remove the flag first by using 'f " + input + "'");
                    } else {
                        reveal(row, col);
                        if (checkWin()) {
                            displayGrid();
                            System.out.println("Congratulations, you have won the game!");
                            gameOver = true;
                        }
                    }
                } else {
                    System.out.println("Invalid coordinate. Please enter a valid square.");
                }
            } else {
                System.out.println("Invalid input. Type 'help' for instructions.");
            }
        }
        // Don't close the scanner here as it could close System.in
    }

    private void toggleFlag(int row, int col) {
        if (row < 0 || row >= grid.getSize() || col < 0 || col >= grid.getSize()) {
            System.out.println("Invalid coordinates. Please enter a valid square.");
            return;
        }
        
        Square square = grid.getSquare(row, col);
        if (square.isRevealed()) {
            System.out.println("Cannot flag a revealed square.");
            return;
        }
        
        if (square.isFlagged()) {
            square.setFlagged(false);
            flagsPlaced--;
            System.out.println("Flag removed from " + (char)('A' + row) + (col + 1));
        } else {
            square.setFlagged(true);
            flagsPlaced++;
            System.out.println("Flag placed at " + (char)('A' + row) + (col + 1));
        }
    }

    private void displayInstructions() {
        System.out.println("\n=== MINESWEEPER INSTRUCTIONS ===");
        System.out.println("- Enter coordinates to reveal a square (e.g., A1)");
        System.out.println("- Flag a suspected mine with 'f A1' or 'flag A1'");
        System.out.println("- Type 'help' to see these instructions again");
        System.out.println("- Type 'exit' or 'quit' to end the game");
        System.out.println("================================\n");
    }
    
    private void displayStats() {
        int totalMines = grid.getMineCount();
        System.out.println("Mines: " + totalMines + " | Flags placed: " + flagsPlaced);
    }

    private boolean isValidInput(String input) {
        if (input == null || input.length() < 2) {
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
        try {
            Square square = grid.getSquare(row, col);
            if (square.isRevealed()) {
                return;
            }
            square.setRevealed(true);
            
            // If it was flagged, remove the flag
            if (square.isFlagged()) {
                square.setFlagged(false);
                flagsPlaced--;
            }
            
            if (square.isMine()) {
                revealAllMines();
                displayGrid();
                System.out.println("Oh no, you detonated a mine! Game over.");
                gameOver = true;
            } else if (square.getAdjacentMines() == 0) {
                // Auto-reveal surrounding squares (flood fill)
                for (int i = row - 1; i <= row + 1; i++) {
                    for (int j = col - 1; j <= col + 1; j++) {
                        if (i >= 0 && i < grid.getSize() && j >= 0 && j < grid.getSize()) {
                            reveal(i, j);
                        }
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            // This should never happen with proper validation, but handle it just in case
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void revealAllMines() {
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                Square square = grid.getSquare(i, j);
                if (square.isMine()) {
                    square.setRevealed(true);
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
        System.out.println();
        // Print column headers
        System.out.print("  ");
        for (int i = 1; i <= grid.getSize(); i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        
        // Print grid
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
                } else if (square.isFlagged()) {
                    System.out.print("F ");
                } else {
                    System.out.print("_ ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
