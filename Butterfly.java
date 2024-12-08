import javafx.scene.image.Image;

public class Butterfly extends Fly {
    public Boolean dropDiamond = true;
    /**
     * Constructor for the Butterfly class.
     *
     * @param row the row of the butterfly
     * @param column the column of the butterfly
     * @param followsLeftEdge whether the butterfly follows the left edge
     */
    public Butterfly(final int row, final int column,
                     final boolean followsLeftEdge) {
        super(row, column, followsLeftEdge);
        image = new Image("images/butterfly.png");
        this.followsLeftEdge = followsLeftEdge;
    }

    /**
     * Returns a string representation of the Butterfly object.
     *
     * @return a string "Butterfly"
     */
    @Override
    public String toString() {
        return "Butterfly";
    }
}
