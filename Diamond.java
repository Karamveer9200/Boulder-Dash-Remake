import javafx.scene.image.Image;

/**
 * Represents a diamond within the grid-based game, which is a type of dangerous rock.
 * The diamond can fall and roll within the grid, carrying the potential for momentum and interaction with other elements.
 * It inherits from the DangerousRock class, gaining the ability to transform when interacting with specific game elements.
 */
public class Diamond extends DangerousRock {

    /**
     * Constructs a Diamond object with specified row and column positions.
     * This diamond is represented visually by an image and
     * inherits characteristics from the DangerousRock class.
     *
     * @param row the row position of the diamond in the grid.
     * @param column the column position of the diamond in the grid.
     */
    public Diamond(int row, int column) {
        super(row, column);
        image = new Image("images/diamond.png");
        name = "Diamond";
    }

    @Override
    public String toString() {
        return "Diamond";
    }
}
