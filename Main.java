import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Main sets up the GUI and initialises everything for a game to take place
 */
public class Main extends Application {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 500;
	public static final int GRID_CELL_WIDTH = 30;
	public static final int GRID_CELL_HEIGHT = 30;

	public static final int DIAMOND_SCORE_VALUE = 100;
	public static final int TIME_SCORE_VALUE = 25;

	// Timeline for periodic ticks
	private Timeline playerTickTimeline;
	private Timeline dangerousRockFallTickTimeline;
	private Timeline dangerousRockRollTimeline;
	private Timeline frogTickTimeline;
	private Timeline amoebaTickTimeline;
	private Timeline flyTickTimeline;
	private Timeline killPlayerTickTimeLine;
	private Timeline explosionTickTimeLine;
	private Timeline timerTimeline;
	private Timeline diamondCountTimeline;
	private Timeline checkLevelWinTimeline;

	private boolean levelWon;
	private int secondsRemaining;
	private ArrayList<PlayerProfile> profiles = new ArrayList<>();
	private PlayerProfile currentProfile;

	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage; // Store the primary stage
		primaryStage.setTitle("BOULDER DASH");

		VBox menuBox = new VBox(10);

		Scene menuScene = new Scene(menuBox, WINDOW_WIDTH, WINDOW_HEIGHT);
		primaryStage.setScene(menuScene);

		profiles = ProfileManager.getAvailableProfiles(); //Load all player profiles in txt to

