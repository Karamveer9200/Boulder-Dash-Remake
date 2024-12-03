import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;

import javax.swing.plaf.basic.BasicMenuBarUI;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * GameController manages the player's movements, interactions, and rendering of the grid-based game.
 * It handles key inputs, updates the player's position, manages boulder interactions, and coordinates
 * with the Renderer and GridManager to display the game.
 */
public class GameController {
    private final Canvas canvas;
    private final GridManager gridManager;
    private final Renderer renderer;
    private final Player player ;
    private final InputHandler inputHandler;
    private int tickCounter;
    private boolean gameStatus = true;

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
        this.player = gridManager.getPlayer();
        this.inputHandler = new InputHandler();
        initializePlayer(gridTemplate);
//        gameStart();
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
                    player.setRow(row);
                    player.setColumn(col);
                    break;
                }
            }
        }
    }

    private void checkNeighboursForPlayer(Element enemy, Element [][] grid) {
        // Check bounds and get neighbors safely
        int enemyRow = enemy.getRow();
        int enemyCol = enemy.getColumn();
        Element currentRightNeighbor = (enemyCol + 1 < grid[0].length) ? grid[enemyRow][enemyCol + 1] : null;
        Element currentLeftNeighbor = (enemyCol - 1 >= 0) ? grid[enemyRow][enemyCol - 1] : null;
        Element currentDownNeighbor = (enemyRow + 1 < grid.length) ? grid[enemyRow + 1][enemyCol] : null;
        Element currentUpNeighbor = (enemyRow - 1 >= 0) ? grid[enemyRow - 1][enemyCol] : null;

        if( currentUpNeighbor instanceof Player
                || currentDownNeighbor instanceof Player || currentRightNeighbor instanceof Player
                || currentLeftNeighbor instanceof Player ){
            //expand method to kill player
//            by stopping input and ending the game
            gridManager.setElement(enemyRow, enemyCol, new Path(enemy.getRow(), enemy.getColumn())); // Replace player with Frog
            gridManager.removeFromList(player); // Remove player from the game
            enemy.setRow(enemyRow);
            enemy.setColumn(enemyCol);
            System.out.println("Player has been killed");
            gameOver();
        }
    }

    public void killPlayerTick() {
        ArrayList<Frog> frogs = gridManager.getFrogs();
        ArrayList<Butterfly> butterflies = gridManager.getButterflies();
        ArrayList<Firefly> fireflies = gridManager.getFireflies();
        ArrayList<Element> enemies = new ArrayList<>();
        enemies.addAll(frogs);
        enemies.addAll(butterflies);
        enemies.addAll(fireflies);

        // Iterate through all entities
        for (Element enemy : enemies) {
//           System.out.println("Checking enemy at row: " + enemy.getRow() + ", col: " + enemy.getColumn());
            checkNeighboursForPlayer(enemy, gridManager.getElementGrid());
        }
    }

    /**
     * Executes the boulder tick, triggering all boulders to perform their movement logic.
     * Updates the grid and redraws the game.
     */
    public void boulderFallTick() {
        // Making a copy of the boulders Arraylist,
        // avoids problems with concurrently changing the arraylist when a boulder passes through magic wall
        ArrayList<Boulder> boulders = new ArrayList<>(gridManager.getBoulders());
        for (Boulder boulder : boulders) {
            boulder.fall(gridManager);

//            System.out.println("Processing Boulder at (" + boulder.getRow() + ", " + boulder.getColumn() + ")");
//            System.out.println("Element below boulder: "+ gridManager.getElement(boulder.getRow()+1, boulder.getColumn()));
        }
        draw();
    }

    /**
     * Executes the boulder tick, triggering all boulders to perform their movement logic.
     * Updates the grid and redraws the game.
     */
    public void boulderRollTick() {
        // Making a copy of the boulders Arraylist,
        // avoids problems with concurrently changing the arraylist when a diamond passes through magic wall
        ArrayList<Boulder> boulders = new ArrayList<>(gridManager.getBoulders());
        for (Boulder boulder : boulders) {
            boulder.roll(gridManager);
        }
        draw();
    }

    /**
     * Executes the boulder tick, triggering all boulders to perform their movement logic.
     * Updates the grid and redraws the game.
     */
    public void diamondFallTick() {
        // Making a copy of the boulders Arraylist,
        // avoids problems with concurrently changing the arraylist when a boulder passes through magic wall
        ArrayList<Diamond> diamonds = new ArrayList<>(gridManager.getDiamonds());
        for (Diamond diamond : diamonds) {
            diamond.fall(gridManager);
//            System.out.println("Processing Diamond at (" + diamond.getRow() + ", " + diamond.getColumn() + ")");
//            System.out.println("Element below Diamond: "+ gridManager.getElement(diamond.getRow()+1, diamond.getColumn()));

        }
        draw();
    }


    /**
     * Executes the boulder tick, triggering all boulders to perform their movement logic.
     * Updates the grid and redraws the game.
     */
    public void diamondRollTick() {
        // Making a copy of the boulders Arraylist,
        // avoids problems with concurrency
        ArrayList<Diamond> diamonds = new ArrayList<>(gridManager.getDiamonds());
        for (Diamond diamond : diamonds) {
            diamond.roll(gridManager);
        }
        draw();
    }

    public void frogTick() {
        // Making a copy of the boulders Arraylist,
        // avoids problems with concurrency
        ArrayList<Frog> frogs = new ArrayList<>(gridManager.getFrogs());
        for (Frog frog : frogs) {
            frog.seekAndKill(gridManager, player);
        }
        draw();
    }

    public void amoebaTick() {
        if (gridManager.getAmoebas().size() > 0) {
            AmoebaManager.spreadAll(gridManager);
        }
        draw();
    }

    public void butterflyTick() {
        ArrayList<Butterfly> butterflies = new ArrayList<>(gridManager.getButterflies());
        for (Butterfly butterfly : butterflies) {
            butterfly.move(gridManager, player);
        }
        draw();
    }

    public void fireflyTick() {
        ArrayList<Firefly> fireflies = new ArrayList<>(gridManager.getFireflies());
        for (Firefly firefly : fireflies) {
            firefly.move(gridManager, player);
        }
        draw();
    }


    /**
     * Executes the player tick, handling input and updating the player's position on the grid.
     * Processes player movement and redraws the game.
     */
    public void playerTick() {
        if(gameStatus) {
            if (inputHandler.isInputPending()) {
                PlayerInput input = inputHandler.consumeInput();
                if (input != null) {
                    switch (input) {
                        case UP -> player.movePlayer(
                                player.getRow() - 1,
                                player.getColumn(),
                                gridManager
                        );
                        case DOWN -> player.movePlayer(
                                player.getRow() + 1,
                                player.getColumn(),
                                gridManager
                        );
                        case LEFT -> player.movePlayer(
                                player.getRow(),
                                player.getColumn() - 1,
                                gridManager
                        );
                        case RIGHT -> player.movePlayer(
                                player.getRow(),
                                player.getColumn() + 1,
                                gridManager
                        );
                    }
                }
            }
            draw();
        }
    }

    public void gameOver() {
        gameStatus = false;
    }

    public void gameStart() {
        gameStatus = true;
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
        player.movePlayer(0, 0, gridManager);
    }

    /**
     * Moves the player to the center of the grid.
     */
    public void movePlayerToCenter() {
        player.movePlayer(
                gridManager.getElementGrid().length / 2,
                gridManager.getElementGrid()[0].length / 2,
                gridManager
        );
    }
}
