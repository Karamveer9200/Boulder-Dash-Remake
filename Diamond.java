import javafx.scene.image.Image;

public class Diamond extends Element {

    public Diamond(int row, int column) {
        super(row, column);
        image = new Image("images/diamond.png");
        canBeEntered = false;
        canExplode = false;
        name = "Diamond";
    }

    @Override
    public String toString() {
        return "Diamond";
    }
}
