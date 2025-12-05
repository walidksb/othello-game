package fr.univ_amu.m1info.board_game_library.graphics.javafx.color;


import fr.univ_amu.m1info.board_game_library.graphics.Color;

public class JavaFXColorMapper {
    private final static javafx.scene.paint.Color LIGHT_RED = javafx.scene.paint.Color.valueOf("#ff474c");

    private JavaFXColorMapper() {}

    public static javafx.scene.paint.Color getJavaFXColor(Color color) {
        return switch (color) {
            case BLUE -> javafx.scene.paint.Color.BLUE;
            case RED -> javafx.scene.paint.Color.RED;
            case GREEN -> javafx.scene.paint.Color.GREEN;
            case BLACK -> javafx.scene.paint.Color.BLACK;
            case WHITE -> javafx.scene.paint.Color.WHITE;
            case DARKRED -> javafx.scene.paint.Color.DARKRED;
            case DARKGREEN -> javafx.scene.paint.Color.DARKGREEN;
            case DARKBLUE -> javafx.scene.paint.Color.DARKBLUE;
            case LIGHTBLUE -> javafx.scene.paint.Color.LIGHTBLUE;
            case LIGHTGREEN -> javafx.scene.paint.Color.LIGHTGREEN;
            case LIGHT_RED -> LIGHT_RED;
        };
    }
}
