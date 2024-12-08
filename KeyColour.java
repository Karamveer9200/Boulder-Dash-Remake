/**
 * Represents the colours of keys in the game.
 * Each colour corresponds to a different type of key.
 */
public enum KeyColour {
    RED, GREEN, YELLOW, BLUE, RAINBOW;

    /**
     * Gets name of key colour.
     * @return the name of key colour as a string.
     */
    public String getName() {
        return name();
    }
}
