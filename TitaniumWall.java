import javafx.scene.image.Image;

/**
 * Represents a Titanium Wall tile in the game.
 * This tile cannot be entered or exploded.
 * It is represented by an image located at "images/TitaniumWall.png".
 */
public class TitaniumWall extends Tile {

    /**
     * Creates a new Titanium Wall tile at the specified row and column.
     *
     * @param row the row position of the tile
     * @param column the column position of the tile
     */
    public TitaniumWall(final int row, final int column) {
        super(row, column);
        image = new Image("images/TitaniumWall.png");
        canBeEntered = false;
        canExplode = false;
        name = "TitaniumWall";
    }

    /**
     * Returns the name of the tile as a string.
     *
     * @return the name "TitaniumWall"
     */
    @Override
    public String toString() {
        return "TitaniumWall";
    }
}
