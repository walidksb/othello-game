package fr.univ_amu.m1info.othello.service;

import fr.univ_amu.m1info.othello.model.*;

import java.util.Stack;

public class GameHistoryService {

    private final Stack<GameState> undoStack = new Stack<>();
    private final Stack<GameState> redoStack = new Stack<>();

    /** Save minimal game state */
    public void pushState(OthelloGame game) {
        undoStack.push(new GameState(game.getBoard(), game.getCurrentPlayer()));
        redoStack.clear();
    }

    public OthelloGame undo(OthelloGame current) {
        if (undoStack.isEmpty())
            return current;

        redoStack.push(new GameState(current.getBoard(), current.getCurrentPlayer()));

        return restoreState(current, undoStack.pop());
    }

    public OthelloGame redo(OthelloGame current) {
        if (redoStack.isEmpty())
            return current;

        undoStack.push(new GameState(current.getBoard(), current.getCurrentPlayer()));

        return restoreState(current, redoStack.pop());
    }

    private OthelloGame restoreState(OthelloGame game, GameState state) {
        // restore board
        OthelloBoard newBoard = state.getBoard();
        for (int r = 0; r < OthelloBoard.SIZE; r++)
            for (int c = 0; c < OthelloBoard.SIZE; c++)
                game.getBoard().set(new Position(r,c), newBoard.get(new Position(r,c)));

        // restore current player
        game.setCurrentPlayer(state.getCurrentPlayer());

        // game never ends during undo/redo
        game.setGameOver(false);

        return game;
    }

    public void clear() {
        undoStack.clear();
        redoStack.clear();
    }
}
