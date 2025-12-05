package fr.univ_amu.m1info.othello.model;

public enum CellState {
    EMPTY,
    BLACK,
    WHITE;

    public CellState opposite() {
        if (this == BLACK) return WHITE;
        if (this == WHITE) return BLACK;
        return EMPTY;
    }
}

