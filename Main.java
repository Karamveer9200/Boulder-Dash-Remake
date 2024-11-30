import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;
import java.util.Optional;

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

	private ProfileManager profileManager;

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Game Menu");

		profileManager = new ProfileManager();

		VBox menuBox = new VBox(10);

		Scene menuScene = new Scene(menuBox, WINDOW_WIDTH, WINDOW_HEIGHT);
		primaryStage.setScene(menuScene);

		// Set up menu buttons
		Button newGameButton = new Button("Start New Game");
		newGameButton.setOnAction(e -> {
			PlayerProfile newProfile = profileManager.promptForProfile(primaryStage);
			setupGame(primaryStage, newProfile);
		});

		Button loadGameButton = new Button("Load Game");
		loadGameButton.setOnAction(e -> {
			List<String> profileFiles = profileManager.getAvailableProfiles();
			ChoiceDialog<String> dialog = new ChoiceDialog<>(profileFiles.isEmpty() ? null : profileFiles.get(0), profileFiles);
			dialog.setTitle("Load Game");
			dialog.setHeaderText("Select a profile to load the game");
			dialog.setContentText("Profile:");

			Optional<String> result = dialog.showAndWait();
			result.ifPresent(fileName -> {
				PlayerProfile selectedProfile = profileManager.loadProfileFromFile(fileName);
				if (selectedProfile != null) {
					setupGame(primaryStage, selectedProfile);
				}
			});
		});

		Button profileButton = new Button("Profile");
		profileButton.setOnAction(e -> {
			List<String> profileFiles = profileManager.getAvailableProfiles();
			if (!profileFiles.isEmpty()) {
				PlayerProfile profile = profileManager.loadProfileFromFile(profileFiles.get(0));
				if (profile != null) {
					showProfileWindow(primaryStage, profile);
				}
			}
		});

		Button highScoresButton = new Button("High Scores");
		highScoresButton.setOnAction(e -> {
			// Show high scores logic here
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
	 * @param profile the player's profile
	 */
	public void setupGame(Stage primaryStage, PlayerProfile profile) {
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
		Label highScoreLabel = new Label("High Score: " + profile.getHighScore());

		Button backButton = new Button("Back");
		backButton.setOnAction(e -> {
			profileStage.close();
			mainStage.show();
		});

		BorderPane root = new BorderPane();
		VBox profileBox = new VBox(10, profileNumberLabel, maxLevelLabel, highScoreLabel);
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
