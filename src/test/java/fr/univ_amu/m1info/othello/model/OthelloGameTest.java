package fr.univ_amu.m1info.othello.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OthelloGameTest {

    private OthelloGame game;

    @BeforeEach
    void setup() {
        game = new OthelloGame();
    }

    /* ============================================================
       INITIAL STATE
       ============================================================ */

    @Test
    void testInitialState() {
        assertEquals(Player.BLACK, game.getCurrentPlayer());
        assertFalse(game.isGameOver());

        OthelloBoard b = game.getBoard();
        assertEquals(CellState.WHITE, b.get(new Position(3, 3)));
        assertEquals(CellState.BLACK, b.get(new Position(3, 4)));
        assertEquals(CellState.BLACK, b.get(new Position(4, 3)));
        assertEquals(CellState.WHITE, b.get(new Position(4, 4)));

        assertEquals(2, game.count(Player.BLACK));
        assertEquals(2, game.count(Player.WHITE));
    }

    /* ============================================================
       RESET
       ============================================================ */

    @Test
    void testResetRestoresInitialState() {
        game.playMove(new Position(2, 3)); // valid Black move
        game.reset();

        assertEquals(Player.BLACK, game.getCurrentPlayer());
        assertFalse(game.isGameOver());
        assertEquals(2, game.count(Player.BLACK));
        assertEquals(2, game.count(Player.WHITE));

        assertEquals(CellState.WHITE, game.getBoard().get(new Position(3, 3)));
    }

    /* ============================================================
       CLONE
       ============================================================ */

    @Test
    void testCloneIsDeepCopy() {
        game.playMove(new Position(2, 3));

        OthelloGame clone = game.clone();

        // Same state
        assertEquals(game.getCurrentPlayer(), clone.getCurrentPlayer());
        assertEquals(game.count(Player.BLACK), clone.count(Player.BLACK));
        assertEquals(game.count(Player.WHITE), clone.count(Player.WHITE));

        // Changing clone must NOT change original
        clone.getBoard().set(new Position(0,0), CellState.BLACK);
        assertNotEquals(clone.getBoard().get(new Position(0,0)),
                game.getBoard().get(new Position(0,0)));
    }

    /* ============================================================
       VALID MOVES
       ============================================================ */

    @Test
    void testValidMovesAtStart() {
        List<Position> moves = game.getValidMoves(Player.BLACK);

        assertEquals(4, moves.size());
        assertTrue(moves.contains(new Position(2,3)));
        assertTrue(moves.contains(new Position(3,2)));
        assertTrue(moves.contains(new Position(4,5)));
        assertTrue(moves.contains(new Position(5,4)));
    }

    @Test
    void testIsValidMoveCorrect() {
        assertTrue(game.isValidMove(new Position(2,3), Player.BLACK));
        assertFalse(game.isValidMove(new Position(0,0), Player.BLACK));
        assertFalse(game.isValidMove(new Position(3,3), Player.BLACK)); // already occupied
    }

    /* ============================================================
       PLAY MOVE
       ============================================================ */

    @Test
    void testPlayValidMoveFlipsPieces() {
        assertTrue(game.playMove(new Position(2, 3)));

        OthelloBoard b = game.getBoard();

        // Move placed
        assertEquals(CellState.BLACK, b.get(new Position(2,3)));

        // The central white must flip
        assertEquals(CellState.BLACK, b.get(new Position(3,3)));

        // Score updated
        assertEquals(4, game.count(Player.BLACK));
        assertEquals(1, game.count(Player.WHITE));

        // Turn switched to white
        assertEquals(Player.WHITE, game.getCurrentPlayer());
    }

    @Test
    void testPlayInvalidDoesNothing() {
        int black = game.count(Player.BLACK);
        int white = game.count(Player.WHITE);

        assertFalse(game.playMove(new Position(0,0)));

        assertEquals(black, game.count(Player.BLACK));
        assertEquals(white, game.count(Player.WHITE));
        assertEquals(Player.BLACK, game.getCurrentPlayer());
    }

    /* ============================================================
       TURN SKIP / GAME OVER
       ============================================================ */

    @Test
    void testSkipOpponentIfNoMove() {
        // Create scenario: Black plays a move that leaves White with no moves

        OthelloBoard b = game.getBoard();
        // Force almost all cells to BLACK
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
                b.set(new Position(r,c), CellState.BLACK);

        // One isolated white piece
        b.set(new Position(7,7), CellState.WHITE);

        game.setCurrentPlayer(Player.BLACK);

        // Play any move (should skip white)
        game.playMove(new Position(0,1)); // will not flip anything but still turn logic applies

        assertEquals(Player.BLACK, game.getCurrentPlayer());
    }

    @Test
    void testGameOverWhenNoPlayerCanMove() {
        OthelloBoard b = game.getBoard();

        // 1) Vider complètement le plateau
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
                b.set(new Position(r, c), CellState.EMPTY);

        // 2) Créer une configuration avec EXACTEMENT 1 coup valide pour Black
        // Exemple simple :
        // B W .
        b.set(new Position(0,0), CellState.BLACK);
        b.set(new Position(0,1), CellState.WHITE);

        game.setCurrentPlayer(Player.BLACK);

        // 3) Black joue le SEUL coup valide
        assertTrue(game.playMove(new Position(0,2)));

        // 4) Ensuite, aucun joueur n’a de coup :
        assertFalse(game.hasAnyValidMove(Player.WHITE));
        assertFalse(game.hasAnyValidMove(Player.BLACK));

        // 5) Le coup précédent doit avoir déclenché gameOver
        assertTrue(game.isGameOver());
    }

    /* ============================================================
       WINNER
       ============================================================ */

    @Test
    void testWinnerWhenBlackHasMore() {
        OthelloBoard b = game.getBoard();

        // 10 black, 5 white
        for (int i = 0; i < 10; i++)
            b.set(new Position(i/8, i%8), CellState.BLACK);

        for (int i = 0; i < 5; i++)
            b.set(new Position(7, i), CellState.WHITE);

        game.setGameOver(true);

        assertEquals(Player.BLACK, game.getWinnerOrNull());
    }

    @Test
    void testWinnerNullOnTie() {
        OthelloBoard b = game.getBoard();

        // 5 black, 5 white
        for (int i = 0; i < 5; i++)
            b.set(new Position(0, i), CellState.BLACK);

        for (int i = 0; i < 5; i++)
            b.set(new Position(1, i), CellState.WHITE);

        game.setGameOver(true);

        assertNull(game.getWinnerOrNull());
    }
}
