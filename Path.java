import javafx.scene.image.Image;

public class Path extends Tile {

    public Path(int row, int column) {
        super(row, column);
        canExplode = true;
        canBeEntered = true;
        name = "Path";
    }

    @Override
    public String toString() {
        return "Path";
    }
}
