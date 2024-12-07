import javafx.scene.image.Image;

/**
 * Represents a Locked Door tile in the game.
 * This tile cannot be entered until it is unlocked.
 * It is represented by an image based on the key colour.
 */
public class LockedDoor extends Tile {
    /**
     * The colour of the key required to unlock this door.
     */
    private final KeyColour colour;

    /**
     * Creates a new Locked Door tile at the specified
     * row and column with the specified key colour.
     *
     * @param row the row position of the tile
     * @param column the column position of the tile
     * @param colour the colour of the key required to unlock the door
     */
    public LockedDoor(final int row, final int column, final KeyColour colour) {
        super(row, column);
        this.colour = colour;
        canBeEntered = false;
        name = colour + "LockedDoor";;
        canExplode = true;

        switch (colour) {
            case RED -> image = new Image("images/RedLockedDoor.png");
            case GREEN -> image = new Image("images/GreenLockedDoor.png");
            case YELLOW -> image = new Image("images/YellowLockedDoor.png");
            case BLUE -> image = new Image("images/BlueLockedDoor.png");
        }
    }

    /**
     * Gets the colour of the key required to unlock the door.
     *
     * @return the colour of the key
     */
    public KeyColour getColour() {
        return colour;
    }

    /**
     * Unlocks the door, allowing it to be entered/walked over.
     */
    public void unlock() {
        this.canBeEntered = true;
    }
}