		// Set up menu buttons
		Button newGameButton = new Button("Start New Game");
		newGameButton.setOnAction(e -> {
			currentProfile = ProfileManager.promptForProfile();
			profiles.add(currentProfile);
			String levelFile = "txt/Level1.txt";
			secondsRemaining = FileHandler.readSecondsFromLevelFile(levelFile);
			setupGame(primaryStage, levelFile);
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
					PlayerProfile profileToSelect = null;
					for (PlayerProfile profile : profiles) {
						if (profile.getName().equals(selectedProfileName)) {
							profileToSelect = profile;
							currentProfile = profile;
							break;
						}
					}
					if (profileToSelect != null) {
						int playerID = currentProfile.getPlayerId();
						if (ProfileManager.doesPlayerSaveFileExist(playerID)) {
							String levelFile = "txt/Save" + playerID + ".txt";
							secondsRemaining = FileHandler.readSecondsFromLevelFile(levelFile);
							setupGame(primaryStage, levelFile);
						} else {
							int level = profileToSelect.getMaxLevelReached();
							String levelFile = "txt/Level" + level + ".txt";
							secondsRemaining = FileHandler.readSecondsFromLevelFile(levelFile);
							setupGame(primaryStage, levelFile);
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
			selectButton.setStyle("-fx-background-color: red;"); // Make button red
			selectButton.setOnAction(event -> {
				String selectedProfileName = profileDropdown.getValue(); // Get the selected profile name
				if (selectedProfileName != null) {
					PlayerProfile profileToDelete = null;
					for (PlayerProfile profile : profiles) {
						if (profile.getName().equals(selectedProfileName)) {
							profileToDelete = profile;
							break;
						}
					}
					if (profileToDelete != null) {
						int idToDelete = profileToDelete.getPlayerId();
						profiles.remove(profileToDelete);
						ProfileManager.deleteProfile(idToDelete);
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
			highScores1Button.setOnAction(event -> {
				dialog.hide();
				HighScoreTableManager.displayHighScoresInMainMenu(1, dialog);
			});

			Button highScores2Button = new Button("Level 2 High Scores");
			highScores2Button.setOnAction(event -> {
				dialog.hide();
				HighScoreTableManager.displayHighScoresInMainMenu(2, dialog);
			});

			Button highScores3Button = new Button("Level 3 High Scores");
			highScores3Button.setOnAction(event -> {
				dialog.hide();
				HighScoreTableManager.displayHighScoresInMainMenu(3, dialog);
			});

			VBox dialogBox = new VBox(10, highScores1Button, highScores2Button, highScores3Button);
			dialogBox.setStyle("-fx-padding: 20; -fx-alignment: center;");

			Scene dialogScene = new Scene(dialogBox, 300, 150);
			dialog.setScene(dialogScene);
			dialog.showAndWait();
		});

		Button quitButton = new Button("Quit Game");
		quitButton.setOnAction(e -> closeGame());


		Image logoImage = new Image("images/BoulderDashTitle.png");
		ImageView logoImageView = new ImageView(logoImage);

		logoImageView.setFitWidth(700);
		logoImageView.setPreserveRatio(true);

		menuBox.getChildren().addAll(logoImageView, newGameButton, loadGameButton, profileButton, highScoresButton, quitButton);
		menuBox.setStyle("-fx-padding: 20; -fx-alignment: center;");

		// Show the menu
		primaryStage.show();
	}

	/**
	 * Sets up the game interface and initializes everything for a game to take place.
	 *
	 * @param primaryStage the primary stage for the game
	 */
	public void setupGame(Stage primaryStage, String levelFile) {
		// Load the initial grid from a file
		String[][] initialGrid = FileHandler.readElementGridFromLevelFile(levelFile);

		final int canvasWidth = initialGrid[0].length * GRID_CELL_WIDTH;
		final int canvasHeight = initialGrid.length * GRID_CELL_HEIGHT;

		int amoebaGrowthRate = FileHandler.readAmoebaGrowthRateFromLevelFile(levelFile); //Read amoeba growth rate


		// Create the canvas
		Canvas canvas = new Canvas(canvasWidth, canvasHeight);

		// Initialize the game controller with the grid and canvas
		GameController gameController = new GameController(initialGrid, canvas);

		gameController.setDiamondsRequired(FileHandler.readRequiredDiamondsFromLevelFile(levelFile));
		gameController.getPlayer().setDiamondCount(FileHandler.readDiamondsCollectedFromLevelFile(levelFile));
		gameController.getPlayer().setKeyInventory(FileHandler.readKeyInventoryFromLevelFile(levelFile));
		gameController.setAmoebaLimit(FileHandler.readAmoebaSizeLimitFromLevelFile(levelFile));

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

		KeyFrame amoebaKeyFrame = new KeyFrame(Duration.millis(amoebaGrowthRate), event -> {
			gameController.amoebaTick();
		});

		KeyFrame explosionKeyFrame = new KeyFrame(Duration.millis(1000), event -> {
			gameController.explosionTick();
		});

		KeyFrame checkLevelWinKeyFrame = new KeyFrame(Duration.millis(49), event -> {
			if (gameController.checkLevelWinTick()) {
				levelCompleted(gameController);
			}
		});

		// Set up the periodic tick timeline
		playerTickTimeline = new Timeline(playerKeyFrame);
		dangerousRockFallTickTimeline = new Timeline(dangerousRocksFallKeyFrame);
		dangerousRockRollTimeline = new Timeline(dangerousRocksRollKeyFrame);
		flyTickTimeline = new Timeline(flyKeyFrame);
		frogTickTimeline = new Timeline(frogKeyFrame);
		amoebaTickTimeline = new Timeline(amoebaKeyFrame);
		killPlayerTickTimeLine = new Timeline(killPlayerKeyFrame);
		explosionTickTimeLine = new Timeline(explosionKeyFrame);
		checkLevelWinTimeline = new Timeline(checkLevelWinKeyFrame);

		// Set the cycle count to Animation.INDEFINITE
		playerTickTimeline.setCycleCount(Animation.INDEFINITE);
		killPlayerTickTimeLine.setCycleCount(Animation.INDEFINITE);
		dangerousRockFallTickTimeline.setCycleCount(Animation.INDEFINITE);
		dangerousRockRollTimeline.setCycleCount(Animation.INDEFINITE);
		flyTickTimeline.setCycleCount(Animation.INDEFINITE);
		frogTickTimeline.setCycleCount(Animation.INDEFINITE);
		amoebaTickTimeline.setCycleCount(Animation.INDEFINITE);
		timerTimeline.setCycleCount(Animation.INDEFINITE);
		explosionTickTimeLine.setCycleCount(Animation.INDEFINITE);
		checkLevelWinTimeline.setCycleCount(Animation.INDEFINITE);

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

		Button startTickButton = new Button("Unpause");
		Button stopTickButton = new Button("Pause");
		stopTickButton.setDisable(true);
		Button saveButton = new Button("Save Game");

		Button resetGridButton = new Button("Reset Level");
		resetGridButton.setOnAction(e -> {
			int levelReached = currentProfile.getMaxLevelReached();
			String levelFile = "txt/Level" + levelReached + ".txt";
			String[][] initialGrid = FileHandler.readElementGridFromLevelFile(levelFile);
			gameController.getGridManager().reinitializeGrid(initialGrid);
			gameController.getGridManager().initializePlayer(initialGrid);
			gameController.draw();
		});

		saveButton.setOnAction(e -> {
			ArrayList<KeyColour> keyInventory = gameController.getPlayer().getKeyInventory();
			FileHandler.writeFile(gameController.getGridManager(), currentProfile, secondsRemaining, keyInventory);
			closeGame();
		});

		startTickButton.setOnAction(e -> {
			timerTimeline.play();
			diamondCountTimeline.play();
			playerTickTimeline.play();
			dangerousRockFallTickTimeline.play();
			dangerousRockRollTimeline.play();
			flyTickTimeline.play();
			frogTickTimeline.play();
			amoebaTickTimeline.play();
			killPlayerTickTimeLine.play();
			explosionTickTimeLine.play();
			checkLevelWinTimeline.play();
			startTickButton.setDisable(true);
			stopTickButton.setDisable(false);
			saveButton.setDisable(true);
			resetGridButton.setDisable(true);
		});

		stopTickButton.setOnAction(e -> {
			timerTimeline.stop();
			diamondCountTimeline.stop();
			playerTickTimeline.stop();
			dangerousRockRollTimeline.stop();
			dangerousRockFallTickTimeline.stop();
			flyTickTimeline.stop();
			frogTickTimeline.stop();
			amoebaTickTimeline.stop();
			killPlayerTickTimeLine.stop();
			explosionTickTimeLine.stop();
			checkLevelWinTimeline.stop();
			stopTickButton.setDisable(true);
			startTickButton.setDisable(false);
			saveButton.setDisable(false);
			resetGridButton.setDisable(false);
		});

		Button testExplosionButton = new Button("Test Explosion");
		testExplosionButton.setOnAction(e -> {
			gameController.applyExplosion(2,2);
			gameController.draw();
		});

		// adds timer to the toolbar
		Text timerText = new Text("Time Remaining: " + secondsRemaining + "s");
		timerTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
			secondsRemaining--;
			timerText.setText("Time Remaining: " + secondsRemaining + "s");
			if (secondsRemaining == 0) {
				gameController.getGridManager().killPlayer();
				gameController.draw();
				timerTimeline.stop();
			}
		}));
		timerTimeline.setCycleCount(Animation.INDEFINITE);

		// adds diamond count to the toolbar, displays as zero if player has not been initialised
		int diamondsCollected = gameController.getPlayer().getDiamondCount();
		Text diamondCountText = new Text("Diamonds Collected: " + diamondsCollected);
		diamondCountTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
			if (gameController.getPlayer() != null) {
				diamondCountText.setText("Diamonds collected: " + gameController.getPlayer().getDiamondCount());
			} else {
				diamondCountText.setText("Diamonds collected: 0");
			}
		}));
		diamondCountTimeline.setCycleCount(Animation.INDEFINITE);

