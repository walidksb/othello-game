package fr.univ_amu.m1info.othello.model;

public enum Player {
    BLACK,
    WHITE;

    public Player opposite() {
        return this == BLACK ? WHITE : BLACK;
    }

    public CellState toCellState() {
        return this == BLACK ? CellState.BLACK : CellState.WHITE;
    }
}