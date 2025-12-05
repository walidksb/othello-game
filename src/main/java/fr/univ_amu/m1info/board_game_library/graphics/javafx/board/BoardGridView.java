package fr.univ_amu.m1info.board_game_library.graphics.javafx.board;

import fr.univ_amu.m1info.board_game_library.graphics.Color;
import fr.univ_amu.m1info.board_game_library.graphics.Shape;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.color.JavaFXColorMapper;
import javafx.scene.layout.GridPane;


public class BoardGridView extends GridPane {
    private final static int BASE_SQUARE_SIZE = 65;
    private SquareView[][] squareViews;
    private int rowCount;
    private int columnCount;
    private BoardActionOnClick boardActionOnClick;

    public BoardGridView() {
    }

    public void setDimensions(int rowCount, int columnCount) {
        squareViews = new SquareView[rowCount][columnCount];
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                addSquareView(row, column);
            }
        }
        setActionOnSquares();
    }

    private void addSquareView(int row, int column) {
        squareViews[row][column] = new SquareView(column, row, BASE_SQUARE_SIZE);
        this.add(squareViews[row][column], column, row);
    }

    public void setColorSquare(int row, int column, Color color) {
        squareViews[row][column].setColor(JavaFXColorMapper.getJavaFXColor(color));
    }

    public void setAction(BoardActionOnClick boardActionOnClick) {
        this.boardActionOnClick = boardActionOnClick;
        setActionOnSquares();
    }

    public void setActionOnSquares() {
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                squareViews[row][column].setAction(boardActionOnClick);
            }
        }
    }

    public void addShapeAtSquare(int row, int column, Shape shape, Color color) {
        squareViews[row][column].addShape(shape, color);
    }

    public void removeShapesAtSquare(int row, int column) {
        squareViews[row][column].removeShapes();
    }
}

