import javafx.scene.image.Image;

public class Butterfly extends Fly{
    public Boolean dropDiamond = true;
    public Butterfly(int column, int row, boolean followsLeftEdge) {
        super(row, column, followsLeftEdge);
        image = new Image("images/butterfly.png");
        this.followsLeftEdge = followsLeftEdge;
    }

    @Override
    public String toString() {
        return "Butterfly";
    }
}
