package com.example;

public class Square {
    private boolean isMine;
    private boolean isRevealed;
    private boolean isFlagged;  // New field to allow flagging suspected mines
    private int adjacentMines;

    public Square() {
        this.isMine = false;
        this.isRevealed = false;
        this.isFlagged = false;
        this.adjacentMines = 0;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        this.isFlagged = flagged;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }

    public void setAdjacentMines(int adjacentMines) {
        if (adjacentMines < 0) {
            throw new IllegalArgumentException("Adjacent mines count cannot be negative");
        }
        this.adjacentMines = adjacentMines;
    }
}
