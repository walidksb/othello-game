package fr.univ_amu.m1info.othello.controller;

import fr.univ_amu.m1info.board_game_library.graphics.BoardGameController;
import fr.univ_amu.m1info.board_game_library.graphics.BoardGameView;
import fr.univ_amu.m1info.othello.model.*;
import fr.univ_amu.m1info.othello.service.*;
import fr.univ_amu.m1info.othello.view.OthelloViewAdapter;
import javafx.application.Platform;

public class OthelloController implements BoardGameController {

    private OthelloGame game;
    private final OthelloAIService aiService;
    private final SaveLoadService saveLoadService;
    private final GameHistoryService history;
    private OthelloViewAdapter viewAdapter;

    /** To Remember last difficulty **/
    private Difficulty difficulty;
    private PlayerType playertype;

    public OthelloController() {
        this.game = new OthelloGame();
        this.aiService = new OthelloAIService();
        this.saveLoadService = new SaveLoadService();
        this.history = new GameHistoryService();

        this.history.pushState(game);
        this.difficulty = null;
    }

    @Override
    public void initializeViewOnStart(BoardGameView view) {
        this.viewAdapter = new OthelloViewAdapter(view);
        refreshView();
    }

    private boolean isTestEnvironment() {
        return System.getProperty("test.env") != null;
    }

    /** Difficulty called from menu AFTER view exists */
    public void setDifficulty(Difficulty diff) {
        this.difficulty = diff;
        aiService.setAI(AIFactory.createAI(diff));
    }

    public void setPlayerTypes(PlayerType pt) {
        this.playertype = pt;
    }

    /* =======================================================================
       PLAYER ACTIONS
       ======================================================================= */
    @Override
    public void boardActionOnClick(int row, int column) {
        if (game.isGameOver()) return;

        Position pos = new Position(row, column);
        if(game.isValidMove(pos, game.getCurrentPlayer())) {
            history.pushState(game);
            if (game.playMove(pos)) {
                refreshView();
                if (!isTestEnvironment()
                        && !game.isGameOver()
                        && game.getCurrentPlayer() == Player.WHITE
                        && difficulty != null) {
                    playAITurn();
                    history.pushState(game);
                }

            }
        }
    }


    @Override
    public void buttonActionOnClick(String buttonId) {
        switch (buttonId) {

            case "reset" -> {
                game.reset();
                history.clear();
                refreshView();
            }

            case "undo" -> {
                game = history.undo(game);
                refreshView();
            }

            case "redo" -> {
                game = history.redo(game);
                refreshView();
            }

            case "save" -> {
                saveLoadService.askSaveName(game, difficulty, playertype);
            }
        }
    }

    /* =======================================================================
       AI TURN
       ======================================================================= */
    private void playAITurn() {
        if (isTestEnvironment()) return;
        if (aiService.getAI() == null) return; // no difficulty selected

        new Thread(() -> {
            try { Thread.sleep(250); } catch (Exception ignored) {}

            Position move = aiService.computeMove(game);

            Platform.runLater(() -> {
                if (move != null && !game.isGameOver()) {
                    game.playMove(move);
                    refreshView();
                }

                // If still white (AI) and not game over, keep playing
                if (!game.isGameOver() && game.getCurrentPlayer() == Player.WHITE) {
                    playAITurn();
                }
            });

        }).start();
    }

    /* =======================================================================
       SAVE / LOAD (called from JavaFXBoardGameApplication.loadSavedGame)
       ======================================================================= */
    public void askLoadName() {

        SaveLoadService.LoadedGameData data = saveLoadService.askLoadNameAndLoad();
        if (data == null) return;

        this.game = data.game;
        this.difficulty = data.difficulty;
        this.playertype = data.playerType;

        if(playertype == PlayerType.AI) {
            aiService.setAI(AIFactory.createAI(difficulty));
            System.out.println(difficulty + " game uploaded");
            history.clear();
            history.pushState(game);

            if (aiService.getAI() == null) {
                if (difficulty == null) {
                    System.out.println("[WARNING] Difficulty is NULL — defaulting to EASY.");
                    difficulty = Difficulty.EASY;
                }
                aiService.setAI(AIFactory.createAI(difficulty));
            }
        }
        if(playertype == PlayerType.HUMAN) {
            history.clear();
            history.pushState(game);
        }

        refreshView();

        if (!isTestEnvironment() && playertype == PlayerType.AI && game.getCurrentPlayer() == Player.WHITE && !game.isGameOver()) {
            playAITurn();
        }
    }

    /* =======================================================================
       VIEW UPDATE
       ======================================================================= */
    private void refreshView() {
        if (viewAdapter != null) {
            viewAdapter.updateBoard(game);
            viewAdapter.updateStatus(game);
        }
    }
}
