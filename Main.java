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

/**
 * Main sets up the GUI and initialises everything for a game to take place
 */
public class Main extends Application {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 500;
	public static final int GRID_CELL_WIDTH = 30;
	public static final int GRID_CELL_HEIGHT = 30;

	// Timeline for periodic ticks
	private Timeline playerTickTimeline;
	private Timeline dangerousRockFallTickTimeline;
	private Timeline dangerousRockRollTimeline;
	private Timeline frogTickTimeline;
	private Timeline aomeebaTickTimeline;
	private Timeline flyTickTimeline;
	private Timeline killPlayerTickTimeLine;
	public static Player player;
	@Override
	public void start(Stage primaryStage) {
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

		//add keyframe for checking neighboring tiles to enemies , instance of that method is in flies 12/1/2024 - Omar
		KeyFrame killPlayerKeyFrame = new KeyFrame(Duration.millis(20), event -> {
			gameController.killPlayerTick();
		});

		KeyFrame dangerousRocksRollKeyFrame = new KeyFrame(Duration.millis(1500), event -> {
			gameController.boulderRollTick();
			gameController.diamondRollTick();
		});

		KeyFrame dangerousRocksFallKeyFrame = new KeyFrame(Duration.millis(500), event -> {
			gameController.boulderFallTick();
			gameController.diamondFallTick();

		});

		KeyFrame flyKeyFrame = new KeyFrame(Duration.millis(1000), event -> {
			gameController.butterflyTick();
			gameController.fireflyTick();
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
		flyTickTimeline = new Timeline(flyKeyFrame);
		frogTickTimeline = new Timeline(frogKeyFrame);
		aomeebaTickTimeline = new Timeline(aomeebaKeyFrame);
		killPlayerTickTimeLine = new Timeline(killPlayerKeyFrame);

		// Set the cycle count to Animation.INDEFINITE
		playerTickTimeline.setCycleCount(Animation.INDEFINITE);
		killPlayerTickTimeLine.setCycleCount(Animation.INDEFINITE);
		dangerousRockFallTickTimeline.setCycleCount(Animation.INDEFINITE);
		dangerousRockRollTimeline.setCycleCount(Animation.INDEFINITE);
		flyTickTimeline.setCycleCount(Animation.INDEFINITE);
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
			flyTickTimeline.play();
			frogTickTimeline.play();
			aomeebaTickTimeline.play();
			killPlayerTickTimeLine.play();
			startTickButton.setDisable(true);
			stopTickButton.setDisable(false);
		});

		stopTickButton.setOnAction(e -> {
			playerTickTimeline.stop();
			dangerousRockRollTimeline.stop();
			dangerousRockFallTickTimeline.stop();
			flyTickTimeline.stop();
			frogTickTimeline.stop();
			aomeebaTickTimeline.stop();
			killPlayerTickTimeLine.stop();
			stopTickButton.setDisable(true);
			startTickButton.setDisable(false);
		});

		Button resetGridButton = new Button("Reset Grid");
		resetGridButton.setOnAction(e -> {
			int[][] initialGrid = FileHandler.readFile("PlaceHolder.txt");
			gameController.getGridManager().reinitializeGrid(initialGrid);
//			gameController.initializePlayer(initialGrid);
			gameController.draw();
		});

		toolbar.getChildren().addAll(resetButton, centerButton, startTickButton, stopTickButton,resetGridButton);
		root.setTop(toolbar);

		return root;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
