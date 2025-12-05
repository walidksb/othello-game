package fr.univ_amu.m1info.othello.ai;

import fr.univ_amu.m1info.othello.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MediumAITest {

    @Test
    void testChooseMoveReturnsValidMove() {
        OthelloGame game = new OthelloGame();
        MediumAI ai = new MediumAI();

        Position move = ai.chooseMove(game);

        assertNotNull(move, "MediumAI should return a move at game start");

        List<Position> validMoves = game.getValidMoves(Player.WHITE);
        assertTrue(validMoves.contains(move), "AI must choose a valid move");
    }

    @Test
    void testChooseMoveReturnsNullWhenNoMoves() {
        OthelloGame game = new OthelloGame();
        MediumAI ai = new MediumAI();

        // Fill board -> WHITE has no moves
        for (int r = 0; r < OthelloBoard.SIZE; r++)
            for (int c = 0; c < OthelloBoard.SIZE; c++)
                game.getBoard().set(new Position(r, c), CellState.WHITE);

        assertNull(ai.chooseMove(game), "AI must return null when no moves exist");
    }

    @Test
    void testAIChoosesCornerOverEdgesAndCenter() {
        MediumAI ai = new MediumAI();
        OthelloGame game = new OthelloGame();

        // Manually create a situation where WHITE has these options:
        // - (0,0) is a corner  → score 100
        // - (0,3) is an edge   → score 10
        // - (3,3) is center    → score 1

        OthelloBoard board = game.getBoard();

        // Clear the board
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
                board.set(new Position(r, c), CellState.EMPTY);

        // Place WHITE discs so all three moves are valid
        board.set(new Position(1, 0), CellState.BLACK);
        board.set(new Position(1, 3), CellState.BLACK);
        board.set(new Position(3, 3), CellState.BLACK);

        // WHITE tokens to flip
        board.set(new Position(2, 0), CellState.WHITE);
        board.set(new Position(2, 3), CellState.WHITE);
        board.set(new Position(4, 3), CellState.WHITE);

        // Now all moves become valid:
        // (0,0), (0,3), (3,3)
        game.setCurrentPlayer(Player.WHITE);

        Position best = ai.chooseMove(game);

        assertEquals(new Position(0,0), best, "MediumAI must pick the corner (best score=100)");
    }

    @Test
    void testAIChoosesEdgeIfNoCornerAvailable() {
        MediumAI ai = new MediumAI();
        OthelloGame game = new OthelloGame();

        OthelloBoard board = game.getBoard();

        // Clear board
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
                board.set(new Position(r, c), CellState.EMPTY);

        // Setup two valid moves: edge (0,5) and center (3,3)
        board.set(new Position(1,5), CellState.BLACK);
        board.set(new Position(2,5), CellState.WHITE);

        board.set(new Position(2,3), CellState.WHITE);
        board.set(new Position(3,3), CellState.BLACK);

        game.setCurrentPlayer(Player.WHITE);

        Position best = ai.chooseMove(game);

        assertEquals(new Position(0,5), best, "MediumAI must prefer edge when no corner exists");
    }

    @Test
    void testAIChoosesCenterIfOnlyCenterMoves() {
        MediumAI ai = new MediumAI();
        OthelloGame game = new OthelloGame();

        OthelloBoard board = game.getBoard();

        // Clear board
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
                board.set(new Position(r, c), CellState.EMPTY);

        // Only one valid move at center (4,4)
        board.set(new Position(5,4), CellState.BLACK);
        board.set(new Position(6,4), CellState.WHITE);

        game.setCurrentPlayer(Player.WHITE);

        Position best = ai.chooseMove(game);

        assertEquals(new Position(4,4), best, "MediumAI must return the only available move");
    }

    @Test
    void testAIDoesNotModifyGameState() {
        MediumAI ai = new MediumAI();
        OthelloGame game = new OthelloGame();
        OthelloGame before = game.clone();

        ai.chooseMove(game);  // AI should not perform the move

        // Compare boards cell by cell
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
                assertEquals(before.getBoard().get(new Position(r,c)),
                        game.getBoard().get(new Position(r,c)),
                        "MediumAI must not modify board");

        assertEquals(before.getCurrentPlayer(), game.getCurrentPlayer(),
                "MediumAI must not change current player");
    }
}