		// display current level for the player
		Text levelText = new Text ("Current Level: " + currentProfile.getMaxLevelReached());

		toolbar.getChildren().addAll(startTickButton, stopTickButton, resetGridButton,
				saveButton, timerText, diamondCountText, levelText);
		root.setTop(toolbar);

		return root;
	}

	/**
	 * Closes the game.
	 */
	private void closeGame() {
		System.exit(0);
	}

	public void levelCompleted(GameController gameController) {
		int levelReached = currentProfile.getMaxLevelReached();
		String levelFile = "txt/Level" + levelReached + ".txt";
		String[][] initialGrid = FileHandler.readElementGridFromLevelFile(levelFile);
		int score = calcHighScore(secondsRemaining, gameController.getPlayer().getDiamondCount());
		gameController.getGridManager().reinitializeGrid(initialGrid);
		gameController.getGridManager().initializePlayer(initialGrid);
		// Stop all timelines
		playerTickTimeline.stop();
		killPlayerTickTimeLine.stop();
		dangerousRockFallTickTimeline.stop();
		dangerousRockRollTimeline.stop();
		flyTickTimeline.stop();
		frogTickTimeline.stop();
		amoebaTickTimeline.stop();
		explosionTickTimeLine.stop();
		timerTimeline.stop();

		// Show the high score table for the current level
		int currentLevel = currentProfile.getMaxLevelReached();
		String currentPlayerName = currentProfile.getName();
		HighScoreTableManager.updateHighScoreTable(currentPlayerName,score,currentLevel);
		Platform.runLater(() -> {
			HighScoreTableManager.displayHighScoresAfterLevel(currentLevel, score);
		});

		// Check if there’s a next level
		int nextLevel = currentLevel + 1;
		String nextLevelFile = "txt/Level" + nextLevel + ".txt";
		if (nextLevel <= 3) {
			currentProfile.setMaxLevelReached(nextLevel); // Update player's progress
			profiles.set(profiles.indexOf(currentProfile), currentProfile); // Update profile list
			ProfileManager.saveProfileToFile(currentProfile); // Persist changes

			secondsRemaining = FileHandler.readSecondsFromLevelFile(nextLevelFile);
			setupGame(primaryStage, nextLevelFile);
		} else {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("VICTORY");
			alert.setHeaderText("Congratulations!");
			alert.setContentText("You have completed all levels!");
			alert.show();

			profiles.remove(currentProfile);
			ProfileManager.deleteProfile(currentProfile.getPlayerId());
			start(primaryStage);
		}
	}

	public int calcHighScore(int secondsRemaining, int diamondsCollected) {
		System.out.println(diamondsCollected);
		System.out.println(DIAMOND_SCORE_VALUE);
		System.out.println(secondsRemaining);
		System.out.println(TIME_SCORE_VALUE);
		return (diamondsCollected * DIAMOND_SCORE_VALUE) + (secondsRemaining * TIME_SCORE_VALUE);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
