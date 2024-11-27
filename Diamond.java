import javafx.scene.image.Image;
public class Diamond extends Tile {

    public Diamond(int x, int y) {
        super(x, y);
        canExplode = true;
        image = new Image("images/Diamond.png");
        canBeEntered = true;
        name = "Diamond";
    }

    @Override
    public String toString() {
        return "Diamond";
    }
}