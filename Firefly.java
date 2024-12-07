import javafx.scene.image.Image;

public class Firefly extends Fly{
    public Boolean dropDiamond = false;
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
