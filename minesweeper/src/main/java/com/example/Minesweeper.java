package com.example;

import java.util.Scanner;

public class Minesweeper {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Minesweeper!");
        System.out.print("Enter the size of the grid (e.g. 4 for a 4x4 grid): ");
        int size = scanner.nextInt();
        int maxMines = (int) (size * size * 0.35);
        System.out.print("Enter the number of mines to place on the grid (maximum is " + maxMines + "): ");
        int numMines = scanner.nextInt();
        if (numMines > maxMines) {
            System.out.println("Number of mines exceeds the maximum allowed. Setting to " + maxMines);
            numMines = maxMines;
        }
        Game game = new Game(size, numMines);
        game.play();
        scanner.close();
    }
}
