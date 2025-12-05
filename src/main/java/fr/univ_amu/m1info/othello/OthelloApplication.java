package fr.univ_amu.m1info.othello;

import fr.univ_amu.m1info.board_game_library.graphics.BoardGameApplicationLauncher;
import fr.univ_amu.m1info.board_game_library.graphics.JavaFXBoardGameApplicationLauncher;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.BoardGameConfiguration;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.BoardGameDimensions;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.LabeledElementConfiguration;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.LabeledElementKind;
import fr.univ_amu.m1info.othello.controller.OthelloController;

import java.util.List;

/**
 * This class no longer launches JavaFX itself.
 * It simply prepares:
 *  - the board configuration
 *  - the Othello controller
 * and triggers the JavaFXBoardGameApplication via the launcher.
 */
public class OthelloApplication {

    /** Called from your IDE or command line. */
    public static void main(String[] args) {
        launchGame();
    }

    /** Launches the game (menus will be shown by JavaFXBoardGameApplication). */
    public static void launchGame() {

        BoardGameConfiguration configuration = new BoardGameConfiguration(
                "Jeu d'Othello",
                new BoardGameDimensions(8, 8),
                List.of(
                        new LabeledElementConfiguration("Tour : Noir", "turnLabel", LabeledElementKind.TEXT),
                        new LabeledElementConfiguration("Score N:2  B:2", "scoreLabel", LabeledElementKind.TEXT),
                        new LabeledElementConfiguration("Reset", "reset", LabeledElementKind.BUTTON),
                        new LabeledElementConfiguration("Undo", "undo", LabeledElementKind.BUTTON),
                        new LabeledElementConfiguration("Redo", "redo", LabeledElementKind.BUTTON),
                        new LabeledElementConfiguration("Save",  "save",  LabeledElementKind.BUTTON))

        );

        OthelloController controller = new OthelloController();

        BoardGameApplicationLauncher launcher = JavaFXBoardGameApplicationLauncher.getInstance();
        launcher.launchApplication(configuration, controller);
    }
}
