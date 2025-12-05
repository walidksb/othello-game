package fr.univ_amu.m1info.othello.service;

import fr.univ_amu.m1info.othello.model.*;
import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class SaveLoadServiceTest {

    private SaveLoadService service;
    private static final String TEST_NAME = "test_save";

    @BeforeEach
    public void setup() {
        service = new SaveLoadService();

        // Ensure folder exists
        File folder = new File("save");
        if (!folder.exists()) folder.mkdir();

        // Delete previous test file if present
        File file = new File("save/" + TEST_NAME + ".othello");
        if (file.exists()) file.delete();
    }

    @Test
    @DisplayName("Saving a game should create a file")
    public void testSaveCreatesFile() {

        OthelloGame game = new OthelloGame();

        service.save(game, Difficulty.HARD, PlayerType.AI, TEST_NAME);

        File file = new File("save/" + TEST_NAME + ".othello");
        assertTrue(file.exists(), "The save file should be created.");
    }


    @Test
    @DisplayName("Saved difficulty should be restored")
    public void testDifficultyIsSavedAndLoaded() {

        OthelloGame game = new OthelloGame();
        game.playMove(new Position(2, 3)); // some change

        service.save(game, Difficulty.MEDIUM, PlayerType.AI, TEST_NAME);

        File file = new File("save/" + TEST_NAME + ".othello");
        SaveLoadService.LoadedGameData data = callLoadGameFile(service, file);

        assertNotNull(data, "LoadedGameData should not be null.");
        assertEquals(Difficulty.MEDIUM, data.difficulty,
                "Difficulty should be restored correctly.");
    }


    @Test
    @DisplayName("Saved PlayerType should be restored")
    public void testPlayerTypeIsSavedAndLoaded() {
        OthelloGame game = new OthelloGame();
        game.playMove(new Position(2, 3));
        service.save(game, Difficulty.MEDIUM, PlayerType.HUMAN, TEST_NAME);
        File file = new File("save/" + TEST_NAME + ".othello");
        SaveLoadService.LoadedGameData data = callLoadGameFile(service, file);
        assertNotNull(data, "LoadedGameData should not be null.");
        assertEquals(PlayerType.HUMAN, data.playerType);
    }


    @Test
    @DisplayName("Loading must restore board, player and gameOver state")
    public void testLoadRestoresGameState() {

        OthelloGame game = new OthelloGame();

        // Modify game
        game.playMove(new Position(2, 3)); // changes pieces
        game.setCurrentPlayer(Player.WHITE);
        game.setGameOver(true);

        service.save(game, Difficulty.EASY, PlayerType.AI, TEST_NAME);

        File file = new File("save/" + TEST_NAME + ".othello");
        SaveLoadService.LoadedGameData data = callLoadGameFile(service, file);

        assertNotNull(data, "LoadedGameData should not be null.");
        OthelloGame loaded = data.game;

        // Check player
        assertEquals(Player.WHITE, loaded.getCurrentPlayer(),
                "CurrentPlayer should be restored.");

        // Check gameOver
        assertTrue(loaded.isGameOver(),
                "GameOver should be restored.");

        // Check board equality
        for (int r = 0; r < OthelloBoard.SIZE; r++) {
            for (int c = 0; c < OthelloBoard.SIZE; c++) {
                Position p = new Position(r, c);
                assertEquals(
                        game.getBoard().get(p),
                        loaded.getBoard().get(p),
                        "Board mismatch at " + p
                );
            }
        }
    }

    /** Helper to call private method loadGameFile() */
    private SaveLoadService.LoadedGameData callLoadGameFile(SaveLoadService service, File file) {
        try {
            var method = SaveLoadService.class.getDeclaredMethod("loadGameFile", File.class);
            method.setAccessible(true);
            return (SaveLoadService.LoadedGameData) method.invoke(service, file);
        } catch (Exception e) {
            fail("Unable to call loadGameFile via reflection: " + e);
            return null;
        }
    }
}
