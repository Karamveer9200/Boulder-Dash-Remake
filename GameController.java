import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;


public class GameController {
    private final Canvas canvas;
    private final Element[][] elementGrid;
    private int playerRow;
    private int playerColumn;
    private static final int GRID_CELL_WIDTH = 50;
    private static final int GRID_CELL_HEIGHT = 50;

    public GameController(int[][] gridTemplate, Canvas canvas) {
        this.canvas = canvas;
        this.elementGrid = new Element[gridTemplate.length][gridTemplate[0].length];
        initializeGrid(gridTemplate);
    }

    private void initializeGrid(int[][] gridTemplate) {
        for (int row = 0; row < gridTemplate.length; row++) {
            for (int col = 0; col < gridTemplate[row].length; col++) {
                elementGrid[row][col] = createElement(gridTemplate[row][col], row, col);
                if (gridTemplate[row][col] == 2) { // Player
                    playerRow = row;
                    playerColumn = col;
                }
            }
        }
    }

    private Element createElement(int code, int row, int col) {
        return switch (code) {
            case 0 -> new Path(row, col);
            case 1 -> new Dirt(row, col);
            case 2 -> new Player(row, col);
            case 3 -> new NormalWall(row, col);
            default -> throw new IllegalArgumentException("Unknown element code: " + code);
        };
    }

    public void handlePlayerMovement(KeyCode code) {
        switch (code) {
            case UP -> movePlayerTo(playerRow - 1, playerColumn);
            case DOWN -> movePlayerTo(playerRow + 1, playerColumn);
            case LEFT -> movePlayerTo(playerRow, playerColumn - 1);
            case RIGHT -> movePlayerTo(playerRow, playerColumn + 1);
        }
        draw();

    }

    public void movePlayerTo(int newRow, int newColumn) {
        if (isValidMove(newRow, newColumn)) {
            elementGrid[playerRow][playerColumn] = new Path(playerRow, playerColumn);
            elementGrid[newRow][newColumn] = new Player(newRow, newColumn);
            playerRow = newRow;
            playerColumn = newColumn;
        }
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < elementGrid.length &&
                col >= 0 && col < elementGrid[0].length &&
                elementGrid[row][col].isCanBeEntered();
    }

    public void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Set the background to gray.
        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int row = 0; row < elementGrid.length; row++) {
            for (int col = 0; col < elementGrid[row].length; col++) {
                gc.drawImage(elementGrid[row][col].getImage(), col * GRID_CELL_WIDTH, row * GRID_CELL_HEIGHT);
            }
        }
    }

    public void resetPlayerLocation() {
        movePlayerTo(0, 0);
    }

    public void movePlayerToCenter() {
        movePlayerTo(elementGrid.length / 2, elementGrid[0].length / 2);
    }

    public void tick() {
        // Example: move the player in a loop
        movePlayerTo(playerRow, (playerColumn + 1) % elementGrid[0].length);
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
