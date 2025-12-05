package fr.univ_amu.m1info.othello.ai;

import fr.univ_amu.m1info.othello.model.*;
import java.util.List;
import java.util.Random;

public class EasyAI implements OthelloAI {
    private final Random random = new Random();

    @Override
    public Position chooseMove(OthelloGame game) {
        List<Position> moves = game.getValidMoves(Player.WHITE);
        if (moves.isEmpty()) return null;
        return moves.get(random.nextInt(moves.size()));
    }
}
