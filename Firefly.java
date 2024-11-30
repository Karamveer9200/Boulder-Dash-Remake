import javafx.scene.image.Image;

public class Firefly extends Fly{
    public Boolean dropDiamond = false;
    public Firefly(int row, int column, boolean followsLeftEdge) {
        super(row, column, followsLeftEdge);
        image = new Image("images/firefly.png");
        this.followsLeftEdge = followsLeftEdge;
    }

    @Override
    public String toString() {
        return "Firefly";
    }
}
