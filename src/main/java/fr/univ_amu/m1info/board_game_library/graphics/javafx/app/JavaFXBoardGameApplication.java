package fr.univ_amu.m1info.board_game_library.graphics.javafx.app;

import fr.univ_amu.m1info.board_game_library.graphics.BoardGameController;
import fr.univ_amu.m1info.board_game_library.graphics.JavaFXBoardGameApplicationLauncher;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.BoardGameConfiguration;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.view.BoardGameConfigurator;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.view.BoardGameControllableView;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.view.JavaFXBoardGameViewBuilder;
import fr.univ_amu.m1info.othello.controller.OthelloController;
import fr.univ_amu.m1info.othello.model.Difficulty;
import fr.univ_amu.m1info.othello.model.PlayerType;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFXBoardGameApplication extends Application {

    @Override
    public void start(Stage stage) {
        showStartMenu(stage);
    }

    /* =======================================================================
       START MENU
       ======================================================================= */
    private void showStartMenu(Stage stage) {
        Button startBtn = new Button("Start Game");
        Button loadBtn = new Button("Load Game");

        startBtn.setMinWidth(200);
        loadBtn.setMinWidth(200);
        startBtn.setStyle("-fx-font-size:20px;");
        loadBtn.setStyle("-fx-font-size:20px;");

        startBtn.setOnAction(e -> showDifficultyMenu(stage));
        loadBtn.setOnAction(e -> loadSavedGame(stage));

        VBox root = new VBox(20, startBtn, loadBtn);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding:50; -fx-background-color:linear-gradient(#4CAF50,#2E7D32);");

        stage.setScene(new Scene(root, 400, 300));
        stage.setTitle("Othello");
        stage.show();
    }

    /* =======================================================================
       DIFFICULTY MENU (and Human vs Human)
       ======================================================================= */
    private void showDifficultyMenu(Stage stage) {
        Button easy = new Button("Easy");
        Button medium = new Button("Medium");
        Button hard = new Button("Hard");
        Button hvh = new Button("Human vs Human");

        easy.setMinWidth(200);
        medium.setMinWidth(200);
        hard.setMinWidth(200);
        hvh.setMinWidth(200);

        /* --- Human vs AI --- */
        easy.setOnAction(e -> startActualGame(stage, Difficulty.EASY, PlayerType.AI));
        medium.setOnAction(e -> startActualGame(stage, Difficulty.MEDIUM, PlayerType.AI));
        hard.setOnAction(e -> startActualGame(stage, Difficulty.HARD, PlayerType.AI));

        /* --- Human vs Human --- */
        hvh.setOnAction(e -> startHumanVsHuman(stage, PlayerType.HUMAN));

        VBox root = new VBox(20, easy, medium, hard, hvh);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding:50; -fx-background-color:linear-gradient(#4FC3F7,#0288D1);");

        stage.setScene(new Scene(root, 400, 350));
        stage.setTitle("Choisir le Mode de jeu");
        stage.show();
    }

    /* =======================================================================
       HUMAN VS HUMAN MODE
       ======================================================================= */
    private void startHumanVsHuman(Stage stage, PlayerType pt) {

        BoardGameConfiguration config = JavaFXBoardGameApplicationLauncher.getConfiguration();
        BoardGameController controller = JavaFXBoardGameApplicationLauncher.getController();

        JavaFXBoardGameViewBuilder viewBuilder = new JavaFXBoardGameViewBuilder(stage);
        new BoardGameConfigurator().configure(viewBuilder, config);

        BoardGameControllableView view = viewBuilder.getView();
        view.setController(controller);

        controller.initializeViewOnStart(view);

        if (controller instanceof OthelloController oc) {
            oc.setPlayerTypes(pt);
        }

        stage.sizeToScene();
        stage.show();
    }

    /* =======================================================================
       START GAME AFTER SELECTING AI DIFFICULTY
       ======================================================================= */
    private void startActualGame(Stage stage, Difficulty diff, PlayerType pt) {

        BoardGameConfiguration config = JavaFXBoardGameApplicationLauncher.getConfiguration();
        BoardGameController controller = JavaFXBoardGameApplicationLauncher.getController();

        JavaFXBoardGameViewBuilder viewBuilder = new JavaFXBoardGameViewBuilder(stage);
        new BoardGameConfigurator().configure(viewBuilder, config);
        BoardGameControllableView view = viewBuilder.getView();
        view.setController(controller);

        controller.initializeViewOnStart(view);

        if (controller instanceof OthelloController oc) {
            oc.setDifficulty(diff);
            oc.setPlayerTypes(pt);
        }

        stage.sizeToScene();
        stage.show();
    }

    /* =======================================================================
       LOAD SAVED GAME
       ======================================================================= */
    private void loadSavedGame(Stage stage) {

        BoardGameConfiguration config = JavaFXBoardGameApplicationLauncher.getConfiguration();
        BoardGameController controller = JavaFXBoardGameApplicationLauncher.getController();

        JavaFXBoardGameViewBuilder viewBuilder = new JavaFXBoardGameViewBuilder(stage);
        new BoardGameConfigurator().configure(viewBuilder, config);
        BoardGameControllableView view = viewBuilder.getView();
        view.setController(controller);

        controller.initializeViewOnStart(view);

        if (controller instanceof OthelloController oc) {
            oc.askLoadName();
        }

        stage.sizeToScene();
        stage.show();
    }
}
