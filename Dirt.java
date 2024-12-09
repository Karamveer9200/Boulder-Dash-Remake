import javafx.scene.image.Image;

/**
 * @author Alex Vesely
 * @author Omar Sanad
 * Represents a Dirt tile in the game.
 * This tile can explode and be entered/walked over.
 * It's represented by an image found at "images/dirt.png".
 */
public class Dirt extends Element {

    /**
     * Creates new Dirt tiles at the specified row and column.
     *
     * @param row the row (x co-ord) position of the tile
     * @param column the column (y co-ord) position of the tile
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
