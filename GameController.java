import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;

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
    private final Player player;
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
        this.player = new Player(0,0);
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
                    player.setRow(row);
                    player.setColumn(col);
                    break;
                }
            }
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
            System.out.println("Processing Boulder at (" + boulder.getRow() + ", " + boulder.getColumn() + ")");
            System.out.println("Element below boulder: "+ gridManager.getElement(boulder.getRow()+1, boulder.getColumn()));

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
            System.out.println("Processing Diamond at (" + diamond.getRow() + ", " + diamond.getColumn() + ")");
            System.out.println("Element below Diamond: "+ gridManager.getElement(diamond.getRow()+1, diamond.getColumn()));
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
            AmoebaManager.spreadAll(gridManager);
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
