module fr.univ_amu.m1info.board_game_library {
    requires javafx.controls;
    requires java.desktop;

    exports fr.univ_amu.m1info.board_game_library.graphics;
    exports fr.univ_amu.m1info.othello;
    exports fr.univ_amu.m1info.board_game_library.graphics.javafx.app;
    exports fr.univ_amu.m1info.board_game_library.graphics.configuration;
    exports fr.univ_amu.m1info.board_game_library.graphics.javafx.view;
    exports fr.univ_amu.m1info.board_game_library.graphics.javafx.bar;
    exports fr.univ_amu.m1info.board_game_library.graphics.javafx.board;

}