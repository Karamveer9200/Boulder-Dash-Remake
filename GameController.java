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
    private Player player;

    public GameController(int[][] gridTemplate, Canvas canvas) {
        this.canvas = canvas;
        this.gridManager = new GridManager(gridTemplate);
        this.renderer = new Renderer();
        initializePlayer(gridTemplate);
    }

    // Initialise the initial playerRow and playerColumn variables, crucial for knowing where the player is
    public void initializePlayer(int[][] gridTemplate) {
        Element[][] elementGrid = gridManager.getElementGrid();
        for (int row = 0; row < gridTemplate.length; row++) {
            for (int col = 0; col < gridTemplate[row].length; col++) {
                if (elementGrid[row][col] instanceof Player playerTile) {
                    playerRow = row;
                    playerColumn = col;
                    player = playerTile;
                    break;
                }
            }
        }
    }

    public void handlePlayerMovement(KeyCode code) {
        switch (code) {
            case UP -> movePlayerTo(playerRow - 1, playerColumn);
            case DOWN -> movePlayerTo(playerRow + 1, playerColumn);
            case LEFT -> movePlayerTo(playerRow, playerColumn - 1);
            case RIGHT -> movePlayerTo(playerRow, playerColumn + 1);
        }
        draw(); //Redraw grid after update of player movement
    }

    public void movePlayerTo(int newRow, int newColumn) {
        if (isValidMove(newRow, newColumn)) {
            Element wantedTile = gridManager.getElement(newRow, newColumn);
            // Collects key if player walks over it
            if (wantedTile instanceof Key key) {
                player.collectKey(key);
            }
            // Collects diamond if player walks over it
            if (wantedTile instanceof Diamond diamond) {
                player.collectDiamond(diamond);
            } // If a player has the correct key, unlocks the door, tells them which key need if they don't have it
            else if (wantedTile instanceof LockedDoor lockedDoor) {
                if (player.hasKey(lockedDoor.getColour())) {
                    player.useKey(lockedDoor.getColour());
                    lockedDoor.unlock();
                } else {
                    System.out.println("Player needs a " + lockedDoor.getColour() + " key to open this door.");
                    return;
                }
            }

            // Check if player is on the Exit tile and if they have enough diamonds
            if (wantedTile instanceof Exit exit) {
                if (player.isHasEnoughDiamonds()) {
                    // If the player has enough diamonds, unlock the Exit and announce level win
                    exit.unlock();
                    exit.announceLevelWin();
                    gridManager.setElement(newRow, newColumn, new Player(newRow, newColumn)); //Place player onto the Exit
                    gridManager.setElement(playerRow, playerColumn, new Path(playerRow, playerColumn)); //Put a path where the player just was
                    playerRow = newRow;
                    playerColumn = newColumn;
                    return;
                } else {
                    System.out.println("Player needs more diamonds to enter the exit!");
                    return;
                }
            }

            gridManager.setElement(playerRow, playerColumn, new Path(playerRow, playerColumn)); //Put a path where the player just was
            gridManager.setElement(newRow, newColumn, new Player(newRow, newColumn)); // Place player on the new position
            playerRow = newRow;
            playerColumn = newColumn;
        }
    }




    private boolean isValidMove(int row, int col) {
        Element[][] elementGrid = gridManager.getElementGrid();
        if (row >= 0 && row < elementGrid.length &&
                col >= 0 && col < elementGrid[0].length) {
            Element targetTile = elementGrid[row][col];
            // Allow attempt to move to a LockedDoor to check if it can be unlocked
            if (targetTile instanceof LockedDoor) {
                return true; // Allows to check for key
            }
            // Allows to check for required amount of diamonds when moving onto Exit
            if (targetTile instanceof Exit) {
                return true;
            }

            return targetTile.isCanBeEntered();
        }
        return false;
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
        // move the player to the right in a loop
        // HERE WE WILL HAVE TO LOOP THROUGH EVERYTHING IN THE ELEMENT GRID AND DO THEIR TICK METHOD
        movePlayerTo(playerRow, (playerColumn + 1) % gridManager.getElementGrid()[0].length);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public GridManager getGridManager() {
        return gridManager;
    }
}

//----------------------------------------------
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.input.KeyCode;
//import javafx.scene.paint.Color;
//
//
//public class GameController {
//    private final Canvas canvas;
//    private final Element[][] elementGrid;
//    private int playerRow;
//    private int playerColumn;
//    private static final int GRID_CELL_WIDTH = 50;
//    private static final int GRID_CELL_HEIGHT = 50;
//
//    public GameController(int[][] gridTemplate, Canvas canvas) {
//        this.canvas = canvas;
//        this.elementGrid = new Element[gridTemplate.length][gridTemplate[0].length];
//        initializeGrid(gridTemplate);
//    }
//
//    private void initializeGrid(int[][] gridTemplate) {
//        for (int row = 0; row < gridTemplate.length; row++) {
//            for (int col = 0; col < gridTemplate[row].length; col++) {
//                elementGrid[row][col] = createElement(gridTemplate[row][col], row, col);
//                if (gridTemplate[row][col] == 2) { // Player
//                    playerRow = row;
//                    playerColumn = col;
//                }
//            }
//        }
//    }
//
//    private Element createElement(int code, int row, int col) {
//        return switch (code) {
//            case 0 -> new Path(row, col);
//            case 1 -> new Dirt(row, col);
//            case 2 -> new Player(row, col);
//            case 3 -> new NormalWall(row, col);
//            default -> throw new IllegalArgumentException("Unknown element code: " + code);
//        };
//    }
//
//    public void handlePlayerMovement(KeyCode code) {
//        switch (code) {
//            case UP -> movePlayerTo(playerRow - 1, playerColumn);
//            case DOWN -> movePlayerTo(playerRow + 1, playerColumn);
//            case LEFT -> movePlayerTo(playerRow, playerColumn - 1);
//            case RIGHT -> movePlayerTo(playerRow, playerColumn + 1);
//        }
//        draw();
//
//    }
//
//    public void movePlayerTo(int newRow, int newColumn) {
//        if (isValidMove(newRow, newColumn)) {
//            elementGrid[playerRow][playerColumn] = new Path(playerRow, playerColumn);
//            elementGrid[newRow][newColumn] = new Player(newRow, newColumn);
//            playerRow = newRow;
//            playerColumn = newColumn;
//        }
//    }
//
//    private boolean isValidMove(int row, int col) {
//        return row >= 0 && row < elementGrid.length &&
//                col >= 0 && col < elementGrid[0].length &&
//                elementGrid[row][col].isCanBeEntered();
//    }
//
//    public void draw() {
//        GraphicsContext gc = canvas.getGraphicsContext2D();
//        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//
//        // Set the background to gray.
//        gc.setFill(Color.GRAY);
//        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
//
//        for (int row = 0; row < elementGrid.length; row++) {
//            for (int col = 0; col < elementGrid[row].length; col++) {
//                gc.drawImage(elementGrid[row][col].getImage(), col * GRID_CELL_WIDTH, row * GRID_CELL_HEIGHT);
//            }
//        }
//    }
//
//    public void resetPlayerLocation() {
//        movePlayerTo(0, 0);
//    }
//
//    public void movePlayerToCenter() {
//        movePlayerTo(elementGrid.length / 2, elementGrid[0].length / 2);
//    }
//
//    public void tick() {
//        // Example: move the player in a loop
//        movePlayerTo(playerRow, (playerColumn + 1) % elementGrid[0].length);
//    }
//
//    public Canvas getCanvas() {
//        return canvas;
//    }
//}
