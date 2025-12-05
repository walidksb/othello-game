package fr.univ_amu.m1info.othello.model;

public class GameState {

    private final OthelloBoard board;
    private final Player currentPlayer;

    public GameState(OthelloBoard board, Player currentPlayer) {
        this.board = board.getCopy();
        this.currentPlayer = currentPlayer;
    }

    public OthelloBoard getBoard() {
        return board.getCopy();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
