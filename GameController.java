import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;

/**
 * GameController manages the player's movements, interactions, and rendering of the grid-based game.
 * It handles key inputs, updates the player's position, manages boulder interactions, and coordinates
 * with the Renderer and GridManager to display the game.
 */
public class GameController {
    private final Canvas canvas;
    private final GridManager gridManager;
    private final Renderer renderer;
    private final PlayerManager playerManager;
    private final InputHandler inputHandler;
    private int tickCounter;

    /**
     * Represents possible inputs for the player.
     */
    public enum PlayerInput {
        LEFT,
        RIGHT,
        DOWN,
        UP;
    }

    /**
     * Constructs a GameController with the given grid template and canvas.
     * Initializes the grid, player, renderer, and input handler.
     *
     * @param gridTemplate the 2D array representing the initial grid layout
     * @param canvas       the Canvas object used for rendering the game
     */
    public GameController(int[][] gridTemplate, Canvas canvas) {
        this.canvas = canvas;
        this.gridManager = new GridManager(gridTemplate);
        this.renderer = new Renderer();
        this.playerManager = new PlayerManager(new Player(0, 0));
        this.inputHandler = new InputHandler();
        initializePlayer(gridTemplate);
    }

    /**
     * Initializes the player's position based on the grid template.
     * Searches for the Player element in the grid and sets its initial location.
     *
     * @param gridTemplate the 2D array representing the grid layout
     */
    public void initializePlayer(int[][] gridTemplate) {
        Element[][] elementGrid = gridManager.getElementGrid();
        for (int row = 0; row < gridTemplate.length; row++) {
            for (int col = 0; col < gridTemplate[row].length; col++) {
                if (elementGrid[row][col] instanceof Player) {
                    playerManager.getPlayer().setRow(row);
                    playerManager.getPlayer().setColumn(col);
                    break;
                }
            }
        }
    }

    /**
     * Executes the boulder tick, triggering all boulders to perform their movement logic.
     * Updates the grid and redraws the game.
     */
    public void boulderTick() {
        for (Boulder boulder : gridManager.getBoulders()) {
            boulder.fall(gridManager);
        }
        draw();
    }

    /**
     * Executes the player tick, handling input and updating the player's position on the grid.
     * Processes player movement and redraws the game.
     */
    public void playerTick() {
        if (inputHandler.isInputPending()) {
            PlayerInput input = inputHandler.consumeInput();
            if (input != null) {
                switch (input) {
                    case UP -> playerManager.movePlayer(
                            playerManager.getPlayer().getRow() - 1,
                            playerManager.getPlayer().getColumn(),
                            gridManager
                    );
                    case DOWN -> playerManager.movePlayer(
                            playerManager.getPlayer().getRow() + 1,
                            playerManager.getPlayer().getColumn(),
                            gridManager
                    );
                    case LEFT -> playerManager.movePlayer(
                            playerManager.getPlayer().getRow(),
                            playerManager.getPlayer().getColumn() - 1,
                            gridManager
                    );
                    case RIGHT -> playerManager.movePlayer(
                            playerManager.getPlayer().getRow(),
                            playerManager.getPlayer().getColumn() + 1,
                            gridManager
                    );
                }
            }
        }
        draw();
    }

    /**
     * Draws the current state of the game using the Renderer.
     */
    public void draw() {
        renderer.draw(canvas.getGraphicsContext2D(), gridManager.getElementGrid());
    }

    /**
     * Registers a key press input and passes it to the InputHandler.
     *
     * @param code the KeyCode representing the player's input
     */
    public void registerInput(KeyCode code) {
        inputHandler.registerInput(code);
    }

    /**
     * Retrieves the Canvas object used for rendering the game.
     *
     * @return the Canvas object
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * Retrieves the GridManager responsible for managing the game grid.
     *
     * @return the GridManager object
     */
    public GridManager getGridManager() {
        return gridManager;
    }

    /**
     * Resets the player's location to the top-left corner of the grid (0, 0).
     */
    public void resetPlayerLocation() {
        playerManager.movePlayer(0, 0, gridManager);
    }

    /**
     * Moves the player to the center of the grid.
     */
    public void movePlayerToCenter() {
        playerManager.movePlayer(
                gridManager.getElementGrid().length / 2,
                gridManager.getElementGrid()[0].length / 2,
                gridManager
        );
    }
}
