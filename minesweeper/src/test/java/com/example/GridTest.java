package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class GridTest {
    @Test
    public void testPlaceMines() {
        Grid grid = new Grid(4);
        grid.placeMines(3);
        int mineCount = 0;
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                if (grid.getSquare(i, j).isMine()) {
                    mineCount++;
                }
            }
        }
        assertEquals(3, mineCount);
    }

    @Test
    public void testCalculateAdjacentMines() {
        Grid grid = new Grid(3);
        grid.getSquare(0, 0).setMine(true);
        grid.getSquare(0, 1).setMine(true);
        grid.getSquare(0, 2).setMine(true);
        grid.calculateAdjacentMines();
        assertEquals(2, grid.getSquare(1, 0).getAdjacentMines());
        assertEquals(3, grid.getSquare(1, 1).getAdjacentMines());
        assertEquals(2, grid.getSquare(1, 2).getAdjacentMines());
    }
}
