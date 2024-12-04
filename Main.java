import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * Main sets up the GUI and initializes everything for a game to take place.
 */
public class Main extends Application {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 500;
	public static final int GRID_CELL_WIDTH = 50;
	public static final int GRID_CELL_HEIGHT = 50;

	// Timeline for periodic ticks
	private Timeline playerTickTimeline;
	private Timeline dangerousRockFallTickTimeline;
	private Timeline dangerousRockRollTimeline;
	private Timeline diamondFallTickTimeline;
	private Timeline diamondRollTimeline;
	private Timeline frogTickTimeline;
	private Timeline amoebaTickTimeline;

	private ArrayList<PlayerProfile> profiles = new ArrayList<>();
	private PlayerProfile currentProfile;

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Game Menu");

		VBox menuBox = new VBox(10);

		Scene menuScene = new Scene(menuBox, WINDOW_WIDTH, WINDOW_HEIGHT);
		primaryStage.setScene(menuScene);

		profiles = ProfileManager.getAvailableProfiles(); //Load all player profiles in txt to

		// Set up menu buttons
		Button newGameButton = new Button("Start New Game");
		newGameButton.setOnAction(e -> {
            currentProfile = ProfileManager.promptForProfile(primaryStage);
			setupGame(primaryStage);
		});

		Button loadGameButton = new Button("Load Game");
		loadGameButton.setOnAction(e -> {
			Stage dialog = new Stage();
			dialog.setTitle("Choose a player profile");

			ComboBox<String> profileDropdown = new ComboBox<>();
			for (PlayerProfile profile : profiles) {
				profileDropdown.getItems().add(profile.getName()); // Add each profile name to the dropdown
			}

			Button selectButton = new Button("Select");
			selectButton.setOnAction(event -> {
				String selectedProfileName = profileDropdown.getValue(); // Get the selected profile name
				if (selectedProfileName != null) {
					//HERE WE LOAD EITHER THE MAX LEVEL OR THEIR SAVED LEVEL
					dialog.close();
				} else {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("No Selection");
					alert.setHeaderText("No Profile Selected");
					alert.setContentText("Please select a profile before proceeding.");
					alert.showAndWait();
				}
			});

			VBox layout = new VBox(10);
			layout.getChildren().addAll(new Label("Select a player profile:"), profileDropdown, selectButton);
			layout.setAlignment(Pos.CENTER);

			Scene scene = new Scene(layout, 300, 200);
			dialog.setScene(scene);
			dialog.show();
		});

		Button profileButton = new Button("Delete Player Profile");
		profileButton.setOnAction(e -> {
			Stage dialog = new Stage();
			dialog.setTitle("Delete a player profile");

			ComboBox<String> profileDropdown = new ComboBox<>();
			for (PlayerProfile profile : profiles) {
				profileDropdown.getItems().add(profile.getName()); // Add each profile name to the dropdown
			}

			Button selectButton = new Button("Delete");
			selectButton.setStyle("-fx-background-color: red;"); // Red background, white text
			selectButton.setOnAction(event -> {
				String selectedProfileName = profileDropdown.getValue(); // Get the selected profile name
				if (selectedProfileName != null) {
					for (PlayerProfile profile : profiles) {
						if (profile.getName().equals(selectedProfileName)) {
							int idToDelete = profile.getPlayerId();
							profiles.remove(profile);
							ProfileManager.deleteProfile(idToDelete);
						}
					}
					dialog.close();
				} else {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("No Selection");
					alert.setHeaderText("No Profile Selected");
					alert.setContentText("Please select a profile before proceeding.");
					alert.showAndWait();
				}
			});

			VBox layout = new VBox(10);
			layout.getChildren().addAll(new Label("Delete a player profile:"), profileDropdown, selectButton);
			layout.setAlignment(Pos.CENTER);

			Scene scene = new Scene(layout, 300, 200);
			dialog.setScene(scene);
			dialog.show();

		});

		Button highScoresButton = new Button("High Scores");
		highScoresButton.setOnAction(e -> {
			Stage dialog = new Stage();
			dialog.setTitle("High Scores");
			dialog.initModality(Modality.APPLICATION_MODAL);

			Button highScores1Button = new Button("Level 1 High Scores");
			highScores1Button.setOnAction(eventHS1 -> {
				HighScoreTableManager.displayHighScoreTable(1);
				dialog.close();
			});

			Button highScores2Button = new Button("Level 2 High Scores");
			highScores2Button.setOnAction(eventHS2 -> {
				HighScoreTableManager.displayHighScoreTable(2);
				dialog.close();
			});

			Button highScores3Button = new Button("Level 3 High Scores");
			highScores3Button.setOnAction(eventHS3 -> {
				HighScoreTableManager.displayHighScoreTable(3);
				dialog.close();
			});

			VBox dialogBox = new VBox(10, highScores1Button, highScores2Button, highScores3Button);
			dialogBox.setStyle("-fx-padding: 20; -fx-alignment: center;");

			Scene dialogScene = new Scene(dialogBox, 300, 150);
			dialog.setScene(dialogScene);
			dialog.showAndWait();
		});

		Button quitButton = new Button("Quit Game");
		quitButton.setOnAction(e -> closeGame());

		menuBox.getChildren().addAll(newGameButton, loadGameButton, profileButton, highScoresButton, quitButton);
		menuBox.setStyle("-fx-padding: 20; -fx-alignment: center;");

