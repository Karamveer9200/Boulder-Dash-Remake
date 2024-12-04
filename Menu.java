//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import javafx.stage.Modality;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * Manages the main menu of the game, providing options to start a new game, load a game, manage profiles, view high scores, and quit the game.
// */
//public class Menu extends Application {
//    private final Map<Integer, PlayerProfile> profiles;
//    private final HighScoreTable highScoreTable;
//    private final ProfileManager profileManager;
//    private List<PlayerProfile> profileList;
//    private int currentIndex;
//
//    /**
//     * Constructs a new Menu with an empty profiles map and a new HighScoreTable.
//     */
//    public Menu() {
//        this.profiles = ProfileManager.getProfiles();
//        this.highScoreTable = new HighScoreTable();
//        this.profileManager = new ProfileManager();
//        this.profileList = new ArrayList<>(profiles.values());
//        this.currentIndex = 0;
//    }
//
//    /**
//     * Returns the profiles map.
//     *
//     * @return the profiles map
//     */
//    public Map<Integer, PlayerProfile> getProfiles() {
//        return profiles;
//    }
//
//    /**
//     * Starts the JavaFX application and sets up the main menu.
//     *
//     * @param primaryStage the primary stage for this application
//     */
//    @Override
//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("Game Menu");
//
//        Button newGameButton = new Button("Start New Game");
//        newGameButton.setOnAction(e -> {
//            PlayerProfile newProfile = profileManager.promptForProfile(primaryStage);
//            profiles.put(newProfile.getPlayerId(), newProfile);
//            profileList = new ArrayList<>(profiles.values());
//            currentIndex = profileList.size() - 1; // Set current index to the newly added profile
//            ((Main) primaryStage.getUserData()).setupGame(primaryStage, newProfile);
//        });
//
//        Button loadGameButton = new Button("Load Game");
//        loadGameButton.setOnAction(e -> loadGame(primaryStage));
//
//        Button profileButton = new Button("Profile");
//        profileButton.setOnAction(e -> showProfileWindow(primaryStage));
//
//        Button highScoresButton = new Button("High Scores");
//        highScoresButton.setOnAction(e -> showHighScores(primaryStage));
//
//        Button quitButton = new Button("Quit Game");
//        quitButton.setOnAction(e -> closeGame());
//
//        VBox vbox = new VBox(10, newGameButton, loadGameButton, profileButton, highScoresButton, quitButton);
//        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");
//
//        Scene scene = new Scene(vbox, 400, 300);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    /**
//     * Loads an existing profile and starts the game.
//     *
//     * @param primaryStage the primary stage for this application
//     */
//    private void loadGame(Stage primaryStage) {
//        Stage dialog = new Stage();
//        dialog.setTitle("Load Game");
//        dialog.initModality(Modality.APPLICATION_MODAL);
//
//        VBox profileBox = new VBox(10);
//        profileBox.setStyle("-fx-padding: 20; -fx-alignment: center;");
//
//        profiles.values().forEach(profile -> {
//            Button profileButton = new Button(profile.getName());
//            profileButton.setOnAction(e -> {
//                dialog.close();
//                ((Main) primaryStage.getUserData()).setupGame(primaryStage, profile);
//            });
//            profileBox.getChildren().add(profileButton);
//        });
//
//        Scene dialogScene = new Scene(profileBox, 300, 200);
//        dialog.setScene(dialogScene);
//        dialog.showAndWait();
//    }
//
//    /**
//     * Displays the profile window with the ability to navigate through profiles.
//     *
//     * @param primaryStage the primary stage for this application
//     */
//    private void showProfileWindow(Stage primaryStage) {
//        if (profileList.isEmpty()) {
//            return;
//        }
//
//        Stage profileStage = new Stage();
//        profileStage.setTitle("Profile Details");
//
//        Label profileLabel = new Label();
//        updateProfileLabel(profileLabel);
//
//        Button previousButton = new Button("<");
//        previousButton.setOnAction(e -> {
//            if (currentIndex > 0) {
//                currentIndex--;
//                updateProfileLabel(profileLabel);
//            }
//        });
//
//        Button nextButton = new Button(">");
//        nextButton.setOnAction(e -> {
//            if (currentIndex < profileList.size() - 1) {
//                currentIndex++;
//                updateProfileLabel(profileLabel);
//            }
//        });
//
//        Button backButton = new Button("Back");
//        backButton.setOnAction(e -> {
//            profileStage.close();
//            primaryStage.show();
//        });
//
//        HBox navigationBox = new HBox(10, previousButton, profileLabel, nextButton);
//        navigationBox.setStyle("-fx-alignment: center;");
//
//        VBox profileBox = new VBox(10, navigationBox, backButton);
//        profileBox.setStyle("-fx-padding: 20; -fx-alignment: center;");
//
//        Scene profileScene = new Scene(profileBox, 400, 200);
//        profileStage.setScene(profileScene);
//        profileStage.show();
//    }
//
//    /**
//     * Updates the profile label with the current profile information.
//     *
//     * @param profileLabel the label to update
//     */
//    private void updateProfileLabel(Label profileLabel) {
//        PlayerProfile currentProfile = profileList.get(currentIndex);
//        profileLabel.setText("#" + currentProfile.getPlayerId() + " " + currentProfile.getName() +
//                "\nMax Level: " + currentProfile.getMaxLevelReached() +
//                "\nHigh Score: " + currentProfile.getHighScore());
//    }
//
//    /**
//     * Displays the high scores table.
//     *
//     * @param primaryStage the primary stage for this application
//     */
//    public void showHighScores(Stage primaryStage) {
//        VBox highScoresBox = new VBox(10);
//        highScoresBox.setStyle("-fx-padding: 20; -fx-alignment: center;");
//        highScoreTable.getHighScores().forEach(playerProfile -> {
//            Label scoreLabel = new Label(playerProfile.getName() + ": " + playerProfile.getHighScore());
//            highScoresBox.getChildren().add(scoreLabel);
//        });
//        Button backButton = new Button("Back");
//        backButton.setOnAction(e -> start(primaryStage));
//        highScoresBox.getChildren().add(backButton);
//        Scene highScoresScene = new Scene(highScoresBox, 400, 300);
//        primaryStage.setScene(highScoresScene);
//        primaryStage.setTitle("High Scores");
//        primaryStage.show();
//    }
//
//    /**
//     * Closes the game.
//     */
//    private void closeGame() {
//        System.exit(0);
//    }
//}
