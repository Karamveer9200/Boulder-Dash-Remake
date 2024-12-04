import javafx.scene.image.Image;

public class TitaniumWall extends Tile {

    public TitaniumWall(int row, int column) {
        super(row, column);
        image = new Image("images/TitaniumWall.png");
        canBeEntered = false;
        canExplode = false;
        name = "TitaniumWall";
    }

    @Override
    public String toString() {
        return "TitaniumWall";
    }
}
