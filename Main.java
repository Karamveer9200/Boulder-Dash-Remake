import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.VBox;
import java.util.List;
import java.util.Optional;


/**
 * Main sets up the GUI and initialises everything for a game to take place
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
	private Timeline aomeebaTickTimeline;
	public static Player player;
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setUserData(this); // Store reference to Main in primaryStage for later use
		primaryStage.setTitle("Game Menu");

		VBox menuBox = new VBox(10);

		Scene menuScene = new Scene(menuBox, WINDOW_WIDTH, WINDOW_HEIGHT);
		primaryStage.setScene(menuScene);

		// Set up menu buttons
		Button newGameButton = new Button("Start New Game");
		newGameButton.setOnAction(e -> {
			PlayerProfile newProfile = PlayerProfile.promptForProfile(primaryStage);
			setupGame(primaryStage, newProfile);
		});

		Button loadGameButton = new Button("Load Game");
		loadGameButton.setOnAction(e -> {
			List<String> profileNames = PlayerProfile.getAllProfiles().stream().map(PlayerProfile::getName).toList();
			ChoiceDialog<String> dialog = new ChoiceDialog<>(profileNames.isEmpty() ? null : profileNames.getFirst(), profileNames);
			dialog.setTitle("Load Game");
			dialog.setHeaderText("Select a profile to load the game");
			dialog.setContentText("Profile:");

			Optional<String> result = dialog.showAndWait();
			result.flatMap(PlayerProfile::loadProfile).ifPresent(selectedProfile -> setupGame(primaryStage, selectedProfile));
		});

		Button profileButton = new Button("Profile");
		profileButton.setOnAction(e -> {
			PlayerProfile.getAllProfiles().stream().findFirst().ifPresent(profile -> profile.showProfileWindow(primaryStage)); // Show the first profile for testing
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

		KeyFrame aomeebaKeyFrame = new KeyFrame(Duration.millis(1000), event -> {
			gameController.amoebaTick();
		});

		// Set up the periodic tick timeline
		playerTickTimeline = new Timeline(playerKeyFrame);
		dangerousRockFallTickTimeline = new Timeline( dangerousRocksFallKeyFrame);
		dangerousRockRollTimeline = new Timeline( dangerousRocksRollKeyFrame);
		frogTickTimeline = new Timeline(frogKeyFrame);
		aomeebaTickTimeline = new Timeline(aomeebaKeyFrame);
		playerTickTimeline.setCycleCount(Animation.INDEFINITE);
		dangerousRockFallTickTimeline.setCycleCount(Animation.INDEFINITE);
		dangerousRockRollTimeline.setCycleCount(Animation.INDEFINITE);
		frogTickTimeline.setCycleCount(Animation.INDEFINITE);
		aomeebaTickTimeline.setCycleCount(Animation.INDEFINITE);
		
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
			aomeebaTickTimeline.play();
			startTickButton.setDisable(true);
			stopTickButton.setDisable(false);
		});

		stopTickButton.setOnAction(e -> {
			playerTickTimeline.stop();
			dangerousRockRollTimeline.stop();
			dangerousRockFallTickTimeline.stop();
			frogTickTimeline.stop();
			aomeebaTickTimeline.stop();
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

		toolbar.getChildren().addAll(resetButton, centerButton, startTickButton, stopTickButton,resetGridButton);
		root.setTop(toolbar);

		return root;
	}

	/**
	 * Closes the game.
	 */
	private void closeGame() {
		System.exit(0);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
