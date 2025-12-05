package fr.univ_amu.m1info.othello.service;

import fr.univ_amu.m1info.othello.ai.OthelloAI;
import fr.univ_amu.m1info.othello.model.*;

public class OthelloAIService {

    private OthelloAI ai;

    public void setAI(OthelloAI ai) {
        this.ai = ai;
    }

    public OthelloAI getAI() {
        return ai;
    }

    public Position computeMove(OthelloGame game) {
        if (ai == null) return null;
        return ai.chooseMove(game);
    }
}
