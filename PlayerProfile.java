
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Represents a player's profile in the game, storing the player's name, maximum level reached, and high score.
 */
public class PlayerProfile extends Application {

    private static int idCounter = 1;
    private static final Map<Integer, PlayerProfile> profiles = new HashMap<>();

    private final int playerId;
    private String name;
    private int maxLevelReached;
    private static int highScore;

    /**
     * Constructs a new PlayerProfile with the specified name, maximum level reached, and high score.
     * Each profile is assigned a unique player ID.
     *
     * @param name the name of the player
     * @param maxLevelReached the maximum level reached by the player
     * @param highScore the player's high score
     */
    public PlayerProfile(String name, int maxLevelReached, int highScore) {
        this.playerId = idCounter++;
        this.name = name;
        this.maxLevelReached = maxLevelReached;
        PlayerProfile.highScore = highScore;
        profiles.put(this.playerId, this); // Add the new profile to the profiles map
    }

    /**
     * Gets the player's unique ID.
     *
     * @return the player's ID
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Gets the player's name.
     *
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the player's name.
     *
     * @param name the new name of the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the maximum level reached by the player.
     *
     * @return the maximum level reached
     */
    public int getMaxLevelReached() {
        return maxLevelReached;
    }

    /**
     * Sets the maximum level reached by the player.
     *
     * @param maxLevelReached the new maximum level reached
     */
    public void setMaxLevelReached(int maxLevelReached) {
        this.maxLevelReached = maxLevelReached;
    }

    /**
     * Gets the player's high score.
     *
     * @return the high score
     */
    public static int getHighScore() {
        return highScore;
    }

    public void updateHighScore(int newScore) {
        if (newScore > highScore) {
            highScore = newScore;
            System.out.println("New high score set for " + name + ": " + highScore);
        }
    }


    /**
     * Sets the player's high score.
     *
     * @param highScore the new high score
     */
    public void setHighScore(int highScore) {
        PlayerProfile.highScore = highScore;
    }

    /**
     * Displays the profile window with the player's information.
     *
     * @param mainStage the main stage to return to the menu
     */
    public void showProfileWindow(Stage mainStage) {
        Stage profileStage = new Stage();
        profileStage.setTitle("Profile Details");

        Label profileNumberLabel = new Label("#" + playerId + " " + name);
        profileNumberLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");

        Label maxLevelLabel = new Label("Highest Level Reached: " + maxLevelReached);
        Label highScoreLabel = new Label("High Score: " + highScore);

        // Back Button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            profileStage.close();
            mainStage.show();
        });

        BorderPane root = new BorderPane();
        VBox profileBox = new VBox(10, profileNumberLabel, maxLevelLabel, highScoreLabel);
        profileBox.setStyle("-fx-padding: 20; -fx-alignment: top;");
        root.setCenter(profileBox);
        root.setBottom(backButton);
        BorderPane.setMargin(backButton, new Insets(10));

        Scene profileScene = new Scene(root, 400, 300);
        profileStage.setScene(profileScene);
        profileStage.show();
    }

    /**
     * Prompts the user to enter a player name and creates a new profile.
     *
     * @param primaryStage the primary stage for the application
     * @return the created PlayerProfile
     */
    public static PlayerProfile promptForProfile(Stage primaryStage) {
        Stage dialog = new Stage();
        dialog.setTitle("Create Profile");
        dialog.initModality(Modality.APPLICATION_MODAL);

        Label nameLabel = new Label("Enter Name:");
        TextField nameField = new TextField();

        final PlayerProfile[] newProfile = new PlayerProfile[1];

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String name = nameField.getText();
            newProfile[0] = new PlayerProfile(name, 0, 0); // Create new profile with default values
            dialog.close();
        });

        VBox dialogBox = new VBox(10, nameLabel, nameField, saveButton);
        dialogBox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene dialogScene = new Scene(dialogBox, 300, 150);
        dialog.setScene(dialogScene);
        dialog.showAndWait();

        return newProfile[0];
    }

    /**
     * Loads an existing profile by name.
     *
     * @param name the name of the profile to load
     * @return the loaded profile, or null if not found
     */
    public static Optional<PlayerProfile> loadProfile(String name) {
        return profiles.values().stream().filter(profile -> profile.getName().equals(name)).findFirst();
    }

    /**
     * Returns all profiles.
     *
     * @return the collection of all profiles
     */
    public static Collection<PlayerProfile> getAllProfiles() {
        return profiles.values();
    }

    @Override
    public void start(Stage primaryStage) {
        // For testing purposes only, you can run this class standalone.
        showProfileWindow(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}