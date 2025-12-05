package fr.univ_amu.m1info.board_game_library.graphics.javafx.view;

import javafx.stage.Stage;

public class JavaFXBoardGameViewBuilder implements BoardGameViewBuilder {
    JavaFXBoardGameView boardGameView;

    public JavaFXBoardGameViewBuilder(Stage primaryStage) {
        boardGameView = new JavaFXBoardGameView(primaryStage);
    }

    public BoardGameViewBuilder resetView(){
        boardGameView.reset();
        boardGameView.getBoardGridView().setAction(boardGameView::boardActionOnclick);
        return this;
    }


    @Override
    public BoardGameViewBuilder setBoardGameDimensions(int rowCount, int columnCount) {
        boardGameView.getBoardGridView().setDimensions(rowCount, columnCount);
        return this;
    }

    @Override
    public BoardGameViewBuilder setTitle(String title) {
        boardGameView.getStage().setTitle(title);
        return this;
    }

    @Override
    public BoardGameViewBuilder addLabel(String id, String initialText) {
        boardGameView.getBar().addLabel(id, initialText);
        return this;
    }

    @Override
    public BoardGameViewBuilder addButton(String id, String label) {
        boardGameView.getBar().addButton(id, label);
        boardGameView.getBar().setButtonAction(id, ()->boardGameView.buttonActionOnclick(id));
        return this;
    }

    @Override
    public BoardGameControllableView getView() {
        return boardGameView;
    }

}
