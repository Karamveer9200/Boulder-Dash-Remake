import javafx.scene.image.Image;

public class TitaniumWall extends Tile {

    public TitaniumWall(int x, int y) {
        super(x, y);
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
