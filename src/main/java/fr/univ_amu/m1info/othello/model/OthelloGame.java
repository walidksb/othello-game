package fr.univ_amu.m1info.othello.model;

import java.util.ArrayList;
import java.util.List;

public class OthelloGame {

    private OthelloBoard board;
    private Player currentPlayer;
    private boolean gameOver;

    private static final int[][] DIRS = {
            {-1,-1}, {-1,0}, {-1,1},
            { 0,-1},         { 0,1},
            { 1,-1}, { 1,0}, { 1,1}
    };

    public OthelloGame() {
        this.board = new OthelloBoard();
        this.currentPlayer = Player.BLACK;
        this.gameOver = false;
    }

    /** Réinitialise la partie. */
    public void reset() {
        board.reset();
        currentPlayer = Player.BLACK;
        gameOver = false;
    }

    /* ============ GETTERS / SETTERS ============ */

    public OthelloBoard getBoard() {
        return board;
    }

    /** Utilisé par GameState / GameHistory / Load. */
    public void setBoard(OthelloBoard newBoard) {
        this.board = newBoard.getCopy();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player p) {
        this.currentPlayer = p;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean over) {
        this.gameOver = over;
    }
    /* ======================== */

    @Override
    public OthelloGame clone() {
        OthelloGame copy = new OthelloGame();
        copy.board = this.board.getCopy();
        copy.currentPlayer = this.currentPlayer;
        copy.gameOver = this.gameOver;
        return copy;
    }

    public int count(Player player) {
        CellState cs = player.toCellState();
        int c = 0;
        for (int r = 0; r < OthelloBoard.SIZE; r++) {
            for (int c2 = 0; c2 < OthelloBoard.SIZE; c2++) {
                if (board.get(new Position(r, c2)) == cs) c++;
            }
        }
        return c;
    }

    public Player getWinnerOrNull() {
        if (!gameOver) return null;
        int b = count(Player.BLACK);
        int w = count(Player.WHITE);
        if (b == w) return null;
        return b > w ? Player.BLACK : Player.WHITE;
    }

    public boolean hasAnyValidMove(Player player) {
        for (int r = 0; r < OthelloBoard.SIZE; r++) {
            for (int c = 0; c < OthelloBoard.SIZE; c++) {
                if (isValidMove(new Position(r, c), player)) return true;
            }
        }
        return false;
    }

    public List<Position> getValidMoves(Player player) {
        List<Position> res = new ArrayList<>();
        for (int r = 0; r < OthelloBoard.SIZE; r++) {
            for (int c = 0; c < OthelloBoard.SIZE; c++) {
                Position pos = new Position(r, c);
                if (isValidMove(pos, player)) {
                    res.add(pos);
                }
            }
        }
        return res;
    }

    public boolean isValidMove(Position pos, Player player) {
        if (!board.inside(pos)) return false;
        if (board.get(pos) != CellState.EMPTY) return false;

        CellState me = player.toCellState();
        CellState opp = player.opposite().toCellState();

        for (int[] d : DIRS) {
            int r = pos.row() + d[0];
            int c = pos.column() + d[1];
            boolean hasOppBetween = false;

            while (board.inside(new Position(r, c)) &&
                    board.get(new Position(r, c)) == opp) {
                hasOppBetween = true;
                r += d[0];
                c += d[1];
            }

            if (hasOppBetween &&
                    board.inside(new Position(r, c)) &&
                    board.get(new Position(r, c)) == me) {
                return true;
            }
        }
        return false;
    }

    public boolean playMove(Position pos) {
        if (gameOver) return false;
        if (!isValidMove(pos, currentPlayer)) return false;

        List<Position> toFlip = collectFlips(pos, currentPlayer);

        board.set(pos, currentPlayer.toCellState());
        for (Position p : toFlip) {
            board.set(p, currentPlayer.toCellState());
        }

        advanceTurnAfterMove();
        return true;
    }

    private List<Position> collectFlips(Position pos, Player player) {
        List<Position> flips = new ArrayList<>();
        CellState me = player.toCellState();
        CellState opp = player.opposite().toCellState();

        for (int[] d : DIRS) {
            List<Position> line = new ArrayList<>();
            int r = pos.row() + d[0];
            int c = pos.column() + d[1];

            while (board.inside(new Position(r, c)) &&
                    board.get(new Position(r, c)) == opp) {
                line.add(new Position(r, c));
                r += d[0];
                c += d[1];
            }

            if (!line.isEmpty() &&
                    board.inside(new Position(r, c)) &&
                    board.get(new Position(r, c)) == me) {
                flips.addAll(line);
            }
        }
        return flips;
    }

    private void advanceTurnAfterMove() {
        Player next = currentPlayer.opposite();

        if (hasAnyValidMove(next)) {
            currentPlayer = next;
        } else if (!hasAnyValidMove(currentPlayer)) {
            gameOver = true;
        }
        // sinon : currentPlayer rejoue (skip adverse)
    }
}
