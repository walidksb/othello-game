package fr.univ_amu.m1info.othello.service;

import fr.univ_amu.m1info.othello.ai.*;
import fr.univ_amu.m1info.othello.model.Difficulty;

public class AIFactory {
    public static OthelloAI createAI(Difficulty diff) {
        return switch (diff) {
            case EASY -> new EasyAI();
            case MEDIUM -> new MediumAI();
            case HARD -> new HardAI();
        };
    }
}
