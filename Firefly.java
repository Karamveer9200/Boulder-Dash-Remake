import javafx.scene.image.Image;

public class Firefly extends Fly{
    public Boolean dropDiamond = false;
    /**
     * Constructor for the Firefly class.
     *
     * @param row the row of the Firefly in the grid
     * @param column the column of the Firefly in the grid
     * @param followsLeftEdge whether the Firefly follows the left edge
     */
    public Firefly(final int row, final int column,
                   final boolean followsLeftEdge) {
        super(row, column, followsLeftEdge);
        image = new Image("images/firefly.png");
        this.followsLeftEdge = followsLeftEdge;
    }

    /**
     * Returns a string representation of the Firefly object.
     *
     * <p>Returns "Firefly" for Firefly objects.
     */
    @Override
    public String toString() {
        return "Firefly";
    }
}
