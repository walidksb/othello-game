package fr.univ_amu.m1info.othello.service;

import fr.univ_amu.m1info.othello.model.*;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;

import java.io.*;

public class SaveLoadService {

    private static final String SAVE_FOLDER = "save";

    /** Ask user for save name, then save with difficulty included */
    public void askSaveName(OthelloGame game, Difficulty difficulty, PlayerType playerType) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Sauvegarde");
        dialog.setHeaderText("Nom de la sauvegarde :");
        dialog.setContentText("Nom :");

        dialog.showAndWait().ifPresent(name -> {
            if (!name.isBlank()) {
                save(game, difficulty,playerType, name);
            }
        });
    }

    /** Save complete game state (including difficulty) */
    public void save(OthelloGame game, Difficulty difficulty, PlayerType playerType, String name) {
        File folder = new File(SAVE_FOLDER);
        if (!folder.exists()) folder.mkdir();

        File file = new File(folder, name + ".othello");

        try (PrintWriter pw = new PrintWriter(file)) {

            // 1. difficulty
            pw.println(difficulty == null ? "EASY" : difficulty);

            // 2. PlayerType
            pw.println(playerType == null ? "AI" : playerType);

            // 3. current player
            pw.println(game.getCurrentPlayer());

            // 4. gameOver
            pw.println(game.isGameOver());

            // 5. board
            for (int r = 0; r < OthelloBoard.SIZE; r++) {
                for (int c = 0; c < OthelloBoard.SIZE; c++) {
                    CellState cs = game.getBoard().get(new Position(r, c));
                    pw.print(switch (cs) {
                        case EMPTY -> "E";
                        case BLACK -> "B";
                        case WHITE -> "W";
                    });
                }
                pw.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Ask user which save to load, then load it */
    public LoadedGameData askLoadNameAndLoad() {

        File folder = new File(SAVE_FOLDER);
        if (!folder.exists()) return null;

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".othello"));
        if (files == null || files.length == 0) return null;

        String[] names = new String[files.length];
        for (int i = 0; i < files.length; i++)
            names[i] = files[i].getName();

        ChoiceDialog<String> dialog = new ChoiceDialog<>(names[0], names);
        dialog.setTitle("Charger une partie");
        dialog.setHeaderText("Choisissez une sauvegarde :");
        dialog.setContentText("Sauvegarde :");

        final LoadedGameData[] result = {null};

        dialog.showAndWait().ifPresent(selected -> {
            File file = new File(folder, selected);
            result[0] = loadGameFile(file);
        });

        return result[0];
    }

    /** Loads complete game state AND difficulty into a container class */
    private LoadedGameData loadGameFile(File file) {

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            // Line 1: difficulty
            Difficulty diff = Difficulty.valueOf(br.readLine());

            // Line 2: PlayerType
            PlayerType playerType = PlayerType.valueOf(br.readLine());

            // Line 3: current player
            Player current = Player.valueOf(br.readLine());

            // Line 4: gameOver
            boolean over = Boolean.parseBoolean(br.readLine());

            // Create an empty game instance
            OthelloGame loaded = new OthelloGame();

            // Clear the board (because constructor initializes start position)
            for (int r = 0; r < OthelloBoard.SIZE; r++)
                for (int c = 0; c < OthelloBoard.SIZE; c++)
                    loaded.getBoard().set(new Position(r, c), CellState.EMPTY);

            loaded.setCurrentPlayer(current);
            loaded.setGameOver(over);

            // Load board state
            for (int r = 0; r < OthelloBoard.SIZE; r++) {
                String line = br.readLine();
                for (int c = 0; c < OthelloBoard.SIZE; c++) {
                    char ch = line.charAt(c);
                    CellState cs = switch (ch) {
                        case 'B' -> CellState.BLACK;
                        case 'W' -> CellState.WHITE;
                        default -> CellState.EMPTY;
                    };
                    loaded.getBoard().set(new Position(r, c), cs);
                }
            }

            // return both game and difficulty
            return new LoadedGameData(loaded, diff, playerType);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /** Small data container for loaded game + difficulty */
    public static class LoadedGameData {
        public final OthelloGame game;
        public final Difficulty difficulty;
        public final PlayerType  playerType;

        public LoadedGameData(OthelloGame game, Difficulty difficulty,  PlayerType playerType) {
            this.game = game;
            this.difficulty = difficulty;
            this.playerType = playerType;
        }
    }
}
