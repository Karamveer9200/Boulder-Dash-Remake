import java.util.HashMap;
import java.util.Map;

/**
 * Represents a player's profile in the game, storing the player's name, maximum level reached, high score, and current score.
 */
public class PlayerProfile {

    private static int idCounter = 1;
    private static final Map<Integer, PlayerProfile> profiles = new HashMap<>();

    private final int playerId;
    private String name;
    private int maxLevelReached;
    private int highScore;
    private int currentScore;

    /**
     * Constructs a new PlayerProfile with the specified name, maximum level reached, and high score.
     * Each profile is assigned a unique player ID.
     *
     * @param name the name of the player
     * @param maxLevelReached the maximum level reached by the player
     * @param highScore the player's high score
     */
    public PlayerProfile(String name, int maxLevelReached, int highScore) {
        this.playerId = idCounter++;
        this.name = name;
        this.maxLevelReached = maxLevelReached;
        this.highScore = highScore;
        this.currentScore = 0;
    }

    /**
     * Gets the player's unique ID.
     *
     * @return the player's ID
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Gets the player's name.
     *
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the player's name.
     *
     * @param name the new name of the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the maximum level reached by the player.
     *
     * @return the maximum level reached
     */
    public int getMaxLevelReached() {
        return maxLevelReached;
    }

    /**
     * Sets the maximum level reached by the player.
     *
     * @param maxLevelReached the new maximum level reached
     */
    public void setMaxLevelReached(int maxLevelReached) {
        this.maxLevelReached = maxLevelReached;
    }

    /**
     * Gets the player's high score.
     *
     * @return the high score
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * Sets the player's high score.
     *
     * @param highScore the new high score
     */
    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    /**
     * Gets the player's current score.
     *
     * @return the current score
     */
    public int getCurrentScore() {
        return currentScore;
    }

    /**
     * Sets the player's current score.
     *
     * @param currentScore the new current score
     */
    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    /**
     * Increments the player's current score by the specified number of points.
     * Updates the player's high score if the current score exceeds the high score.
     *
     * @param points the number of points to add to the current score
     */
    public void incrementScore(int points) {
        this.currentScore += points;
        if (this.currentScore > this.highScore) {
            this.highScore = this.currentScore;
        }
    }
}
