import javafx.scene.image.Image;

/**
 * Represents a Dirt tile in the game.
 * This tile can explode and be entered/walked over.
 * It is represented by an image located at "images/dirt.png".
 */
public class Dirt extends Tile {

    /**
     * Creates a new Dirt tile at the specified row and column.
     *
     * @param row the row position of the tile
     * @param column the column position of the tile
     */
    public Dirt(final int row, final int column) {
        super(row, column);
        canExplode = true;
        image = new Image("images/dirt.png");
        canBeEntered = true;
        name = "Dirt";
    }

    /**
     * Returns a string representation of the dirt tile.
     *
     * @return the string "dirt"
     */
    @Override
    public String toString() {
        return "dirt";
    }
}
