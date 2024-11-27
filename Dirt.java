import javafx.scene.image.Image;
public class Dirt extends Element {

    public Dirt(int row, int column) {
        super(row, column);
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
