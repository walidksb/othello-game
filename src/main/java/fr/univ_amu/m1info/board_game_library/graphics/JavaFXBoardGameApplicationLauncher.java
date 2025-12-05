package fr.univ_amu.m1info.board_game_library.graphics;

import fr.univ_amu.m1info.board_game_library.graphics.configuration.BoardGameConfiguration;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.app.JavaFXBoardGameApplication;
import javafx.application.Application;

/**
 * Updated launcher: stores configuration & controller as STATIC
 * so they are available when JavaFX starts.
 */
public class JavaFXBoardGameApplicationLauncher implements BoardGameApplicationLauncher {

    private static JavaFXBoardGameApplicationLauncher instance;

    /** Must be static so JavaFXBoardGameApplication.start() can read them safely */
    private static BoardGameConfiguration configuration;
    private static BoardGameController controller;

    private JavaFXBoardGameApplicationLauncher() {}

    public static JavaFXBoardGameApplicationLauncher getInstance() {
        if (instance == null) instance = new JavaFXBoardGameApplicationLauncher();
        return instance;
    }

    /** STATIC GETTERS used inside JavaFXBoardGameApplication */
    public static BoardGameConfiguration getConfiguration() {
        return configuration;
    }

    public static BoardGameController getController() {
        return controller;
    }

    /**
     * Store configuration + controller BEFORE JavaFX boots.
     * This fixes all null problems in the menus.
     */
    @Override
    public void launchApplication(BoardGameConfiguration config,
                                  BoardGameController ctrl) {

        System.out.println("Launcher: storing configuration + controller");

        JavaFXBoardGameApplicationLauncher.configuration = config;
        JavaFXBoardGameApplicationLauncher.controller = ctrl;

        // Start JavaFX ONLY ONCE
        Application.launch(JavaFXBoardGameApplication.class);
    }
}
