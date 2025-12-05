package fr.univ_amu.m1info.othello.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OthelloBoardTest {

    @Test
    void testInitialSetup() {
        OthelloBoard board = new OthelloBoard();

        // Initial four pieces
        assertEquals(CellState.WHITE, board.get(new Position(3, 3)));
        assertEquals(CellState.BLACK, board.get(new Position(3, 4)));
        assertEquals(CellState.BLACK, board.get(new Position(4, 3)));
        assertEquals(CellState.WHITE, board.get(new Position(4, 4)));

        // All other cells must be empty
        for (int r = 0; r < OthelloBoard.SIZE; r++) {
            for (int c = 0; c < OthelloBoard.SIZE; c++) {
                if (!((r == 3 && c == 3) ||
                        (r == 3 && c == 4) ||
                        (r == 4 && c == 3) ||
                        (r == 4 && c == 4))) {
                    assertEquals(CellState.EMPTY, board.get(new Position(r, c)));
                }
            }
        }
    }

    @Test
    void testGetAndSet() {
        OthelloBoard board = new OthelloBoard();
        Position pos = new Position(2, 5);

        board.set(pos, CellState.BLACK);
        assertEquals(CellState.BLACK, board.get(pos));

        board.set(pos, CellState.WHITE);
        assertEquals(CellState.WHITE, board.get(pos));
    }

    @Test
    void testInside() {
        OthelloBoard board = new OthelloBoard();

        assertTrue(board.inside(new Position(0, 0)));
        assertTrue(board.inside(new Position(7, 7)));
        assertTrue(board.inside(new Position(3, 4)));

        assertFalse(board.inside(new Position(-1, 0)));
        assertFalse(board.inside(new Position(0, -1)));
        assertFalse(board.inside(new Position(8, 3)));
        assertFalse(board.inside(new Position(3, 8)));
    }

    @Test
    void testReset() {
        OthelloBoard board = new OthelloBoard();

        // Modify some cells
        board.set(new Position(0, 0), CellState.BLACK);
        board.set(new Position(7, 7), CellState.WHITE);

        board.reset();

        // Reset must restore ALL initial values
        assertEquals(CellState.EMPTY, board.get(new Position(0, 0)));
        assertEquals(CellState.EMPTY, board.get(new Position(7, 7)));

        assertEquals(CellState.WHITE, board.get(new Position(3, 3)));
        assertEquals(CellState.BLACK, board.get(new Position(3, 4)));
        assertEquals(CellState.BLACK, board.get(new Position(4, 3)));
        assertEquals(CellState.WHITE, board.get(new Position(4, 4)));
    }

    @Test
    void testGetCopyProducesDeepCopy() {
        OthelloBoard board = new OthelloBoard();
        OthelloBoard copy = board.getCopy();

        // They should not be the same object
        assertNotSame(board, copy);

        // But should have identical contents
        for (int r = 0; r < OthelloBoard.SIZE; r++) {
            for (int c = 0; c < OthelloBoard.SIZE; c++) {
                Position p = new Position(r, c);
                assertEquals(board.get(p), copy.get(p));
            }
        }

        // Modify original → must NOT modify copy
        board.set(new Position(0, 0), CellState.BLACK);

        assertEquals(CellState.BLACK, board.get(new Position(0, 0)));
        assertEquals(CellState.EMPTY, copy.get(new Position(0, 0))); // unchanged
    }
}
