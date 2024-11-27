import javafx.scene.image.Image;
public class Dirt extends Tile {

    public Dirt(int x, int y) {
        super(x, y);
        canExplode = true;
        image = new Image("images/dirt.png");
        canBeEntered = true;
        name = "Dirt";
    }

    @Override
    public String toString() {
        return "dirt";
    }
}
