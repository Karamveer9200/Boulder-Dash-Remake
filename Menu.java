import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Modality;




import java.util.HashMap;
import java.util.Map;

/**
 * Manages the main menu of the game, providing options to start a new game, load a game, manage profiles, view high scores, and quit the game.
 */
public class Menu extends Application {
    private final Map<Integer, PlayerProfile> profiles;
    private final HighScoreTable highScoreTable;

    /**
     * Constructs a new Menu with an empty profiles map and a new HighScoreTable.
     */
    public Menu() {
        this.profiles = new HashMap<>();
        this.highScoreTable = new HighScoreTable();
    }

    /**
     * Returns the profiles map.
     *
     * @return the profiles map
     */
    public Map<Integer, PlayerProfile> getProfiles() {
        return profiles;
    }

    /**
     * Starts the JavaFX application and sets up the main menu.
     *
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Game Menu");

        Button newGameButton = new Button("Start New Game");
        newGameButton.setOnAction(e -> {
            PlayerProfile newProfile = PlayerProfile.promptForProfile(primaryStage);
            profiles.put(newProfile.getPlayerId(), newProfile);
            ((Main) primaryStage.getUserData()).setupGame(primaryStage, newProfile);
        });

        Button loadGameButton = new Button("Load Game");
        loadGameButton.setOnAction(e -> loadGame(primaryStage));

        Button profileButton = new Button("Profile");
        profileButton.setOnAction(e -> {
            if (!profiles.isEmpty()) {
                profiles.values().iterator().next().showProfileWindow(primaryStage); // Show the first profile for testing
            }
        });

        Button highScoresButton = new Button("High Scores");
        highScoresButton.setOnAction(e -> showHighScores(primaryStage));

        Button quitButton = new Button("Quit Game");
        quitButton.setOnAction(e -> closeGame());

        VBox vbox = new VBox(10, newGameButton, loadGameButton, profileButton, highScoresButton, quitButton);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Loads an existing profile and starts the game.
     *
     * @param primaryStage the primary stage for this application
     */
    private void loadGame(Stage primaryStage) {
        Stage dialog = new Stage();
        dialog.setTitle("Load Game");
        dialog.initModality(Modality.APPLICATION_MODAL);

        VBox profileBox = new VBox(10);
        profileBox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        profiles.values().forEach(profile -> {
            Button profileButton = new Button(profile.getName());
            profileButton.setOnAction(e -> {
                dialog.close();
                ((Main) primaryStage.getUserData()).setupGame(primaryStage, profile);
            });
            profileBox.getChildren().add(profileButton);
        });

        Scene dialogScene = new Scene(profileBox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }

    /**
     * Displays the high scores table.
     *
     * @param primaryStage the primary stage for this application
     */
    public void showHighScores(Stage primaryStage) {
        VBox highScoresBox = new VBox(10);
        highScoresBox.setStyle("-fx-padding: 20; -fx-alignment: center;");
        highScoreTable.getHighScores().forEach(playerProfile -> {
            Label scoreLabel = new Label(playerProfile.getName() + ": " + playerProfile.getHighScore());
            highScoresBox.getChildren().add(scoreLabel);
        });
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> start(primaryStage));
        highScoresBox.getChildren().add(backButton);
        Scene highScoresScene = new Scene(highScoresBox, 400, 300);
        primaryStage.setScene(highScoresScene);
        primaryStage.setTitle("High Scores");
        primaryStage.show();
    }


    /**
     * Closes the game.
     */
    private void closeGame() {
        System.exit(0);
    }
}