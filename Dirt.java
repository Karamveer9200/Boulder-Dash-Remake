import javafx.scene.image.Image;
public class Dirt extends Element {

    public Dirt(int x, int y) {
        super(x, y);
        boolean canExplode = true;
        image = new Image("dirt.png");
        canBeEntered = true;
        name = "Dirt";
    }

    @Override
    public String toString() {
        return "dirt";
    }
}
