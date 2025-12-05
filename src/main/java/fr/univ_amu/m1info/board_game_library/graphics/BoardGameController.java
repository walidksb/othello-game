package fr.univ_amu.m1info.board_game_library.graphics;

/**
 * Interface representing the controller for a board game.
 * It handles user interactions with the game view.
 */
public interface BoardGameController {

    /**
     * Method called when the user clicks on a cell in the game board.
     *
     * @param row the row of the clicked cell.
     * @param column the column of the clicked cell.
     */
    void boardActionOnClick(int row, int column);

    /**
     * Method called when the user clicks on a specific button.
     *
     * @param buttonId the identifier of the clicked button.
     */
    void buttonActionOnClick(String buttonId);

    /**
     * Initialize the view, method called at the start of the application.
     *
     * @param view the game view to be initialized, implemented by the {@link BoardGameView} interface.
     */
    void initializeViewOnStart(BoardGameView view);


}
