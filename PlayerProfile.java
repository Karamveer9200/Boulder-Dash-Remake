/**
 * Represents a player's profile in the game, storing the player's name, maximum level reached, high score, and current score.
 */
public class PlayerProfile {

    private static int idCounter = 1;
    private final int playerId;
    private String name;
    private int maxLevelReached;
//    private final int[] highScores;

    /**
     * Constructs a new PlayerProfile with the specified name, maximum level reached, and high score.
     * Each profile is assigned a unique player ID.
     *
     * @param name the name of the player
     */
    public PlayerProfile(String name) {
        this.playerId = idCounter++;
        this.name = name;
        this.maxLevelReached = 0;
//        this.highScores = new int[]{0, 0, 0};
    }

    /**
     * Constructs a new PlayerProfile with the specified name, maximum level reached, and high score.
     * Each profile is assigned a unique player ID.
     *
     */
    public PlayerProfile() {
        this.playerId = idCounter++;
        this.maxLevelReached = 1;
//        this.highScores = new int[]{0, 0, 0};
    }

    /**
     * Constructs a new PlayerProfile with the specified name, maximum level reached, and high score.
     * Each profile is assigned a unique player ID.
     *
     */
    public PlayerProfile(int playerId, String name, int maxLevelReached
//                         , int[] highScores
    ) {
        this.playerId = playerId;
        this.name = name;
        this.maxLevelReached = maxLevelReached;
//        this.highScores = new int[]{0, 0, 0};
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

//    /**
//     * Gets the player's high score.
//     *
//     * @return the high score
//     */
//    public int[] getHighScores() {
//        return highScores;
//    }
//
//    /**
//     * Sets the player's high score.
//     *
//     * @param highScore the new high score
//     */
//    public void setHighScore(int index, int highScore) {
//        if (highScores[index] < highScore) {
//            highScores[index] = highScore;
//        }
//    }

}
