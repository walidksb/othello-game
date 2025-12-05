package fr.univ_amu.m1info.othello.ai;

import fr.univ_amu.m1info.othello.model.Position;
import fr.univ_amu.m1info.othello.model.OthelloGame;

public interface OthelloAI {
    Position chooseMove(OthelloGame game);
}
