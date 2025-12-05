package fr.univ_amu.m1info.board_game_library.graphics.javafx.view;

public interface BoardGameViewBuilder {
    BoardGameViewBuilder resetView();
    BoardGameViewBuilder setBoardGameDimensions(int rowCount, int columnCount);
    BoardGameViewBuilder setTitle(String title);
    BoardGameViewBuilder addLabel(String id, String initialText);
    BoardGameViewBuilder addButton(String id, String label);
    BoardGameControllableView getView();
}
