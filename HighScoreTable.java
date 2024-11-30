import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the high scores for the game, allowing new entries to be added and saved to a file.
 */
public class HighScoreTable extends Application {
    private static final int MAX_HIGH_SCORES = 10;
    private int level;
    private List<PlayerProfile> highScores;

    /**
     * Constructs a new HighScoreTable with an empty list of high scores.
     */
    public HighScoreTable() {
        this.highScores = new ArrayList<>();
    }

    /**
     * Adds a new entry to the high score table and ensures the table only retains the top scores.
     *
     * @param playerProfile the player profile to add
     * @param currentLevel the current level of the game
     */
    public void addNewEntry(PlayerProfile playerProfile, int currentLevel) {
        this.level = currentLevel;
        highScores.add(playerProfile);
        if (highScores.size() > MAX_HIGH_SCORES) {
            highScores = highScores.subList(0, MAX_HIGH_SCORES);
        }
    }

    /**
     * Gets the list of high scores.
     *
     * @return the list of high scores
     */
    public List<PlayerProfile> getHighScores() {
        return highScores;
    }

    /**
     * Gets the top 5 high scores.
     *
     * @return the list of top 5 high scores
     */
    public List<PlayerProfile> getTop5HighScores() {
        return highScores.subList(0, Math.min(highScores.size(), 5));
    }

    /**
     * Starts the JavaFX application and sets up the high score display.
     *
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("High Scores");

        // Create a VBox to display high scores
        VBox highScoresBox = new VBox(10);
        highScoresBox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Retrieve and display top 5 high scores
        List<PlayerProfile> top5Scores = getTop5HighScores();
        for (int i = 0; i < top5Scores.size(); i++) {
            PlayerProfile profile = top5Scores.get(i);
            Label scoreLabel = new Label((i + 1) + ". " + profile.getName() + ": " + profile.getHighScore());
            highScoresBox.getChildren().add(scoreLabel);
        }

        // Create a "Back" button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.close()); // Close the high score window and go back to the previous menu

        BorderPane root = new BorderPane();
        root.setCenter(highScoresBox);
        root.setBottom(backButton);
        BorderPane.setMargin(backButton, new Insets(10, 0, 10, 10));

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
