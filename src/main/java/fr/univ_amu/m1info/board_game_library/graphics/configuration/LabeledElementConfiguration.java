package fr.univ_amu.m1info.board_game_library.graphics.configuration;

/**
 * Record representing the configuration of a labeled element in a board game.
 * It stores the label, an identifier, and the type of the labeled element.
 *
 * @param label the text label of the element.
 * @param id the unique identifier for the element.
 * @param kind the type of the labeled element, represented by {@link LabeledElementKind}.
 */
public record LabeledElementConfiguration(String label, String id, LabeledElementKind kind) {
}
