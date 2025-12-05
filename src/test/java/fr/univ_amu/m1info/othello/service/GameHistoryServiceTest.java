package fr.univ_amu.m1info.othello.service;

import fr.univ_amu.m1info.othello.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameHistoryServiceTest {

    private GameHistoryService history;
    private OthelloGame game;

    @BeforeEach
    void setup() {
        history = new GameHistoryService();
        game = new OthelloGame();
    }

    @Test
    void testPushStateStoresSnapshot() {
        history.pushState(game);

        // Modify game afterwards
        game.playMove(new Position(2,3));

        // Undo should NOT return the modified one
        OthelloGame restored = history.undo(game);

        assertEquals(Player.BLACK, restored.getCurrentPlayer());
        assertEquals(CellState.WHITE,
                restored.getBoard().get(new Position(3,3)),
                "Undo must restore original board.");
    }

    @Test
    public void testUndoRestoresPreviousState() {
        OthelloGame game = new OthelloGame();
        GameHistoryService history = new GameHistoryService();

        // État initial (2 noirs, 2 blancs)
        assertEquals(2, game.count(Player.BLACK));

        // Le joueur noir joue (2,3)
        Position move = new Position(2,3);

        // On push l’état AVANT le coup (comme dans ton controller)
        history.pushState(game);

        // Noir joue
        game.playMove(move);

        // Après le coup : 4 noirs, 1 blanc
        assertEquals(4, game.count(Player.BLACK));

        // UNDO → revient au plateau initial
        game = history.undo(game);

        assertEquals(2, game.count(Player.BLACK),
                "Undo should return to the original board before the move.");

        // Current player should be BLACK again
        assertEquals(Player.BLACK, game.getCurrentPlayer(),
                "Undo must restore the previous currentPlayer.");
    }


    @Test
    void testRedoRestoresForwardState() {
        // State 1
        history.pushState(game);

        // Move → State 2
        game.playMove(new Position(2,3));
        history.pushState(game);

        // Undo → back to State 1
        OthelloGame afterUndo = history.undo(game);

        // Redo → forward to State 2
        OthelloGame afterRedo = history.redo(afterUndo);

        assertEquals(4, afterRedo.count(Player.BLACK));
        assertEquals(1, afterRedo.count(Player.WHITE));
        assertEquals(Player.WHITE, afterRedo.getCurrentPlayer(),
                "Redo must restore WHITE after move.");
    }

    @Test
    void testUndoEmptyDoesNothing() {
        OthelloGame result = history.undo(game);
        assertSame(game, result, "Undo on empty stack should return same game.");
    }

    @Test
    void testRedoEmptyDoesNothing() {
        OthelloGame result = history.redo(game);
        assertSame(game, result, "Redo on empty stack should return same game.");
    }

    @Test
    void testRedoStackClearedAfterPush() {
        // State 1
        history.pushState(game);

        // State 2
        game.playMove(new Position(2,3));
        history.pushState(game);

        // Undo
        history.undo(game);

        // New push should remove redo history
        history.pushState(game);

        // Redo should now do nothing
        OthelloGame result = history.redo(game);
        assertSame(game, result, "Redo must NOT be possible after pushState().");
    }

    @Test
    public void testUndoRestoresCurrentPlayer() {
        OthelloGame game = new OthelloGame();
        GameHistoryService history = new GameHistoryService();

        // Push initial
        history.pushState(game);

        // Noir joue un coup
        game.playMove(new Position(2,3));

        // Après coup, currentPlayer = WHITE
        assertEquals(Player.WHITE, game.getCurrentPlayer());

        // Undo → noir rejoue
        game = history.undo(game);

        assertEquals(Player.BLACK, game.getCurrentPlayer(),
                "Undo must restore the currentPlayer BEFORE the move.");
    }


    @Test
    void testUndoRedoNeverSetsGameOver() {
        game.playMove(new Position(2,3));
        history.pushState(game);

        game.setGameOver(true);

        OthelloGame restored = history.undo(game);

        assertFalse(restored.isGameOver(),
                "Undo must always disable gameOver.");
    }

    @Test
    void testDeepCopyOnBoardRestore() {
        history.pushState(game);

        // Modify game
        game.getBoard().set(new Position(0,0), CellState.WHITE);

        OthelloGame restored = history.undo(game);

        assertEquals(CellState.EMPTY,
                restored.getBoard().get(new Position(0,0)),
                "Undo must restore independent board copy.");
    }
}
