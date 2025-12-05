package fr.univ_amu.m1info.board_game_library.graphics;

/**
 * Interface representing the view for a board game.
 * It defines methods for updating and managing the visual elements of the game,
 * such as board cells, labels, and buttons.
 */
public interface BoardGameView {

    /**
     * Updates the text of a labeled element in the game view.
     *
     * @param id the identifier of the labeled element to update.
     * @param newText the new text to display.
     */
    void updateLabeledElement(String id, String newText);

    /**
     * Sets the color of a specific cell on the game board.
     *
     * @param row the row of the cell to update.
     * @param column the column of the cell to update.
     * @param color the new color to apply to the cell.
     */
    void setCellColor(int row, int column, Color color);

    /**
     * Adds a shape to a specific cell on the game board.
     *
     * @param row the row of the cell where the shape will be added.
     * @param column the column of the cell where the shape will be added.
     * @param shape the shape to add.
     * @param color the color of the shape.
     */
    void addShapeAtCell(int row, int column, Shape shape, Color color);

    /**
     * Removes all shapes from a specific cell on the game board.
     *
     * @param row the row of the cell to clear.
     * @param column the column of the cell to clear.
     */
    void removeShapesAtCell(int row, int column);

    /**
     * Removes all shapes from all cells of the board.
     */
    void resetBoard();

    /**
     * Removes a labeled element from the game view.
     *
     * @param id the identifier of the labeled element to remove.
     */
    void removeLabeledElement(String id);

    /**
     * Adds a new label to the game view.
     *
     * @param label the text to display in the label.
     * @param id the unique identifier to assign to the label.
     */
    void addLabel(String label, String id);

    /**
     * Adds a new button to the game view.
     *
     * @param label the text to display on the button.
     * @param id the unique identifier to assign to the button.
     */
    void addButton(String label, String id);
}
