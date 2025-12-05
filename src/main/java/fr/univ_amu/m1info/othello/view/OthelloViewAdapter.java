package fr.univ_amu.m1info.othello.view;

import fr.univ_amu.m1info.board_game_library.graphics.*;
import fr.univ_amu.m1info.othello.model.*;
import javafx.scene.control.Alert;
import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;
public class OthelloViewAdapter {

    private final BoardGameView view;
    private boolean endPopupShown = false;

    public OthelloViewAdapter(BoardGameView view) {
        this.view = view;
    }

    public void updateBoard(OthelloGame game) {
        for (int r = 0; r < OthelloBoard.SIZE; r++) {
            for (int c = 0; c < OthelloBoard.SIZE; c++) {

                view.removeShapesAtCell(r, c);

                boolean isEven = (r + c) % 2 == 0;
                view.setCellColor(r, c, isEven ? Color.LIGHTGREEN : Color.DARKGREEN);

                CellState cs = game.getBoard().get(new Position(r, c));
                switch (cs) {
                    case BLACK -> view.addShapeAtCell(r, c, Shape.CIRCLE, Color.BLACK);
                    case WHITE -> view.addShapeAtCell(r, c, Shape.CIRCLE, Color.WHITE);
                }
            }
        }

        // highlight valid moves
        for (Position p : game.getValidMoves(game.getCurrentPlayer()))
            view.setCellColor(p.row(), p.column(), Color.LIGHTBLUE);
    }

    public void updateStatus(OthelloGame game) {
        int b = game.count(Player.BLACK);
        int w = game.count(Player.WHITE);

        view.updateLabeledElement("scoreLabel", "Score N:" + b + "  B:" + w);

        if (game.isGameOver()) {

            Player winner = game.getWinnerOrNull();
            String msg = (winner == null) ? "Égalité !" :
                    (winner == Player.BLACK ? "Noir gagne !" : "Blanc gagne !");

            view.updateLabeledElement("turnLabel", msg);

            if (!endPopupShown) {
                endPopupShown = true;

                Platform.runLater(() -> {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Résultat de la partie");
                    alert.setHeaderText("Fin de la partie !");
                    alert.setContentText(msg);
                    alert.showAndWait();
                });
            }

        } else {
            view.updateLabeledElement("turnLabel",
                    "Tour : " + (game.getCurrentPlayer() == Player.BLACK ? "Noir" : "Blanc"));
        }
    }
}
