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

public class Main extends Application {
	// Constants for window, grid, and canvas sizes
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 500;
	private static final int GRID_CELL_WIDTH = 50;
	private static final int GRID_CELL_HEIGHT = 50;
	private static final int GRID_WIDTH = 5;
	private static final int GRID_LENGTH = 5;
	private static final int CANVAS_WIDTH = GRID_CELL_WIDTH * GRID_WIDTH;
	private static final int CANVAS_HEIGHT = GRID_CELL_HEIGHT * GRID_LENGTH;

	// Timeline for periodic ticks
	private Timeline tickTimeline;

	@Override
	public void start(Stage primaryStage) {
		// Load the initial grid from a file
		int[][] initialGrid = FileHandler.readFile("PlaceHolder.txt");

		// Create the canvas
		Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);

		// Initialize the game controller with the grid and canvas
		GameController gameController = new GameController(initialGrid, canvas);

		// Build the GUI
		Pane root = buildGUI(gameController);

		// Create a scene and register key press events
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			gameController.handlePlayerMovement(event.getCode());
			event.consume();
		});
		// Set up the periodic tick timeline
		tickTimeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {
			gameController.tick();
			gameController.draw(); // Redraw the game on each tick
		}));
		tickTimeline.setCycleCount(Animation.INDEFINITE);

		// Draw the initial game state
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
			tickTimeline.play();
			startTickButton.setDisable(true);
			stopTickButton.setDisable(false);
		});

		stopTickButton.setOnAction(e -> {
			tickTimeline.stop();
			stopTickButton.setDisable(true);
			startTickButton.setDisable(false);
		});

		toolbar.getChildren().addAll(resetButton, centerButton, startTickButton, stopTickButton);
		root.setTop(toolbar);

		return root;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
