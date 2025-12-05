package fr.univ_amu.m1info.othello.ai;

import fr.univ_amu.m1info.othello.model.*;
import java.util.List;

public class MediumAI implements OthelloAI {
    @Override
    public Position chooseMove(OthelloGame game) {
        List<Position> moves = game.getValidMoves(Player.WHITE);
        if(moves.isEmpty()) return null;

        Position best = null;
        int bestScore = -999;

        for (Position p : moves) {
            int score = evaluate(game, p);
            if (score > bestScore) {
                bestScore = score;
                best = p;
            }
        }
        return best;
    }

    private int evaluate(OthelloGame game, Position p) {
        // Simple heuristic: corners > edges > inner
        if (isCorner(p)) return 100;
        if (isEdge(p)) return 10;
        return 1;
    }

    private boolean isCorner(Position p) {
        return (p.row() == 0 && p.column() == 0)
                || (p.row() == 0 && p.column() == 7)
                || (p.row() == 7 && p.column() == 0)
                || (p.row() == 7 && p.column() == 7);
    }

    private boolean isEdge(Position p) {
        return p.row() == 0 || p.row() == 7 || p.column() == 0 || p.column() == 7;
    }
}
