import javafx.scene.image.Image;

/**
 * Represents a Key tile in the game.
 * This tile can be entered/walked over and is
 * represented by an image based on the colour of the key.
 * @author Joshua Aka
 */
public class Key extends Element {
    /**
     * The colour of the key.
     */
    private final KeyColour colour;

    /**
     * Creates a new Key tile at the specified row
     * and column with the specified key colour.
     * @param row the row position of the tile
     * @param column the column position of the tile
     * @param colour the colour of the key
     */
    public Key(final int row, final int column, final KeyColour colour) {
        super(row, column);
        this.colour = colour;
        canBeEntered = true;
        name = colour + "Key";
        canExplode = true;

        switch (colour) {
            case RED -> image = new Image("images/RedKey.png");
            case GREEN -> image = new Image("images/GreenKey.png");
            case YELLOW -> image = new Image("images/YellowKey.png");
            case BLUE -> image = new Image("images/BlueKey.png");
            case RAINBOW -> image = new Image("images/RainbowKey.png");
        }
    }

    /**
     * Gets the colour of the key.
     * @return the colour of the key
     */
    public KeyColour getColour() {
        return colour;
    }

    /**
     * Returns a string representation of the key and
     * what colour it is.
     * @return the string representation of the key
     */
    @Override
    public String toString() {
        return "Key Colour: " + colour;
    }

}
