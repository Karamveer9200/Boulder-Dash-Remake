import javafx.scene.image.Image;

/**
 * The Firefly class represents a type of fly in the grid-based game environment
 * that extends the capabilities of a Fly.
 *
 * In addition to the properties and methods of Fly, Firefly can optionally drop a diamond.
 */
public class Firefly extends Fly{
    public static final Boolean dropDiamond = false;
    public Firefly(int row, int column, boolean followsLeftEdge) {
        super(row, column, followsLeftEdge);
        image = new Image("images/firefly.png");
        this.followsLeftEdge = followsLeftEdge;
        name = getName();
    }

    public String getName(){
        String extraInfo;
        if (followsLeftEdge) {
            extraInfo = "Left";
        } else {
            extraInfo = "Right";
        }
        return "Firefly" + extraInfo;
    }



    @Override
    public String toString() {
        return "Firefly";
    }
}