		// Show the menu
		primaryStage.show();
	}

	/**
	 * Sets up the game interface and initializes everything for a game to take place.
	 *
	 * @param primaryStage the primary stage for the game
	 */
	public void setupGame(Stage primaryStage) {
		// Load the initial grid from a file
		int[][] initialGrid = FileHandler.readFile("PlaceHolder.txt");

		final int canvasWidth = initialGrid[0].length * GRID_CELL_WIDTH;
		final int canvasHeight = initialGrid.length * GRID_CELL_HEIGHT;

		// Create the canvas
		Canvas canvas = new Canvas(canvasWidth, canvasHeight);

		// Initialize the game controller with the grid and canvas
		GameController gameController = new GameController(initialGrid, canvas);

		// Build the GUI
		Pane root = buildGUI(gameController);

		// Create a scene and register key press events
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			gameController.registerInput(event.getCode());
			event.consume();
		});

		KeyFrame playerKeyFrame = new KeyFrame(Duration.millis(50), event -> {
			gameController.playerTick();
		});

		KeyFrame dangerousRocksRollKeyFrame = new KeyFrame(Duration.millis(1500), event -> {
			gameController.boulderRollTick();
			gameController.diamondRollTick();
		});

		KeyFrame dangerousRocksFallKeyFrame = new KeyFrame(Duration.millis(500), event -> {
			gameController.boulderFallTick();
			gameController.diamondFallTick();
		});

		KeyFrame frogKeyFrame = new KeyFrame(Duration.millis(1000), event -> {
			gameController.frogTick();
		});

		KeyFrame amoebaKeyFrame = new KeyFrame(Duration.millis(1000), event -> {
			gameController.amoebaTick();
		});

		// Set up the periodic tick timeline
		playerTickTimeline = new Timeline(playerKeyFrame);
		dangerousRockFallTickTimeline = new Timeline(dangerousRocksFallKeyFrame);
		dangerousRockRollTimeline = new Timeline(dangerousRocksRollKeyFrame);
		frogTickTimeline = new Timeline(frogKeyFrame);
		amoebaTickTimeline = new Timeline(amoebaKeyFrame);
		playerTickTimeline.setCycleCount(Animation.INDEFINITE);
		dangerousRockFallTickTimeline.setCycleCount(Animation.INDEFINITE);
		dangerousRockRollTimeline.setCycleCount(Animation.INDEFINITE);
		frogTickTimeline.setCycleCount(Animation.INDEFINITE);
		amoebaTickTimeline.setCycleCount(Animation.INDEFINITE);

		// Draw the initial grid
		gameController.draw();

		// Show the stage
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private Pane buildGUI(GameController gameController) {
		BorderPane root = new BorderPane();

		// Add the canvas to the center
		root.setCenter(gameController.getCanvas());

		// Create a toolbar with buttons
		HBox toolbar = new HBox(10);
		toolbar.setPadding(new Insets(10));

		Button resetButton = new Button("Reset Player");
		resetButton.setOnAction(e -> {
			gameController.resetPlayerLocation();
			gameController.draw();
		});

		Button centerButton = new Button("Center Player");
		centerButton.setOnAction(e -> {
			gameController.movePlayerToCenter();
			gameController.draw();
		});

		Button startTickButton = new Button("Start Ticks");
		Button stopTickButton = new Button("Stop Ticks");
		stopTickButton.setDisable(true);

		startTickButton.setOnAction(e -> {
			playerTickTimeline.play();
			dangerousRockFallTickTimeline.play();
			dangerousRockRollTimeline.play();
			frogTickTimeline.play();
			amoebaTickTimeline.play();
			startTickButton.setDisable(true);
			stopTickButton.setDisable(false);
		});

		stopTickButton.setOnAction(e -> {
			playerTickTimeline.stop();
			dangerousRockRollTimeline.stop();
			dangerousRockFallTickTimeline.stop();
			frogTickTimeline.stop();
			amoebaTickTimeline.stop();
			stopTickButton.setDisable(true);
			startTickButton.setDisable(false);
		});

		Button resetGridButton = new Button("Reset Grid");
		resetGridButton.setOnAction(e -> {
			int[][] initialGrid = FileHandler.readFile("PlaceHolder.txt");
			gameController.getGridManager().initializeGrid(initialGrid);
			gameController.initializePlayer(initialGrid);
			gameController.draw();
		});

		toolbar.getChildren().addAll(resetButton, centerButton, startTickButton, stopTickButton, resetGridButton);
		root.setTop(toolbar);

		return root;
	}

	/**
	 * Closes the game.
	 */
	private void closeGame() {
		System.exit(0);
	}

	/**
	 * Displays the profile details in a new window.
	 *
	 * @param mainStage the main stage of the application
	 * @param profile the player profile to display
	 */
	public void showProfileWindow(Stage mainStage, PlayerProfile profile) {
		Stage profileStage = new Stage();
		profileStage.setTitle("Profile Details");

		Label profileNumberLabel = new Label("#" + profile.getPlayerId() + " " + profile.getName());
		profileNumberLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");

		Label maxLevelLabel = new Label("Highest Level Reached: " + profile.getMaxLevelReached());

		Button backButton = new Button("Back");
		backButton.setOnAction(e -> {
			profileStage.close();
			mainStage.show();
		});

		BorderPane root = new BorderPane();
		VBox profileBox = new VBox(10, profileNumberLabel, maxLevelLabel);
		profileBox.setStyle("-fx-padding: 20; -fx-alignment: center;");
		root.setCenter(profileBox);
		root.setBottom(backButton);
		BorderPane.setMargin(backButton, new Insets(10));

		Scene profileScene = new Scene(root, 400, 300);
		profileStage.setScene(profileScene);
		profileStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
