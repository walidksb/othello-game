package fr.univ_amu.m1info.board_game_library.graphics.configuration;

/**
 * Record representing the dimensions of a board game.
 * It defines the number of rows and columns that the game board consists of.
 *
 * @param rowCount the number of rows on the game board.
 * @param columnCount the number of columns on the game board.
 */
public record BoardGameDimensions(int rowCount, int columnCount) {
}
