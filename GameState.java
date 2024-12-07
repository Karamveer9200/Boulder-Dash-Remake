/**
 * Manages the state of the game, providing methods to pause, play, and end the game.
 */
public class GameState {
    private String state;

    /**
     * Initializes the GameState with a default state.
     */
    public GameState() {
        this.state = "initial";  // Default state
    }

    /**
     * Pauses the game.
     * Sets the game state to "paused" and performs actions needed to pause the game.
     */
    public void pause() {
        state = "paused";
        System.out.println("Game is paused.");
    }

    /**
     * Starts or resumes the game.
     * Sets the game state to "playing" and performs actions needed to play the game.
     */
    public void play() {
        state = "playing";
        System.out.println("Game is playing.");
    }

    /**
     * Ends the game.
     * Sets the game state to "game over" and performs actions needed to end the game.
     */
    public void gameOver() {
        state = "game over";
        System.out.println("Game is over.");
    }

    /**
     * Gets the current state of the game.
     *
     * @return the current state of the game
     */
    public String getState() {
        return state;
    }
}
