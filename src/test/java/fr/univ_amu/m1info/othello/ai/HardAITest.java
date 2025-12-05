package fr.univ_amu.m1info.othello.ai;

import fr.univ_amu.m1info.othello.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HardAITest {

    @Test
    void testChooseMoveReturnsValidMove() {
        OthelloGame game = new OthelloGame();
        HardAI ai = new HardAI();

        Position move = ai.chooseMove(game);

        assertNotNull(move, "HardAI should return a move at game start.");

        List<Position> valid = game.getValidMoves(Player.WHITE);
        assertTrue(valid.contains(move), "HardAI must choose a valid move.");
    }

    @Test
    void testChooseMoveReturnsNullIfNoMoves() {
        OthelloGame game = new OthelloGame();
        HardAI ai = new HardAI();

        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
                game.getBoard().set(new Position(r,c), CellState.WHITE);

        assertNull(ai.chooseMove(game), "HardAI must return null when no valid moves exist.");
    }

    @Test
    void testHardAIPrefersCorner() {
        HardAI ai = new HardAI();
        OthelloGame game = new OthelloGame();

        OthelloBoard board = game.getBoard();

        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
                board.set(new Position(r,c), CellState.EMPTY);

        // Create 3 WHITE moves: corner, edge, center
        // Corner move at (0,0)
        board.set(new Position(1,0), CellState.BLACK);
        board.set(new Position(2,0), CellState.WHITE);

        // Edge move at (0,5)
        board.set(new Position(1,5), CellState.BLACK);
        board.set(new Position(2,5), CellState.WHITE);

        // Center move at (3,3)
        board.set(new Position(4,3), CellState.WHITE);
        board.set(new Position(3,3), CellState.BLACK);

        game.setCurrentPlayer(Player.WHITE);

        Position best = ai.chooseMove(game);

        assertEquals(new Position(0,0), best,
                "HardAI must prefer corner due to positional weights.");
    }

    @Test
    void testHardAIPrefersEdgeIfNoCorner() {
        HardAI ai = new HardAI();
        OthelloGame game = new OthelloGame();
        OthelloBoard board = game.getBoard();

        // Clear board
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
                board.set(new Position(r,c), CellState.EMPTY);

        // EDGE valid
        board.set(new Position(1,5), CellState.BLACK);
        board.set(new Position(2,5), CellState.WHITE);

        // CENTER valid
        board.set(new Position(4,4), CellState.BLACK);
        board.set(new Position(5,4), CellState.WHITE);

        game.setCurrentPlayer(Player.WHITE);

        Position best = ai.chooseMove(game);

        assertEquals(new Position(0,5), best,
                "HardAI must prefer edge over center when no corner is available.");
    }

    @Test
    void testHardAIDoesNotModifyGameState() {
        HardAI ai = new HardAI();
        OthelloGame game = new OthelloGame();
        OthelloGame before = game.clone();

        ai.chooseMove(game);

        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
                assertEquals(before.getBoard().get(new Position(r,c)),
                        game.getBoard().get(new Position(r,c)),
                        "HardAI must NOT modify the board.");

        assertEquals(before.getCurrentPlayer(), game.getCurrentPlayer(),
                "HardAI must NOT change the current player.");
    }

    @Test
    void testMinimaxHandlesPassTurnWhenNoMoves() {
        HardAI ai = new HardAI();
        OthelloGame game = new OthelloGame();
        OthelloBoard board = game.getBoard();

        // Force WHITE to have no moves but BLACK has moves
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
                board.set(new Position(r,c), CellState.BLACK);

        board.set(new Position(3,3), CellState.WHITE); // One white piece only
        game.setCurrentPlayer(Player.WHITE);

        // HardAI should detect pass turn, depth decreases but not crash
        assertDoesNotThrow(() -> ai.chooseMove(game),
                "Minimax must handle pass-turn cases without crashing.");
    }

    @Test
    void testEvaluationMatchesWeights() {
        HardAI ai = new HardAI();
        OthelloGame game = new OthelloGame();
        OthelloBoard board = game.getBoard();

        // Clear board
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
                board.set(new Position(r,c), CellState.EMPTY);

        // Put WHITE on high-value corner (0,0)
        board.set(new Position(0,0), CellState.WHITE);

        // Put BLACK on low-value inner cell (3,3)
        board.set(new Position(3,3), CellState.BLACK);

        int score = invokeEvaluate(ai, game);

        // corner white = +100, inner black = -0
        assertTrue(score > 50,
                "Evaluation should strongly favor White because of corner value.");
    }

    // Helper to access private evaluate() using reflection
    private int invokeEvaluate(HardAI ai, OthelloGame game) {
        try {
            var method = HardAI.class.getDeclaredMethod("evaluate", OthelloGame.class);
            method.setAccessible(true);
            return (int) method.invoke(ai, game);
        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
            return 0;
        }
    }
}
