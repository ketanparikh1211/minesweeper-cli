# Minesweeper

This is a command-line Minesweeper game written in Java.

## How to Play

1.  The game will prompt you for the grid size and the number of mines.
2.  Enter a grid size (e.g., 4 for a 4x4 grid).
3.  Enter the number of mines to place on the grid.
4.  The game will display the minefield.
5.  Select a square to reveal by entering its coordinates (e.g., A1).
6.  If you reveal a mine, the game is over.
7.  If you reveal a square with no adjacent mines, it will automatically reveal its neighbors.
8.  The game is won when all non-mine squares are revealed.

## Environment

*   Java Development Kit (JDK) 8 or higher
*   Apache Maven

## Instructions to Run

1.  Open a terminal or command prompt.
2.  Navigate to the `minesweeper` directory: `cd minesweeper`
3.  Compile the project using Maven: `mvn compile`
4.  Package the application into an executable JAR: `mvn package`
5.  Run the application: `java -jar target/minesweeper-1.0-SNAPSHOT-jar-with-dependencies.jar`
