import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;

/**
 * GameController manages the player's movements, interactions, and rendering of the grid-based game.
 * It handles key inputs, updates the player's position, and coordinates with the Renderer and GridManager.
 */
public class GameController {
    private final Canvas canvas;
    private final GridManager gridManager;
    private final Renderer renderer;
    private int playerRow;
    private int playerColumn;

    private boolean isThereAPlayerInput;
    private enum PlayerInput {
        LEFT,
        RIGHT,
        DOWN,
        UP;
    }
    private PlayerInput nextPlayerInput;
    private int counter;

    public GameController(int[][] gridTemplate, Canvas canvas) {
        this.canvas = canvas;
        this.gridManager = new GridManager(gridTemplate);
        this.renderer = new Renderer();
        initializePlayer(gridTemplate);
        this.counter = 0;
    }

    // Initialise the initial playerRow and playerColumn variables, crucial for knowing where the player is
    public void initializePlayer(int[][] gridTemplate) {
        Element[][] elementGrid = gridManager.getElementGrid();
        for (int row = 0; row < gridTemplate.length; row++) {
            for (int col = 0; col < gridTemplate[row].length; col++) {
                if (elementGrid[row][col] instanceof Player) {
                    playerRow = row;
                    playerColumn = col;
                    break;
                }
            }
        }
    }

    public void handlePlayerMovement(PlayerInput nextPlayerInput) {
        switch (nextPlayerInput) {
            case UP -> movePlayerTo(playerRow - 1, playerColumn);
            case DOWN -> movePlayerTo(playerRow + 1, playerColumn);
            case LEFT -> movePlayerTo(playerRow, playerColumn - 1);
            case RIGHT -> movePlayerTo(playerRow, playerColumn + 1);
        }
    }

    public void movePlayerTo(int newRow, int newColumn) {
        if (isValidMove(newRow, newColumn)) {
            gridManager.setElement(playerRow, playerColumn, new Path(playerRow, playerColumn)); //Put a path where the player just was
            gridManager.setElement(newRow, newColumn, new Player(newRow, newColumn)); //Put a player where the player is going to
            playerRow = newRow;
            playerColumn = newColumn;
        }
    }

    private boolean isValidMove(int row, int col) {
        Element[][] elementGrid = gridManager.getElementGrid();
        return row >= 0 && row < elementGrid.length &&
                col >= 0 && col < elementGrid[0].length && // returns true if player is moving to a co-ordinate in bounds
                elementGrid[row][col].isCanBeEntered(); // and moving to a co-ordinate that canBeEntered == true
    }

    public void draw() {
        renderer.draw(canvas.getGraphicsContext2D(), gridManager.getElementGrid());
    }

    public void resetPlayerLocation() {
        movePlayerTo(0, 0);
    }

    public void movePlayerToCenter() {
        movePlayerTo(gridManager.getElementGrid().length / 2, gridManager.getElementGrid()[0].length / 2);
    }

    public void tick() {
        counter++;
        if (isThereAPlayerInput && counter % 2 == 0) {
            handlePlayerMovement(nextPlayerInput);
            isThereAPlayerInput = false;
            System.out.println("hi");
        }
        System.out.println(counter);
        draw(); //Redraw grid after each tick
    }

    public void holdNextPlayerInput(KeyCode code) {
        switch (code) {
            case UP -> nextPlayerInput = PlayerInput.UP;
            case DOWN -> nextPlayerInput = PlayerInput.DOWN;
            case LEFT -> nextPlayerInput = PlayerInput.LEFT;
            case RIGHT -> nextPlayerInput = PlayerInput.RIGHT;
        }
        isThereAPlayerInput = true;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public GridManager getGridManager() {
        return gridManager;
    }
}