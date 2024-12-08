import javafx.scene.image.Image;

public class Butterfly extends Fly{
    public static final Boolean dropDiamond = true;;
    public Butterfly(int row, int column, boolean followsLeftEdge) {
        super(row, column, followsLeftEdge);
        image = new Image("images/butterfly.png");
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
        return "Butterfly" + extraInfo;
    }

    @Override
    public String toString() {
        return "Butterfly";
    }
}
