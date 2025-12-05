package fr.univ_amu.m1info.othello.model;

public class OthelloBoard {
    public static final int SIZE = 8;
    private final CellState[][] grid;


    public OthelloBoard() {
        grid = new CellState[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                grid[i][j] = CellState.EMPTY;

        grid[3][3] = CellState.WHITE;
        grid[3][4] = CellState.BLACK;
        grid[4][3] = CellState.BLACK;
        grid[4][4] = CellState.WHITE;
    }

    public CellState get(Position position) {
        return grid[position.row()][position.column()];
    }

    public void set(Position position, CellState state) {
        grid[position.row()][position.column()] = state;
    }

    public boolean inside(Position position) {
        int row = position.row();
        int col = position.column();
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }

    public void reset() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                grid[i][j] = CellState.EMPTY;

        grid[3][3] = CellState.WHITE;
        grid[3][4] = CellState.BLACK;
        grid[4][3] = CellState.BLACK;
        grid[4][4] = CellState.WHITE;
    }

    /** Deep copy of the board */
    public OthelloBoard getCopy() {
        OthelloBoard copy = new OthelloBoard();
        for (int r = 0; r < SIZE; r++)
            for (int c = 0; c < SIZE; c++)
                copy.grid[r][c] = this.grid[r][c];
        return copy;
    }
}
