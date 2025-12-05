package fr.univ_amu.m1info.othello.ai;

import fr.univ_amu.m1info.othello.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EasyAITest {

    @Test
    void testChooseMoveReturnsValidMove() {
        OthelloGame game = new OthelloGame();
        EasyAI ai = new EasyAI();

        Position move = ai.chooseMove(game);

        assertNotNull(move, "EasyAI should return a move at game start");

        List<Position> validMoves = game.getValidMoves(Player.WHITE);
        assertTrue(validMoves.contains(move), "AI must return a valid move");
    }

    @Test
    void testChooseMoveReturnsNullIfNoMoveAvailable() {
        OthelloGame game = new OthelloGame();
        EasyAI ai = new EasyAI();

        OthelloBoard board = game.getBoard();
        for (int r = 0; r < OthelloBoard.SIZE; r++)
            for (int c = 0; c < OthelloBoard.SIZE; c++)
                board.set(new Position(r, c), CellState.WHITE);

        assertNull(ai.chooseMove(game), "AI must return null when no moves");
    }

    @Test
    void testChooseMoveDoesNotModifyGameState() {
        OthelloGame game = new OthelloGame();
        EasyAI ai = new EasyAI();

        OthelloGame before = game.clone();

        ai.chooseMove(game);

        for (int r = 0; r < OthelloBoard.SIZE; r++) {
            for (int c = 0; c < OthelloBoard.SIZE; c++) {
                Position p = new Position(r, c);
                assertEquals(
                        before.getBoard().get(p),
                        game.getBoard().get(p),
                        "EasyAI must not modify the board"
                );
            }
        }

        assertEquals(before.getCurrentPlayer(), game.getCurrentPlayer(),
                "EasyAI must not change current player");
    }

    @Test
    void testRandomnessProducesDifferentMovesSometimes() {
        OthelloGame game = new OthelloGame();
        EasyAI ai = new EasyAI();

        Position m1 = ai.chooseMove(game);
        Position m2 = ai.chooseMove(game);

        List<Position> validMoves = game.getValidMoves(Player.WHITE);

        assertTrue(validMoves.contains(m1));
        assertTrue(validMoves.contains(m2));
    }
}
