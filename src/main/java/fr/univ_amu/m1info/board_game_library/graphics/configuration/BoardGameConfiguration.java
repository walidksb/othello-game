package fr.univ_amu.m1info.board_game_library.graphics.configuration;

import java.util.List;

/**
 * Record that represents the configuration of a board game.
 * It stores essential information such as the game's title, dimensions, and labeled elements.
 *
 * @param title the title of the board game.
 * @param dimensions the dimensions of the board, represented by {@link BoardGameDimensions}.
 * @param labeledElementConfigurations the list of configurations for labeled elements in the game,
 *                                     represented by {@link LabeledElementConfiguration}.
 */
public record BoardGameConfiguration(String title,
                                     BoardGameDimensions dimensions,
                                     List<LabeledElementConfiguration> labeledElementConfigurations) {
}
