package fr.univ_amu.m1info.othello.ai;

import fr.univ_amu.m1info.othello.model.*;
import java.util.List;

public class HardAI implements OthelloAI {

    private static final int MAX_DEPTH = 8;

    @Override
    public Position chooseMove(OthelloGame game) {

        List<Position> moves = game.getValidMoves(Player.WHITE);
        if (moves.isEmpty())
            return null;

        Position bestMove = null;
        int bestValue = Integer.MIN_VALUE;

        for (Position move : moves) {
            OthelloGame sim = game.clone();
            sim.playMove(move);

            int value = minimax(sim, MAX_DEPTH - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);

            if (value > bestValue) {
                bestValue = value;
                bestMove = move;
            }
        }
        return bestMove;
    }

    /** MINIMAX with alpha-beta pruning */
    private int minimax(OthelloGame game, int depth, int alpha, int beta, boolean maximizing) {

        if (depth == 0 || game.isGameOver())
            return evaluate(game);

        Player current = maximizing ? Player.WHITE : Player.BLACK;
        List<Position> moves = game.getValidMoves(current);

        if (moves.isEmpty()) {
            return minimax(game, depth - 1, alpha, beta, !maximizing);
        }

        if (maximizing) {
            int maxEval = Integer.MIN_VALUE;

            for (Position move : moves) {
                OthelloGame sim = game.clone();
                sim.playMove(move);

                int eval = minimax(sim, depth - 1, alpha, beta, false);
                maxEval = Math.max(maxEval, eval);

                alpha = Math.max(alpha, eval);
                if (beta <= alpha)
                    break;  // élagage

            }
            return maxEval;

        } else {
            int minEval = Integer.MAX_VALUE;

            for (Position move : moves) {
                OthelloGame sim = game.clone();
                sim.playMove(move);

                int eval = minimax(sim, depth - 1, alpha, beta, true);
                minEval = Math.min(minEval, eval);

                beta = Math.min(beta, eval);
                if (beta <= alpha)
                    break;

            }
            return minEval;
        }
    }

    /** Heuristic evaluation of the board */
    private int evaluate(OthelloGame game) {

        int score = 0;

        OthelloBoard board = game.getBoard();
        for (int r = 0; r < OthelloBoard.SIZE; r++) {
            for (int c = 0; c < OthelloBoard.SIZE; c++) {

                CellState cs = board.get(new Position(r, c));

                int weight = WEIGHTS[r][c];

                if (cs == CellState.WHITE)
                    score += weight;
                else if (cs == CellState.BLACK)
                    score -= weight;
            }
        }

        return score;
    }

    /** Positional weight table */
    private static final int[][] WEIGHTS = {
            {100, -20, 10, 5, 5, 10, -20, 100},
            {-20, -50, -2, -2, -2, -2, -50, -20},
            {10, -2, 5, 0, 0, 5, -2, 10},
            {5, -2, 0, 0, 0, 0, -2, 5},
            {5, -2, 0, 0, 0, 0, -2, 5},
            {10, -2, 5, 0, 0, 5, -2, 10},
            {-20, -50, -2, -2, -2, -2, -50, -20},
            {100, -20, 10, 5, 5, 10, -20, 100}
    };
}
