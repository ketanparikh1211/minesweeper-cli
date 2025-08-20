package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {
    @Test
    public void testCheckWin() {
        Game game = new Game(2, 1);
        game.getGrid().getSquare(0, 0).setMine(true);
        game.getGrid().getSquare(0, 1).setRevealed(true);
        game.getGrid().getSquare(1, 0).setRevealed(true);
        game.getGrid().getSquare(1, 1).setRevealed(true);
        assertTrue(game.checkWin());
    }
}
